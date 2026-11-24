package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.KraftPalette
import kotlin.math.hypot

/**
 * Renders the warm kraft catalog sheet background with subtle halftone dot corner matrices
 * matching the signature aesthetic of the Mohammad Carton smart catalog.
 */
@Composable
fun CatalogSheetContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(KraftPalette.Backdrop),
        contentAlignment = Alignment.TopCenter,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 620.dp)
                .background(KraftPalette.SheetBackground)
                .drawBehind {
                    val spacing = 10.dp.toPx()
                    val dotRadius = 1.4.dp.toPx()
                    val maxDist = 175.dp.toPx()

                    // Top-right corner halftone dots
                    var x = size.width - spacing
                    while (x > size.width - maxDist) {
                        var y = spacing
                        while (y < maxDist) {
                            val d = hypot((size.width - x).toDouble(), y.toDouble()).toFloat()
                            if (d < maxDist) {
                                val alpha = ((1f - d / maxDist) * 0.42f).coerceIn(0f, 0.42f)
                                drawCircle(
                                    color = KraftPalette.HalftoneDot.copy(alpha = alpha),
                                    radius = dotRadius,
                                    center = Offset(x, y),
                                )
                            }
                            y += spacing
                        }
                        x -= spacing
                    }

                    // Bottom-left corner halftone dots
                    var bx = spacing
                    while (bx < maxDist) {
                        var by = size.height - spacing
                        while (by > size.height - maxDist) {
                            val d = hypot(bx.toDouble(), (size.height - by).toDouble()).toFloat()
                            if (d < maxDist) {
                                val alpha = ((1f - d / maxDist) * 0.42f).coerceIn(0f, 0.42f)
                                drawCircle(
                                    color = KraftPalette.HalftoneDot.copy(alpha = alpha),
                                    radius = dotRadius,
                                    center = Offset(bx, by),
                                )
                            }
                            by -= spacing
                        }
                        bx += spacing
                    }
                },
            content = content,
        )
    }
}

@Composable
fun InternalCatalogHeader(
    onBack: () -> Unit,
    onMenu: (() -> Unit)? = null,
    onSearch: (() -> Unit)? = null,
    onShare: (() -> Unit)? = null,
    onAdmin: (() -> Unit)? = null,
    showMenuButton: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showMenuButton && onMenu != null) {
                HeaderActionChip(
                    onClick = onMenu,
                    testTag = "header_menu_button",
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.cd_menu_button),
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            if (onSearch != null) {
                HeaderActionChip(
                    onClick = onSearch,
                    testTag = "header_search_button",
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.cd_search_button),
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            if (onShare != null) {
                HeaderActionChip(
                    onClick = onShare,
                    testTag = "header_share_button",
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = stringResource(R.string.cd_share_button),
                        tint = KraftPalette.AmberDeep,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
            if (onAdmin != null) {
                HeaderActionChip(
                    onClick = onAdmin,
                    testTag = "header_admin_button",
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.cd_admin_button),
                        tint = KraftPalette.InkMuted,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }

        HeaderActionChip(
            onClick = onBack,
            testTag = "header_back_button",
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.cd_back_button),
                tint = KraftPalette.AmberDeep,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Composable
fun HeaderActionChip(
    onClick: () -> Unit,
    testTag: String,
    content: @Composable () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.94f),
        border = BorderStroke(1.dp, KraftPalette.Border),
        shadowElevation = 1.dp,
        modifier = Modifier
            .size(48.dp)
            .testTag(testTag),
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }
}

@Composable
fun BrandEmblem(
    logoUrl: String,
    height: Dp = 76.dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CatalogImage(
            path = logoUrl,
            contentDescription = stringResource(R.string.cd_brand_logo),
            contentScale = ContentScale.Fit,
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .height(height)
                .width(height * 1.45f),
        )
    }
}

object IntentHelpers {
    fun dialPhone(context: Context, phoneNumber: String) {
        val clean = phoneNumber.filter { it.isDigit() || it == '+' }
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$clean"))
        runCatching { context.startActivity(intent) }
    }

    fun openExternalUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        runCatching { context.startActivity(intent) }
    }

    fun copyToClipboard(context: Context, label: String, text: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        cm?.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    fun shareText(context: Context, subject: String, text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        runCatching {
            context.startActivity(Intent.createChooser(intent, "اشتراک‌گذاری"))
        }
    }
}
