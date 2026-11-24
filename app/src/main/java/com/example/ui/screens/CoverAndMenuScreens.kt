package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CatalogCustomState
import com.example.ui.components.BrandEmblem
import com.example.ui.components.CatalogImage
import com.example.ui.components.CatalogSheetContainer
import com.example.ui.components.HeaderActionChip
import com.example.ui.components.InternalCatalogHeader
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.KraftPalette

@Composable
fun CoverScreen(
    catalogState: CatalogCustomState,
    onStartCatalog: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenContact: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenAdmin: () -> Unit,
    onZoomImage: (String, String) -> Unit,
) {
    val cover = catalogState.elements.cover
    val logoUrl = catalogState.elements.logoUrl
    val featureIcons = listOf(
        Icons.Default.EmojiEvents,
        Icons.Default.VerifiedUser,
        Icons.Default.GridView,
        Icons.Default.LocalShipping,
    )

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            // Top Sculpted Brand Tab + Quick Action Buttons
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HeaderActionChip(
                    onClick = onOpenSearch,
                    testTag = "cover_search_button",
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.cd_search_button),
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(20.dp),
                    )
                }

                // Sculpted White Brand Tab matching cover design
                Surface(
                    shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, KraftPalette.Border),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .width(188.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CatalogImage(
                            path = logoUrl,
                            contentDescription = stringResource(R.string.cd_brand_logo),
                            contentScale = ContentScale.Fit,
                            backgroundColor = Color.Transparent,
                            modifier = Modifier
                                .height(68.dp)
                                .fillMaxWidth(),
                        )
                    }
                }

                Box(modifier = Modifier.align(Alignment.TopEnd)) {
                    HeaderActionChip(
                        onClick = onOpenAdmin,
                        testTag = "cover_admin_button",
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(R.string.cd_admin_button),
                            tint = KraftPalette.InkMuted,
                            modifier = Modifier.size(19.dp),
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            // Main Hero Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = cover.heroTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = KraftPalette.InkPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )

                // Hero Packaging Showcase Visual
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2D8C5)),
                    shadowElevation = 3.dp,
                    color = KraftPalette.MediaBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(214.dp)
                        .testTag("cover_hero_image"),
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CatalogImage(
                            path = cover.heroImage,
                            contentDescription = cover.heroTitle,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            onZoomClick = { onZoomImage(cover.heroImage, cover.heroTitle) },
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent,
                                            KraftPalette.SheetBackground.copy(alpha = 0.88f),
                                        ),
                                    ),
                                ),
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.92f),
                                border = BorderStroke(1.dp, KraftPalette.Border),
                            ) {
                                Text(
                                    text = cover.badgeRight,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KraftPalette.AmberDark,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.92f),
                                border = BorderStroke(1.dp, KraftPalette.Border),
                            ) {
                                Text(
                                    text = cover.badgeLeft,
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontSize = 10.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = KraftPalette.AmberDark,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                )
                            }
                        }
                    }
                }

                // 2x2 Feature Highlights Grid
                if (cover.features.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        cover.features.chunked(2).forEachIndexed { rowIdx, rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                rowItems.forEachIndexed { colIdx, featText ->
                                    val icon = featureIcons[(rowIdx * 2 + colIdx) % featureIcons.size]
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = Color.White.copy(alpha = 0.94f),
                                        border = BorderStroke(1.dp, KraftPalette.Border),
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 11.dp, vertical = 10.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                                tint = KraftPalette.AmberPrimary,
                                                modifier = Modifier.size(18.dp),
                                            )
                                            Text(
                                                text = featText,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = KraftPalette.InkPrimary,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                                if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(6.dp))

                // Primary Gradient CTA Button ("شروع کاتالوگ")
                val ctaGradient = Brush.horizontalGradient(
                    colors = listOf(KraftPalette.AmberDeep, Color(0xFFC25E0E), KraftPalette.AmberPrimary),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(CircleShape)
                        .background(ctaGradient)
                        .clickable(onClick = onStartCatalog)
                        .testTag("cover_start_catalog_button"),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = cover.startButtonText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(KraftPalette.InkPrimary.copy(alpha = 0.22f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }

                // Secondary Buttons Row ("درباره ما" + "تماس با ما")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    CoverSecondaryButton(
                        text = cover.aboutButtonText,
                        icon = Icons.Default.Business,
                        onClick = onOpenAbout,
                        testTag = "cover_about_button",
                        modifier = Modifier.weight(1f),
                    )
                    CoverSecondaryButton(
                        text = cover.contactButtonText,
                        icon = Icons.Default.Phone,
                        onClick = onOpenContact,
                        testTag = "cover_contact_button",
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            // Footer Copyright
            Text(
                text = cover.copyrightText,
                fontSize = 11.sp,
                color = KraftPalette.InkSubtle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 14.dp),
            )
        }
    }
}

@Composable
private fun CoverSecondaryButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE2D8C5)),
        shadowElevation = 1.dp,
        modifier = modifier
            .heightIn(min = 50.dp)
            .testTag(testTag),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = KraftPalette.AmberPrimary,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = KraftPalette.InkPrimary,
            )
        }
    }
}

@Composable
fun MenuScreen(
    catalogState: CatalogCustomState,
    onBack: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenGroups: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenContact: () -> Unit,
    onOpenAdmin: () -> Unit,
) {
    val menu = catalogState.elements.menu
    val logoUrl = catalogState.elements.logoUrl

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            InternalCatalogHeader(
                onBack = onBack,
                onSearch = onOpenSearch,
                onAdmin = onOpenAdmin,
                showMenuButton = false,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BrandEmblem(logoUrl = logoUrl, height = 94.dp)

                Spacer(Modifier.height(26.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = menu.promptText,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KraftPalette.InkMuted,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )

                    if (menu.showAbout) {
                        MenuOptionCard(
                            label = menu.aboutLabel,
                            icon = Icons.Default.Business,
                            onClick = onOpenAbout,
                            testTag = "menu_about_card",
                        )
                    }
                    if (menu.showProducts) {
                        MenuOptionCard(
                            label = menu.productsLabel,
                            icon = Icons.Default.GridView,
                            onClick = onOpenGroups,
                            testTag = "menu_products_card",
                        )
                    }
                    if (menu.showSearch) {
                        MenuOptionCard(
                            label = menu.searchLabel,
                            icon = Icons.Default.Search,
                            onClick = onOpenSearch,
                            testTag = "menu_search_card",
                        )
                    }
                    if (menu.showContact) {
                        MenuOptionCard(
                            label = menu.contactLabel,
                            icon = Icons.Default.Phone,
                            onClick = onOpenContact,
                            testTag = "menu_contact_card",
                        )
                    }
                }
            }

            Text(
                text = menu.footerText,
                fontFamily = JetBrainsMonoFontFamily,
                fontSize = 10.5.sp,
                color = KraftPalette.InkHint,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
            )
        }
    }
}

@Composable
private fun MenuOptionCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    testTag: String,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE7E0D2)),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 68.dp)
            .testTag(testTag),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(KraftPalette.AmberLight),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(22.dp),
                    )
                }
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = KraftPalette.InkPrimary,
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = KraftPalette.AmberDeep,
                modifier = Modifier.size(22.dp),
            )
        }
    }
}
