package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CatalogCustomState
import com.example.data.CatalogDomain
import com.example.data.CatalogProduct
import com.example.data.MessengerId
import com.example.data.SpecFieldKey
import com.example.ui.components.CatalogImage
import com.example.ui.components.CatalogSheetContainer
import com.example.ui.components.IntentHelpers
import com.example.ui.components.InternalCatalogHeader
import com.example.ui.components.ProductDiagramCanvas
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.KraftPalette

@Composable
fun GroupsScreen(
    catalogState: CatalogCustomState,
    onBack: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenAdmin: () -> Unit,
    onSelectGroup: (String) -> Unit,
) {
    val groupsPage = catalogState.elements.groupsPage

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            InternalCatalogHeader(
                onBack = onBack,
                onMenu = onOpenMenu,
                onSearch = onOpenSearch,
                onAdmin = onOpenAdmin,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                // Title + Accent Bar
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = groupsPage.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = KraftPalette.AmberDeep,
                    )
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(KraftPalette.AmberPrimary),
                    )
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = groupsPage.promptText,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = KraftPalette.InkSecondary,
                )

                Spacer(Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    catalogState.groups.forEach { group ->
                        val count = catalogState.products.count { it.groupId == group.id }
                        Surface(
                            onClick = { onSelectGroup(group.id) },
                            shape = RoundedCornerShape(24.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, KraftPalette.Border),
                            shadowElevation = 3.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(142.dp)
                                .testTag("group_card_${group.id}"),
                        ) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text(
                                            text = group.title,
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = KraftPalette.InkPrimary,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                        Text(
                                            text = "${CatalogDomain.toPersianDigits(count)} سری محصول · ${group.accentLabel}",
                                            fontSize = 11.sp,
                                            color = KraftPalette.InkSubtle,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                        )
                                    }

                                    Surface(
                                        shape = CircleShape,
                                        color = KraftPalette.AmberDeep,
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        ) {
                                            Text(
                                                text = groupsPage.ctaButtonText,
                                                fontSize = 11.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                            )
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp),
                                            )
                                        }
                                    }
                                }

                                CatalogImage(
                                    path = group.image,
                                    contentDescription = group.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(136.dp)
                                        .fillMaxHeight(),
                                )
                            }
                        }
                    }
                }
            }

            Text(
                text = groupsPage.footerText,
                fontSize = 11.5.sp,
                color = KraftPalette.InkSubtle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
            )
        }
    }
}

@Composable
fun GroupDetailScreen(
    groupId: String,
    pageNum: Int,
    catalogState: CatalogCustomState,
    onBackToGroups: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenSearch: () -> Unit,
    onSelectPage: (String, Int) -> Unit,
    onSelectProduct: (String) -> Unit,
) {
    val pages = remember(groupId, catalogState.products, catalogState.groups) {
        CatalogDomain.getPaginatedPagesForGroup(groupId, catalogState.products, catalogState.groups)
    }
    val clampedPage = pageNum.coerceIn(1, pages.size.coerceAtLeast(1))
    val currentEntry = pages.find { it.groupPageNum == clampedPage } ?: pages.firstOrNull()
    val hasPrev = clampedPage > 1
    val hasNext = clampedPage < pages.size
    val groupsPage = catalogState.elements.groupsPage

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            InternalCatalogHeader(
                onBack = onBackToGroups,
                onMenu = onOpenMenu,
                onSearch = onOpenSearch,
            )

            // Category Title Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = currentEntry?.groupTitle ?: "محصولات",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = KraftPalette.AmberDeep,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (currentEntry != null) {
                        Text(
                            text = "(صفحه ${CatalogDomain.toPersianDigits(currentEntry.groupPageNum)} از ${CatalogDomain.toPersianDigits(currentEntry.totalGroupPages)})",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = KraftPalette.InkSubtle,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .height(3.5.dp)
                        .clip(CircleShape)
                        .background(KraftPalette.AmberPrimary),
                )
            }

            Spacer(Modifier.height(10.dp))

            // 2-Column Product Grid (up to 8 products per page)
            val productsOnPage = currentEntry?.products.orEmpty()
            if (productsOnPage.isEmpty()) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("محصولی در این دسته ثبت نشده است.", color = KraftPalette.InkSubtle)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    items(productsOnPage, key = { it.id }) { product ->
                        val visibleVariants = remember(product) { CatalogDomain.getVisibleProductVariants(product) }
                        val firstMedia = remember(product, visibleVariants) {
                            CatalogDomain.getVariantEffectiveMedia(product, visibleVariants.firstOrNull())
                        }
                        Surface(
                            onClick = { onSelectProduct(product.id) },
                            shape = RoundedCornerShape(18.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, KraftPalette.Border),
                            shadowElevation = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("product_grid_card_${product.id}"),
                        ) {
                            Column {
                                ProductDiagramCanvas(
                                    diagramType = product.diagramType,
                                    customImage = firstMedia.schematicImage,
                                    compact = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp)
                                        .height(86.dp),
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                ) {
                                    Text(
                                        text = product.name,
                                        fontSize = 13.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = KraftPalette.InkPrimary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "${CatalogDomain.toPersianDigits(visibleVariants.size)} تیپ",
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = KraftPalette.AmberDeep,
                                        )
                                        Text(
                                            text = product.technicalCode.split(" ").firstOrNull().orEmpty(),
                                            fontFamily = JetBrainsMonoFontFamily,
                                            fontSize = 10.sp,
                                            color = KraftPalette.InkSubtle,
                                            maxLines = 1,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Pagination + All Groups Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Button(
                        onClick = { if (hasPrev) onSelectPage(groupId, clampedPage - 1) },
                        enabled = hasPrev,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KraftPalette.AmberDeep,
                            disabledContainerColor = KraftPalette.AmberDeep.copy(alpha = 0.42f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f),
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("group_prev_page_button"),
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("قبلی", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { if (hasNext) onSelectPage(groupId, clampedPage + 1) },
                        enabled = hasNext,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KraftPalette.AmberDeep,
                            disabledContainerColor = KraftPalette.AmberDeep.copy(alpha = 0.42f),
                            disabledContentColor = Color.White.copy(alpha = 0.7f),
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("group_next_page_button"),
                    ) {
                        Text("بعدی", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }

                OutlinedButton(
                    onClick = onBackToGroups,
                    border = BorderStroke(1.dp, KraftPalette.StrongBorder),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    modifier = Modifier
                        .heightIn(min = 44.dp)
                        .testTag("group_all_groups_button"),
                ) {
                    Text(
                        text = groupsPage.allGroupsButtonText,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = KraftPalette.InkSecondary,
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    product: CatalogProduct,
    catalogState: CatalogCustomState,
    onBackToGroup: (String, Int) -> Unit,
    onOpenMenu: () -> Unit,
    onOpenSearch: () -> Unit,
    onNavigateToProduct: (String) -> Unit,
    onOpenFullTable: (CatalogProduct) -> Unit,
    onOpenSpecsModal: (CatalogProduct) -> Unit,
    onOpenOrderModal: (CatalogProduct, String) -> Unit,
    onZoomImage: (String, String) -> Unit,
    onShowToast: (String) -> Unit,
    selectedVariantOverride: String?,
    onConsumeVariantOverride: () -> Unit = {},
) {
    val context = LocalContext.current
    val visibleVariants = remember(product) { CatalogDomain.getVisibleProductVariants(product) }
    var selectedVariantId by rememberSaveable(product.id) {
        mutableStateOf(visibleVariants.firstOrNull()?.id ?: product.variants.firstOrNull()?.id.orEmpty())
    }
    LaunchedEffect(selectedVariantOverride) {
        if (!selectedVariantOverride.isNullOrBlank() && visibleVariants.any { it.id == selectedVariantOverride }) {
            selectedVariantId = selectedVariantOverride
            onConsumeVariantOverride()
        }
    }
    var visualMode by rememberSaveable(product.id) { mutableStateOf("diagram") }

    val activeVariant = visibleVariants.find { it.id == selectedVariantId } ?: visibleVariants.firstOrNull()
    val activeMedia = remember(product, activeVariant) {
        CatalogDomain.getVariantEffectiveMedia(product, activeVariant)
    }

    val (prevProduct, nextProduct) = remember(product.id, catalogState.products) {
        CatalogDomain.getAdjacentProducts(product.id, catalogState.products)
    }
    val (backGroupId, backPageNum) = remember(product.id, catalogState.products) {
        CatalogDomain.getProductGroupPageNumber(product.id, catalogState.products)
    }

    val productPage = catalogState.elements.productPage
    val contactPage = catalogState.elements.contactPage

    val messengerLinks = remember(product, activeVariant, catalogState.groups, contactPage.whatsappNumber, productPage.messengers) {
        CatalogDomain.buildProductMessengerLinks(
            product = product,
            variantLabel = activeVariant?.label ?: product.name,
            groups = catalogState.groups,
            fallbackNumber = contactPage.whatsappNumber,
            globalMessengers = productPage.messengers,
        ).filter { it.enabled }
    }

    val showSpecCard = CatalogDomain.hasAnyVisibleVariantSpec(activeVariant)

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            InternalCatalogHeader(
                onBack = { onBackToGroup(backGroupId, backPageNum) },
                onMenu = onOpenMenu,
                onSearch = onOpenSearch,
                onShare = {
                    val shareBody = buildString {
                        append("کاتالوگ هوشمند شرکت کارتن محمد\n")
                        append("محصول: ${product.name}\n")
                        if (activeVariant != null) {
                            append("تیپ: ${activeVariant.label} (${activeVariant.subLabel})\n")
                            append("ابعاد: ${activeVariant.specs.dimensions} | تحمل وزن: ${activeVariant.specs.weightCapacity}\n")
                        }
                        append("تلفن کارخانه: ${contactPage.phoneFormatted}\n")
                        append("وب‌سایت: ${contactPage.websiteUrl}")
                    }
                    IntentHelpers.shareText(context, product.name, shareBody)
                },
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Top Showcase Card: Technical Code + Diagram / Sample Photo Switcher
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, KraftPalette.Border),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = product.technicalCode,
                                fontFamily = JetBrainsMonoFontFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KraftPalette.AmberDeep,
                            )

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(KraftPalette.SoftSurface)
                                    .padding(3.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                VisualModeTab(
                                    label = productPage.diagramTabLabel,
                                    icon = Icons.Default.Layers,
                                    selected = visualMode == "diagram",
                                    onClick = { visualMode = "diagram" },
                                    testTag = "product_tab_diagram",
                                )
                                VisualModeTab(
                                    label = productPage.photoTabLabel,
                                    icon = Icons.Default.Image,
                                    selected = visualMode == "photo",
                                    onClick = { visualMode = "photo" },
                                    testTag = "product_tab_photo",
                                )
                            }
                        }

                        if (visualMode == "diagram") {
                            ProductDiagramCanvas(
                                diagramType = product.diagramType,
                                customImage = activeMedia.schematicImage,
                                compact = false,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(218.dp),
                                onZoomCustomImage = { img -> onZoomImage(img, product.name) },
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(218.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                            ) {
                                CatalogImage(
                                    path = activeMedia.image,
                                    contentDescription = activeVariant?.let { "${product.name} - ${it.label}" } ?: product.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                    onZoomClick = {
                                        onZoomImage(
                                            activeMedia.image,
                                            activeVariant?.let { "${product.name} — ${it.label}" } ?: product.name,
                                        )
                                    },
                                )
                            }
                        }
                    }
                }

                // Breadcrumb & Product Title
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = product.groupTitle,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KraftPalette.AmberDeep,
                            modifier = Modifier.clickable { onBackToGroup(backGroupId, backPageNum) },
                        )
                        Text("·", color = KraftPalette.InkHint)
                        Text(
                            text = product.badgeText,
                            fontSize = 11.5.sp,
                            color = KraftPalette.InkMuted,
                        )
                    }
                    Text(
                        text = product.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = KraftPalette.InkPrimary,
                    )
                }

                // Variant Selector Grid (2x2)
                if (visibleVariants.isNotEmpty() && activeVariant != null) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "${product.variantPromptLabel} :",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = KraftPalette.InkMuted,
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            visibleVariants.chunked(2).forEach { pair ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                ) {
                                    pair.forEach { variant ->
                                        val isSelected = variant.id == activeVariant.id
                                        val showSub = variant.showSubLabel != false && variant.subLabel.isNotBlank()
                                        Surface(
                                            onClick = { selectedVariantId = variant.id },
                                            shape = RoundedCornerShape(16.dp),
                                            color = if (isSelected) KraftPalette.AmberDeep else Color.White,
                                            border = BorderStroke(
                                                1.dp,
                                                if (isSelected) KraftPalette.AmberDeep else Color(0xFFE2D8C5),
                                            ),
                                            shadowElevation = if (isSelected) 3.dp else 0.dp,
                                            modifier = Modifier
                                                .weight(1f)
                                                .heightIn(min = 50.dp)
                                                .testTag("variant_button_${variant.id}"),
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(horizontal = 11.dp, vertical = 8.dp),
                                                verticalArrangement = Arrangement.Center,
                                            ) {
                                                Text(
                                                    text = variant.label,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = if (isSelected) Color.White else KraftPalette.InkPrimary,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                )
                                                if (showSub) {
                                                    Text(
                                                        text = variant.subLabel,
                                                        fontSize = 10.sp,
                                                        color = if (isSelected) KraftPalette.AmberLight else KraftPalette.InkSubtle,
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
                    }
                }

                // Technical Specification Card with Amber Top Accent Strip
                if (activeVariant != null && showSpecCard) {
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, KraftPalette.Border),
                        shadowElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .background(KraftPalette.AmberPrimary),
                            )
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                val gridSpecs = buildList {
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.GRAMMAGE)) {
                                        add(Triple(productPage.specLabels.grammage, activeVariant.specs.grammage, false))
                                    }
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.THICKNESS)) {
                                        add(Triple(productPage.specLabels.thickness, activeVariant.specs.thickness, false))
                                    }
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.WEIGHT_CAPACITY)) {
                                        add(Triple(productPage.specLabels.weightCapacity, activeVariant.specs.weightCapacity, true))
                                    }
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.DIMENSIONS)) {
                                        add(Triple(productPage.specLabels.dimensions, activeVariant.specs.dimensions, false))
                                    }
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.PRINT_TYPE)) {
                                        add(Triple(productPage.specLabels.printType, activeVariant.specs.printType, false))
                                    }
                                    if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.MIN_ORDER)) {
                                        add(Triple(productPage.specLabels.minOrder, activeVariant.specs.minOrder, false))
                                    }
                                }

                                gridSpecs.chunked(2).forEach { pair ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        pair.forEach { (label, value, highlight) ->
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(text = label, fontSize = 10.sp, color = KraftPalette.InkSubtle)
                                                Text(
                                                    text = value,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = if (highlight) KraftPalette.AmberDeep else KraftPalette.InkPrimary,
                                                )
                                            }
                                        }
                                        if (pair.size == 1) Spacer(Modifier.weight(1f))
                                    }
                                }

                                if (CatalogDomain.isVariantSpecVisible(activeVariant, SpecFieldKey.JOINT_TYPE)) {
                                    if (gridSpecs.isNotEmpty()) {
                                        HorizontalDivider(color = KraftPalette.SoftSurface)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = productPage.specLabels.jointType,
                                            fontSize = 10.5.sp,
                                            color = KraftPalette.InkSubtle,
                                        )
                                        Text(
                                            text = activeVariant.specs.jointType,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = KraftPalette.InkPrimary,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Consultation Bar: WhatsApp / Eitaa / Rubika
                if (messengerLinks.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = KraftPalette.SoftWarmSurface,
                        border = BorderStroke(1.dp, KraftPalette.StrongBorder),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = null,
                                    tint = KraftPalette.AmberDeep,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = "${productPage.consultButtonText}:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KraftPalette.AmberDeep,
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                messengerLinks.forEach { link ->
                                    val bgColor = when (link.id) {
                                        MessengerId.WHATSAPP -> KraftPalette.WhatsAppGreen
                                        MessengerId.EITAA -> KraftPalette.EitaaOrange
                                        MessengerId.RUBIKA -> KraftPalette.RubikaPurple
                                    }
                                    Button(
                                        onClick = {
                                            IntentHelpers.copyToClipboard(
                                                context,
                                                "درخواست مشاوره",
                                                "${link.messageText}\nشماره تماس پشتیبانی: ${link.phoneLocal}",
                                            )
                                            onShowToast("متن درخواست در حافظه کپی شد و ${link.label} باز می‌شود…")
                                            IntentHelpers.openExternalUrl(context, link.href)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
                                        modifier = Modifier
                                            .weight(1f)
                                            .heightIn(min = 44.dp)
                                            .testTag("messenger_${link.id.name.lowercase()}"),
                                    ) {
                                        Text(
                                            text = link.label,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            maxLines = 1,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Order Price Calculator CTA + Full Table + Specs
                Button(
                    onClick = { onOpenOrderModal(product, activeVariant?.id.orEmpty()) },
                    colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberDeep),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("product_order_inquiry_button"),
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${productPage.orderButtonText} و محاسبه‌گر آنلاین قیمت",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }

                // Row of 4 Actions: Full Table, Technical Specs, Prev, Next
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    ProductActionOutlineButton(
                        text = productPage.fullTableButtonText,
                        icon = Icons.Default.TableChart,
                        enabled = true,
                        onClick = { onOpenFullTable(product) },
                        testTag = "product_full_table_button",
                        modifier = Modifier.weight(1.1f),
                    )
                    ProductActionOutlineButton(
                        text = productPage.specsButtonText,
                        icon = Icons.Default.Tune,
                        enabled = true,
                        onClick = { onOpenSpecsModal(product) },
                        testTag = "product_specs_button",
                        modifier = Modifier.weight(1.05f),
                    )
                    ProductActionOutlineButton(
                        text = productPage.prevButtonText,
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        enabled = prevProduct != null,
                        onClick = { prevProduct?.let { onNavigateToProduct(it.id) } },
                        testTag = "product_prev_button",
                        modifier = Modifier.weight(0.92f),
                    )
                    ProductActionOutlineButton(
                        text = productPage.nextButtonText,
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        enabled = nextProduct != null,
                        onClick = { nextProduct?.let { onNavigateToProduct(it.id) } },
                        testTag = "product_next_button",
                        modifier = Modifier.weight(0.92f),
                    )
                }

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun VisualModeTab(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(9.dp),
        color = if (selected) KraftPalette.AmberDeep else Color.Transparent,
        modifier = Modifier
            .heightIn(min = 34.dp)
            .testTag(testTag),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) Color.White else KraftPalette.InkMuted,
                modifier = Modifier.size(14.dp),
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else KraftPalette.InkMuted,
            )
        }
    }
}

@Composable
private fun ProductActionOutlineButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        color = if (enabled) Color.White else KraftPalette.SoftSurface,
        border = BorderStroke(
            1.dp,
            if (enabled) KraftPalette.AmberDeep.copy(alpha = 0.42f) else KraftPalette.Border,
        ),
        modifier = modifier
            .heightIn(min = 46.dp)
            .testTag(testTag),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (enabled) KraftPalette.AmberDeep else KraftPalette.InkHint,
                modifier = Modifier.size(15.dp),
            )
            Spacer(Modifier.width(3.dp))
            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (enabled) KraftPalette.AmberDeep else KraftPalette.InkHint,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
