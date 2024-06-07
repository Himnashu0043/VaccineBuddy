package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal

import java.io.Serializable

data class MoreDoseDetailsModel(
    val does: String,
    val vaccineName: String,
    val price: String,
    val description: String
):Serializable