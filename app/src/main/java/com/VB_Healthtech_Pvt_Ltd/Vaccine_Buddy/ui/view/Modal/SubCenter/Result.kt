package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCenter

data class Result(
    val __v: Int,
    val _id: String,
    val address: String,
    val centerName: String,
    val center_number: String,
    val city: String,
    val concernedPersonName: String,
    val country: String,
    val createdAt: String,
    val dist: Dist,
    val emailAddress: String,
    val lat: String,
    val location: LocationX,
    val long: String,
    val is_cod: Boolean? = null,
    val pinCode: String,
    val state: String,
    val status: String,
    val updatedAt: String
)
