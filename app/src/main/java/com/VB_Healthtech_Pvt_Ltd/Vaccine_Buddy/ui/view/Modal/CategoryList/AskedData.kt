package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CategoryList

//data class AskedData(
//    val __v: Int,
//    val _id: String,
//    val adminId: String,
//    val categoryName: String,
//    val categoryType: String,
//    val category_number: String,
//    val createdAt: String,
//    val image: String,
//    val status: String,
//    val updatedAt: String
//)
data class AskedData(
    val __v: Int,
    val _id: String,
    val adminId: String,
    val categoryName: String,
    val categoryType: String,
    val category_number: String,
    val createdAt: String,
    val image: String,
    val status: String,
    val subCatName: List<String>,
    val subCategoryCount: List<String>,
    val updatedAt: String
)