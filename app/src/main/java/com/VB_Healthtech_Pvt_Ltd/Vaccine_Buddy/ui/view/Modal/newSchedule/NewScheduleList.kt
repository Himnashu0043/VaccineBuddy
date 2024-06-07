package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.newSchedule

data class NewScheduleList(
    var code: Int,
    var message: String,
    var result: ArrayList<Result>
) {
    data class Result(
        var __v: Int,
        var _id: String,
        var adminId: String,
        var ageGroup: String,
        var createdAt: String,
        var gender: String,
        var status: String,
        var toDaysAge: Int,
        var updatedAt: String,
        var vaccine: List<Vaccine>,
        var vaccines: List<Vaccines>
    ) {
        data class Vaccine(
            var __v: Int,
            var _id: String,
            var adminId: String,
            var ageGroup: List<String>,
            var ballanceStock: String,
            var benifits: String,
            var createdAt: String,
            var description: String,
            var doseInfo: List<DoseInfo>,
            var doseRecord: List<DoseRecord>,
            var fromAge: String,
            var fromAgeDays: String,
            var homeVaccinationFee: String,
            var is_custom: Boolean,
            var noOfVaccination: String,
            var precautions: String,
            var price: String,
            var status: String,
            var toAge: String,
            var toAgeDays: String,
            var totalCost: String,
            var totalHomeVaccinationFee: String,
            var totalStock: String,
            var totalVaccineFee: String,
            var updatedAt: String,
            var usedStock: String,
            var vaccineAgeDays: List<Any>,
            var vaccineBrand: String,
            var vaccineImage: String,
            var vaccineName: String,
            var vaccine_number: String
        ) {
            data class DoseInfo(
                var _id: String,
                var doseNumber: String,
                var timePeriod: String
            )

            data class DoseRecord(
                var _id: String,
                var doseNumber: String,
                var timePeriod: String
            )
        }

        data class Vaccines(
            var _id: String,
            var vaccine_id: String
        )
    }
}