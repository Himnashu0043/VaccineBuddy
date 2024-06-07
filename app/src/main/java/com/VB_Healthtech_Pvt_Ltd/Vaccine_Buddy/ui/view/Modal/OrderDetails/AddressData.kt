package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails

import java.io.Serializable

data class AddressData(
    val _id: String,
    val addressType: String,
    val city: String,
    val contactPersonName: String,
    val phoneNumber: String,
    val state: String,
    val streetAddress: String,
    val zipCode: String
):Serializable