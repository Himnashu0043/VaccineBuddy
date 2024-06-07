package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.CityList

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CityList.City


data class CityListRes(
    val cities: List<City>,
    val code: Int,
    val message: String
)