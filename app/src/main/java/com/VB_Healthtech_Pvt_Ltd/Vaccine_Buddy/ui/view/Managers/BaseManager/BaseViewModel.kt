package com.bountybunch.Managers.BaseManager

import ApiInterface
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper.ApiClient
import androidx.lifecycle.ViewModel

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Services.BaseUrlWithRequest.BASE_URL


abstract class BaseViewModel : ViewModel(){


    val apiInterface : ApiInterface by lazy { ApiClient.getApiClient(BASE_URL) }
   // val apiGoogleInterface : ApiInterface by lazy { ApiClient.getApiClient(ParamEnum.GOOGLE_MAP_BASE_URL.theValue() as String) }
}