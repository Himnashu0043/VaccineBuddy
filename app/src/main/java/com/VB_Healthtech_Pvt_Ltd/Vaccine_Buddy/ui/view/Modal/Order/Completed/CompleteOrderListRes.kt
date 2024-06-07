package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed

data class CompleteOrderListRes(
    val code: Int,
    val message: String,
    val orderData: List<OrderData>
)