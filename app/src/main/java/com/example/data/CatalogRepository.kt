package com.example.data

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Instant

class CatalogRepository(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    private val customStateFile: File
        get() = File(context.filesDir, "catalog_state_custom.json")

    private val customImagesDir: File
        get() = File(context.filesDir, "custom_images").apply { mkdirs() }

    suspend fun loadCatalogState(): CatalogCustomState = withContext(Dispatchers.IO) {
        if (customStateFile.exists()) {
            runCatching {
                val raw = customStateFile.readText(Charsets.UTF_8)
                json.decodeFromString<CatalogCustomState>(raw)
            }.getOrNull()?.let { return@withContext it }
        }
        loadDefaultBundledState()
    }

    suspend fun loadDefaultBundledState(): CatalogCustomState = withContext(Dispatchers.IO) {
        val assetJson = context.assets.open("catalog_state.json").bufferedReader(Charsets.UTF_8).use {
            it.readText()
        }
        json.decodeFromString<CatalogCustomState>(assetJson)
    }

    suspend fun saveCatalogState(state: CatalogCustomState) = withContext(Dispatchers.IO) {
        val encoded = json.encodeToString(state)
        customStateFile.writeText(encoded, Charsets.UTF_8)
    }

    suspend fun resetCatalogState(): CatalogCustomState = withContext(Dispatchers.IO) {
        if (customStateFile.exists()) {
            customStateFile.delete()
        }
        loadDefaultBundledState()
    }

    fun exportBackupJson(state: CatalogCustomState): String {
        val payload = CatalogBackupPayload(
            exportedAt = Instant.now().toString(),
            catalogState = state,
        )
        return json.encodeToString(payload)
    }

    fun parseBackupJson(rawJson: String): CatalogCustomState {
        val trimmed = rawJson.trim()
        if (trimmed.isEmpty()) {
            throw IllegalArgumentException("محتوای بکاپ خالی است.")
        }
        // Support both wrapped CatalogBackupPayload and raw CatalogCustomState JSON
        return runCatching {
            json.decodeFromString<CatalogBackupPayload>(trimmed).catalogState
        }.recoverCatching {
            json.decodeFromString<CatalogCustomState>(trimmed)
        }.getOrElse {
            throw IllegalArgumentException("فرمت JSON بکاپ با ساختار کاتالوگ کارتن محمد مطابقت ندارد.")
        }
    }

    suspend fun persistPickedImage(uri: Uri): String? = withContext(Dispatchers.IO) {
        runCatching {
            val outFile = File(customImagesDir, "img_${System.currentTimeMillis()}.jpg")
            context.contentResolver.openInputStream(uri)?.use { input ->
                outFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext null
            outFile.toURI().toString()
        }.getOrNull()
    }
}
