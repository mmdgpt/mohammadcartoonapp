package com.example.data

import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Serializable
data class MessengerConfig(
    val whatsapp: Boolean = true,
    val eitaa: Boolean = true,
    val rubika: Boolean = true,
)

@Serializable
data class PartialMessengerConfig(
    val whatsapp: Boolean? = null,
    val eitaa: Boolean? = null,
    val rubika: Boolean? = null,
)

@Serializable
data class VariantSpecsVisibility(
    val grammage: Boolean? = null,
    val thickness: Boolean? = null,
    val weightCapacity: Boolean? = null,
    val dimensions: Boolean? = null,
    val printType: Boolean? = null,
    val minOrder: Boolean? = null,
    val jointType: Boolean? = null,
)

@Serializable
data class VariantSpecs(
    val grammage: String = "",
    val thickness: String = "",
    val weightCapacity: String = "",
    val dimensions: String = "",
    val printType: String = "",
    val minOrder: String = "",
    val jointType: String = "",
)

@Serializable
data class ProductVariant(
    val id: String,
    val label: String,
    val subLabel: String = "",
    val image: String? = null,
    val schematicImage: String? = null,
    val enabled: Boolean? = null,
    val showSubLabel: Boolean? = null,
    val specsVisibility: VariantSpecsVisibility? = null,
    val specs: VariantSpecs = VariantSpecs(),
    val unitBasePriceToman: Long = 15000L,
)

@Serializable
data class CatalogProduct(
    val id: String,
    val groupId: String,
    val groupTitle: String,
    val name: String,
    val shortSubtitle: String = "",
    val technicalCode: String = "",
    val badgeText: String = "",
    val image: String = "",
    val schematicImage: String? = null,
    val diagramType: String = "stand",
    val variantPromptLabel: String = "تیپ و ساختار محصول را انتخاب کنید",
    val description: String = "",
    val applications: List<String> = emptyList(),
    val structuralFeatures: List<String> = emptyList(),
    val paperCombinations: List<String> = emptyList(),
    val variants: List<ProductVariant> = emptyList(),
    val whatsappNumber: String? = null,
    val messengers: PartialMessengerConfig? = null,
)

@Serializable
data class ProductGroup(
    val id: String,
    val numericId: Int = 1,
    val title: String,
    val subtitle: String = "",
    val seriesCountLabel: String = "",
    val image: String = "",
    val accentLabel: String = "",
    val whatsappNumber: String? = null,
    val messengers: PartialMessengerConfig? = null,
)

@Serializable
data class CompanyAward(
    val year: String,
    val title: String,
    val image: String = "",
)

@Serializable
data class DepartmentContact(
    val unit: String,
    val mobile: String,
    val mobileTel: String,
    val ext: String,
)

@Serializable
data class CoverConfig(
    val heroTitle: String = "کاتالوگ محصولات شرکت کارتن محمد",
    val heroImage: String = "drawable://catalog_heroimage_1",
    val badgeRight: String = "کیفیت صادراتی و صنعتی",
    val badgeLeft: String = "FEFCO STANDARD",
    val features: List<String> = listOf(
        "تولید سفارشی تیراژ بالا",
        "کاغذ و ورق استاندارد",
        "سبد کامل جعبه و کارتن",
        "ارسال سریع سراسر کشور",
    ),
    val startButtonText: String = "شروع کاتالوگ",
    val aboutButtonText: String = "درباره ما",
    val contactButtonText: String = "تماس با ما",
    val copyrightText: String = "© کلیهٔ محتوای این کاتالوگ متعلق به شرکت کارتن محمد می‌باشد.",
)

@Serializable
data class MenuConfig(
    val promptText: String = "بر روی گزینهٔ مورد نظر خود کلیک کنید :",
    val aboutLabel: String = "دربارهٔ ما",
    val productsLabel: String = "محصولات",
    val searchLabel: String = "جست‌وجوی محصول",
    val contactLabel: String = "تماس با ما",
    val showAbout: Boolean = true,
    val showProducts: Boolean = true,
    val showSearch: Boolean = true,
    val showContact: Boolean = true,
    val footerText: String = "SMART PACKAGING CATALOG · MOHAMMAD CARTON",
)

@Serializable
data class GroupsPageConfig(
    val title: String = "محصولات",
    val promptText: String = "بر روی گزینهٔ مورد نظر خود کلیک کنید :",
    val ctaButtonText: String = "مشاهدهٔ محصولات",
    val footerText: String = "۳ گروه اصلی · سبد کامل محصولات استاندارد و سفارشی",
    val allGroupsButtonText: String = "همه گروه‌ها",
    val groupDetailHintText: String = "برای مشاهده جزئیات روی محصول کلیک کنید",
)

@Serializable
data class SpecLabelsConfig(
    val grammage: String = "گرماژ / ترکیب کاغذ",
    val thickness: String = "ضخامت ورق (mm)",
    val weightCapacity: String = "تحمل وزن / مقاومت",
    val dimensions: String = "ابعاد استاندارد (mm)",
    val printType: String = "نوع چاپ و خدمات",
    val minOrder: String = "حداقل تیراژ سفارش",
    val jointType: String = "ساختار و نحوه اتصال:",
)

@Serializable
data class ProductPageConfig(
    val diagramTabLabel: String = "شماتیک فنی",
    val photoTabLabel: String = "تصویر نمونه",
    val specLabels: SpecLabelsConfig = SpecLabelsConfig(),
    val orderButtonText: String = "ثبت سفارش",
    val consultButtonText: String = "درخواست مشاوره",
    val fullTableButtonText: String = "جدول کامل",
    val specsButtonText: String = "مشخصات",
    val nextButtonText: String = "بعدی",
    val prevButtonText: String = "قبلی",
    val messengers: MessengerConfig = MessengerConfig(),
)

@Serializable
data class AboutPageConfig(
    val title: String = "دربارهٔ ما",
    val aboutText: String = "",
    val awardsTitle: String = "افتخارات و دستاوردها",
    val bottomLinkText: String = "مشاهده محصولات کارتن محمد",
)

@Serializable
data class ContactPageConfig(
    val title: String = "تماس با ما",
    val subtitle: String = "کارشناسان ما در اولین فرصت پاسخگوی شما خواهند بود",
    val phoneLabel: String = "شماره تماس کارخانه",
    val phoneFormatted: String = "۴۰ ۲۰ ۲۰ ۴۲ - ۰۶۱",
    val phoneTel: String = "06142202040",
    val salesMobileLabel: String = "شماره مستقیم واحد فروش",
    val mobileDisplay: String = "۰۹۳۹۶۴۱۰۲۷۶",
    val mobileTel: String = "09396410276",
    val whatsappLabel: String = "واتساپ واحد فروش",
    val whatsappNumber: String = "989396410276",
    val websiteLabel: String = "وب‌سایت رسمی",
    val website: String = "mohammadcarton.com",
    val websiteUrl: String = "https://www.mohammadcarton.com",
    val departmentsTitle: String = "ارتباط با دیگر واحدها:",
    val addressTitle: String = "دفتر مرکزی و کارخانه (مسیریابی روی نقشه)",
    val address: String = "دزفول، شهرک صنعتی شماره یک، خیابان چهارم",
    val mapUrl: String = "https://maps.google.com/?cid=13330582876234232006",
    val directCallButtonText: String = "تماس مستقیم با کارخانه",
)

@Serializable
data class PageElementsConfig(
    val headerTitle: String = "کاتالوگ هوشمند شرکت کارتن محمد",
    val headerSubtitle: String = "روی دکمه‌های داخل کاتالوگ بزنید",
    val logoUrl: String = "drawable://brand_logo",
    val cover: CoverConfig = CoverConfig(),
    val menu: MenuConfig = MenuConfig(),
    val groupsPage: GroupsPageConfig = GroupsPageConfig(),
    val productPage: ProductPageConfig = ProductPageConfig(),
    val aboutPage: AboutPageConfig = AboutPageConfig(),
    val contactPage: ContactPageConfig = ContactPageConfig(),
)

@Serializable
data class CatalogCustomState(
    val elements: PageElementsConfig = PageElementsConfig(),
    val groups: List<ProductGroup> = emptyList(),
    val products: List<CatalogProduct> = emptyList(),
    val awards: List<CompanyAward> = emptyList(),
    val departments: List<DepartmentContact> = emptyList(),
    val adminPassword: String = "1234",
)

@Serializable
data class CatalogBackupPayload(
    val backupFormat: String = "mohammad-carton-catalog-backup",
    val version: Int = 1,
    val exportedAt: String = "",
    val catalogState: CatalogCustomState,
)

enum class SpecFieldKey(val labelFa: String) {
    GRAMMAGE("گرماژ / ترکیب کاغذ"),
    THICKNESS("ضخامت ورق"),
    WEIGHT_CAPACITY("تحمل وزن / مقاومت"),
    DIMENSIONS("ابعاد استاندارد"),
    PRINT_TYPE("نوع چاپ و خدمات"),
    MIN_ORDER("حداقل تیراژ سفارش"),
    JOINT_TYPE("ساختار و نحوه اتصال"),
}

enum class MessengerId(val labelFa: String) {
    WHATSAPP("واتساپ"),
    EITAA("ایتا"),
    RUBIKA("روبیکا"),
}

data class ProductMessengerLink(
    val id: MessengerId,
    val label: String,
    val href: String,
    val phoneIntl: String,
    val phoneLocal: String,
    val messageText: String,
    val enabled: Boolean,
)

data class EffectiveMedia(
    val image: String,
    val schematicImage: String?,
)

data class OrderEstimateResult(
    val unitPrice: Long,
    val discountPercent: Int,
    val totalPrice: Long,
    val isValidQuantity: Boolean,
)

data class PaginatedGroupPage(
    val groupId: String,
    val groupTitle: String,
    val groupPageNum: Int,
    val totalGroupPages: Int,
    val products: List<CatalogProduct>,
)

object CatalogDomain {
    const val GROUP_PRODUCTS_PER_PAGE = 8
    const val DEFAULT_WHATSAPP = "989396410276"

    val DIAGRAM_TYPES = listOf(
        "stand" to "جعبه استندی",
        "lock-bottom" to "جعبه لاک‌باتم",
        "snap-lock" to "جعبه ته‌قفلی",
        "mailer" to "جعبه کیبوردی",
        "fancy" to "جعبه فانتزی",
        "pharma" to "جعبه درب دارویی",
        "holder" to "هولدر و مقسم",
        "tray" to "سینی و کفی",
        "rsc-carton" to "کارتن معمولی RSC",
        "diecut-carton" to "کارتن دایکاتی",
        "postal-carton" to "کارتن پستی",
        "charcoal-carton" to "کارتن ذغال",
        "fruit-carton" to "کارتن میوه و صیفی‌جات",
        "date-carton" to "کارتن خرما",
        "flute-e" to "فلوت E (۱.۵mm)",
        "flute-b" to "فلوت B (۳.۰mm)",
        "flute-c" to "فلوت C (۴.۰mm)",
        "flute-cb" to "فلوت CB (۷.۰mm)",
        "flute-ce" to "فلوت CE (۵.۵mm)",
        "flute-be" to "فلوت BE (۴.۵mm)",
    )

    private val persianDigits = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')

    fun toPersianDigits(input: Any): String {
        val text = input.toString()
        val sb = StringBuilder(text.length)
        for (ch in text) {
            if (ch in '0'..'9') {
                sb.append(persianDigits[ch - '0'])
            } else {
                sb.append(ch)
            }
        }
        return sb.toString()
    }

    fun formatToman(amount: Long): String {
        val formatted = NumberFormat.getNumberInstance(Locale.US).format(amount)
        return toPersianDigits(formatted)
    }

    fun normalizeWhatsappNumber(raw: String?, fallback: String = DEFAULT_WHATSAPP): String {
        fun cleanDigits(str: String): String {
            val sb = StringBuilder()
            for (ch in str) {
                when {
                    ch in '0'..'9' -> sb.append(ch)
                    ch in '۰'..'۹' -> sb.append((ch - '۰'))
                    ch in '٠'..'٩' -> sb.append((ch - '٠'))
                }
            }
            return sb.toString()
        }

        var digits = cleanDigits(raw.orEmpty())
        if (digits.isEmpty()) {
            digits = cleanDigits(fallback).ifEmpty { DEFAULT_WHATSAPP }
        }
        if (digits.startsWith("00")) {
            digits = digits.drop(2)
        }
        if (digits.startsWith("09") && digits.length == 11) {
            digits = "98" + digits.drop(1)
        } else if (digits.startsWith("9") && digits.length == 10) {
            digits = "98$digits"
        }
        return digits
    }

    fun formatLocalIranPhone(intlDigits: String): String {
        return if (intlDigits.startsWith("98") && intlDigits.length == 12) {
            "0" + intlDigits.drop(2)
        } else {
            intlDigits
        }
    }

    fun getProductWhatsappNumber(
        product: CatalogProduct,
        groups: List<ProductGroup>,
        fallback: String = DEFAULT_WHATSAPP,
    ): String {
        if (!product.whatsappNumber.isNullOrBlank()) {
            return normalizeWhatsappNumber(product.whatsappNumber, fallback)
        }
        val group = groups.find { it.id == product.groupId }
        if (!group?.whatsappNumber.isNullOrBlank()) {
            return normalizeWhatsappNumber(group.whatsappNumber, fallback)
        }
        return normalizeWhatsappNumber(fallback, DEFAULT_WHATSAPP)
    }

    fun getProductMessengerConfig(
        product: CatalogProduct,
        groups: List<ProductGroup>,
        globalMessengers: MessengerConfig = MessengerConfig(),
    ): MessengerConfig {
        val group = groups.find { it.id == product.groupId }
        return MessengerConfig(
            whatsapp = product.messengers?.whatsapp
                ?: group?.messengers?.whatsapp
                ?: globalMessengers.whatsapp,
            eitaa = product.messengers?.eitaa
                ?: group?.messengers?.eitaa
                ?: globalMessengers.eitaa,
            rubika = product.messengers?.rubika
                ?: group?.messengers?.rubika
                ?: globalMessengers.rubika,
        )
    }

    fun buildProductMessengerLinks(
        product: CatalogProduct,
        variantLabel: String,
        groups: List<ProductGroup>,
        fallbackNumber: String,
        globalMessengers: MessengerConfig,
    ): List<ProductMessengerLink> {
        val phoneIntl = getProductWhatsappNumber(product, groups, fallbackNumber)
        val phoneLocal = formatLocalIranPhone(phoneIntl)
        val cfg = getProductMessengerConfig(product, groups, globalMessengers)
        val messageText = "سلام، لطفاً قیمت و شرایط تولید «${product.name} - تیپ $variantLabel» را اعلام فرمایید."
        val encodedMsg = URLEncoder.encode(messageText, "UTF-8")
        val encodedWithPhone = URLEncoder.encode(
            "$messageText\nشماره تماس پشتیبانی: $phoneLocal",
            "UTF-8",
        )
        val encodedTel = URLEncoder.encode("tel:+$phoneIntl", "UTF-8")

        return listOf(
            ProductMessengerLink(
                id = MessengerId.WHATSAPP,
                label = "واتساپ",
                href = "https://wa.me/$phoneIntl?text=$encodedMsg",
                phoneIntl = phoneIntl,
                phoneLocal = phoneLocal,
                messageText = messageText,
                enabled = cfg.whatsapp,
            ),
            ProductMessengerLink(
                id = MessengerId.EITAA,
                label = "ایتا",
                href = "https://eitaa.com/share/url?url=$encodedTel&text=$encodedWithPhone",
                phoneIntl = phoneIntl,
                phoneLocal = phoneLocal,
                messageText = messageText,
                enabled = cfg.eitaa,
            ),
            ProductMessengerLink(
                id = MessengerId.RUBIKA,
                label = "روبیکا",
                href = "https://rubika.ir/$phoneLocal?text=$encodedMsg",
                phoneIntl = phoneIntl,
                phoneLocal = phoneLocal,
                messageText = messageText,
                enabled = cfg.rubika,
            ),
        )
    }

    fun getVisibleProductVariants(product: CatalogProduct): List<ProductVariant> {
        return product.variants.filter { it.enabled != false }
    }

    fun isVariantSpecVisible(variant: ProductVariant?, key: SpecFieldKey): Boolean {
        if (variant == null) return false
        val vis = variant.specsVisibility ?: return true
        return when (key) {
            SpecFieldKey.GRAMMAGE -> vis.grammage != false
            SpecFieldKey.THICKNESS -> vis.thickness != false
            SpecFieldKey.WEIGHT_CAPACITY -> vis.weightCapacity != false
            SpecFieldKey.DIMENSIONS -> vis.dimensions != false
            SpecFieldKey.PRINT_TYPE -> vis.printType != false
            SpecFieldKey.MIN_ORDER -> vis.minOrder != false
            SpecFieldKey.JOINT_TYPE -> vis.jointType != false
        }
    }

    fun hasAnyVisibleVariantSpec(variant: ProductVariant?): Boolean {
        if (variant == null) return false
        return SpecFieldKey.entries.any { isVariantSpecVisible(variant, it) }
    }

    fun getVariantEffectiveMedia(product: CatalogProduct, variant: ProductVariant?): EffectiveMedia {
        val target = variant ?: product.variants.firstOrNull()
        val effectiveImg = target?.image?.takeIf { it.isNotBlank() } ?: product.image
        val effectiveSchematic = target?.schematicImage?.takeIf { it.isNotBlank() } ?: product.schematicImage
        return EffectiveMedia(image = effectiveImg, schematicImage = effectiveSchematic)
    }

    fun getPaginatedPagesForGroup(
        groupId: String,
        products: List<CatalogProduct>,
        groups: List<ProductGroup>,
    ): List<PaginatedGroupPage> {
        val group = groups.find { it.id == groupId } ?: groups.firstOrNull()
        val groupTitle = group?.title ?: "محصولات"
        val groupProducts = products.filter { it.groupId == groupId }
        val totalPages = maxOf(1, ceil(groupProducts.size.toDouble() / GROUP_PRODUCTS_PER_PAGE).toInt())
        return (1..totalPages).map { pNum ->
            val fromIdx = (pNum - 1) * GROUP_PRODUCTS_PER_PAGE
            val toIdx = minOf(pNum * GROUP_PRODUCTS_PER_PAGE, groupProducts.size)
            val slice = if (fromIdx < groupProducts.size) groupProducts.subList(fromIdx, toIdx) else emptyList()
            PaginatedGroupPage(
                groupId = groupId,
                groupTitle = groupTitle,
                groupPageNum = pNum,
                totalGroupPages = totalPages,
                products = slice,
            )
        }
    }

    fun getProductGroupPageNumber(productId: String, products: List<CatalogProduct>): Pair<String, Int> {
        val product = products.find { it.id == productId } ?: return "boxes" to 1
        val siblings = products.filter { it.groupId == product.groupId }
        val idx = siblings.indexOfFirst { it.id == productId }
        val page = if (idx >= 0) (idx / GROUP_PRODUCTS_PER_PAGE) + 1 else 1
        return product.groupId to page
    }

    fun getAdjacentProducts(
        productId: String,
        products: List<CatalogProduct>,
    ): Pair<CatalogProduct?, CatalogProduct?> {
        val current = products.find { it.id == productId } ?: return null to null
        val siblings = products.filter { it.groupId == current.groupId }
        val idx = siblings.indexOfFirst { it.id == productId }
        val prev = if (idx > 0) siblings[idx - 1] else null
        val next = if (idx in 0 until siblings.lastIndex) siblings[idx + 1] else null
        return prev to next
    }

    fun filterCatalogProducts(
        query: String,
        categoryFilter: String,
        products: List<CatalogProduct>,
    ): List<CatalogProduct> {
        val normalized = query.trim().lowercase()
        return products.filter { item ->
            if (categoryFilter != "all" && item.groupId != categoryFilter) return@filter false
            if (normalized.isEmpty()) return@filter true
            val haystack = buildString {
                append(item.name).append(' ')
                append(item.shortSubtitle).append(' ')
                append(item.technicalCode).append(' ')
                append(item.groupTitle).append(' ')
                append(item.description).append(' ')
                item.applications.forEach { append(it).append(' ') }
                item.variants.forEach { append(it.label).append(' ').append(it.subLabel).append(' ') }
            }.lowercase()
            haystack.contains(normalized)
        }
    }

    fun calculateOrderEstimate(
        product: CatalogProduct,
        variantId: String,
        quantity: Long,
        customPrint: Boolean,
    ): OrderEstimateResult {
        val variant = product.variants.find { it.id == variantId } ?: product.variants.firstOrNull()
        if (variant == null || quantity <= 0L) {
            return OrderEstimateResult(0L, 0, 0L, false)
        }
        val discountPercent = when {
            quantity >= 5000L -> 14
            quantity >= 2000L -> 9
            quantity >= 1000L -> 5
            else -> 0
        }
        val printAddon = if (customPrint) (variant.unitBasePriceToman * 0.12).roundToInt().toLong() else 0L
        val rawUnit = variant.unitBasePriceToman + printAddon
        val discountedUnit = (rawUnit * (1.0 - discountPercent / 100.0)).roundToInt().toLong()
        val totalPrice = discountedUnit * floor(quantity.toDouble()).toLong()
        return OrderEstimateResult(
            unitPrice = discountedUnit,
            discountPercent = discountPercent,
            totalPrice = totalPrice,
            isValidQuantity = quantity >= 100L,
        )
    }

    fun createBlankProduct(groupId: String, fallbackWhatsapp: String = DEFAULT_WHATSAPP): CatalogProduct {
        val stamp = (System.currentTimeMillis() % 10000L).toString().padStart(4, '0')
        val (groupTitle, defaultImage, defaultDiagram) = when (groupId) {
            "cartons" -> Triple("انواع کارتن", "drawable://cat_cartons_showcase", "rsc-carton")
            "sheets" -> Triple("انواع ورق کارتن", "drawable://cat_sheets_flutes", "flute-b")
            else -> Triple("انواع جعبه", "drawable://cat_boxes_showcase", "stand")
        }
        return CatalogProduct(
            id = "custom-prod-$stamp",
            groupId = groupId,
            groupTitle = groupTitle,
            name = "محصول جدید کارتن محمد",
            shortSubtitle = "تولید سفارشی با ابعاد و چاپ اختصاصی",
            technicalCode = "FEFCO CUSTOM-$stamp",
            badgeText = "تولید اختصاصی",
            image = defaultImage,
            diagramType = defaultDiagram,
            variantPromptLabel = "تیپ و ساختار محصول را انتخاب کنید",
            description = "این محصول با بهره‌گیری از خطوط مدرن ورق‌سازی، کارتن‌سازی و جعبه‌سازی شرکت کارتن محمد در ابعاد و ترکیب کاغذ دلخواه مشتری تولید می‌گردد.",
            applications = listOf(
                "بسته‌بندی صنعتی و صادراتی",
                "محافظت کامل در برابر ضربه و فشار چیدمان",
            ),
            structuralFeatures = listOf(
                "تولید با ورق و کاغذ استاندارد با استحکام بالا",
                "قابلیت چاپ فلکسو و افست لمینتی",
            ),
            paperCombinations = listOf("تست‌لاینر + فلوت سمی‌کمیکال", "کرافت وارداتی صادراتی"),
            whatsappNumber = fallbackWhatsapp,
            variants = listOf(
                ProductVariant(
                    id = "var-$stamp-1",
                    label = "تیپ استاندارد ۱",
                    subLabel = "۳ لایه · B فلوت",
                    specs = VariantSpecs(
                        grammage = "۴۵۰ گرم بر مترمربع",
                        thickness = "۳.۰ میلی‌متر",
                        weightCapacity = "۸ کیلوگرم",
                        dimensions = "۳۰۰×۲۰۰×۲۰۰",
                        printType = "فلکسو یا افست لمینتی",
                        minOrder = "۱٬۰۰۰ عدد",
                        jointType = "چسب صنعتی / دایکات",
                    ),
                    unitBasePriceToman = 18500L,
                ),
                ProductVariant(
                    id = "var-$stamp-2",
                    label = "تیپ تقویت‌شده ۲",
                    subLabel = "۵ لایه · CB/BE فلوت",
                    specs = VariantSpecs(
                        grammage = "۷۲۰ گرم بر مترمربع",
                        thickness = "۵.۵ میلی‌متر",
                        weightCapacity = "۱۸ کیلوگرم",
                        dimensions = "۴۰۰×۳۰۰×۲۵۰",
                        printType = "چاپ اختصاصی تا ۴ رنگ",
                        minOrder = "۵۰۰ عدد",
                        jointType = "اتصال تقویت‌شده صادراتی",
                    ),
                    unitBasePriceToman = 32000L,
                ),
            ),
        )
    }
}
