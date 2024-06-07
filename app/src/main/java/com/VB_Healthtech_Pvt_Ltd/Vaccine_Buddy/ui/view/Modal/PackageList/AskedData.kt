package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList

data class AskedData(
    val _id: String,
    val ageGroup: List<String>,
    val ballanceStock: String,
    val categoryId: String,
    val categoryName: String,
    val completedDose: Int,
    val consultingFee: String,
    val description: String,
    val doseInfo: List<DoseInfo>,
    val homeVaccinationFee: String,
    val includedConsultant: List<String>,
    var isWishList: Boolean,
    val offerPrice: String,
    val packageImage: String,
    val packageName: String,
    val price: String,
    val status: String,
    val subCategoryId: String,
    val subCategoryName: String? = null,
    val totalConsultantFee: String,
    val totalPrice: String,
    val totalStock: String,
    val totalVaccinationFee: String,
    val usedStock: String
)