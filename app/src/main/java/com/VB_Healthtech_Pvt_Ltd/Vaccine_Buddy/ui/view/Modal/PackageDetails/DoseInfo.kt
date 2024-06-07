package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails

data class DoseInfo(
    val _id: String,
    val description: String,
    val dose: List<String>,
    val price: Int,
    val doseAgeDays: List<String>,
    val vaccineName: String,
    val vaccine_id: String
)