package com.smartbus.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Search : Screen("search")
    object SeatSelection : Screen("seat_selection")
    object Payment : Screen("payment")
    object MyTickets : Screen("my_tickets")
    object Tracking : Screen("tracking")
    object NFCPayment : Screen("nfc_payment")
    object Points : Screen("points")
    object TravelCredit : Screen("travel_credit")
    object Profile : Screen("profile")
}
