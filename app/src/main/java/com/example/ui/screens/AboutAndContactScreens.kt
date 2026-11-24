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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.CatalogCustomState
import com.example.data.CatalogDomain
import com.example.ui.components.BrandEmblem
import com.example.ui.components.CatalogImage
import com.example.ui.components.CatalogSheetContainer
import com.example.ui.components.IntentHelpers
import com.example.ui.components.InternalCatalogHeader
import com.example.ui.theme.KraftPalette

@Composable
fun AboutScreen(
    catalogState: CatalogCustomState,
    onBack: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenSearch: () -> Unit,
    onOpenGroups: () -> Unit,
    onZoomImage: (String, String) -> Unit,
) {
    val aboutPage = catalogState.elements.aboutPage
    val logoUrl = catalogState.elements.logoUrl

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            InternalCatalogHeader(
                onBack = onBack,
                onMenu = onOpenMenu,
                onSearch = onOpenSearch,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = aboutPage.title,
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

                BrandEmblem(logoUrl = logoUrl, height = 74.dp)

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.94f),
                    border = BorderStroke(1.dp, KraftPalette.Border),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = aboutPage.aboutText,
                        fontSize = 13.sp,
                        lineHeight = 25.sp,
                        color = KraftPalette.InkPrimary,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp),
                    )
                }

                // Company Awards & Trophies Grid
                if (catalogState.awards.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = aboutPage.awardsTitle,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = KraftPalette.InkPrimary,
                            )
                            Box(
                                modifier = Modifier
                                    .width(48.dp)
                                    .height(3.dp)
                                    .clip(CircleShape)
                                    .background(KraftPalette.AmberPrimary),
                            )
                        }

                        catalogState.awards.chunked(2).forEach { rowAwards ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                rowAwards.forEach { award ->
                                    Surface(
                                        shape = RoundedCornerShape(18.dp),
                                        color = Color.White,
                                        border = BorderStroke(1.dp, KraftPalette.Border),
                                        shadowElevation = 1.dp,
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            CatalogImage(
                                                path = award.image,
                                                contentDescription = "تندیس ${award.title} سال ${award.year}",
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(96.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                onZoomClick = {
                                                    onZoomImage(
                                                        award.image,
                                                        "${award.title} (${award.year})",
                                                    )
                                                },
                                            )
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                text = award.year,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Black,
                                                color = KraftPalette.InkPrimary,
                                            )
                                            Text(
                                                text = award.title,
                                                fontSize = 10.5.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = KraftPalette.InkMuted,
                                                textAlign = TextAlign.Center,
                                                lineHeight = 16.sp,
                                            )
                                        }
                                    }
                                }
                                if (rowAwards.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }

                // Bottom link to products
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onOpenGroups)
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                        .testTag("about_view_products_link"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = aboutPage.bottomLinkText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = KraftPalette.AmberDeep,
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(18.dp),
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ContactScreen(
    catalogState: CatalogCustomState,
    onBack: () -> Unit,
    onOpenMenu: () -> Unit,
    onOpenSearch: () -> Unit,
) {
    val context = LocalContext.current
    val contactPage = catalogState.elements.contactPage
    val logoUrl = catalogState.elements.logoUrl

    data class ContactRowItem(val label: String, val value: String, val action: () -> Unit)

    val contactRows = listOf(
        ContactRowItem(contactPage.phoneLabel, contactPage.phoneFormatted) {
            IntentHelpers.dialPhone(context, contactPage.phoneTel)
        },
        ContactRowItem(contactPage.salesMobileLabel, contactPage.mobileDisplay) {
            IntentHelpers.dialPhone(context, contactPage.mobileTel)
        },
        ContactRowItem(contactPage.whatsappLabel, contactPage.mobileDisplay) {
            val wa = CatalogDomain.normalizeWhatsappNumber(contactPage.whatsappNumber)
            IntentHelpers.openExternalUrl(context, "https://wa.me/$wa")
        },
        ContactRowItem(contactPage.websiteLabel, contactPage.website) {
            IntentHelpers.openExternalUrl(context, contactPage.websiteUrl)
        },
    )

    CatalogSheetContainer {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
        ) {
            InternalCatalogHeader(
                onBack = onBack,
                onMenu = onOpenMenu,
                onSearch = onOpenSearch,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = contactPage.title,
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

                BrandEmblem(logoUrl = logoUrl, height = 56.dp)

                Text(
                    text = contactPage.subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = KraftPalette.InkMuted,
                    textAlign = TextAlign.Center,
                )

                // Main Contact Links Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, KraftPalette.Border),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column {
                        contactRows.forEachIndexed { idx, row ->
                            if (idx > 0) HorizontalDivider(color = KraftPalette.SubtleDivider)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = row.action)
                                    .heightIn(min = 48.dp)
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = row.label,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = KraftPalette.InkMuted,
                                )
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    Text(
                                        text = row.value,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KraftPalette.AmberDeep,
                                    )
                                }
                            }
                        }
                    }
                }

                // Departments Table
                if (catalogState.departments.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, KraftPalette.Border),
                        shadowElevation = 1.dp,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(KraftPalette.HeaderSurface)
                                    .padding(horizontal = 16.dp, vertical = 9.dp),
                            ) {
                                Text(
                                    text = contactPage.departmentsTitle,
                                    fontSize = 12.5.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = KraftPalette.InkPrimary,
                                )
                            }
                            HorizontalDivider(color = KraftPalette.Border)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 8.dp),
                            ) {
                                Text("واحد", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = KraftPalette.InkSubtle, modifier = Modifier.weight(1.1f))
                                Text("موبایل", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = KraftPalette.InkSubtle, modifier = Modifier.weight(1.2f))
                                Text("داخلی", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = KraftPalette.InkSubtle, textAlign = TextAlign.Center, modifier = Modifier.weight(0.6f))
                            }
                            HorizontalDivider(color = KraftPalette.SubtleDivider)
                            catalogState.departments.forEachIndexed { idx, dept ->
                                if (idx > 0) HorizontalDivider(color = KraftPalette.SoftSurface)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { IntentHelpers.dialPhone(context, dept.mobileTel) }
                                        .heightIn(min = 44.dp)
                                        .padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = dept.unit,
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KraftPalette.InkPrimary,
                                        modifier = Modifier.weight(1.1f),
                                    )
                                    Text(
                                        text = dept.mobile,
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KraftPalette.AmberDeep,
                                        modifier = Modifier.weight(1.2f),
                                    )
                                    Text(
                                        text = dept.ext,
                                        fontSize = 12.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = KraftPalette.InkMuted,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.6f),
                                    )
                                }
                            }
                        }
                    }
                }

                // Address Map Banner Card
                Surface(
                    onClick = { IntentHelpers.openExternalUrl(context, contactPage.mapUrl) },
                    shape = RoundedCornerShape(18.dp),
                    color = KraftPalette.AmberDeep,
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_map_banner"),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            Text(
                                text = contactPage.addressTitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KraftPalette.AmberGold,
                            )
                            Text(
                                text = contactPage.address,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                lineHeight = 20.sp,
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.16f)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(R.string.cd_map_navigation),
                                tint = KraftPalette.AmberGold,
                            )
                        }
                    }
                }

                // Primary Direct Factory Call CTA
                Button(
                    onClick = { IntentHelpers.dialPhone(context, contactPage.phoneTel) },
                    colors = ButtonDefaults.buttonColors(containerColor = KraftPalette.AmberPrimary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("contact_direct_call_button"),
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = stringResource(R.string.cd_direct_call),
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = contactPage.directCallButtonText,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }

                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
