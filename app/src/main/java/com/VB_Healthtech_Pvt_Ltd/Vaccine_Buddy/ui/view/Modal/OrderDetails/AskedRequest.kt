package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails

data class AskedRequest(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val doseAgeDays: List<String>,
    val packageId: String,
    val price: String,
    val result: List<Result>,
    val status: String,
    val updatedAt: String,
    val vaccineName: String,
    val vaccine_id: String
)