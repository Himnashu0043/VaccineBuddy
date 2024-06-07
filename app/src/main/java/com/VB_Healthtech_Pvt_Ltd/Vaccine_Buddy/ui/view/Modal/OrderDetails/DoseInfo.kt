package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails

import java.io.Serializable

data class DoseInfo(
    val _id: String,
    val description: String,
    val dose: List<String>,
    val doseAgeDays: List<String>,
    val price: String,
    val vaccineName: String,
    val vaccine_id: String,
    val doseNumber: String,
    val timePeriod: String
) : Serializable