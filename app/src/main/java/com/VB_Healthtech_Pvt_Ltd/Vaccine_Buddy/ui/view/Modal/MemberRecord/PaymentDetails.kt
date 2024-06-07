package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord

data class PaymentDetails(
    val amount: String,
    val packageName: String,
    val payableAmount: String,
    val paymentType: String,
    val tax: String,
    val vaccineName: String
)