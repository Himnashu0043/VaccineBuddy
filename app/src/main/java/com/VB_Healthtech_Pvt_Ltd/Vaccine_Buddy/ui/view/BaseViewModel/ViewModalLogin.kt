package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Abouts.AboutsRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddCreateOwnPackage.AddCreateOwnPackageRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMember.AddFamilyMemberRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.AddMemberList.AddFamilyMemberListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.Delete.DeleteMemberRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFamilyMember.UpdateFamilyMember.UpdateFamilyMemberRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddFavrate.AddFavRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.AddSecondAddress.AddSecondAddressRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.Delete.DeleteRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.AddressList.AddressListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.BannerList.BannerListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CategoryList.CategoryListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.CityList.CityListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.DefaultAddress.DefaultAdressRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.LoginRes.LoginResponse
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.StateList.StateListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Address.UpdateAddress.UpdateAddressRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Book_Schedule.BookScheduleRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CallSchedule.CallScheduleListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CancelOrder.CancelOrderRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.AddtoCartRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.CartList.CartListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.DeleteCart.DeleteCartRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CheckOutCart.CheckOutCartRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CommonModel.CommonModelRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CreateOwnPackage.CreateOwnPackageListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CustomPackage.CustomPackageListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.GlobalSearch.GlobalSearchRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.KidsGrowths.KidsGrowthsRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.MemberRecord.MemberRecordRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Offers.OffersListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Cancelled.CancelOrederListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Completed.CompleteOrderListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Order.Current.CurrentOrderListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OrderDetails.OrderDetailsRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.OtpEmail.OtpEmailRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.PackageDetailsRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageList.PackageListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RelatedPackage.RelatedPackageListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SetNotification.SetNotificationRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SignUp.SignUpRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SlotList.SlotListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCategoryList.SubcategoryListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCenter.SubCenterRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Testimonial.TestimonialListRes

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.UpdateProfile.UpdateProfileRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineDetails.VaccineDetailsRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.VaccineList.VaccineListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ViewProfile.ViewProfileRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishPackageList.WishPackageListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.WishVaccineList.WishVaccineListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CompanyList.CompanyListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.DoseForAge.DoseForAgeListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.GetUpNotification.GetUpNotificationRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.NotificationListRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.ParticularDiscount.GetDiscountRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.RatingReview.RatingReviewRes
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.newSchedule.NewScheduleList
import com.bountybunch.Managers.BaseManager.BaseViewModel
import com.catalyist.helper.CustomLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ViewModalLogin : BaseViewModel() {
    var responseLogin = MutableLiveData<LoginResponse?>()
    var responseViewProfile = MutableLiveData<ViewProfileRes?>()
    var responseCategoryList = MutableLiveData<CategoryListRes?>()
    var responsePackageList = MutableLiveData<PackageListRes?>()
    var responseSubCategoryPackageList = MutableLiveData<PackageListRes?>()
    var responseVaccineList = MutableLiveData<VaccineListRes?>()
    var responseWishPackageList = MutableLiveData<WishPackageListRes?>()
    var responseWishVaccineList = MutableLiveData<WishVaccineListRes?>()
    var responseBannerList = MutableLiveData<BannerListRes?>()
    var responseSubcategoryList = MutableLiveData<SubcategoryListRes?>()
    var responseUpdateProfile = MutableLiveData<UpdateProfileRes?>()
    var responseStateList = MutableLiveData<StateListRes?>()
    var responseCityList = MutableLiveData<CityListRes?>()
    var responseAddAddress = MutableLiveData<AddSecondAddressRes?>()
    var responseAddressList = MutableLiveData<AddressListRes?>()
    var responseDelete = MutableLiveData<DeleteRes?>()
    var responseUpdateAddress = MutableLiveData<UpdateAddressRes?>()
    var responseDefaultAddress = MutableLiveData<DefaultAdressRes?>()
    var responseAddFamilyMember = MutableLiveData<AddFamilyMemberRes?>()
    var responseFamilyMemberList = MutableLiveData<AddFamilyMemberListRes?>()
    var responseDeleteFamilyMember = MutableLiveData<DeleteMemberRes?>()
    var responseUpdateFamilyMember = MutableLiveData<UpdateFamilyMemberRes?>()
    var responsePackageDetails = MutableLiveData<PackageDetailsRes?>()
    var responseVaccineDetails = MutableLiveData<VaccineDetailsRes?>()
    var responseAbouts = MutableLiveData<AboutsRes?>()
    var responseAddtoCart = MutableLiveData<AddtoCartRes?>()
    var responseCartList = MutableLiveData<CartListRes?>()
    var responseDeleteCart = MutableLiveData<DeleteCartRes?>()
    var responseSignup = MutableLiveData<SignUpRes?>()
    var responseCommonModel = MutableLiveData<CommonModelRes?>()
    var responseOtpEmail = MutableLiveData<OtpEmailRes?>()
    var responseOtpVerify = MutableLiveData<CommonModelRes?>()
    var responseSetPassword = MutableLiveData<CommonModelRes?>()
    var responseSlotList = MutableLiveData<SlotListRes?>()
    var responseCallScheduleList = MutableLiveData<CallScheduleListRes?>()
    var responseRelatedPackageList = MutableLiveData<RelatedPackageListRes?>()
    var responseForgotPassword = MutableLiveData<CommonModelRes?>()
    var responseCheckOutCart = MutableLiveData<CheckOutCartRes?>()
    var responseBookSchedule = MutableLiveData<BookScheduleRes?>()
    var responseOrder = MutableLiveData<CurrentOrderListRes?>()
    var responseOrderCompleted = MutableLiveData<CompleteOrderListRes>()
    var responseOrderCancel = MutableLiveData<CancelOrederListRes>()
    var responseTestimonialList = MutableLiveData<TestimonialListRes?>()
    var responseGlobalSearch = MutableLiveData<GlobalSearchRes?>()
    var responseOrderDetails = MutableLiveData<OrderDetailsRes?>()
    var responseAddFav = MutableLiveData<AddFavRes?>()
    var responseCancelOrder = MutableLiveData<CancelOrderRes?>()
    var responseChangePassword = MutableLiveData<CommonModelRes?>()
    var responseSetNotification = MutableLiveData<SetNotificationRes?>()
    var responseMemberRecord = MutableLiveData<MemberRecordRes?>()
    var responseKidsGrowthsRecord = MutableLiveData<KidsGrowthsRes?>()
    var responseCustomePackageList = MutableLiveData<CustomPackageListRes?>()
    var responseOffersList = MutableLiveData<OffersListRes?>()
    var responseCreateOwnPackageList = MutableLiveData<CreateOwnPackageListRes?>()
    var responseAddCreateOwnPackage = MutableLiveData<AddCreateOwnPackageRes?>()
    var responseSubCenter = MutableLiveData<SubCenterRes?>()
    var responseNotification = MutableLiveData<NotificationListRes?>()
    var responseDoseForAge = MutableLiveData<DoseForAgeListRes?>()
    var responseGetUpNotification = MutableLiveData<GetUpNotificationRes?>()
    var responseCompanyList = MutableLiveData<CompanyListRes?>()
    var responseGetDiscount = MutableLiveData<GetDiscountRes?>()
    var responseRatingReview = MutableLiveData<RatingReviewRes?>()
    var responseNewSchedule = MutableLiveData<NewScheduleList?>()
    var error = MutableLiveData<Throwable>()

    @SuppressLint("CheckResult")
    fun userLogin(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.userLogin(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseLogin.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onViewProfile(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onViewProfile(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseViewProfile.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCategoryList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onCategoryList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCategoryList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onPackageList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onPackageList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responsePackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onPackageSubCategoryList(context: Context, subCategoryId: String, type: String) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onPackageSubCategoryList(
            PrefrencesHelper.setSaveToken(context),
            subCategoryId,
            type
        )
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSubCategoryPackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onNewPackageSubCategoryList(context: Context, subCategoryId: String) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onNewPackageSubCategoryList(
            PrefrencesHelper.setSaveToken(context),
            subCategoryId
        )
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSubCategoryPackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onVaccineList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onVaccineList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseVaccineList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    @SuppressLint("CheckResult")
    fun onWishPackageList(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onWishPacakgeList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseWishPackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    @SuppressLint("CheckResult")
    fun onWishVaccineList(context: Context, params: HashMap<String, Any>) {
        //  CustomLoader.showLoader(context as Activity)
        apiInterface.onWishVaccineList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseWishVaccineList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onBannerList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onBannerList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseBannerList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onSubcategoryList(context: Context, categoryId: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onSubcategoryList(PrefrencesHelper.setSaveToken(context), categoryId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSubcategoryList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ///////
    @SuppressLint("CheckResult")
    fun onUpdateProfile(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onUpdateProfile(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseUpdateProfile.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onStateList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onStateList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseStateList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCityList(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onCityList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCityList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onAddAddress(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAddAddress(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAddAddress.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onAddresseList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAddressList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAddressList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onDelete(context: Context, addressId: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onDelete(PrefrencesHelper.setSaveToken(context), addressId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseDelete.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onUpdateAddress(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onUpdateAddress(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseUpdateAddress.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onDefaultAddress(context: Context, params: HashMap<String, Any>) {
        //CustomLoader.showLoader(context as Activity)
        apiInterface.onDefaultAddress(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseDefaultAddress.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onAddFamilyMember(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAddFamilyMember(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAddFamilyMember.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onFamilyMemberList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onFamilyMemberList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseFamilyMemberList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onDeleteFamilyMember(context: Context, memberId: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onDeleteFamilyMember(PrefrencesHelper.setSaveToken(context), memberId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseDeleteFamilyMember.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    ////////////////////////
    @SuppressLint("CheckResult")
    fun onUpdateFamilyMember(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onUpdateFamilyMember(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseUpdateFamilyMember.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onPackageDetails(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onPackageDetails(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responsePackageDetails.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    ////////////////////////
    @SuppressLint("CheckResult")
    fun onVaccineDetails(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onVaccineDetails(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseVaccineDetails.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }
    ////////////////////////

    @SuppressLint("CheckResult")
    fun onAbout(context: Context, type: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAbout(type)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAbouts.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    ////////////////////////
    @SuppressLint("CheckResult")
    fun onAddtoCart(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onAddtoCart(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAddtoCart.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCartList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCartList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCartList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onDeleteCart(context: Context, params: HashMap<String, Any>) {
        //CustomLoader.showLoader(context as Activity)
        apiInterface.onDeleteCart(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseDeleteCart.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onSignup(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onSignup(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSignup.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////
    @SuppressLint("CheckResult")
    fun onCheckUser(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCheckUser(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseCommonModel.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////
    @SuppressLint("CheckResult")
    fun onOtpEmail(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onOtpEmail(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOtpEmail.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    @SuppressLint("CheckResult")
    fun onOtpVerify(context: Context, params: HashMap<String, Any>) {
        //CustomLoader.showLoader(context as Activity)
        apiInterface.onOtpVerify(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOtpVerify.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    @SuppressLint("CheckResult")
    fun onSetPassword(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onSetPassword(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseSetPassword.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onSlotList(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onSlotList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseSlotList.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCallScheduleList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCallScheduleList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCallScheduleList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onRelatedPackageList(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onRelatedPackageList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseRelatedPackageList.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onForgotPassword(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onForgotPassword(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseForgotPassword.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCheckOut(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onCheckOut(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseCheckOutCart.postValue(it)
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onBookSchedule(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onBookSchedule(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseBookSchedule.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onOrderList(context: Context, orderType: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onOrderList(PrefrencesHelper.setSaveToken(context), orderType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOrder.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onOrderCompletedList(context: Context, orderType: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onOrderCompletedList(PrefrencesHelper.setSaveToken(context), orderType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOrderCompleted.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onOrderCancelList(context: Context, orderType: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onOrderCancelList(PrefrencesHelper.setSaveToken(context), orderType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOrderCancel.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onTestimonialList(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onTestimonialList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseTestimonialList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onGlobalSearch(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onGlobalSearch(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseGlobalSearch.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onOrderDetails(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onOrderDetails(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseOrderDetails.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////
    @SuppressLint("CheckResult")
    fun onAddFav(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAddFav(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseAddFav.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////
    @SuppressLint("CheckResult")
    fun onCancelOrder(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCancelOrder(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseCancelOrder.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////
    @SuppressLint("CheckResult")
    fun onChangePassword(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onChangePassword(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseChangePassword.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onSetNotification(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onSetNotification(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSetNotification.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////
    @SuppressLint("CheckResult")
    fun onMemberRecord(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onMemberRecord(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseMemberRecord.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////
    @SuppressLint("CheckResult")
    fun onKidsGrowthsRecord(context: Context, params: HashMap<String, Any>) {
        //CustomLoader.showLoader(context as Activity)
        apiInterface.onKidsGrowthsRecord(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                CustomLoader.hideLoader()
                this.responseKidsGrowthsRecord.value = it
            },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })

    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onCustomePackageList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCustomePackageList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCustomePackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onOffersList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onOffersList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseOffersList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }


    @SuppressLint("CheckResult")
    fun onCreateOwnPackageList(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onCreateOwnPackageList(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCreateOwnPackageList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    @SuppressLint("CheckResult")
    fun onAddCreateOwnPackage(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onAddCreateOwnPackage(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseAddCreateOwnPackage.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ///////////
    @SuppressLint("CheckResult")
    fun onSubCenter(context: Context, params: HashMap<String, Any>) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onSubCenter(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseSubCenter.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////
    @SuppressLint("CheckResult")
    fun onNotificationList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onNotificationList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseNotification.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ///////////
    @SuppressLint("CheckResult")
    fun onDoseForAge(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onDoseForAge(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseDoseForAge.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    @SuppressLint("CheckResult")
    fun onGetUpNotificationRes(context: Context) {
        // CustomLoader.showLoader(context as Activity)
        apiInterface.onGetUpNotificationRes(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseGetUpNotification.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////

    @SuppressLint("CheckResult")
    fun onCompanyList(context: Context) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onCompanyList(PrefrencesHelper.setSaveToken(context))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseCompanyList.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ////////////////////////

    @SuppressLint("CheckResult")
    fun onGetDiscountList(context: Context, companyId: String, empId: String, phoneNumber: String) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onGetDiscountList(
            PrefrencesHelper.setSaveToken(context),
            companyId,
            empId,
            phoneNumber
        )
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseGetDiscount.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ///////
    @SuppressLint("CheckResult")
    fun onNewSchedulrList(context: Context, toAgeDays: Int, gender: String) {
        //CustomLoader.showLoader(context as Activity)
        apiInterface.onNewScheduleList(PrefrencesHelper.setSaveToken(context), toAgeDays, gender)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseNewSchedule.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

    ///////////
    @SuppressLint("CheckResult")
    fun onRatingReview(context: Context, params: HashMap<String, Any>) {
        CustomLoader.showLoader(context as Activity)
        apiInterface.onRatingReview(PrefrencesHelper.setSaveToken(context), params)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    CustomLoader.hideLoader()
                    this.responseRatingReview.value = it
                },
                {
                    CustomLoader.hideLoader()
                    error.value = it
                })
    }

}

