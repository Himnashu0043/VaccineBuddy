package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList

data class Slot(
    val _id: String,
    val from: String,
    val status: String,
    val to: String,
    val slotStatus:String,
    var isSelected:Boolean
)