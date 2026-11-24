package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.CatalogDomain
import com.example.data.CatalogProduct
import com.example.data.ContactPageConfig
import com.example.data.SpecFieldKey
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.KraftPalette
import java.net.URLEncoder
import kotlin.random.Random

@Composable
fun SearchCatalogDialog(
    products: List<CatalogProduct>,
    onSelectProduct: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("all") }

    val results = remember(query, category, products) {
        CatalogDomain.filterCatalogProducts(query, category, products)
    }

    val boxesCount = remember(products) { products.count { it.groupId == "boxes" } }
    val cartonsCount = remember(products) { products.count { it.groupId == "cartons" } }
    val sheetsCount = remember(products) { products.count { it.groupId == "sheets" } }

    val tabs = listOf(
        "all" to "همه (${CatalogDomain.toPersianDigits(products.size)})",
        "boxes" to "جعبه (${CatalogDomain.toPersianDigits(boxesCount)})",
        "cartons" to "کارتن (${CatalogDomain.toPersianDigits(cartonsCount)})",
        "sheets" to "ورق (${CatalogDomain.toPersianDigits(sheetsCount)})",
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = KraftPalette.SheetBackground,
            border = BorderStroke(1.dp, KraftPalette.Border),
            shadowElevation = 12.dp,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .heightIn(max = 640.dp),
        ) {
            Column {
                // Header
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
                        Icon(Icons.Default.Search, contentDescription = null, tint = KraftPalette.AmberPrimary)
                        Text(
                            text = "جست‌وجوی هوشمند محصولات",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = KraftPalette.InkPrimary,
                        )
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(40.dp)) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.cd_close_dialog),
                            tint = KraftPalette.InkMuted,
                        )
                    }
                }
                HorizontalDivider(color = KraftPalette.SubtleDivider)

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = {
                            Text(
                                "نام جعبه، کارتن یا فلوت (مثلاً: لاک باتم، ذغال، CB)...",
                                fontSize = 12.sp,
                                color = KraftPalette.InkHint,
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = KraftPalette.AmberPrimary,
                            unfocusedBorderColor = KraftPalette.StrongBorder,
                        ),
                        trailingIcon = if (query.isNotEmpty()) {
                            {
                                IconButton(onClick = { query = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = "پاک کردن", tint = KraftPalette.InkSubtle)
                                }
                            }
                        } else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_query_input"),
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(KraftPalette.Backdrop)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        tabs.forEach { (id, label) ->
                            val selected = category == id
                            Surface(
                                onClick = { category = id },
                                shape = RoundedCornerShape(10.dp),
                                color = if (selected) Color.White else Color.Transparent,
                                shadowElevation = if (selected) 1.dp else 0.dp,
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(min = 38.dp),
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 4.dp)) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (selected) KraftPalette.AmberDeep else KraftPalette.InkMuted,
                                        maxLines = 1,
                                    )
                                }
                            }
                        }
                    }

                    if (results.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "محصولی با عبارت «$query» یافت نشد.",
                                color = KraftPalette.InkSubtle,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, fill = false),
                        ) {
                            items(results, key = { it.id }) { item ->
                                Surface(
                                    onClick = {
                                        onSelectProduct(item.id)
                                        onDismiss()
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.White,
                                    border = BorderStroke(1.dp, KraftPalette.Border),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("search_result_${item.id}"),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = item.groupTitle,
                                                    fontSize = 11.sp,
                                                    color = KraftPalette.InkSubtle,
                                                )
                                                Text("·", color = KraftPalette.InkHint)
                                                Text(
                                                    text = item.technicalCode,
                                                    fontFamily = JetBrainsMonoFontFamily,
                                                    fontSize = 10.5.sp,
                                                    color = KraftPalette.AmberDeep,
                                                )
                                            }
                                            Text(
                                                text = item.name,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = KraftPalette.InkPrimary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                            if (item.shortSubtitle.isNotBlank()) {
                                                Text(
                                                    text = item.shortSubtitle,
                                                    fontSize = 11.5.sp,
                                                    color = KraftPalette.InkMuted,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                            }
                                        }
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = null,
                                            tint = KraftPalette.AmberPrimary,
                                            modifier = Modifier.size(18.dp),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FullSpecTableDialog(
    product: CatalogProduct,
    onSelectVariant: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val visibleVariants = remember(product) { CatalogDomain.getVisibleProductVariants(product) }
    val showGrammage = visibleVariants.any { CatalogDomain.isVariantSpecVisible(it, SpecFieldKey.GRAMMAGE) }
    val showThickness = visibleVariants.any { CatalogDomain.isVariantSpecVisible(it, SpecFieldKey.THICKNESS) }
    val showWeight = visibleVariants.any { CatalogDomain.isVariantSpecVisible(it, SpecFieldKey.WEIGHT_CAPACITY) }
    val showDimensions = visibleVariants.any { CatalogDomain.isVariantSpecVisible(it, SpecFieldKey.DIMENSIONS) }
    val showMinOrder = visibleVariants.any { CatalogDomain.isVariantSpecVisible(it, SpecFieldKey.MIN_ORDER) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = KraftPalette.SheetBackground,
            border = BorderStroke(1.dp, KraftPalette.Border),
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .heightIn(max = 560.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Default.TableChart, contentDescription = null, tint = KraftPalette.AmberPrimary)
                        Column {
                            Text(
                                text = "جدول کامل مقایسه تیپ‌ها — ${product.name}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KraftPalette.InkPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = "کد استاندارد: ${product.technicalCode} · برای انتخاب روی هر ردیف بزنید",
                                fontSize = 11.sp,
                                color = KraftPalette.InkSubtle,
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cd_close_dialog), tint = KraftPalette.InkMuted)
                    }
                }
                HorizontalDivider(color = KraftPalette.SubtleDivider)

                if (visibleVariants.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(28.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("هیچ تیپ فعالی برای نمایش وجود ندارد.", color = KraftPalette.InkSubtle, fontSize = 13.sp)
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                            .verticalScroll(rememberScrollState())
                            .horizontalScroll(rememberScrollState()),
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, KraftPalette.Border),
                        ) {
                            Column {
                                // Header row
                                Row(
                                    modifier = Modifier
                                        .background(KraftPalette.HeaderSurface)
                                        .padding(vertical = 10.dp, horizontal = 10.dp),
                                ) {
                                    TableCell("مدل / تیپ", 145.dp, isHeader = true)
                                    if (showGrammage) TableCell("گرماژ (GSM)", 130.dp, isHeader = true)
                                    if (showThickness) TableCell("ضخامت", 120.dp, isHeader = true)
                                    if (showWeight) TableCell("تحمل وزن", 125.dp, isHeader = true)
                                    if (showDimensions) TableCell("ابعاد (mm)", 125.dp, isHeader = true)
                                    if (showMinOrder) TableCell("حداقل سفارش", 105.dp, isHeader = true)
                                }
                                visibleVariants.forEachIndexed { idx, v ->
                                    if (idx > 0) HorizontalDivider(color = KraftPalette.Backdrop)
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                onSelectVariant(v.id)
                                                onDismiss()
                                            }
                                            .padding(vertical = 10.dp, horizontal = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Column(modifier = Modifier.width(145.dp).padding(end = 6.dp)) {
                                            Text(v.label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = KraftPalette.AmberDeep)
                                            if (v.showSubLabel != false && v.subLabel.isNotBlank()) {
                                                Text(v.subLabel, fontSize = 10.5.sp, color = KraftPalette.InkSubtle)
                                            }
                                        }
                                        if (showGrammage) {
                                            TableCell(
                                                if (CatalogDomain.isVariantSpecVisible(v, SpecFieldKey.GRAMMAGE)) v.specs.grammage else "—",
                                                130.dp,
                                            )
                                        }
                                        if (showThickness) {
                                            TableCell(
                                                if (CatalogDomain.isVariantSpecVisible(v, SpecFieldKey.THICKNESS)) v.specs.thickness else "—",
                                                120.dp,
                                            )
                                        }
                                        if (showWeight) {
                                            TableCell(
                                                if (CatalogDomain.isVariantSpecVisible(v, SpecFieldKey.WEIGHT_CAPACITY)) v.specs.weightCapacity else "—",
                                                125.dp,
                                                highlight = true,
                                            )
                                        }
                                        if (showDimensions) {
                                            TableCell(
                                                if (CatalogDomain.isVariantSpecVisible(v, SpecFieldKey.DIMENSIONS)) v.specs.dimensions else "—",
                                                125.dp,
                                            )
                                        }
                                        if (showMinOrder) {
                                            TableCell(
                                                if (CatalogDomain.isVariantSpecVisible(v, SpecFieldKey.MIN_ORDER)) v.specs.minOrder else "—",
                                                105.dp,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableCell(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    isHeader: Boolean = false,
    highlight: Boolean = false,
) {
    Text(
        text = text,
        fontSize = if (isHeader) 11.5.sp else 11.5.sp,
        fontWeight = when {
            isHeader -> FontWeight.Bold
            highlight -> FontWeight.SemiBold
            else -> FontWeight.Normal
        },
        color = when {
            isHeader -> KraftPalette.InkMuted
            highlight -> KraftPalette.AmberDeep
            else -> KraftPalette.InkPrimary
        },
        modifier = Modifier
            .width(width)
            .padding(horizontal = 4.dp),
    )
}

@Composable
fun TechnicalFeaturesDialog(
    product: CatalogProduct,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = KraftPalette.SheetBackground,
            border = BorderStroke(1.dp, KraftPalette.Border),
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .heightIn(max = 600.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Default.Tune, contentDescription = null, tint = KraftPalette.AmberPrimary)
                        Text(
                            text = "مشخصات ساختاری — ${product.name}",
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KraftPalette.InkPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cd_close_dialog), tint = KraftPalette.InkMuted)
                    }
                }
                HorizontalDivider(color = KraftPalette.SubtleDivider)

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    FeatureInfoCard(title = "معرفی و مهندسی ساختار") {
                        Text(
                            text = product.description,
                            fontSize = 12.5.sp,
                            lineHeight = 22.sp,
                            color = KraftPalette.InkSecondary,
                        )
                    }

                    if (product.structuralFeatures.isNotEmpty()) {
                        FeatureInfoCard(
                            title = "ویژگی‌های فنی و مزایا",
                            icon = Icons.Default.Layers,
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                product.structuralFeatures.forEach { feat ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("•", color = KraftPalette.AmberPrimary, fontWeight = FontWeight.Bold)
                                        Text(feat, fontSize = 12.sp, lineHeight = 20.sp, color = KraftPalette.InkPrimary)
                                    }
                                }
                            }
                        }
                    }

                    if (product.applications.isNotEmpty()) {
                        FeatureInfoCard(
                            title = "کاربردهای اصلی در صنایع",
                            icon = Icons.Default.Inventory2,
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                product.applications.forEach { app ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text("•", color = KraftPalette.AmberPrimary, fontWeight = FontWeight.Bold)
                                        Text(app, fontSize = 12.sp, lineHeight = 20.sp, color = KraftPalette.InkPrimary)
                                    }
                                }
                            }
                        }
                    }

                    if (product.paperCombinations.isNotEmpty()) {
                        FeatureInfoCard(title = "ترکیب لایه‌ها و کاغذهای قابل سفارش") {
                            Text(
                                text = product.paperCombinations.joinToString("  ·  "),
                                fontSize = 12.sp,
                                lineHeight = 21.sp,
                                color = KraftPalette.InkSecondary,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureInfoCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    content: @Composable () -> Unit,
) {
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = KraftPalette.AmberDeep, modifier = Modifier.size(16.dp))
                }
                Text(
                    text = title,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = KraftPalette.AmberDeep,
                )
            }
            content()
        }
    }
}

@Composable
fun OrderInquiryDialog(
    product: CatalogProduct,
    initialVariantId: String,
    contactConfig: ContactPageConfig,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val selectableVariants = remember(product) {
        CatalogDomain.getVisibleProductVariants(product).ifEmpty { product.variants }
    }
    var selectedVariantId by remember(product.id, initialVariantId) {
        mutableStateOf(initialVariantId.ifBlank { selectableVariants.firstOrNull()?.id.orEmpty() })
    }
    var quantityText by remember { mutableStateOf("1000") }
    var customPrint by remember { mutableStateOf(true) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var dimensionsNote by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var submittedCode by remember { mutableStateOf<String?>(null) }

    val activeVariant = selectableVariants.find { it.id == selectedVariantId } ?: selectableVariants.firstOrNull()
    val quantity = quantityText.toLongOrNull() ?: 0L
    val estimate = remember(product, activeVariant?.id, quantity, customPrint) {
        CatalogDomain.calculateOrderEstimate(product, activeVariant?.id.orEmpty(), quantity, customPrint)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = KraftPalette.SheetBackground,
            border = BorderStroke(1.dp, KraftPalette.Border),
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .heightIn(max = 650.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = KraftPalette.AmberPrimary)
                        Text(
                            text = "استعلام قیمت و سفارش — ${product.name}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KraftPalette.InkPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cd_close_dialog), tint = KraftPalette.InkMuted)
                    }
                }
                HorizontalDivider(color = KraftPalette.SubtleDivider)

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (submittedCode != null && activeVariant != null) {
                        val waMsg = buildString {
                            append("سلام، وقت بخیر.\n")
                            append("استعلام قیمت از کاتالوگ هوشمند کارتن محمد:\n")
                            append("• کد پیگیری: $submittedCode\n")
                            append("• محصول: ${product.name}\n")
                            append("• تیپ: ${activeVariant.label}\n")
                            append("• تیراژ: $quantity واحد\n")
                            append("• چاپ اختصاصی: ${if (customPrint) "بله" else "خیر"}\n")
                            if (dimensionsNote.isNotBlank()) append("• توضیحات: $dimensionsNote\n")
                            append("• نام: $customerName\n")
                            append("• تماس: $customerPhone")
                        }
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, KraftPalette.AmberPrimary.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(KraftPalette.AmberLight),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = KraftPalette.AmberDeep)
                                }
                                Text(
                                    text = "درخواست پیش‌فاکتور با کد «${CatalogDomain.toPersianDigits(submittedCode!!)}» ثبت شد",
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KraftPalette.InkPrimary,
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = "کارشناسان فروش شرکت کارتن محمد در کوتاه‌ترین زمان با شماره ${CatalogDomain.toPersianDigits(customerPhone)} تماس خواهند گرفت.",
                                    fontSize = 12.sp,
                                    color = KraftPalette.InkMuted,
                                    textAlign = TextAlign.Center,
                                )
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = KraftPalette.SheetBackground,
                                    border = BorderStroke(1.dp, KraftPalette.SubtleDivider),
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(6.dp),
                                    ) {
                                        SummaryRow("محصول انتخابی:", "${product.name} (${activeVariant.label})")
                                        SummaryRow("تیراژ درخواستی:", "${CatalogDomain.formatToman(quantity)} واحد")
                                        SummaryRow("برآورد اولیه کل:", "${CatalogDomain.formatToman(estimate.totalPrice)} تومان", accent = true)
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Button(
                                        onClick = {
                                            val waNum = CatalogDomain.normalizeWhatsappNumber(contactConfig.whatsappNumber)
                                            val url = "https://wa.me/$waNum?text=${URLEncoder.encode(waMsg, "UTF-8")}"
                                            IntentHelpers.openExternalUrl(context, url)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.WhatsAppGreen),
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text("ارسال در واتساپ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                    OutlinedButton(
                                        onClick = onDismiss,
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier.heightIn(min = 48.dp),
                                    ) {
                                        Text("بازگشت", fontSize = 12.sp, color = KraftPalette.InkPrimary)
                                    }
                                }
                            }
                        }
                    } else {
                        // Variant chip picker
                        Text("تیپ / مدل محصول:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = KraftPalette.InkSecondary)
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            selectableVariants.chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    pair.forEach { v ->
                                        val selected = v.id == activeVariant?.id
                                        Surface(
                                            onClick = { selectedVariantId = v.id },
                                            shape = RoundedCornerShape(12.dp),
                                            color = if (selected) KraftPalette.AmberDeep else Color.White,
                                            border = BorderStroke(1.dp, if (selected) KraftPalette.AmberDeep else KraftPalette.StrongBorder),
                                            modifier = Modifier.weight(1f),
                                        ) {
                                            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                                                Text(
                                                    text = v.label,
                                                    fontSize = 11.5.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = if (selected) Color.White else KraftPalette.InkPrimary,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                                if (v.subLabel.isNotBlank()) {
                                                    Text(
                                                        text = v.subLabel,
                                                        fontSize = 10.sp,
                                                        color = if (selected) KraftPalette.AmberLight else KraftPalette.InkSubtle,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    if (pair.size == 1) Spacer(Modifier.weight(1f))
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            OutlinedTextField(
                                value = quantityText,
                                onValueChange = { input -> quantityText = input.filter { it.isDigit() } },
                                label = { Text("تیراژ سفارش", fontSize = 11.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                ),
                                modifier = Modifier.weight(1f).testTag("order_quantity_input"),
                            )
                            Surface(
                                onClick = { customPrint = !customPrint },
                                shape = RoundedCornerShape(14.dp),
                                color = Color.White,
                                border = BorderStroke(1.dp, KraftPalette.StrongBorder),
                                modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Checkbox(
                                        checked = customPrint,
                                        onCheckedChange = { customPrint = it },
                                        colors = CheckboxDefaults.colors(checkedColor = KraftPalette.AmberPrimary),
                                    )
                                    Text("چاپ اختصاصی", fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }

                        // Real-time Price Calculator Card
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, KraftPalette.Border),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                SummaryRow("قیمت واحد تخمینی:", "${CatalogDomain.formatToman(estimate.unitPrice)} تومان")
                                if (estimate.discountPercent > 0) {
                                    SummaryRow(
                                        "تخفیف تیراژ بالا:",
                                        "${CatalogDomain.toPersianDigits(estimate.discountPercent)}٪ کسر شد",
                                        accent = true,
                                    )
                                }
                                HorizontalDivider(color = KraftPalette.SoftSurface)
                                SummaryRow(
                                    "برآورد کل پیش‌فاکتور:",
                                    "${CatalogDomain.formatToman(estimate.totalPrice)} تومان",
                                    accent = true,
                                )
                            }
                        }

                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("نام و نام خانوادگی / شرکت *", fontSize = 11.5.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("order_name_input"),
                        )

                        OutlinedTextField(
                            value = customerPhone,
                            onValueChange = { customerPhone = it },
                            label = { Text("شماره همراه / تلفن *", fontSize = 11.5.sp) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("order_phone_input"),
                        )

                        OutlinedTextField(
                            value = dimensionsNote,
                            onValueChange = { dimensionsNote = it },
                            label = { Text("ابعاد دقیق یا توضیحات (اختیاری)", fontSize = 11.5.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        errorMessage?.let { err ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = KraftPalette.DangerBg,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = err,
                                    color = KraftPalette.DangerRed,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(10.dp),
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Button(
                                onClick = {
                                    when {
                                        customerName.isBlank() || customerPhone.isBlank() -> {
                                            errorMessage = "لطفاً نام و شماره تماس خود را جهت ارسال پیش‌فاکتور وارد نمایید."
                                        }
                                        !estimate.isValidQuantity -> {
                                            errorMessage = "حداقل تیراژ قابل ثبت ۱۰۰ عدد / مترمربع می‌باشد."
                                        }
                                        else -> {
                                            errorMessage = null
                                            submittedCode = "MC-${Random.nextInt(1000, 9999)}"
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberDeep),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(min = 48.dp)
                                    .testTag("order_submit_button"),
                            ) {
                                Text("ثبت استعلام قیمت", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
                            }
                            OutlinedButton(
                                onClick = { IntentHelpers.dialPhone(context, contactConfig.phoneTel) },
                                border = BorderStroke(1.dp, KraftPalette.StrongBorder),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.heightIn(min = 48.dp),
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = KraftPalette.AmberPrimary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("تماس", fontSize = 12.sp, color = KraftPalette.InkPrimary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(label: String, value: String, accent: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, fontSize = 12.sp, color = KraftPalette.InkSubtle)
        Text(
            text = value,
            fontSize = if (accent) 13.sp else 12.sp,
            fontWeight = if (accent) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (accent) KraftPalette.AmberPrimary else KraftPalette.InkPrimary,
        )
    }
}
