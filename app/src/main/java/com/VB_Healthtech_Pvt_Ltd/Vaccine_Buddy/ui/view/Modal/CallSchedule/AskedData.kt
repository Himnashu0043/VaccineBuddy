package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CallSchedule

data class AskedData(
    val __v: Int,
    val _id: String,
    val addedBy: String,
    val addedFor: String,
    val callStatus: String,
    val confirmStatus: String,
    val counsel_number: String,
    val createdAt: String,
    val customer_number: String,
    val memberType: String,
    val preferredDate: String,
    val requestedDate: String,
    val sheduledDate: String,
    val sheduledSlot: List<SheduledSlot>,
    val slot: List<Slot>,
    val slotDay: String,
    val slotId: String,
    val status: String,
    val updatedAt: String,
    val userData: List<UserData>,
    val userName: String
)