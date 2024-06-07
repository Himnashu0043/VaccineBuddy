package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails

data class OrderDetailsRes(
    val askedData: AskedData,
    val askedItem: AskedItem,
    val askedRecord: ArrayList<AskedRecord>,
    val askedRequests: List<AskedRequest>,
    val code: Int,
    val message: String,
    val is_nurse: Boolean? = null,
)