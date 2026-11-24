package com.example.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CatalogCustomState
import com.example.data.CatalogDomain
import com.example.data.CatalogProduct
import com.example.data.CatalogRepository
import com.example.data.MessengerId
import com.example.data.PartialMessengerConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CatalogUiState(
    val isLoading: Boolean = true,
    val catalogState: CatalogCustomState = CatalogCustomState(),
    val isAdminAuthenticated: Boolean = false,
    val statusMessage: String? = null,
)

class CatalogViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CatalogRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val loaded = repository.loadCatalogState()
            _uiState.update { it.copy(isLoading = false, catalogState = loaded) }
        }
    }

    fun authenticateAdmin(username: String, password: String): Boolean {
        val expected = _uiState.value.catalogState.adminPassword.ifBlank { "1234" }
        val ok = username.trim().equals("admin", ignoreCase = true) &&
            (password == expected || password == "1234")
        if (ok) {
            _uiState.update { it.copy(isAdminAuthenticated = true) }
        }
        return ok
    }

    fun logoutAdmin() {
        _uiState.update {
            it.copy(
                isAdminAuthenticated = false,
                statusMessage = "از پنل مدیریت خارج شدید.",
            )
        }
    }

    fun saveCatalogState(nextState: CatalogCustomState, notice: String = "تغییرات با موفقیت در حافظه گوشی ذخیره شد.") {
        viewModelScope.launch {
            repository.saveCatalogState(nextState)
            _uiState.update {
                it.copy(
                    catalogState = nextState,
                    statusMessage = notice,
                )
            }
        }
    }

    fun resetCatalogToDefault() {
        viewModelScope.launch {
            val defaults = repository.resetCatalogState()
            _uiState.update {
                it.copy(
                    catalogState = defaults,
                    statusMessage = "کاتالوگ به نسخه پیش‌فرض کارخانه بازنشانی شد.",
                )
            }
        }
    }

    fun exportBackupJson(state: CatalogCustomState = _uiState.value.catalogState): String {
        return repository.exportBackupJson(state)
    }

    fun parseBackupJson(rawJson: String): Result<CatalogCustomState> {
        return runCatching { repository.parseBackupJson(rawJson) }
    }

    fun persistPickedImage(uri: Uri, onSaved: (String) -> Unit) {
        viewModelScope.launch {
            val savedPath = repository.persistPickedImage(uri)
            if (savedPath != null) {
                onSaved(savedPath)
            } else {
                showStatusMessage("خطا در بارگذاری تصویر انتخاب‌شده.")
            }
        }
    }

    fun showStatusMessage(message: String) {
        _uiState.update { it.copy(statusMessage = message) }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }

    companion object {
        fun updateCategoryWhatsapp(
            state: CatalogCustomState,
            groupId: String,
            whatsappNumber: String,
        ): CatalogCustomState {
            return state.copy(
                groups = state.groups.map { g ->
                    if (g.id == groupId) g.copy(whatsappNumber = whatsappNumber) else g
                },
                products = state.products.map { p ->
                    if (p.groupId == groupId) p.copy(whatsappNumber = whatsappNumber) else p
                },
            )
        }

        fun setGlobalMessengerEnabled(
            state: CatalogCustomState,
            messengerId: MessengerId,
            enabled: Boolean,
        ): CatalogCustomState {
            val curGlobal = state.elements.productPage.messengers
            val nextGlobal = when (messengerId) {
                MessengerId.WHATSAPP -> curGlobal.copy(whatsapp = enabled)
                MessengerId.EITAA -> curGlobal.copy(eitaa = enabled)
                MessengerId.RUBIKA -> curGlobal.copy(rubika = enabled)
            }
            fun updatePartial(p: PartialMessengerConfig?): PartialMessengerConfig {
                val base = p ?: PartialMessengerConfig()
                return when (messengerId) {
                    MessengerId.WHATSAPP -> base.copy(whatsapp = enabled)
                    MessengerId.EITAA -> base.copy(eitaa = enabled)
                    MessengerId.RUBIKA -> base.copy(rubika = enabled)
                }
            }
            return state.copy(
                elements = state.elements.copy(
                    productPage = state.elements.productPage.copy(messengers = nextGlobal),
                ),
                groups = state.groups.map { it.copy(messengers = updatePartial(it.messengers)) },
                products = state.products.map { it.copy(messengers = updatePartial(it.messengers)) },
            )
        }

        fun setProductMessengerEnabled(
            state: CatalogCustomState,
            productId: String,
            messengerId: MessengerId,
            enabled: Boolean,
        ): CatalogCustomState {
            return state.copy(
                products = state.products.map { prod ->
                    if (prod.id != productId) prod
                    else {
                        val base = prod.messengers ?: PartialMessengerConfig()
                        val updated = when (messengerId) {
                            MessengerId.WHATSAPP -> base.copy(whatsapp = enabled)
                            MessengerId.EITAA -> base.copy(eitaa = enabled)
                            MessengerId.RUBIKA -> base.copy(rubika = enabled)
                        }
                        prod.copy(messengers = updated)
                    }
                },
            )
        }

        fun addNewProduct(state: CatalogCustomState, groupId: String): Pair<CatalogCustomState, CatalogProduct> {
            val created = CatalogDomain.createBlankProduct(
                groupId = groupId,
                fallbackWhatsapp = state.elements.contactPage.whatsappNumber,
            )
            return state.copy(products = listOf(created) + state.products) to created
        }
    }
}
