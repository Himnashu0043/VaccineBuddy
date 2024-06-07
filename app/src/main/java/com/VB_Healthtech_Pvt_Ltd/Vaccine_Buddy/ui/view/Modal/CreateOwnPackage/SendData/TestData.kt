package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.SendData

data class TestData(
    var vaccine_id: String,
    var vaccineName: String,
    var price: String,
    var description: String,
    var dose: ArrayList<Any>,
    var doseAgeDays: ArrayList<Any>
)
