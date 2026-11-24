package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CatalogCustomState
import com.example.data.CatalogDomain
import com.example.data.CatalogProduct
import com.example.data.ProductVariant
import com.example.data.VariantSpecs
import com.example.ui.components.CatalogImage
import com.example.ui.components.ProductDiagramCanvas
import com.example.ui.theme.KraftPalette

@Composable
fun AdminProductEditor(
    product: CatalogProduct,
    onUpdate: (CatalogProduct) -> Unit,
    onDelete: () -> Unit,
    onPickImage: ((String) -> Unit) -> Unit,
    onPreviewInCatalog: () -> Unit,
    onBackToList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Editor Header Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = onBackToList,
                shape = RoundedCornerShape(12.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("لیست محصولات", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedButton(onClick = onPreviewInCatalog, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("نمایش", fontSize = 12.sp)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف محصول", tint = KraftPalette.DangerRed)
                }
            }
        }

        // Core Product Details Card
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
                Text("مشخصات اصلی محصول", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)

                OutlinedTextField(
                    value = product.name,
                    onValueChange = { onUpdate(product.copy(name = it)) },
                    label = { Text("نام محصول") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )

                // Group Selector Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    listOf(
                        "boxes" to "انواع جعبه",
                        "cartons" to "انواع کارتن",
                        "sheets" to "انواع ورق کارتن",
                    ).forEach { (gId, gTitle) ->
                        val selected = product.groupId == gId
                        Surface(
                            onClick = { onUpdate(product.copy(groupId = gId, groupTitle = gTitle)) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (selected) KraftPalette.AmberDeep else KraftPalette.SoftSurface,
                            modifier = Modifier.weight(1f).heightIn(min = 38.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = gTitle,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selected) Color.White else KraftPalette.InkMuted,
                                )
                            }
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = product.technicalCode,
                        onValueChange = { onUpdate(product.copy(technicalCode = it)) },
                        label = { Text("کد فنی / FEFCO", fontSize = 11.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
                    )
                    OutlinedTextField(
                        value = product.badgeText,
                        onValueChange = { onUpdate(product.copy(badgeText = it)) },
                        label = { Text("برچسب ویژگی", fontSize = 11.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
                    )
                }

                OutlinedTextField(
                    value = product.shortSubtitle,
                    onValueChange = { onUpdate(product.copy(shortSubtitle = it)) },
                    label = { Text("زیرعنوان کوتاه") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = product.whatsappNumber.orEmpty(),
                    onValueChange = { onUpdate(product.copy(whatsappNumber = it)) },
                    label = { Text("شماره مشاوره اختصاصی این محصول (اختیاری)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )

                OutlinedTextField(
                    value = product.description,
                    onValueChange = { onUpdate(product.copy(description = it)) },
                    label = { Text("توضیحات فنی و معرفی محصول") },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        // Media & Schematic Picker Card (Android Photo Picker - zero permission!)
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
                Text("تصویر نمونه و شماتیک فنی", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        CatalogImage(
                            path = product.image,
                            contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clip(RoundedCornerShape(12.dp)),
                        )
                        OutlinedButton(
                            onClick = { onPickImage { path -> onUpdate(product.copy(image = path)) } },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("تغییر تصویر نمونه", fontSize = 11.sp)
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        ProductDiagramCanvas(
                            diagramType = product.diagramType,
                            customImage = product.schematicImage,
                            compact = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                        )
                        OutlinedButton(
                            onClick = { onPickImage { path -> onUpdate(product.copy(schematicImage = path)) } },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("تصویر شماتیک", fontSize = 11.sp)
                        }
                    }
                }

                // Built-in Diagram Type Horizontal Picker
                Text("انتخاب شماتیک مهندسی پیش‌فرض:", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = KraftPalette.InkMuted)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    CatalogDomain.DIAGRAM_TYPES.forEach { (typeKey, typeLabel) ->
                        val selected = product.diagramType == typeKey && product.schematicImage.isNullOrBlank()
                        Surface(
                            onClick = { onUpdate(product.copy(diagramType = typeKey, schematicImage = null)) },
                            shape = RoundedCornerShape(10.dp),
                            color = if (selected) KraftPalette.AmberDeep else KraftPalette.SoftSurface,
                        ) {
                            Text(
                                text = typeLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected) Color.White else KraftPalette.InkSecondary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                            )
                        }
                    }
                }
            }
        }

        // Variants Editor
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "تیپ‌ها و مشخصات فنی (${CatalogDomain.toPersianDigits(product.variants.size)})",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = KraftPalette.AmberDeep,
                    )
                    Button(
                        onClick = {
                            val newVar = ProductVariant(
                                id = "var-${System.currentTimeMillis() % 100000}",
                                label = "تیپ جدید ${CatalogDomain.toPersianDigits(product.variants.size + 1)}",
                                subLabel = "۳ لایه · B فلوت",
                                specs = VariantSpecs(
                                    grammage = "۴۸۰ گرم بر مترمربع",
                                    thickness = "۳.۰ میلی‌متر",
                                    weightCapacity = "۱۰ کیلوگرم",
                                    dimensions = "۳۰۰×۲۰۰×۲۰۰",
                                    printType = "فلکسو / لمینت",
                                    minOrder = "۵۰۰ عدد",
                                    jointType = "چسب صنعتی",
                                ),
                                unitBasePriceToman = 20000L,
                            )
                            onUpdate(product.copy(variants = product.variants + newVar))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberPrimary),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(15.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("افزودن تیپ", fontSize = 11.5.sp, fontWeight = FontWeight.Bold)
                    }
                }

                product.variants.forEachIndexed { idx, variant ->
                    if (idx > 0) HorizontalDivider(color = KraftPalette.SubtleDivider)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "تیپ ${CatalogDomain.toPersianDigits(idx + 1)}: ${variant.label}",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KraftPalette.InkPrimary,
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Switch(
                                    checked = variant.enabled != false,
                                    onCheckedChange = { nextEnabled ->
                                        onUpdate(
                                            product.copy(
                                                variants = product.variants.map {
                                                    if (it.id == variant.id) it.copy(enabled = nextEnabled) else it
                                                },
                                            ),
                                        )
                                    },
                                    colors = SwitchDefaults.colors(checkedTrackColor = KraftPalette.WhatsAppGreen),
                                )
                                if (product.variants.size > 1) {
                                    IconButton(
                                        onClick = {
                                            onUpdate(product.copy(variants = product.variants.filterNot { it.id == variant.id }))
                                        },
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "حذف تیپ", tint = KraftPalette.DangerRed, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }

                        fun updateVar(transform: (ProductVariant) -> ProductVariant) {
                            onUpdate(
                                product.copy(
                                    variants = product.variants.map { if (it.id == variant.id) transform(it) else it },
                                ),
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = variant.label,
                                onValueChange = { v -> updateVar { it.copy(label = v) } },
                                label = { Text("نام تیپ", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                            OutlinedTextField(
                                value = variant.subLabel,
                                onValueChange = { v -> updateVar { it.copy(subLabel = v) } },
                                label = { Text("زیرعنوان", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = variant.specs.grammage,
                                onValueChange = { v -> updateVar { it.copy(specs = it.specs.copy(grammage = v)) } },
                                label = { Text("گرماژ", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                            OutlinedTextField(
                                value = variant.specs.thickness,
                                onValueChange = { v -> updateVar { it.copy(specs = it.specs.copy(thickness = v)) } },
                                label = { Text("ضخامت", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = variant.specs.weightCapacity,
                                onValueChange = { v -> updateVar { it.copy(specs = it.specs.copy(weightCapacity = v)) } },
                                label = { Text("تحمل وزن", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                            OutlinedTextField(
                                value = variant.specs.dimensions,
                                onValueChange = { v -> updateVar { it.copy(specs = it.specs.copy(dimensions = v)) } },
                                label = { Text("ابعاد (mm)", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = variant.specs.minOrder,
                                onValueChange = { v -> updateVar { it.copy(specs = it.specs.copy(minOrder = v)) } },
                                label = { Text("حداقل سفارش", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                            OutlinedTextField(
                                value = variant.unitBasePriceToman.toString(),
                                onValueChange = { v ->
                                    val num = v.filter { it.isDigit() }.toLongOrNull() ?: 0L
                                    updateVar { it.copy(unitBasePriceToman = num) }
                                },
                                label = { Text("قیمت پایه (تومان)", fontSize = 11.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.weight(1f),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminPageElementsEditor(
    draftState: CatalogCustomState,
    onUpdateState: (CatalogCustomState) -> Unit,
    onPickImage: ((String) -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    var subTab by remember { mutableStateOf("cover") }
    val elems = draftState.elements

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            listOf(
                "cover" to "صفحه جلد",
                "menu" to "فهرست اصلی",
                "groups" to "گروه‌ها",
                "about" to "درباره ما",
                "contact" to "تماس با ما",
            ).forEach { (id, label) ->
                val selected = subTab == id
                Surface(
                    onClick = { subTab = id },
                    shape = RoundedCornerShape(10.dp),
                    color = if (selected) KraftPalette.AmberPrimary else Color.White,
                    border = BorderStroke(1.dp, if (selected) KraftPalette.AmberPrimary else KraftPalette.Border),
                ) {
                    Text(
                        text = label,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selected) Color.White else KraftPalette.InkMuted,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
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
                    when (subTab) {
                        "cover" -> {
                            Text("تنظیمات صفحه جلد (Cover)", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                            OutlinedTextField(
                                value = elems.cover.heroTitle,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(heroTitle = v)))) },
                                label = { Text("تیتر اصلی روی جلد") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.cover.startButtonText,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(startButtonText = v)))) },
                                label = { Text("متن دکمه شروع") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.cover.badgeRight,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(badgeRight = v)))) },
                                label = { Text("برچسب راست روی تصویر") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.cover.badgeLeft,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(badgeLeft = v)))) },
                                label = { Text("برچسب چپ (استاندارد)") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedButton(
                                onClick = {
                                    onPickImage { path ->
                                        onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(heroImage = path))))
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("تغییر تصویر اصلی روی جلد از گالری")
                            }
                            OutlinedTextField(
                                value = elems.cover.copyrightText,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(cover = elems.cover.copy(copyrightText = v)))) },
                                label = { Text("متن کپی‌رایت پایین جلد") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        "menu" -> {
                            Text("تنظیمات صفحه فهرست اصلی", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                            OutlinedTextField(
                                value = elems.menu.promptText,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(menu = elems.menu.copy(promptText = v)))) },
                                label = { Text("متن راهنمای بالا") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.menu.productsLabel,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(menu = elems.menu.copy(productsLabel = v)))) },
                                label = { Text("عنوان گزینه محصولات") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.menu.aboutLabel,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(menu = elems.menu.copy(aboutLabel = v)))) },
                                label = { Text("عنوان گزینه درباره ما") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.menu.contactLabel,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(menu = elems.menu.copy(contactLabel = v)))) },
                                label = { Text("عنوان گزینه تماس با ما") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        "groups" -> {
                            Text("تنظیمات صفحه گروه‌های محصول", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                            OutlinedTextField(
                                value = elems.groupsPage.title,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(groupsPage = elems.groupsPage.copy(title = v)))) },
                                label = { Text("تیتر صفحه") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.groupsPage.ctaButtonText,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(groupsPage = elems.groupsPage.copy(ctaButtonText = v)))) },
                                label = { Text("متن دکمه مشاهده محصولات") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            draftState.groups.forEach { grp ->
                                HorizontalDivider(color = KraftPalette.SubtleDivider)
                                OutlinedTextField(
                                    value = grp.title,
                                    onValueChange = { v ->
                                        onUpdateState(
                                            draftState.copy(
                                                groups = draftState.groups.map { if (it.id == grp.id) it.copy(title = v) else it },
                                            ),
                                        )
                                    },
                                    label = { Text("عنوان گروه (${grp.id})") },
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                OutlinedButton(
                                    onClick = {
                                        onPickImage { path ->
                                            onUpdateState(
                                                draftState.copy(
                                                    groups = draftState.groups.map { if (it.id == grp.id) it.copy(image = path) else it },
                                                ),
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                ) {
                                    Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(15.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text("تغییر تصویر ${grp.title}", fontSize = 11.5.sp)
                                }
                            }
                        }

                        "about" -> {
                            Text("تنظیمات صفحه درباره ما", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                            OutlinedTextField(
                                value = elems.aboutPage.title,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(aboutPage = elems.aboutPage.copy(title = v)))) },
                                label = { Text("تیتر صفحه") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.aboutPage.aboutText,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(aboutPage = elems.aboutPage.copy(aboutText = v)))) },
                                label = { Text("متن معرفی شرکت") },
                                minLines = 5,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.aboutPage.awardsTitle,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(aboutPage = elems.aboutPage.copy(awardsTitle = v)))) },
                                label = { Text("تیتر بخش افتخارات") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                        "contact" -> {
                            Text("تنظیمات صفحه تماس با ما", fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, color = KraftPalette.AmberDeep)
                            OutlinedTextField(
                                value = elems.contactPage.phoneFormatted,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(phoneFormatted = v)))) },
                                label = { Text("شماره تماس کارخانه (نمایشی)") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.contactPage.phoneTel,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(phoneTel = v)))) },
                                label = { Text("شماره شماره‌گیری کارخانه") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.contactPage.mobileDisplay,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(mobileDisplay = v)))) },
                                label = { Text("شماره موبایل فروش") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.contactPage.whatsappNumber,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(whatsappNumber = v)))) },
                                label = { Text("شماره واتساپ پیش‌فرض") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.contactPage.address,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(address = v)))) },
                                label = { Text("آدرس کارخانه و دفتر مرکزی") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            OutlinedTextField(
                                value = elems.contactPage.website,
                                onValueChange = { v -> onUpdateState(draftState.copy(elements = elems.copy(contactPage = elems.contactPage.copy(website = v)))) },
                                label = { Text("آدرس وب‌سایت") },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
