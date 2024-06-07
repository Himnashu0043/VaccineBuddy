package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification

data class AskedNotification(
    val __v: Int,
    val _id: String,
    val content: String,
    val createdAt: String,
    val doseInfo: ArrayList<DoseInfo>,
    val memberId: String,
    val notificationType: String,
    val packageId: String,
    val readStatus: Boolean,
    val status: String,
    val title: String,
    val updatedAt: String,
    val user_id: String,
    val vaccineId: String
)