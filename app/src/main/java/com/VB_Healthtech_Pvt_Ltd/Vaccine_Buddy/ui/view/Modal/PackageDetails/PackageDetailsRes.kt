package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails

data class PackageDetailsRes(
    val askedPackage: AskedPackage,
    val askedRequests: List<AskedRequest>,
    val code: Int,
    val message: String
)