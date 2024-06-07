package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage

data class Item(
    val brand: String,
    val description: String,
    val dose: List<Dose>,
    val homeVaccinationFee: String,
    val id: String,
    val name: String,
    val price: String,
    var isSelectedStatus:Boolean=false,
)