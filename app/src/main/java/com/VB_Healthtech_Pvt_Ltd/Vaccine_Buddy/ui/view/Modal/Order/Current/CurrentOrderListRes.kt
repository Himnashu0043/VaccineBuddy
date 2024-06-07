package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Current

data class CurrentOrderListRes(
    val code: Int,
    val message: String,
    val orderData: List<OrderData>
)