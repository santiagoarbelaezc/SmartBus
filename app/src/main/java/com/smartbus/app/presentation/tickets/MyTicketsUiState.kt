package com.smartbus.app.presentation.tickets

data class MyTicketsUiState(
    val activeTickets: List<TicketInfo> = listOf(
        TicketInfo("1", "Medellín", "Bogotá", "15 Abr, 2026", "08:30 AM", "12", "Confirmado", "Bolivariano", com.smartbus.app.R.raw.busbolivariano),
        TicketInfo("2", "Bogotá", "Cali", "20 Abr, 2026", "10:00 PM", "A4", "Pendiente", "Flota Occidental", com.smartbus.app.R.raw.busflota)
    ),
    val historyTickets: List<TicketInfo> = listOf(
        TicketInfo("3", "Cali", "Medellín", "01 Mar, 2026", "02:00 PM", "05", "Finalizado", "Flota Magdalena", com.smartbus.app.R.raw.busgacela)
    )
)

data class TicketInfo(
    val id: String,
    val origin: String,
    val destination: String,
    val date: String,
    val time: String,
    val seat: String,
    val status: String,
    val companyName: String = "SmartBus",
    val companyImageRes: Int = com.smartbus.app.R.raw.smartbus
)
