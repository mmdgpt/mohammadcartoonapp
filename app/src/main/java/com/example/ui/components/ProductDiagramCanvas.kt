package com.example.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.KraftPalette
import java.io.File

fun resolveImageModel(context: Context, rawPath: String?): Any? {
    val path = rawPath?.trim().orEmpty()
    if (path.isEmpty()) return R.drawable.cat_boxes_showcase
    if (path.startsWith("drawable://")) {
        val name = path.removePrefix("drawable://")
        val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
        return if (resId != 0) resId else R.drawable.cat_boxes_showcase
    }
    if (path.startsWith("file://")) {
        return File(Uri.parse(path).path ?: path.removePrefix("file://"))
    }
    if (path.startsWith("content://")) {
        return Uri.parse(path)
    }
    // Map legacy web asset paths if imported from a web backup JSON
    return when {
        path.contains("Logo_-2") -> R.drawable.brand_logo
        path.contains("685225f2") -> R.drawable.award_1395
        path.contains("1a8fe992") -> R.drawable.award_1396
        path.contains("9874cb4c") -> R.drawable.award_1397
        path.contains("3805a96c") -> R.drawable.award_1398
        path.contains("cover_packaging_hero") -> R.drawable.cover_packaging_hero
        path.contains("cat_boxes_showcase") -> R.drawable.cat_boxes_showcase
        path.contains("cat_cartons_showcase") -> R.drawable.cat_cartons_showcase
        path.contains("cat_sheets_flutes") -> R.drawable.cat_sheets_flutes
        else -> path
    }
}

@Composable
fun CatalogImage(
    path: String?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    backgroundColor: Color = KraftPalette.MediaBackground,
    onZoomClick: (() -> Unit)? = null,
) {
    val context = LocalContext.current
    val model = remember(path) { resolveImageModel(context, path) }
    val request = remember(model) {
        ImageRequest.Builder(context)
            .data(model)
            .crossfade(true)
            .build()
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .then(
                if (onZoomClick != null) Modifier.clickable(onClick = onZoomClick) else Modifier
            ),
    ) {
        AsyncImage(
            model = request,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize(),
        )
        if (onZoomClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.85f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.ZoomIn,
                    contentDescription = stringResource(R.string.cd_zoom_image),
                    tint = KraftPalette.AmberDeep,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
fun ZoomableImageLightbox(
    imagePath: String,
    title: String,
    onDismiss: () -> Unit,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val context = LocalContext.current
    val model = remember(imagePath) { resolveImageModel(context, imagePath) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE81C1917))
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val nextScale = (scale * zoom).coerceIn(1f, 4f)
                        scale = nextScale
                        offset = if (nextScale > 1f) {
                            Offset(offset.x + pan.x, offset.y + pan.y)
                        } else {
                            Offset.Zero
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (scale > 1.2f) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scale = 2.4f
                            }
                        },
                    )
                },
        ) {
            AsyncImage(
                model = model,
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y,
                    ),
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(alpha = 0.55f),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp, start = 16.dp, end = 64.dp),
            ) {
                Text(
                    text = "$title · بزرگنمایی با دو انگشت یا دوبار لمس",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                )
            }

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 36.dp, end = 12.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f)),
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.cd_close_dialog),
                    tint = KraftPalette.InkPrimary,
                )
            }
        }
    }
}

@Composable
fun ProductDiagramCanvas(
    diagramType: String,
    customImage: String? = null,
    compact: Boolean = false,
    modifier: Modifier = Modifier,
    onZoomCustomImage: ((String) -> Unit)? = null,
) {
    val bgBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFFAF6EE), Color(0xFFF0E7D8)),
    )

    if (!customImage.isNullOrBlank()) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bgBrush)
                .border(BorderStroke(1.dp, Color(0xFFE5DAC7)), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center,
        ) {
            CatalogImage(
                path = customImage,
                contentDescription = "شماتیک فنی محصول",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize().padding(4.dp),
                onZoomClick = onZoomCustomImage?.let { cb -> { cb(customImage) } },
            )
        }
        return
    }

    val isFlute = diagramType.startsWith("flute-")
    val isDoubleWall = diagramType in listOf("flute-cb", "flute-ce", "flute-be")

    val fluteFooterLabel = when (diagramType) {
        "flute-e" -> "E-FLUTE · ۱.۵ mm (۹۴ کنگره)"
        "flute-b" -> "B-FLUTE · ۳.۰ mm (۵۰ کنگره)"
        "flute-c" -> "C-FLUTE · ۴.۰ mm (۴۱ کنگره)"
        "flute-cb" -> "CB DOUBLE-WALL · ۷.۰ mm (۵ لایه سنگین)"
        "flute-ce" -> "CE DOUBLE-WALL · ۵.۵ mm (۵ لایه چاپی)"
        "flute-be" -> "BE MICRO ۵-PLY · ۴.۵ mm (۵ لایه دایکاتی)"
        else -> null
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bgBrush)
            .border(BorderStroke(1.dp, Color(0xFFE5DAC7)), RoundedCornerShape(16.dp))
            .padding(if (compact) 6.dp else 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            if (isFlute) {
                drawFluteCrossSection(diagramType)
            } else {
                drawIsometricBox(diagramType)
            }
        }

        if (isFlute && compact) {
            Surface(
                color = KraftPalette.AmberLight.copy(alpha = 0.85f),
                shape = RoundedCornerShape(6.dp),
            ) {
                Text(
                    text = "${if (isDoubleWall) "5-PLY" else "3-PLY"} · ${diagramType.removePrefix("flute-").uppercase()}",
                    fontFamily = JetBrainsMonoFontFamily,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = KraftPalette.AmberDeep,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp),
                )
            }
        } else if (isFlute && !compact && fluteFooterLabel != null) {
            Text(
                text = fluteFooterLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = KraftPalette.AmberDark,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}

private fun DrawScope.drawFluteCrossSection(diagramType: String) {
    val vw = 240f
    val vh = 100f
    val scale = minOf(size.width / vw, size.height / vh)
    val ox = (size.width - vw * scale) / 2f
    val oy = (size.height - vh * scale) / 2f

    fun sx(x: Float) = ox + x * scale
    fun sy(y: Float) = oy + y * scale

    val linerBrush = Brush.horizontalGradient(
        colors = listOf(Color(0xFFB88655), Color(0xFFCFA06E), Color(0xFFA97645)),
        startX = sx(16f),
        endX = sx(224f),
    )
    val mediumBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF925E30), Color(0xFF6E431E)),
    )
    val dash = PathEffect.dashPathEffect(floatArrayOf(3f * scale, 3f * scale))

    // Subtle technical guide lines
    drawLine(Color(0xFFE2D5C1), Offset(sx(16f), sy(14f)), Offset(sx(224f), sy(14f)), strokeWidth = 1f * scale, pathEffect = dash)
    drawLine(Color(0xFFE2D5C1), Offset(sx(16f), sy(86f)), Offset(sx(224f), sy(86f)), strokeWidth = 1f * scale, pathEffect = dash)

    fun drawLiner(y: Float, h: Float) {
        drawRoundRect(
            brush = linerBrush,
            topLeft = Offset(sx(16f), sy(y)),
            size = Size(208f * scale, h * scale),
            cornerRadius = CornerRadius(1.5f * scale, 1.5f * scale),
        )
    }

    fun drawWave(yCenter: Float, amplitude: Float, wavelength: Float, strokeW: Float) {
        val path = Path()
        path.moveTo(sx(16f), sy(yCenter))
        var x = 16f
        while (x < 224f) {
            val half = wavelength / 2f
            val q1 = x + half / 2f
            val q2 = minOf(224f, x + half)
            val q3 = x + half + half / 2f
            val q4 = minOf(224f, x + wavelength)
            path.quadraticTo(sx(q1), sy(yCenter - amplitude), sx(q2), sy(yCenter))
            if (q2 < 224f) {
                path.quadraticTo(sx(q3), sy(yCenter + amplitude), sx(q4), sy(yCenter))
            }
            x += wavelength
        }
        drawPath(
            path = path,
            brush = mediumBrush,
            style = Stroke(width = strokeW * scale, cap = StrokeCap.Round, join = StrokeJoin.Round),
        )
    }

    when (diagramType) {
        "flute-e" -> {
            drawLiner(40f, 3.5f)
            drawWave(49.5f, 5.5f, 9f, 2.4f)
            drawLiner(55.5f, 3.5f)
        }
        "flute-b" -> {
            drawLiner(34f, 4f)
            drawWave(48f, 9.5f, 16f, 2.8f)
            drawLiner(58f, 4f)
        }
        "flute-c" -> {
            drawLiner(28f, 4.5f)
            drawWave(47f, 13.5f, 22f, 3f)
            drawLiner(61f, 4.5f)
        }
        "flute-cb" -> {
            drawLiner(18f, 4f)
            drawWave(31f, 8.5f, 16f, 2.6f)
            drawLiner(40f, 3.5f)
            drawWave(57f, 13f, 22f, 3f)
            drawLiner(70.5f, 4f)
        }
        "flute-ce" -> {
            drawLiner(22f, 3.5f)
            drawWave(31.5f, 5.5f, 9f, 2.3f)
            drawLiner(37.5f, 3.5f)
            drawWave(54.5f, 13f, 22f, 2.9f)
            drawLiner(68f, 4f)
        }
        "flute-be" -> {
            drawLiner(24f, 3.5f)
            drawWave(33.5f, 5.5f, 9f, 2.2f)
            drawLiner(39.5f, 3.5f)
            drawWave(52.5f, 9f, 16f, 2.6f)
            drawLiner(62f, 3.8f)
        }
    }
}

private fun DrawScope.drawIsometricBox(diagramType: String) {
    val vw = 200f
    val vh = 130f
    val scale = minOf(size.width / vw, size.height / vh)
    val ox = (size.width - vw * scale) / 2f
    val oy = (size.height - vh * scale) / 2f

    fun pt(x: Float, y: Float) = Offset(ox + x * scale, oy + y * scale)
    val outlineColor = Color(0xFF6E431E)
    val stroke = Stroke(width = 1.6f * scale, join = StrokeJoin.Round, cap = StrokeCap.Round)

    val boxTop = Brush.linearGradient(listOf(Color(0xFFE6C9A2), Color(0xFFD4AF80)))
    val boxLeft = Brush.verticalGradient(listOf(Color(0xFFC89D6C), Color(0xFFB08250)))
    val boxRight = Brush.verticalGradient(listOf(Color(0xFFB48655), Color(0xFF96693B)))

    fun poly(vararg coords: Pair<Float, Float>): Path {
        val p = Path()
        coords.forEachIndexed { idx, (x, y) ->
            if (idx == 0) p.moveTo(ox + x * scale, oy + y * scale)
            else p.lineTo(ox + x * scale, oy + y * scale)
        }
        p.close()
        return p
    }

    fun drawPoly(path: Path, brush: Brush? = null, color: Color? = null, strokeCol: Color? = outlineColor) {
        if (brush != null) drawPath(path, brush = brush)
        else if (color != null) drawPath(path, color = color)
        if (strokeCol != null) drawPath(path, color = strokeCol, style = stroke)
    }

    // Ground shadow ellipse
    drawOval(
        color = Color(0x8CD5C7B2),
        topLeft = pt(44f, 102f),
        size = Size(112f * scale, 20f * scale),
    )

    when (diagramType) {
        "stand" -> {
            drawPoly(poly(62f to 20f, 138f to 20f, 138f to 60f, 62f to 60f), brush = boxTop)
            drawRoundRect(
                color = Color(0xFFFFFBEB),
                topLeft = pt(74f, 28f),
                size = Size(52f * scale, 10f * scale),
                cornerRadius = CornerRadius(2f * scale, 2f * scale),
            )
            drawRoundRect(
                color = Color(0xFFB45309),
                topLeft = pt(74f, 28f),
                size = Size(52f * scale, 10f * scale),
                cornerRadius = CornerRadius(2f * scale, 2f * scale),
                style = Stroke(1f * scale),
            )
            drawPoly(poly(50f to 68f, 62f to 48f, 62f to 96f, 50f to 104f), brush = boxLeft)
            drawPoly(poly(150f to 68f, 138f to 48f, 138f to 96f, 150f to 104f), brush = boxRight)
            drawPoly(poly(50f to 82f, 150f to 82f, 150f to 104f, 50f to 104f), brush = boxTop)
            drawLine(
                color = Color(0xFFB45309),
                start = pt(62f, 52f),
                end = pt(138f, 52f),
                strokeWidth = 1.5f * scale,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f * scale, 3f * scale)),
            )
        }

        "lock-bottom" -> {
            drawPoly(poly(100f to 24f, 148f to 42f, 100f to 60f, 52f to 42f), brush = boxTop)
            drawPoly(poly(52f to 42f, 100f to 60f, 100f to 106f, 52f to 88f), brush = boxLeft)
            drawPoly(poly(148f to 42f, 100f to 60f, 100f to 106f, 148f to 88f), brush = boxRight)
            val dash = PathEffect.dashPathEffect(floatArrayOf(3f * scale, 2f * scale))
            drawLine(Color(0xFFFFFBEB), pt(52f, 88f), pt(82f, 74f), 1.6f * scale, pathEffect = dash)
            drawLine(Color(0xFFFFFBEB), pt(148f, 88f), pt(118f, 74f), 1.6f * scale, pathEffect = dash)
            drawCircle(Color(0xFFB45309), radius = 9f * scale, center = pt(100f, 84f))
            drawCircle(Color.White, radius = 9f * scale, center = pt(100f, 84f), style = Stroke(1.5f * scale))
            val check = Path().apply {
                moveTo(ox + 96f * scale, oy + 84f * scale)
                lineTo(ox + 99f * scale, oy + 87f * scale)
                lineTo(ox + 105f * scale, oy + 81f * scale)
            }
            drawPath(check, Color.White, style = Stroke(1.8f * scale, cap = StrokeCap.Round, join = StrokeJoin.Round))
        }

        "snap-lock" -> {
            drawPoly(poly(100f to 26f, 146f to 44f, 100f to 62f, 54f to 44f), brush = boxTop)
            drawPoly(poly(54f to 44f, 100f to 62f, 100f to 104f, 54f to 86f), brush = boxLeft)
            drawPoly(poly(146f to 44f, 100f to 62f, 100f to 104f, 146f to 86f), brush = boxRight)
            drawPoly(poly(75f to 75f, 100f to 65f, 125f to 75f, 100f to 85f), color = Color(0xFFFEF3C7), strokeCol = Color(0xFF92400E))
            drawLine(Color(0xFF92400E), pt(88f, 70f), pt(112f, 80f), 1.5f * scale)
        }

        "mailer" -> {
            drawPoly(poly(54f to 42f, 100f to 26f, 146f to 42f, 136f to 16f, 90f to 6f, 44f to 20f), brush = boxTop)
            drawPoly(poly(44f to 20f, 34f to 34f, 54f to 42f), brush = boxLeft)
            drawPoly(poly(136f to 16f, 154f to 28f, 146f to 42f), brush = boxRight)
            drawPoly(poly(54f to 42f, 100f to 26f, 146f to 42f, 100f to 60f), color = Color(0xD978350F))
            drawPoly(poly(54f to 42f, 100f to 60f, 100f to 98f, 54f to 80f), brush = boxLeft)
            drawPoly(poly(146f to 42f, 100f to 60f, 100f to 98f, 146f to 80f), brush = boxRight)
        }

        "fancy" -> {
            drawPoly(poly(100f to 22f, 146f to 40f, 100f to 58f, 54f to 40f), brush = boxTop)
            drawPoly(poly(54f to 40f, 100f to 58f, 100f to 104f, 54f to 86f), brush = boxLeft)
            drawPoly(poly(146f to 40f, 100f to 58f, 100f to 104f, 146f to 86f), brush = boxRight)
            drawPoly(poly(64f to 54f, 92f to 65f, 92f to 90f, 64f to 79f), color = Color(0xD9E0F2FE), strokeCol = Color(0xFF0284C7))
            drawLine(Color(0xFFD97706), pt(100f, 22f), pt(100f, 58f), 2.5f * scale)
        }

        "pharma" -> {
            drawPoly(poly(100f to 16f, 130f to 28f, 100f to 40f, 70f to 28f), color = Color(0xFFFFFBEB))
            drawPoly(poly(70f to 28f, 100f to 40f, 100f to 106f, 70f to 94f), color = Color(0xFFF5EFE6))
            drawPoly(poly(130f to 28f, 100f to 40f, 100f to 106f, 130f to 94f), brush = boxTop)
            val flap = Path().apply {
                moveTo(ox + 70f * scale, oy + 28f * scale)
                quadraticTo(ox + 85f * scale, oy + 12f * scale, ox + 100f * scale, oy + 16f * scale)
                lineTo(ox + 130f * scale, oy + 28f * scale)
            }
            drawPath(flap, brush = boxLeft)
            drawPath(flap, color = outlineColor, style = stroke)
            drawRoundRect(Color(0xFFB45309), pt(77f, 52f), Size(16f * scale, 6f * scale), CornerRadius(1f * scale, 1f * scale))
            drawRoundRect(Color(0xFFA8A29E), pt(77f, 62f), Size(12f * scale, 3f * scale), CornerRadius(1f * scale, 1f * scale))
        }

        "holder" -> {
            drawPoly(poly(48f to 60f, 100f to 40f, 152f to 60f, 100f to 80f), brush = boxTop)
            drawPoly(poly(48f to 60f, 100f to 80f, 100f to 104f, 48f to 84f), brush = boxLeft)
            drawPoly(poly(152f to 60f, 100f to 80f, 100f to 104f, 152f to 84f), brush = boxRight)
            listOf(82f to 56f, 118f to 56f, 82f to 68f, 118f to 68f).forEach { (cx, cy) ->
                drawOval(
                    color = Color(0xFF78350F),
                    topLeft = pt(cx - 10f, cy - 5.5f),
                    size = Size(20f * scale, 11f * scale),
                )
            }
        }

        "tray" -> {
            drawPoly(poly(44f to 64f, 100f to 44f, 156f to 64f, 100f to 84f), brush = boxTop)
            drawPoly(poly(44f to 64f, 100f to 84f, 100f to 104f, 44f to 84f), brush = boxLeft)
            drawPoly(poly(156f to 64f, 100f to 84f, 100f to 104f, 156f to 84f), brush = boxRight)
            drawPoly(poly(56f to 68f, 92f to 81f, 92f to 94f, 56f to 81f), color = Color(0x4078350F), strokeCol = null)
        }

        else -> {
            // Cartons (RSC, diecut, postal, charcoal, fruit, date)
            drawPoly(poly(100f to 26f, 152f to 44f, 100f to 62f, 48f to 44f), brush = boxTop)
            drawPoly(poly(48f to 44f, 100f to 62f, 100f to 106f, 48f to 88f), brush = boxLeft)
            drawPoly(poly(152f to 44f, 100f to 62f, 100f to 106f, 152f to 88f), brush = boxRight)
            drawLine(Color(0xFF78350F), pt(74f, 35f), pt(126f, 53f), 1.8f * scale)

            when (diagramType) {
                "fruit-carton" -> {
                    listOf(64f to 64f, 76f to 68f, 88f to 72f).forEach { (cx, cy) ->
                        drawCircle(Color(0xFF451A03), 3.5f * scale, pt(cx, cy))
                    }
                    drawRoundRect(Color(0xFF451A03), pt(116f, 64f), Size(18f * scale, 6f * scale), CornerRadius(3f * scale, 3f * scale))
                }
                "charcoal-carton" -> {
                    drawRoundRect(Color(0xFF1C1917), pt(62f, 62f), Size(26f * scale, 18f * scale), CornerRadius(2f * scale, 2f * scale))
                    drawRoundRect(Color(0xFFF59E0B), pt(62f, 62f), Size(26f * scale, 18f * scale), CornerRadius(2f * scale, 2f * scale), style = Stroke(1f * scale))
                    val handle = Path().apply {
                        moveTo(ox + 90f * scale, oy + 34f * scale)
                        quadraticTo(ox + 100f * scale, oy + 18f * scale, ox + 110f * scale, oy + 34f * scale)
                    }
                    drawPath(handle, Color(0xFF1C1917), style = Stroke(2.5f * scale, cap = StrokeCap.Round))
                }
                "postal-carton" -> {
                    drawRoundRect(Color(0xFFFFFBEB), pt(58f, 58f), Size(30f * scale, 16f * scale), CornerRadius(1.5f * scale, 1.5f * scale))
                    drawLine(Color(0xFF92400E), pt(62f, 63f), pt(84f, 63f), 1.2f * scale)
                    drawLine(Color(0xFF92400E), pt(62f, 68f), pt(78f, 68f), 1.2f * scale)
                }
                "date-carton" -> {
                    drawPoly(poly(46f to 42f, 100f to 22f, 154f to 42f, 100f to 62f), color = Color(0xFFFEF3C7), strokeCol = Color(0xFF92400E))
                    drawRoundRect(Color(0xFF78350F), pt(112f, 66f), Size(26f * scale, 14f * scale), CornerRadius(2f * scale, 2f * scale))
                }
                "diecut-carton" -> {
                    drawRoundRect(Color(0xFF451A03), pt(115f, 62f), Size(20f * scale, 7f * scale), CornerRadius(3.5f * scale, 3.5f * scale))
                    drawLine(
                        color = Color(0xFFFEF3C7),
                        start = pt(56f, 48f),
                        end = pt(56f, 86f),
                        strokeWidth = 1.3f * scale,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f * scale, 2f * scale)),
                    )
                }
            }
        }
    }
}
