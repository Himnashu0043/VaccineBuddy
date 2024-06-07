package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.SendData

data class CreateCustomePackageData(
    var packageName:String,
    var price:String,
    var description:String,
    var homeVaccinationFee:String,
    var image:String,
    var totalVaccinationFee:String,
    var totalPrice:String,
    var doseInfo:ArrayList<DoseInfoC>
)
