package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord

data class Inclusion(
    val _id: String,
    val description: String,
    val dose: List<String>,
    val price: String,
    val vaccineName: String,
    val vaccine_id: String
)