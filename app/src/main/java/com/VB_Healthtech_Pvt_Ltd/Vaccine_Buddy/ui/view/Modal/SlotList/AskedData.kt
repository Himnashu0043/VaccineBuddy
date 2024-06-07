package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList

data class AskedData(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val day: String,
    val date: String,
    val slot: List<Slot>,
    val slotData: List<Any>,
    val slotFor: String,
    val status: String,
    val updatedAt: String,
    var isSelected: Boolean
)