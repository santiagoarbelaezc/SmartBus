package com.smartbus.app.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.smartbus.app.core.AppLanguage
import com.smartbus.app.core.LanguageManager
import com.smartbus.app.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.smartbus.app.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.smartbus.app.presentation.auth.forgotpassword.ResetPasswordSentScreen
import com.smartbus.app.presentation.auth.login.LoginScreen
import com.smartbus.app.presentation.auth.login.LoginViewModel
import com.smartbus.app.presentation.auth.register.RegistrationLoadingScreen
import com.smartbus.app.presentation.auth.register.RegistrationStep2Screen
import com.smartbus.app.presentation.auth.register.RegistrationStep3Screen
import com.smartbus.app.presentation.auth.register.RegisterScreen
import com.smartbus.app.presentation.auth.register.RegisterViewModel
import com.smartbus.app.presentation.home.HomeScreen
import com.smartbus.app.presentation.home.HomeViewModel
import com.smartbus.app.presentation.nfc.NFCPaymentScreen
import com.smartbus.app.presentation.nfc.NFCPaymentViewModel
import com.smartbus.app.presentation.payment.PaymentScreen
import com.smartbus.app.presentation.payment.PaymentViewModel
import com.smartbus.app.presentation.points.PointsScreen
import com.smartbus.app.presentation.points.PointsViewModel
import com.smartbus.app.presentation.profile.ProfileScreen
import com.smartbus.app.presentation.profile.ProfileViewModel
import com.smartbus.app.presentation.search.SearchScreen
import com.smartbus.app.presentation.search.SearchViewModel
import com.smartbus.app.presentation.seatselection.SeatSelectionScreen
import com.smartbus.app.presentation.seatselection.SeatSelectionViewModel
import com.smartbus.app.presentation.splash.SplashScreen
import com.smartbus.app.presentation.splash.SplashViewModel
import com.smartbus.app.presentation.tickets.MyTicketsScreen
import com.smartbus.app.presentation.tickets.MyTicketsViewModel
import com.smartbus.app.presentation.tracking.TrackingScreen
import com.smartbus.app.presentation.tracking.TrackingViewModel
import com.smartbus.app.presentation.travelcredit.TravelCreditScreen
import com.smartbus.app.presentation.travelcredit.TravelCreditViewModel
import com.smartbus.app.presentation.notifications.NotificationsScreen
import com.smartbus.app.presentation.payment.PaymentMethodsScreen
import com.smartbus.app.presentation.help.HelpSupportScreen
import com.smartbus.app.presentation.terms.TermsScreen
import com.smartbus.app.presentation.welcome.WelcomeScreen
import com.smartbus.app.ui.components.BottomNavBar
import com.smartbus.app.ui.components.NavigationItem

/**
 * Gestiona toda la navegación de la aplicación.
 * Incluye NavHost con rutas tipadas y Scaffold con BottomNavigationBar.
 */
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    val currentLang = LanguageManager.currentLanguage
    val isEn = currentLang.value == AppLanguage.ENGLISH

    // Rutas donde se muestra el BottomBar
    val bottomBarRoutes = listOf("Home", "Search", "MyTickets", "Points", "Profile")
    val showBottomBar = bottomBarRoutes.any { currentRoute.contains(it, ignoreCase = true) }

    val navItems = listOf(
        NavigationItem(
            route = MainRoutes.Home::class.simpleName ?: "home",
            icon = Icons.Default.Home,
            label = if (isEn) "Home" else "Inicio"
        ),
        NavigationItem(
            route = MainRoutes.Search::class.simpleName ?: "search",
            icon = Icons.Default.Search,
            label = if (isEn) "Search" else "Buscar"
        ),
        NavigationItem(
            route = MainRoutes.MyTickets::class.simpleName ?: "tickets",
            icon = Icons.Default.ConfirmationNumber,
            label = if (isEn) "Tickets" else "Tiquetes",
            badgeCount = 2  // mock: 2 tiquetes activos
        ),
        NavigationItem(
            route = MainRoutes.Points::class.simpleName ?: "points",
            icon = Icons.Default.Stars,
            label = if (isEn) "Points" else "Puntos"
        ),
        NavigationItem(
            route = MainRoutes.Profile::class.simpleName ?: "profile",
            icon = Icons.Default.Person,
            label = if (isEn) "Profile" else "Perfil"
        )
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    selectedRoute = currentRoute,
                    onItemClick = { route -> navigateToBottomNavItem(navController, route) },
                    items = navItems
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp
            )
        ) {
            BuildNavHost(navController)
        }
    }
}

/**
 * Construye el NavHost con todas las rutas tipadas.
 */
@Composable
private fun BuildNavHost(navController: NavHostController) {
    val registerViewModel: RegisterViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MainRoutes.Splash
    ) {
        // ── Splash ──────────────────────────────────────────────────
        composable<MainRoutes.Splash> {
            SplashScreen(
                viewModel = SplashViewModel(),
                onNavigateNext = { isActive ->
                    if (isActive) {
                        navController.navigate(MainRoutes.Home) {
                            popUpTo<MainRoutes.Splash> { inclusive = true }
                        }
                    } else {
                        navController.navigate(MainRoutes.Welcome) {
                            popUpTo<MainRoutes.Splash> { inclusive = true }
                        }
                    }
                }
            )
        }

        // ── Welcome ──────────────────────────────────────────────────
        composable<MainRoutes.Welcome> {
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate(MainRoutes.Login) },
                onNavigateToRegister = { navController.navigate(MainRoutes.Register) },
                onGoogleSignIn = {
                    navController.navigate(MainRoutes.Home) {
                        popUpTo<MainRoutes.Welcome> { inclusive = true }
                    }
                }
            )
        }

        // ── Login ────────────────────────────────────────────────────
        composable<MainRoutes.Login> {
            LoginScreen(
                viewModel = LoginViewModel(),
                onLoginSuccess = {
                    navController.navigate(MainRoutes.Home) {
                        popUpTo<MainRoutes.Login> { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(MainRoutes.Register) },
                onNavigateToForgotPassword = { navController.navigate(MainRoutes.ForgotPassword) }
            )
        }

        // ── Forgot Password ──────────────────────────────────────────
        composable<MainRoutes.ForgotPassword> {
            ForgotPasswordScreen(
                viewModel = ForgotPasswordViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onEmailSent = {
                    navController.navigate(MainRoutes.ResetPasswordSent) {
                        popUpTo<MainRoutes.ForgotPassword> { inclusive = true }
                    }
                }
            )
        }

        // ── Reset Password Sent ──────────────────────────────────────
        composable<MainRoutes.ResetPasswordSent> {
            ResetPasswordSentScreen(
                onBackToLogin = {
                    navController.navigate(MainRoutes.Login) {
                        popUpTo<MainRoutes.Welcome> { inclusive = false }
                    }
                }
            )
        }

        // ── Register ─────────────────────────────────────────────────
        composable<MainRoutes.Register> {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = { navController.navigate(MainRoutes.RegisterStep2) },
                onNavigateToLogin = {
                    navController.navigate(MainRoutes.Login) {
                        popUpTo<MainRoutes.Register> { inclusive = true }
                    }
                }
            )
        }

        composable<MainRoutes.RegisterStep2> {
            RegistrationStep2Screen(
                viewModel = registerViewModel,
                onNext = { navController.navigate(MainRoutes.RegisterStep3) },
                onSkip = {
                    navController.navigate(MainRoutes.Home) {
                        popUpTo<MainRoutes.Welcome> { inclusive = true }
                    }
                }
            )
        }

        composable<MainRoutes.RegisterStep3> {
            RegistrationStep3Screen(
                viewModel = registerViewModel,
                onFinish = { navController.navigate(MainRoutes.RegisterLoading) },
                onSkip = {
                    navController.navigate(MainRoutes.Home) {
                        popUpTo<MainRoutes.Welcome> { inclusive = true }
                    }
                }
            )
        }

        composable<MainRoutes.RegisterLoading> {
            RegistrationLoadingScreen(
                onFinished = {
                    navController.navigate(MainRoutes.Home) {
                        popUpTo<MainRoutes.Welcome> { inclusive = true }
                    }
                }
            )
        }

        // ── Home ─────────────────────────────────────────────────────
        composable<MainRoutes.Home> {
            HomeScreen(
                viewModel = HomeViewModel(),
                onNavigateToSearch = { navController.navigate(MainRoutes.Search) },
                onNavigateToTickets = { navController.navigate(MainRoutes.MyTickets) },
                onNavigateToTracking = { navController.navigate(MainRoutes.Tracking(ticketId = "")) },
                onNavigateToPoints = { navController.navigate(MainRoutes.Points) },
                onNavigateToNFC = { navController.navigate(MainRoutes.NFCPayment) }
            )
        }

        // ── Search ───────────────────────────────────────────────────
        composable<MainRoutes.Search> {
            SearchScreen(
                viewModel = SearchViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onResultSelected = { navController.navigate(MainRoutes.SeatSelection(tripId = "")) }
            )
        }

        composable<MainRoutes.SeatSelection> { backStackEntry ->
            SeatSelectionScreen(
                viewModel = SeatSelectionViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onContinueToPayment = { navController.navigate(MainRoutes.Payment(bookingId = "")) }
            )
        }

        composable<MainRoutes.Payment> { backStackEntry ->
            PaymentScreen(
                viewModel = PaymentViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onTrackBus = { navController.navigate(MainRoutes.Tracking(ticketId = "")) },
                onPaymentSuccess = {
                    navController.navigate(MainRoutes.MyTickets) {
                        popUpTo<MainRoutes.Search> { inclusive = true }
                    }
                }
            )
        }

        // ── My Tickets ───────────────────────────────────────────────
        composable<MainRoutes.MyTickets> {
            MyTicketsScreen(
                viewModel = MyTicketsViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onTrackBus = { navController.navigate(MainRoutes.Tracking(ticketId = "")) }
            )
        }

        // ── Tracking ─────────────────────────────────────────────────
        composable<MainRoutes.Tracking> { backStackEntry ->
            TrackingScreen(
                viewModel = TrackingViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── NFC Payment ──────────────────────────────────────────────
        composable<MainRoutes.NFCPayment> {
            NFCPaymentScreen(
                viewModel = NFCPaymentViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Points ───────────────────────────────────────────────────
        composable<MainRoutes.Points> {
            PointsScreen(
                viewModel = PointsViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // ── Travel Credit ────────────────────────────────────────────
        composable<MainRoutes.TravelCredit> {
            TravelCreditScreen(
                viewModel = TravelCreditViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onUseCredit = { navController.popBackStack() }
            )
        }

        // ── Profile ──────────────────────────────────────────────────
        composable<MainRoutes.Profile> {
            ProfileScreen(
                viewModel = ProfileViewModel(),
                onLogout = {
                    navController.navigate(MainRoutes.Welcome) {
                        popUpTo<MainRoutes.Splash> { inclusive = true }
                    }
                },
                onNavigateToTravelCredit      = { navController.navigate(MainRoutes.TravelCredit) },
                onNavigateToPaymentMethods    = { navController.navigate(MainRoutes.PaymentMethods) },
                onNavigateToNotifications     = { navController.navigate(MainRoutes.Notifications) },
                onNavigateToHelp              = { navController.navigate(MainRoutes.HelpSupport) },
                onNavigateToTerms             = { navController.navigate(MainRoutes.Terms) }
            )
        }

        // ── Notifications ────────────────────────────────────────────
        composable<MainRoutes.Notifications> {
            NotificationsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ── Payment Methods ──────────────────────────────────────────
        composable<MainRoutes.PaymentMethods> {
            PaymentMethodsScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ── Help & Support ───────────────────────────────────────────
        composable<MainRoutes.HelpSupport> {
            HelpSupportScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ── Terms & Conditions ───────────────────────────────────────
        composable<MainRoutes.Terms> {
            TermsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

/**
 * Navega a los elementos del BottomNavBar manteniendo el estado.
 */
private fun navigateToBottomNavItem(navController: NavHostController, route: String) {
    when {
        route.contains("Home", ignoreCase = true) -> navController.navigate(MainRoutes.Home) {
            popUpTo<MainRoutes.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
        route.contains("Search", ignoreCase = true) -> navController.navigate(MainRoutes.Search) {
            popUpTo<MainRoutes.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
        route.contains("MyTickets", ignoreCase = true) || route.contains("Ticket", ignoreCase = true) -> navController.navigate(MainRoutes.MyTickets) {
            popUpTo<MainRoutes.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
        route.contains("Points", ignoreCase = true) -> navController.navigate(MainRoutes.Points) {
            popUpTo<MainRoutes.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
        route.contains("Profile", ignoreCase = true) -> navController.navigate(MainRoutes.Profile) {
            popUpTo<MainRoutes.Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
