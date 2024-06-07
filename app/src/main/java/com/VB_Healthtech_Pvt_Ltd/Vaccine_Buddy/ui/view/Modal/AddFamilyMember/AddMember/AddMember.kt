package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMember

data class AddMember(
    val __v: Int,
    val _id: String,
    val addedBy: String,
    val anyReaction: String? = null,
    val bithHistory: String? = null,
    val createdAt: String,
    val deliveryPeriod: String? = null,
    val deliveryType: String? = null,
    val dob: String,
    val fullName: String,
    val gender: String,
    val medicalCondition: String,
    val medicalDisorder: String? = null,
    val memberStatus: String,
    val memberType: String,
    val nicuDetails: String? = null,
    val relation: String,
    val seizuresAtBirth: String? = null,
    val updatedAt: String
)