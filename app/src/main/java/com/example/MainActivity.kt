package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.data.CatalogProduct
import com.example.ui.AboutRoute
import com.example.ui.AdminPanelRoute
import com.example.ui.CatalogViewModel
import com.example.ui.ContactRoute
import com.example.ui.CoverRoute
import com.example.ui.GroupDetailRoute
import com.example.ui.GroupsRoute
import com.example.ui.MenuRoute
import com.example.ui.ProductDetailRoute
import com.example.ui.components.FullSpecTableDialog
import com.example.ui.components.OrderInquiryDialog
import com.example.ui.components.SearchCatalogDialog
import com.example.ui.components.TechnicalFeaturesDialog
import com.example.ui.components.ZoomableImageLightbox
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminLoginDialog
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.CoverScreen
import com.example.ui.screens.GroupDetailScreen
import com.example.ui.screens.GroupsScreen
import com.example.ui.screens.MenuScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.theme.KraftPalette
import com.example.ui.theme.MohammadCartonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
        )
        setContent {
            MohammadCartonTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    MohammadCartonApp()
                }
            }
        }
    }
}

@Composable
fun MohammadCartonApp(
    viewModel: CatalogViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    var isSearchOpen by remember { mutableStateOf(false) }
    var isAdminLoginOpen by remember { mutableStateOf(false) }
    var fullTableProduct by remember { mutableStateOf<CatalogProduct?>(null) }
    var specsModalProduct by remember { mutableStateOf<CatalogProduct?>(null) }
    var orderModalState by remember { mutableStateOf<Pair<CatalogProduct, String>?>(null) }
    var zoomLightboxState by remember { mutableStateOf<Pair<String, String>?>(null) }
    var selectedVariantOverride by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState.statusMessage) {
        val msg = uiState.statusMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(msg)
        viewModel.clearStatusMessage()
    }

    fun openAdminFlow() {
        if (uiState.isAdminAuthenticated) {
            navController.navigate(AdminPanelRoute)
        } else {
            isAdminLoginOpen = true
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = KraftPalette.Backdrop,
    ) { _ ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(KraftPalette.SheetBackground),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = KraftPalette.AmberDeep)
            }
        } else {
            val catalogState = uiState.catalogState

            NavHost(
                navController = navController,
                startDestination = CoverRoute,
                enterTransition = {
                    fadeIn(animationSpec = tween(220)) +
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(240))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(180))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(200))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(180)) +
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(220))
                },
            ) {
                composable<CoverRoute> {
                    CoverScreen(
                        catalogState = catalogState,
                        onStartCatalog = { navController.navigate(MenuRoute) },
                        onOpenAbout = { navController.navigate(AboutRoute) },
                        onOpenContact = { navController.navigate(ContactRoute) },
                        onOpenSearch = { isSearchOpen = true },
                        onOpenAdmin = ::openAdminFlow,
                        onZoomImage = { img, title -> zoomLightboxState = img to title },
                    )
                }

                composable<MenuRoute> {
                    MenuScreen(
                        catalogState = catalogState,
                        onBack = { navController.popBackStack() },
                        onOpenAbout = { navController.navigate(AboutRoute) },
                        onOpenGroups = { navController.navigate(GroupsRoute) },
                        onOpenSearch = { isSearchOpen = true },
                        onOpenContact = { navController.navigate(ContactRoute) },
                        onOpenAdmin = ::openAdminFlow,
                    )
                }

                composable<GroupsRoute> {
                    GroupsScreen(
                        catalogState = catalogState,
                        onBack = { navController.popBackStack() },
                        onOpenMenu = {
                            navController.navigate(MenuRoute) {
                                launchSingleTop = true
                            }
                        },
                        onOpenSearch = { isSearchOpen = true },
                        onOpenAdmin = ::openAdminFlow,
                        onSelectGroup = { groupId ->
                            navController.navigate(GroupDetailRoute(groupId = groupId, pageNum = 1))
                        },
                    )
                }

                composable<GroupDetailRoute> { backStackEntry ->
                    val route = backStackEntry.toRoute<GroupDetailRoute>()
                    GroupDetailScreen(
                        groupId = route.groupId,
                        pageNum = route.pageNum,
                        catalogState = catalogState,
                        onBackToGroups = {
                            if (!navController.popBackStack(GroupsRoute, inclusive = false)) {
                                navController.navigate(GroupsRoute) { launchSingleTop = true }
                            }
                        },
                        onOpenMenu = { navController.navigate(MenuRoute) { launchSingleTop = true } },
                        onOpenSearch = { isSearchOpen = true },
                        onSelectPage = { gId, nextPage ->
                            navController.navigate(GroupDetailRoute(groupId = gId, pageNum = nextPage)) {
                                popUpTo(route) { inclusive = true }
                            }
                        },
                        onSelectProduct = { prodId ->
                            selectedVariantOverride = null
                            navController.navigate(ProductDetailRoute(productId = prodId))
                        },
                    )
                }

                composable<ProductDetailRoute> { backStackEntry ->
                    val route = backStackEntry.toRoute<ProductDetailRoute>()
                    val product = catalogState.products.find { it.id == route.productId }
                    if (product == null) {
                        GroupsScreen(
                            catalogState = catalogState,
                            onBack = { navController.popBackStack() },
                            onOpenMenu = { navController.navigate(MenuRoute) },
                            onOpenSearch = { isSearchOpen = true },
                            onOpenAdmin = ::openAdminFlow,
                            onSelectGroup = { gId -> navController.navigate(GroupDetailRoute(gId, 1)) },
                        )
                    } else {
                        ProductDetailScreen(
                            product = product,
                            catalogState = catalogState,
                            onBackToGroup = { gId, pNum ->
                                if (!navController.popBackStack()) {
                                    navController.navigate(GroupDetailRoute(gId, pNum))
                                }
                            },
                            onOpenMenu = { navController.navigate(MenuRoute) { launchSingleTop = true } },
                            onOpenSearch = { isSearchOpen = true },
                            onNavigateToProduct = { nextProdId ->
                                selectedVariantOverride = null
                                navController.navigate(ProductDetailRoute(nextProdId)) {
                                    popUpTo(route) { inclusive = true }
                                }
                            },
                            onOpenFullTable = { fullTableProduct = it },
                            onOpenSpecsModal = { specsModalProduct = it },
                            onOpenOrderModal = { p, vId -> orderModalState = p to vId },
                            onZoomImage = { img, title -> zoomLightboxState = img to title },
                            onShowToast = { viewModel.showStatusMessage(it) },
                            selectedVariantOverride = selectedVariantOverride,
                            onConsumeVariantOverride = { selectedVariantOverride = null },
                        )
                    }
                }

                composable<AboutRoute> {
                    AboutScreen(
                        catalogState = catalogState,
                        onBack = { navController.popBackStack() },
                        onOpenMenu = { navController.navigate(MenuRoute) { launchSingleTop = true } },
                        onOpenSearch = { isSearchOpen = true },
                        onOpenGroups = { navController.navigate(GroupsRoute) },
                        onZoomImage = { img, title -> zoomLightboxState = img to title },
                    )
                }

                composable<ContactRoute> {
                    ContactScreen(
                        catalogState = catalogState,
                        onBack = { navController.popBackStack() },
                        onOpenMenu = { navController.navigate(MenuRoute) { launchSingleTop = true } },
                        onOpenSearch = { isSearchOpen = true },
                    )
                }

                composable<AdminPanelRoute> {
                    AdminPanelScreen(
                        savedState = catalogState,
                        onSaveState = { next -> viewModel.saveCatalogState(next) },
                        onResetToDefault = { viewModel.resetCatalogToDefault() },
                        onExportBackupJson = { state -> viewModel.exportBackupJson(state) },
                        onParseBackupJson = { raw -> viewModel.parseBackupJson(raw) },
                        onPersistPickedImage = { uri, cb -> viewModel.persistPickedImage(uri, cb) },
                        onViewProductInCatalog = { prodId ->
                            selectedVariantOverride = null
                            navController.navigate(ProductDetailRoute(prodId))
                        },
                        onLogout = {
                            viewModel.logoutAdmin()
                            navController.popBackStack()
                        },
                        onClose = { navController.popBackStack() },
                        onShowToast = { viewModel.showStatusMessage(it) },
                    )
                }
            }

            // Modals & Lightbox Overlays
            if (isSearchOpen) {
                SearchCatalogDialog(
                    products = catalogState.products,
                    onSelectProduct = { prodId ->
                        selectedVariantOverride = null
                        navController.navigate(ProductDetailRoute(prodId))
                    },
                    onDismiss = { isSearchOpen = false },
                )
            }

            fullTableProduct?.let { prod ->
                FullSpecTableDialog(
                    product = prod,
                    onSelectVariant = { vId -> selectedVariantOverride = vId },
                    onDismiss = { fullTableProduct = null },
                )
            }

            specsModalProduct?.let { prod ->
                TechnicalFeaturesDialog(
                    product = prod,
                    onDismiss = { specsModalProduct = null },
                )
            }

            orderModalState?.let { (prod, vId) ->
                OrderInquiryDialog(
                    product = prod,
                    initialVariantId = vId,
                    contactConfig = catalogState.elements.contactPage,
                    onDismiss = { orderModalState = null },
                )
            }

            zoomLightboxState?.let { (imgPath, title) ->
                ZoomableImageLightbox(
                    imagePath = imgPath,
                    title = title,
                    onDismiss = { zoomLightboxState = null },
                )
            }

            if (isAdminLoginOpen) {
                AdminLoginDialog(
                    onAuthenticate = { u, p -> viewModel.authenticateAdmin(u, p) },
                    onSuccess = {
                        isAdminLoginOpen = false
                        navController.navigate(AdminPanelRoute)
                    },
                    onDismiss = { isAdminLoginOpen = false },
                )
            }
        }
    }
}
