package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList


data class AskedData(
    val __v: Int,
    val _id: String,
    val addedBy: String,
    val addressType: String,
    val city: String,
    val contactPersonName: String,
    val createdAt: String,
    var defaultAddress: Boolean,
    val isoCode: String,
    val location: Location,
    val phoneNumber: String,
    val state: String,
    val status: String,
    val tower:String?,
    val flat_no:String?,
    val streetAddress: String,
    val updatedAt: String,
    val zipCode: String,
    var isSelected:Boolean
)