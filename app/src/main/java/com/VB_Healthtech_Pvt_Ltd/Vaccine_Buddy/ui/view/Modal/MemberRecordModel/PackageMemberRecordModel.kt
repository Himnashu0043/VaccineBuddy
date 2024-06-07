package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecordModel

import java.io.Serializable

class PackageMemberRecordModel : Serializable {
    var ageofbaby: String? = null
    var statusObj: StatusMemberRecordModel? = null

    constructor(ageofbaby: String?,statusObj: StatusMemberRecordModel?) {
        this.ageofbaby = ageofbaby
        this.statusObj = statusObj
    }
}
