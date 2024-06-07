package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails

import java.io.Serializable

data class AskedRecord(
    val __v: Int,
    val _id: String,
    val ageOfBaby: String,
    val appointmentId: String,
    val batchNumber: String,
    val categoryName: String,
    val certificate: String,
    val completeDate: String,
    val completeDosage: Int,
    val createdAt: String,
    val doseInfo: List<DoseInfoX>,
    val includedVaccine: Int,
    val inclusion: List<Inclusion>,
    val itemType: String,
    val memberId: String,
    val nurseName: String,
    val packageId: String,
    val packageName: String,
    val pendingDosage: Int,
    val price: String,
    val slot: List<Slot>,
    val slotDate: String,
    val slotDay: String,
    val status: String,
    val subCategoryName: String,
    val updatedAt: String,
    val user_id: String,
    val nurseId:String?,
    val certificateLink:String?,
    val vaccines: List<String>,

):Serializable