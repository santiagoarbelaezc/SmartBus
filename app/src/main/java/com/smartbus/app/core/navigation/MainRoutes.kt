package com.smartbus.app.core.navigation

import kotlinx.serialization.Serializable

/**
 * Define todas las rutas de la aplicación de forma tipada y serializable.
 */
sealed class MainRoutes {
    @Serializable
    data object Splash : MainRoutes()

    @Serializable
    data object Welcome : MainRoutes()

    @Serializable
    data object Login : MainRoutes()

    @Serializable
    data object ForgotPassword : MainRoutes()

    @Serializable
    data object ResetPasswordSent : MainRoutes()

    @Serializable
    data object Register : MainRoutes()

    @Serializable
    data object RegisterStep2 : MainRoutes()

    @Serializable
    data object RegisterStep3 : MainRoutes()

    @Serializable
    data object RegisterLoading : MainRoutes()

    @Serializable
    data object Home : MainRoutes()

    @Serializable
    data object Search : MainRoutes()

    @Serializable
    data class SeatSelection(val tripId: String) : MainRoutes()

    @Serializable
    data class Payment(val bookingId: String) : MainRoutes()

    @Serializable
    data object MyTickets : MainRoutes()

    @Serializable
    data class Tracking(val ticketId: String) : MainRoutes()

    @Serializable
    data object NFCPayment : MainRoutes()

    @Serializable
    data object Points : MainRoutes()

    @Serializable
    data object TravelCredit : MainRoutes()

    @Serializable
    data object Notifications : MainRoutes()

    @Serializable
    data object PaymentMethods : MainRoutes()

    @Serializable
    data object HelpSupport : MainRoutes()

    @Serializable
    data object Terms : MainRoutes()

    @Serializable
    data object Profile : MainRoutes()
}
