package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord.test

data class Test(
    var askedRequests: List<AskedRequest>,
    var code: Int,
    var message: String
) {
    data class AskedRequest(
        var __v: Int,
        var _id: String,
        var addedBy: String,
        var addedFor: String,
        var addressData: List<AddressData>,
        var appointmentId: String,
        var approvedStatus: Boolean,
        var cartId: String,
        var categoryName: String,
        var completedDose: Int,
        var createdAt: String,
        var customer_number: String,
        var daysArray: List<String>,
        var description: String,
        var discount: String,
        var discountAmount: String,
        var discountStatus: Boolean,
        var doseInfo: List<Any>,
        var doseRecord: List<Any>,
        var gstAmount: String,
        var homeVaccineFee: String,
        var inclusiveVaccine: Int,
        var invoiceLink: String,
        var invoiceStatus: Boolean,
        var is_custom: Boolean,
        var itemData: List<Any>,
        var itemId: String,
        var itemImage: String,
        var itemName: String,
        var itemPrice: String,
        var itemStatus: String,
        var noOfDose: String,
        var noOfPackageDose: String,
        var offerPrice: String,
        var order_number: String,
        var order_type: String,
        var packagePrice: String,
        var packageRecord: List<PackageRecord>,
        var paymentDetails: PaymentDetails,
        var paymentStatus: Boolean,
        var paymentType: String,
        var pendingDose: Int,
        var record: List<Record>,
        var slot: List<Slot>,
        var slotDate: String,
        var slotDay: String,
        var status: String,
        var subCategoryName: String,
        var updatedAt: String,
        var userData: List<UserData>,
        var userName: String,
        var vaccineRecord: List<Any>,
        var viewStatus: Boolean,
        var walletAmount: String,
        var withoutGstAmount: String
    ) {
        data class AddressData(
            var _id: String,
            var addressType: String,
            var city: String,
            var contactPersonName: String,
            var phoneNumber: String,
            var state: String,
            var streetAddress: String,
            var zipCode: String
        )

        data class PackageRecord(
            var __v: Int,
            var _id: String,
            var adminId: String,
            var ageGroup: List<String>,
            var ballanceStock: String,
            var categoryId: String,
            var categoryName: String,
            var consultingFee: String,
            var createdAt: String,
            var description: String,
            var discountPrice: String,
            var doseInfo: List<DoseInfo>,
            var homeVaccinationFee: String,
            var includedConsultant: List<String>,
            var is_purchase: Boolean,
            var offerPrice: String,
            var packageImage: String,
            var packageName: String,
            var packageType: String,
            var package_number: String,
            var package_rank: Int,
            var price: String,
            var ratingcount: List<Any>,
            var status: String,
            var subCategoryId: String,
            var subCategoryName: String,
            var totalConsultantFee: String,
            var totalPrice: String,
            var totalStock: String,
            var totalVaccinationFee: String,
            var updatedAt: String,
            var usedStock: String
        ) {
            data class DoseInfo(
                var _id: String,
                var description: String,
                var dose: List<String>,
                var doseAgeDays: List<String>,
                var price: String,
                var vaccineName: String,
                var vaccine_id: String
            )
        }

        data class PaymentDetails(
            var amount: String,
            var packageName: String,
            var payableAmount: String,
            var paymentType: String,
            var tax: String,
            var vaccineName: String
        )

        data class Record(
            var __v: Int,
            var _id: String,
            var ageOfBaby: String,
            var appointmentId: String,
            var batchNumber: String,
            var categoryName: String,
            var certificate: String,
            var completeDate: String,
            var completeDosage: Int,
            var createdAt: String,
            var doseInfo: List<Any>,
            var includedVaccine: Int,
            var inclusion: List<Inclusion>,
            var itemType: String,
            var memberId: String,
            var nurseName: String,
            var orderId: String,
            var packageId: String,
            var packageName: String,
            var pendingDosage: Int,
            var price: String,
            var slot: List<Slot>,
            var slotDate: String,
            var slotDay: String,
            var status: String,
            var subCategoryName: String,
            var updatedAt: String,
            var user_id: String,
            var vaccines: List<String>
        ) {
            data class Inclusion(
                var _id: String,
                var description: String,
                var dose: List<String>,
                var price: String,
                var vaccineName: String,
                var vaccine_id: String
            )

            data class Slot(
                var _id: String,
                var from: String,
                var status: String,
                var to: String
            )
        }

        data class Slot(
            var _id: String,
            var from: String,
            var status: String,
            var to: String
        )

        data class UserData(
            var _id: String,
            var anyReaction: String,
            var bithHistory: String,
            var deliveryPeriod: String,
            var deliveryType: String,
            var dob: String,
            var fullName: String,
            var gender: String,
            var medicalCondition: String,
            var medicalDisorder: String,
            var memberType: String,
            var nicuDetails: String,
            var relation: String,
            var seizuresAtBirth: String,
            var uploadVaccinationChart: String
        )
    }
}