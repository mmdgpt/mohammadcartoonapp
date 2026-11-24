package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ViewQuilt
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.CatalogCustomState
import com.example.data.CatalogDomain
import com.example.data.CatalogProduct
import com.example.data.MessengerId
import com.example.ui.CatalogViewModel
import com.example.ui.components.CatalogImage
import com.example.ui.components.IntentHelpers
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.KraftPalette

@Composable
fun AdminLoginDialog(
    onAuthenticate: (String, String) -> Boolean,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit,
) {
    var username by remember { mutableStateOf("admin") }
    var password by remember { mutableStateOf("1234") }
    var error by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = KraftPalette.SheetBackground,
            border = BorderStroke(1.dp, KraftPalette.Border),
            shadowElevation = 10.dp,
            modifier = Modifier.fillMaxWidth(0.9f),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = KraftPalette.AmberPrimary)
                        Text(
                            text = "ورود به پنل مدیریت کاتالوگ",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KraftPalette.InkPrimary,
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cd_close_dialog), tint = KraftPalette.InkMuted)
                    }
                }
                HorizontalDivider(color = KraftPalette.SubtleDivider)

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("نام کاربری") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("admin_username_input"),
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("رمز عبور") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("admin_password_input"),
                    )

                    Text(
                        text = "اطلاعات پیش‌فرض: نام کاربری admin و رمز 1234",
                        fontSize = 11.5.sp,
                        color = KraftPalette.InkSubtle,
                    )

                    error?.let { err ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = KraftPalette.DangerBg,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = err,
                                color = KraftPalette.DangerRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (onAuthenticate(username, password)) {
                                error = null
                                onSuccess()
                            } else {
                                error = "نام کاربری یا رمز عبور اشتباه است (پیش‌فرض: admin / 1234)"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberDeep),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("admin_login_submit_button"),
                    ) {
                        Text("ورود به پنل ادمین", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPanelScreen(
    savedState: CatalogCustomState,
    onSaveState: (CatalogCustomState) -> Unit,
    onResetToDefault: () -> Unit,
    onExportBackupJson: (CatalogCustomState) -> String,
    onParseBackupJson: (String) -> Result<CatalogCustomState>,
    onPersistPickedImage: (Uri, (String) -> Unit) -> Unit,
    onViewProductInCatalog: (String) -> Unit,
    onLogout: () -> Unit,
    onClose: () -> Unit,
    onShowToast: (String) -> Unit,
) {
    val context = LocalContext.current
    var draftState by remember(savedState) { mutableStateOf(savedState) }
    var hasUnsavedChanges by remember { mutableStateOf(false) }
    var activeTab by remember { mutableStateOf("products") }
    var editingProductId by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var filterCat by remember { mutableStateOf("all") }
    var newProductCat by remember { mutableStateOf("boxes") }
    var confirmResetOpen by remember { mutableStateOf(false) }

    // Photo Picker callback holder
    var pendingPhotoTarget by remember { mutableStateOf<((String) -> Unit)?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        if (uri != null) {
            onPersistPickedImage(uri) { savedPath ->
                pendingPhotoTarget?.invoke(savedPath)
                onShowToast("تصویر جدید بارگذاری شد — برای ذخیره نهایی دکمه «ذخیره» را بزنید")
            }
        }
        pendingPhotoTarget = null
    }

    fun pickImage(onPicked: (String) -> Unit) {
        pendingPhotoTarget = onPicked
        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
        )
    }

    fun updateDraft(next: CatalogCustomState) {
        draftState = next
        hasUnsavedChanges = true
    }

    val editingProduct = draftState.products.find { it.id == editingProductId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(KraftPalette.SheetBackground)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        // Top Admin Bar
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(KraftPalette.AmberLight),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null, tint = KraftPalette.AmberDeep, modifier = Modifier.size(20.dp))
                        }
                        Column {
                            Text(
                                text = "پنل مدیریت کاتالوگ",
                                fontSize = 14.5.sp,
                                fontWeight = FontWeight.Black,
                                color = KraftPalette.InkPrimary,
                            )
                            Text(
                                text = if (hasUnsavedChanges) "تغییرات ذخیره‌نشده دارید" else "همه تغییرات ذخیره است",
                                fontSize = 11.sp,
                                color = if (hasUnsavedChanges) KraftPalette.AmberPrimary else KraftPalette.InkSubtle,
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                onSaveState(draftState)
                                hasUnsavedChanges = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (hasUnsavedChanges) KraftPalette.WhatsAppGreen else KraftPalette.AmberDeep,
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .heightIn(min = 42.dp)
                                .testTag("admin_save_all_button"),
                        ) {
                            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("ذخیره", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        IconButton(onClick = onLogout) {
                            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "خروج ادمین", tint = KraftPalette.DangerRed)
                        }
                        IconButton(onClick = onClose) {
                            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cd_close_dialog), tint = KraftPalette.InkMuted)
                        }
                    }
                }

                // 3 Main Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(KraftPalette.HeaderSurface)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    AdminMainTab(
                        label = "محصولات (${CatalogDomain.toPersianDigits(draftState.products.size)})",
                        icon = Icons.Default.Inventory2,
                        selected = activeTab == "products",
                        onClick = { activeTab = "products" },
                        modifier = Modifier.weight(1f),
                    )
                    AdminMainTab(
                        label = "عناصر صفحات",
                        icon = Icons.Default.ViewQuilt,
                        selected = activeTab == "pages",
                        onClick = {
                            editingProductId = null
                            activeTab = "pages"
                        },
                        modifier = Modifier.weight(1f),
                    )
                    AdminMainTab(
                        label = "بکاپ و تنظیمات",
                        icon = Icons.Default.Settings,
                        selected = activeTab == "settings",
                        onClick = {
                            editingProductId = null
                            activeTab = "settings"
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        // Tab Body
        when (activeTab) {
            "products" -> {
                if (editingProduct != null) {
                    AdminProductEditor(
                        product = editingProduct,
                        onUpdate = { updated ->
                            updateDraft(
                                draftState.copy(
                                    products = draftState.products.map { if (it.id == updated.id) updated else it },
                                ),
                            )
                        },
                        onDelete = {
                            updateDraft(
                                draftState.copy(
                                    products = draftState.products.filterNot { it.id == editingProduct.id },
                                ),
                            )
                            editingProductId = null
                            onShowToast("محصول حذف شد — برای اعمال نهایی «ذخیره» را بزنید")
                        },
                        onPickImage = ::pickImage,
                        onPreviewInCatalog = { onViewProductInCatalog(editingProduct.id) },
                        onBackToList = { editingProductId = null },
                        modifier = Modifier.weight(1f),
                    )
                } else {
                    AdminProductsListTab(
                        draftState = draftState,
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        filterCat = filterCat,
                        onFilterCatChange = { filterCat = it },
                        newProductCat = newProductCat,
                        onNewProductCatChange = { newProductCat = it },
                        onAddNewProduct = {
                            val (next, created) = CatalogViewModel.addNewProduct(draftState, newProductCat)
                            updateDraft(next)
                            editingProductId = created.id
                            onShowToast("محصول جدید اضافه شد")
                        },
                        onEditProduct = { editingProductId = it },
                        onPreviewProduct = onViewProductInCatalog,
                        onDeleteProduct = { prod ->
                            updateDraft(
                                draftState.copy(
                                    products = draftState.products.filterNot { it.id == prod.id },
                                ),
                            )
                            onShowToast("محصول «${prod.name}» حذف شد")
                        },
                        onToggleGlobalMessenger = { mId, nextEnabled ->
                            updateDraft(CatalogViewModel.setGlobalMessengerEnabled(draftState, mId, nextEnabled))
                        },
                        onUpdateCategoryWhatsapp = { groupId, number ->
                            updateDraft(CatalogViewModel.updateCategoryWhatsapp(draftState, groupId, number))
                        },
                        onToggleProductMessenger = { prodId, mId, nextEnabled ->
                            updateDraft(CatalogViewModel.setProductMessengerEnabled(draftState, prodId, mId, nextEnabled))
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            "pages" -> {
                AdminPageElementsEditor(
                    draftState = draftState,
                    onUpdateState = ::updateDraft,
                    onPickImage = ::pickImage,
                    modifier = Modifier.weight(1f),
                )
            }

            "settings" -> {
                AdminSettingsAndBackupTab(
                    draftState = draftState,
                    onUpdatePassword = { nextPass ->
                        updateDraft(draftState.copy(adminPassword = nextPass))
                    },
                    onShareBackup = {
                        val json = onExportBackupJson(draftState)
                        IntentHelpers.shareText(context, "پشتیبان کاتالوگ کارتن محمد", json)
                    },
                    onCopyBackup = {
                        val json = onExportBackupJson(draftState)
                        IntentHelpers.copyToClipboard(context, "Catalog Backup JSON", json)
                        onShowToast("کد JSON بکاپ کامل در حافظه کپی شد.")
                    },
                    onRestoreFromJson = { raw ->
                        onParseBackupJson(raw)
                            .onSuccess { restored ->
                                updateDraft(restored)
                                onShowToast("بکاپ با ${CatalogDomain.toPersianDigits(restored.products.size)} محصول بارگذاری شد — «ذخیره» را بزنید.")
                            }
                            .onFailure { err ->
                                onShowToast(err.message ?: "فرمت فایل بکاپ نامعتبر است.")
                            }
                    },
                    onRequestReset = { confirmResetOpen = true },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    if (confirmResetOpen) {
        AlertDialog(
            onDismissRequest = { confirmResetOpen = false },
            title = { Text("بازنشانی به تنظیمات کارخانه", fontWeight = FontWeight.ExtraBold) },
            text = { Text("تمامی تغییرات محلی حذف و کاتالوگ به نسخه پیش‌فرض کارخانه بازگردانده شود؟") },
            confirmButton = {
                Button(
                    onClick = {
                        confirmResetOpen = false
                        onResetToDefault()
                        hasUnsavedChanges = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.DangerRed),
                ) {
                    Text("بله، بازنشانی شود")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmResetOpen = false }) {
                    Text("انصراف")
                }
            },
        )
    }
}

@Composable
private fun AdminMainTab(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) KraftPalette.AmberDeep else Color.White,
        modifier = modifier.heightIn(min = 42.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) Color.White else KraftPalette.InkMuted,
                modifier = Modifier.size(15.dp),
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (selected) Color.White else KraftPalette.InkMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun AdminProductsListTab(
    draftState: CatalogCustomState,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    filterCat: String,
    onFilterCatChange: (String) -> Unit,
    newProductCat: String,
    onNewProductCatChange: (String) -> Unit,
    onAddNewProduct: () -> Unit,
    onEditProduct: (String) -> Unit,
    onPreviewProduct: (String) -> Unit,
    onDeleteProduct: (CatalogProduct) -> Unit,
    onToggleGlobalMessenger: (MessengerId, Boolean) -> Unit,
    onUpdateCategoryWhatsapp: (String, String) -> Unit,
    onToggleProductMessenger: (String, MessengerId, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val filtered = remember(draftState.products, searchQuery, filterCat) {
        CatalogDomain.filterCatalogProducts(searchQuery, filterCat, draftState.products)
    }

    LazyColumn(
        modifier = modifier.padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item { Spacer(Modifier.height(6.dp)) }

        // Add Product & Search Controls
        item {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = BorderStroke(1.dp, KraftPalette.Border),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        listOf("boxes" to "جعبه", "cartons" to "کارتن", "sheets" to "ورق").forEach { (id, lbl) ->
                            val selected = newProductCat == id
                            Surface(
                                onClick = { onNewProductCatChange(id) },
                                shape = RoundedCornerShape(10.dp),
                                color = if (selected) KraftPalette.AmberLight else KraftPalette.SoftSurface,
                                border = BorderStroke(1.dp, if (selected) KraftPalette.AmberPrimary else KraftPalette.Border),
                                modifier = Modifier.heightIn(min = 38.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 10.dp)) {
                                    Text(lbl, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = KraftPalette.AmberDeep)
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = onAddNewProduct,
                            colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberDeep),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("admin_add_product_button"),
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("افزودن محصول", fontSize = 11.5.sp, fontWeight = FontWeight.ExtraBold)
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = { Text("جست‌وجو در محصولات...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        listOf("all" to "همه", "boxes" to "جعبه", "cartons" to "کارتن", "sheets" to "ورق").forEach { (id, lbl) ->
                            val selected = filterCat == id
                            Surface(
                                onClick = { onFilterCatChange(id) },
                                shape = RoundedCornerShape(10.dp),
                                color = if (selected) KraftPalette.AmberDeep else KraftPalette.SoftSurface,
                                modifier = Modifier.weight(1f).heightIn(min = 36.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = lbl,
                                        fontSize = 11.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selected) Color.White else KraftPalette.InkMuted,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Global Messenger Activation & Category Phone Numbers
        item {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = BorderStroke(1.dp, KraftPalette.Border),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "پیام‌رسان‌های فعال مشاوره (سراسری):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KraftPalette.AmberDeep,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        val globalM = draftState.elements.productPage.messengers
                        listOf(
                            Triple(MessengerId.WHATSAPP, globalM.whatsapp, KraftPalette.WhatsAppGreen),
                            Triple(MessengerId.EITAA, globalM.eitaa, KraftPalette.EitaaOrange),
                            Triple(MessengerId.RUBIKA, globalM.rubika, KraftPalette.RubikaPurple),
                        ).forEach { (mId, enabled, activeColor) ->
                            Surface(
                                onClick = { onToggleGlobalMessenger(mId, !enabled) },
                                shape = RoundedCornerShape(12.dp),
                                color = if (enabled) activeColor else KraftPalette.SoftSurface,
                                border = BorderStroke(1.dp, if (enabled) activeColor else KraftPalette.StrongBorder),
                                modifier = Modifier.weight(1f).heightIn(min = 40.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${mId.labelFa}: ${if (enabled) "فعال" else "خاموش"}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (enabled) Color.White else KraftPalette.InkSubtle,
                                    )
                                }
                            }
                        }
                    }

                    draftState.groups.forEach { grp ->
                        OutlinedTextField(
                            value = grp.whatsappNumber ?: draftState.elements.contactPage.whatsappNumber,
                            onValueChange = { onUpdateCategoryWhatsapp(grp.id, it) },
                            label = { Text("شماره مشاوره ${grp.title}", fontSize = 11.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }

        // Products List
        items(filtered, key = { it.id }) { prod ->
            val resolvedM = CatalogDomain.getProductMessengerConfig(
                prod,
                draftState.groups,
                draftState.elements.productPage.messengers,
            )
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                border = BorderStroke(1.dp, KraftPalette.Border),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        CatalogImage(
                            path = prod.image,
                            contentDescription = prod.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${prod.groupTitle} · ${prod.technicalCode}",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = KraftPalette.AmberDeep,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = prod.name,
                                fontSize = 13.5.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KraftPalette.InkPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = "${CatalogDomain.toPersianDigits(prod.variants.size)} تیپ · ${prod.badgeText}",
                                fontSize = 11.sp,
                                color = KraftPalette.InkSubtle,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf(
                                Triple(MessengerId.WHATSAPP, resolvedM.whatsapp, KraftPalette.WhatsAppGreen),
                                Triple(MessengerId.EITAA, resolvedM.eitaa, KraftPalette.EitaaOrange),
                                Triple(MessengerId.RUBIKA, resolvedM.rubika, KraftPalette.RubikaPurple),
                            ).forEach { (mId, active, activeBg) ->
                                Surface(
                                    onClick = { onToggleProductMessenger(prod.id, mId, !active) },
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (active) activeBg else KraftPalette.SoftSurface,
                                ) {
                                    Text(
                                        text = mId.labelFa,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (active) Color.White else KraftPalette.InkHint,
                                        textDecoration = if (active) TextDecoration.None else TextDecoration.LineThrough,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    )
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(
                                onClick = { onPreviewProduct(prod.id) },
                                modifier = Modifier.size(38.dp),
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = "نمایش", tint = KraftPalette.InkMuted, modifier = Modifier.size(18.dp))
                            }
                            IconButton(
                                onClick = { onEditProduct(prod.id) },
                                modifier = Modifier.size(38.dp),
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "ویرایش", tint = KraftPalette.AmberDeep, modifier = Modifier.size(18.dp))
                            }
                            IconButton(
                                onClick = { onDeleteProduct(prod) },
                                modifier = Modifier.size(38.dp),
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "حذف", tint = KraftPalette.DangerRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }

        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
private fun AdminSettingsAndBackupTab(
    draftState: CatalogCustomState,
    onUpdatePassword: (String) -> Unit,
    onShareBackup: () -> Unit,
    onCopyBackup: () -> Unit,
    onRestoreFromJson: (String) -> Unit,
    onRequestReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var backupInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Backup & Export Card
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            border = BorderStroke(1.dp, KraftPalette.Border),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text("پشتیبان‌گیری و بازیابی کامل کاتالوگ (JSON)", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                Text(
                    text = "می‌توانید از کل محصولات، تصاویر و متون کاتالوگ خروجی JSON بگیرید یا فایل پشتیبان قبلی را بازیابی کنید.",
                    fontSize = 11.5.sp,
                    color = KraftPalette.InkMuted,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(
                        onClick = onShareBackup,
                        colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberDeep),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).heightIn(min = 46.dp),
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("ارسال بکاپ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(
                        onClick = onCopyBackup,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).heightIn(min = 46.dp),
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("کپی JSON", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedTextField(
                    value = backupInput,
                    onValueChange = { backupInput = it },
                    label = { Text("جای‌گذاری کد JSON جهت بازیابی بکاپ", fontSize = 11.5.sp) },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth(),
                )

                Button(
                    onClick = {
                        onRestoreFromJson(backupInput)
                        backupInput = ""
                    },
                    enabled = backupInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 46.dp),
                ) {
                    Text("بازیابی اطلاعات از کد JSON", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Security Password Card
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            border = BorderStroke(1.dp, KraftPalette.Border),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("تغییر رمز ورود پنل مدیریت", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.InkPrimary)
                OutlinedTextField(
                    value = draftState.adminPassword,
                    onValueChange = onUpdatePassword,
                    label = { Text("رمز عبور ادمین") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        // Danger Reset Card
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = KraftPalette.DangerBg,
            border = BorderStroke(1.dp, KraftPalette.DangerRed.copy(alpha = 0.25f)),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text("بازنشانی به نسخه پیش‌فرض کارخانه", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.DangerRed)
                Text(
                    text = "تمام ویرایش‌های محلی حذف شده و لیست کامل محصولات و تنظیمات اولیه کارتن محمد بازیابی می‌گردد.",
                    fontSize = 11.5.sp,
                    color = KraftPalette.InkSecondary,
                )
                Button(
                    onClick = onRequestReset,
                    colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.DangerRed),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 46.dp),
                ) {
                    Icon(Icons.Default.Restore, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("بازنشانی کل کاتالوگ", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
