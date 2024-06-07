package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal

import java.io.Serializable

class OrderDetailsPackageDoesModel : Serializable {
    var dose: String? = null
    var vaccineName: String? = null
    var statusObj: StatusPackageModel? = null

    constructor(dose: String?, vaccineName: String?, statusObj: StatusPackageModel?) {
        this.dose = dose
        this.vaccineName = vaccineName
        this.statusObj = statusObj
    }
}