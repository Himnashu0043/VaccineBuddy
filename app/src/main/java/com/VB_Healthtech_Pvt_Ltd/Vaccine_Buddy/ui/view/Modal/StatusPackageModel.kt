package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal

import java.io.Serializable

class StatusPackageModel : Serializable {
    var ageOfBaby: String? = null
    var status: String? = null
    var completeDate: String? = null
    var nurseName: String? = null
    var nurseId :String ? = null

    constructor(ageOfBaby: String?, status: String?, completeDate: String?, nurseName: String?,nurseId:String?) {
        this.ageOfBaby = ageOfBaby
        this.status = status
        this.completeDate = completeDate
        this.nurseName = nurseName
        this.nurseId
    }
}