package com.smartbus.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartbus.app.presentation.home.HomeScreen
import com.smartbus.app.presentation.home.HomeViewModel
import com.smartbus.app.presentation.search.SearchScreen
import com.smartbus.app.presentation.search.SearchViewModel
import com.smartbus.app.presentation.seatselection.SeatSelectionScreen
import com.smartbus.app.presentation.seatselection.SeatSelectionViewModel
import com.smartbus.app.presentation.payment.PaymentScreen
import com.smartbus.app.presentation.payment.PaymentViewModel
import com.smartbus.app.presentation.tickets.MyTicketsScreen
import com.smartbus.app.presentation.tickets.MyTicketsViewModel
import com.smartbus.app.presentation.tracking.TrackingScreen
import com.smartbus.app.presentation.tracking.TrackingViewModel
import com.smartbus.app.presentation.points.PointsScreen
import com.smartbus.app.presentation.points.PointsViewModel
import com.smartbus.app.presentation.travelcredit.TravelCreditScreen
import com.smartbus.app.presentation.travelcredit.TravelCreditViewModel
import com.smartbus.app.presentation.profile.ProfileScreen
import com.smartbus.app.presentation.profile.ProfileViewModel
import com.smartbus.app.presentation.splash.SplashScreen
import com.smartbus.app.presentation.splash.SplashViewModel
import com.smartbus.app.presentation.welcome.WelcomeScreen
import com.smartbus.app.presentation.auth.login.LoginScreen
import com.smartbus.app.presentation.auth.login.LoginViewModel
import com.smartbus.app.presentation.auth.register.RegisterScreen
import com.smartbus.app.presentation.auth.register.RegisterViewModel
import com.smartbus.app.presentation.nfc.NFCPaymentScreen
import com.smartbus.app.presentation.nfc.NFCPaymentViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember

import com.smartbus.app.presentation.auth.register.RegistrationStep2Screen
import com.smartbus.app.presentation.auth.register.RegistrationStep3Screen
import com.smartbus.app.presentation.auth.register.RegistrationLoadingScreen

@Composable
fun AppNavGraph(
    navController: NavHostController
) {
    val registerViewModel: RegisterViewModel = viewModel()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                viewModel = SplashViewModel(),
                onNavigateNext = { isActive ->
                    if (isActive) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onGoogleSignIn = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = LoginViewModel(),
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.RegisterStep2.route)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.RegisterStep2.route) {
            RegistrationStep2Screen(
                viewModel = registerViewModel,
                onNext = {
                    navController.navigate(Screen.RegisterStep3.route)
                },
                onSkip = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.RegisterStep3.route) {
            RegistrationStep3Screen(
                viewModel = registerViewModel,
                onFinish = {
                    navController.navigate(Screen.RegisterLoading.route)
                },
                onSkip = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.RegisterLoading.route) {
            RegistrationLoadingScreen(
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = HomeViewModel(),
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToTickets = { navController.navigate(Screen.MyTickets.route) },
                onNavigateToTracking = { navController.navigate(Screen.Tracking.route) },
                onNavigateToPoints = { navController.navigate(Screen.Points.route) },
                onNavigateToNFC = { navController.navigate(Screen.NFCPayment.route) }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = SearchViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onResultSelected = { navController.navigate(Screen.SeatSelection.route) }
            )
        }

        composable(Screen.SeatSelection.route) {
            SeatSelectionScreen(
                viewModel = SeatSelectionViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onContinueToPayment = { navController.navigate(Screen.Payment.route) }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                viewModel = PaymentViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onTrackBus = { navController.navigate(Screen.Tracking.route) },
                onPaymentSuccess = { 
                    navController.navigate(Screen.MyTickets.route) {
                        popUpTo(Screen.Search.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.MyTickets.route) {
            MyTicketsScreen(
                viewModel = MyTicketsViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onTrackBus = { p -> navController.navigate(Screen.Tracking.route) }
            )
        }

        composable(Screen.Tracking.route) {
            TrackingScreen(
                viewModel = TrackingViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.NFCPayment.route) {
            NFCPaymentScreen(
                viewModel = NFCPaymentViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Points.route) {
            PointsScreen(
                viewModel = PointsViewModel(),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TravelCredit.route) {
            TravelCreditScreen(
                viewModel = TravelCreditViewModel(),
                onNavigateBack = { navController.popBackStack() },
                onUseCredit = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = ProfileViewModel(),
                onLogout = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
