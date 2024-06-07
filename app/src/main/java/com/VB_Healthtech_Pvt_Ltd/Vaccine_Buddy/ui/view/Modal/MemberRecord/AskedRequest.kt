package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord

data class AskedRequest(
    val __v: Int,
    val _id: String,
    val addedBy: String,
    val addedFor: String,
    val addressData: List<AddressData>,
    val approvedStatus: Boolean,
    val assignedCenter: String,
    val assignedNurse: String,
    val cartId: String,
    val categoryName: String,
    val completedDose: Int,
    val createdAt: String,
    val customer_number: String,
    val daysArray: List<String>,
    val description: String,
    val doseInfo: List<Any>,
    val gstAmount: String,
    val homeVaccineFee: String,
    val inclusiveVaccine: Int,
    val itemData: List<Any>,
    val itemId: String,
    val itemImage: String,
    val itemName: String,
    val itemPrice: String,
    val itemStatus: String,
    val noOfDose: String,
    val noOfPackageDose: String,
    val nurseId: String,
    val order_number: String,
    val order_type: String,
    val packagePrice: String,
    val packageRecord: List<Any>,
    val paymentDetails: PaymentDetails,
    val pendingDose: Int,
    val record: ArrayList<Record>,
    val slot: List<SlotX>,
    val slotDate: String,
    val slotDay: String,
    val status: String,
    val subCategoryName: String,
    val updatedAt: String,
    val userData: List<UserData>,
    val userName: String,
    val vaccineRecord: List<Any>,
    val withoutGstAmount: String
)