package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord

data class VaccineRecord(
    val __v: Int,
    val _id: String,
    val adminId: String,
    val ageGroup: List<String>,
    val ballanceStock: String,
    val benifits: String,
    val createdAt: String,
    val description: String,
    val doseInfo: List<DoseInfo>,
    val fromAge: String,
    val fromAgeDays: String,
    val homeVaccinationFee: String,
    val noOfVaccination: String,
    val precautions: String,
    val price: String,
    val status: String,
    val toAge: String,
    val toAgeDays: String,
    val totalCost: String,
    val totalHomeVaccinationFee: String,
    val totalStock: String,
    val totalVaccineFee: String,
    val updatedAt: String,
    val usedStock: String,
    val vaccineBrand: String,
    val vaccineImage: String,
    val vaccineName: String,
    val vaccine_number: String
)