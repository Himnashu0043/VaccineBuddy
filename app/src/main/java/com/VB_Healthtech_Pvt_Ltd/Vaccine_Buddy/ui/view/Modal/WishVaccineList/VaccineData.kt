package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList

data class VaccineData(
    val __v: Int,
    val _id: String,
    val adminId: String,
    val ageGroup: List<String>,
    val ballanceStock: String,
    val benifits: String,
    val categoryId: String,
    val categoryName: String,
    val createdAt: String,
    val description: String,
    val doseInfo: List<DoseInfo>,
    val precautions: String,
    val price: String,
    val status: String,
    val subCategoryId: String,
    val subCategoryName: String,
    val totalStock: String,
    val updatedAt: String,
    val usedStock: String,
    val vaccineBrand: String,
    val vaccineImage: String,
    val vaccineName: String,
    val vaccine_number: String,
    val noOfVaccination:String
)