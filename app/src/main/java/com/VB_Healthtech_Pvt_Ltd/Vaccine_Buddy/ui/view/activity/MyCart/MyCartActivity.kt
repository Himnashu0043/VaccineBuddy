package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MyCart


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityMyCartBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.MyCartAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.PopupPackage.TotalPackagePriceAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.PopupVaccine.TotalVaccinePriceAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Cart.CartList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AddressActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Offer.OffersActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.ScheduleingConsolling.Schedule_Counseling
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.CooperatePopupBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SpinnerAdapterBrands
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.AvenuesParams
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CartIdModel
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.swipeview.OnActiveListener
import com.catalyist.helper.ErrorUtil
import com.google.gson.Gson
import org.w3c.dom.Text


class MyCartActivity : AppCompatActivity(), MyCartAdapter.OnClickItem {
    lateinit var bin: ActivityMyCartBinding
    private var adpterMyCart: MyCartAdapter? = null
    private var adapter_total_package_price: TotalPackagePriceAdapter? = null
    private var vaccine_adapter_total_price: TotalVaccinePriceAdapter? = null
    private lateinit var viewModel: ViewModalLogin
    var cart_id_list = ArrayList<String>()
    var positionDelete: Int = 0
    private var btm_total_price: Double = 0.0
    var pacakge_final_price = 0.0
    var vaccine_final_Result = 0
    var totalCstVacine = 0
    var totalHomeVaccine = 0
    var total_package_price = 0.0
    var vaccine_price = 0
    var noofDose = 0
    var totalVaccinePrice = 0
    var home_vaccination_free_price = 0
    var total_home_vaccination_free_price = 0
    var coupenCode: String? = null
    var discountPercentage: Double? = 0.0
    var walletAmount: Double? = 0.0
    var discamount: Double? = 0.0
    private var companyList = ArrayList<String>()
    private var companyHashMapID: HashMap<String, String> = HashMap()
    private var companyId: String = ""
    private lateinit var companySpiner: Spinner
    private var cartlist = ArrayList<AskedData>()
    private var from = ""
    lateinit var companyName: TextView
    private var isSelectedCompany: Boolean = true
    private var get_discount_value: Double = 0.0
    private var status_discount: Boolean = false
    private var noti_offer_price = 0.0
    private var order_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMyCartBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        from = intent.getStringExtra("from").toString()
        Log.d("TAG", "onCreate: from$from")
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
        if (intent.hasExtra("coupenCode")) {
            coupenCode = intent.getStringExtra("coupenCode").toString()
            discountPercentage = intent.getDoubleExtra("discount", 0.0)
        }

    }

    private fun initView() {
        bin.mycartToolbar.tvTittle.text = "Cart"
        apiCallingForCartList()
    }

    private fun lstnr() {
        bin.checkBoxCoOperate.setOnClickListener {
            co_operatePopup()
            viewModel.onCompanyList(this)

        }
        bin.tvCrossCoOperate.setOnClickListener {
            bin.tvCrossCoOperate.visibility = View.GONE
            bin.checkBoxCoOperate.isChecked = false
            bin.walletConstraint.visibility = View.VISIBLE
            bin.tvCoOperate.visibility = View.GONE
            bin.discount.visibility = View.GONE
            bin.discountText.visibility = View.GONE
            bin.textView160.text = "₹$btm_total_price"

        }
        bin.mycartToolbar.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        bin.applyCoupen.setOnClickListener {
            startActivity(Intent(this, OffersActivity::class.java))

        }
        bin.swipe.setOnActiveListener(OnActiveListener {
            cartPopup()
        })
        bin.ivInfoPack.setOnClickListener {
            totalPackagePrice()
        }
        bin.ivInfoVaccine.setOnClickListener {
            totalVaccinePrice()
        }
        var number = "9650039988"
        bin.ivwhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${"+91"}$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

    private fun spinnerForCompanyList(companyList: List<String>) {
        val adapter =
            SpinnerAdapterBrands(
                this,
                R.layout.spinner_dropdown_item, companyList
            )
        companySpiner.adapter = adapter
        companySpiner.onItemSelectedListener = onItemSelectedStateListenerCompanyList

    }

    ///////////spinner program CompanyList////
    private var onItemSelectedStateListenerCompanyList: AdapterView.OnItemSelectedListener =
        object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                postion: Int,
                l: Long
            ) {
                val itemSelected = adapterView!!.getItemAtPosition(postion) as String
                companyName.text = itemSelected
                companyId =
                    companyHashMapID.get(companySpiner.selectedItem.toString()).toString()
                println("---memberId$companyId")
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

    private fun totalPackagePrice() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.total_package_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val rcy = dialog.findViewById<RecyclerView>(R.id.rv_Total_Package)
        rcy.layoutManager = LinearLayoutManager(this)
        adapter_total_package_price = TotalPackagePriceAdapter(this, modifyList2(cartlist))
        rcy.adapter = adapter_total_package_price
        adapter_total_package_price!!.notifyDataSetChanged()
        val tvOk = dialog.findViewById<TextView>(R.id.tvOk_Ttl_Package)
        val tvprice = dialog.findViewById<TextView>(R.id.tvTotalPrice_Ttl_Package)
        tvprice.setText("₹$pacakge_final_price")
        Log.e("TAG", "totalPackagePrice:${Gson().toJson(modifyList2(cartlist))}")
        tvOk.setOnClickListener {
            dialog.dismiss()
        }
        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun totalVaccinePrice() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.vaccine_total_price_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        val rcy = dialog.findViewById<RecyclerView>(R.id.rv_Total_vaccine)
        rcy.layoutManager = LinearLayoutManager(this)
        vaccine_adapter_total_price = TotalVaccinePriceAdapter(this, modifyList(cartlist))
        rcy.adapter = vaccine_adapter_total_price
        vaccine_adapter_total_price!!.notifyDataSetChanged()
        val tvOk = dialog.findViewById<TextView>(R.id.tvOk_Ttl_vaccinee)
        val tv_Vaccine_price = dialog.findViewById<TextView>(R.id.tvTotalPrice_Ttl_vaccine)
        val tst = vaccine_final_Result - noti_offer_price
        tv_Vaccine_price.setText("₹${tst}")
        Log.e("TAG", "totalVaccinePrice:${Gson().toJson(modifyList(cartlist))}")

        tvOk.setOnClickListener {
            dialog.dismiss()
        }

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun discountCalc() {
        var discountPrice: Double = discountPercentage!!.toDouble()
        var packagePrice = btm_total_price.toString()
        val packageprcdouble: Double = packagePrice!!.toDouble()
        discamount = packageprcdouble * discountPrice / 100
        bin.discountText.text = "₹$discamount"
        val total = packageprcdouble - discamount!!
        bin.textView160.text = "₹$total"
        bin.coupenCode.text = "$coupenCode"
    }

    private fun cartPopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.cart_item_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        val no = dialog.findViewById<TextView>(R.id.tvNo)
        val yes = dialog.findViewById<TextView>(R.id.tvYes)
        no.setOnClickListener {
            dialog.dismiss()
//            startActivity(Intent(this, Schedule_Counseling::class.java))
//            finish()
            startActivity(
                Intent(this, AddressActivity::class.java)
                    .putExtra("cart_price", btm_total_price)
                    .putExtra("cart_id_list", cart_id_list)
                    .putExtra("walletAmount", walletAmount)
                    .putExtra("discount", discountPercentage)
                    .putExtra("discountAmount", discamount)
                    .putExtra("status_discount", status_discount)
                    .putExtra(AvenuesParams.ORDER_ID, order_id)
            )
        }
        yes.setOnClickListener {
            dialog.dismiss()
            Log.e("TAG", "cartPopup:${btm_total_price}")
            /*startActivity(
                Intent(this, AddressActivity::class.java)
                    .putExtra("cart_price", btm_total_price)
                    .putExtra("cart_id_list", cart_id_list)
                    .putExtra("walletAmount", walletAmount)
                    .putExtra("discount", discountPercentage)
                    .putExtra("discountAmount", discamount)
                    .putExtra("status_discount", status_discount)
                    .putExtra(AvenuesParams.ORDER_ID, order_id)
            )*/
            startActivity(Intent(this, Schedule_Counseling::class.java))
            finish()
        }
        dialog.show()

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun co_operatePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.cooperate_popup)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        val tvconfrom = dialog.findViewById<TextView>(R.id.dialogConfromCo_Operate)
        val tvCancel = dialog.findViewById<TextView>(R.id.dialog_cancelCo_operate)
        companySpiner = dialog.findViewById(R.id.CoperteSpinner)
        companyName = dialog.findViewById(R.id.tvCo_operatePopup)
        tvconfrom.setOnClickListener {
            dialog.dismiss()
            viewModel.onGetDiscountList(
                this,
                companyId,
                dialog.findViewById<EditText>(R.id.ed_EmployeeId).text.toString(),
                PrefrencesHelper.getEmail_Phone(this)
            )
            bin.tvCrossCoOperate.visibility = View.VISIBLE
            bin.walletConstraint.visibility = View.GONE
            bin.tvCoOperate.visibility = View.VISIBLE
            bin.discount.visibility = View.VISIBLE
            bin.discountText.visibility = View.VISIBLE

        }
        tvCancel.setOnClickListener {
            dialog.dismiss()
            bin.checkBoxCoOperate.isChecked = false
            bin.checkBoxCoOperate.isClickable = true
        }
        dialog.show()

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        companyName.setOnClickListener {
            companySpiner.performClick()
        }
    }

    private fun modifyList(cartlist: ArrayList<AskedData>): ArrayList<AskedData> {
        val data = ArrayList<AskedData>()
        for (mydata in cartlist) {
            if (mydata.cartType.equals("VACCINE")) {
                data.addAll(listOf(mydata))
            }
        }
        return data

    }

    private fun modifyList2(cartlist: ArrayList<AskedData>): ArrayList<AskedData> {
        val data = ArrayList<AskedData>()
        for (mydata in cartlist) {
            if (mydata.cartType.equals("PACKAGE")) {
                data.addAll(listOf(mydata))
            }
        }
        return data

    }


    var type = ""
    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseCartList.observe(this) {
            if (it?.code == 200) {
                order_id = it.orderId
                cart_id_list.clear()
                cartlist.clear()
                cartlist.addAll(it.askedData)
                for (of in it.askedData) {
                    if (of.offerPrice != null && of.cartType.equals("VACCINE")) {
                        noti_offer_price += of.offerPrice.toDouble()
                    } else {
                        noti_offer_price = 0.0
                    }
                }
                println("---off$noti_offer_price")
                priceCalculation()
                bin.rupee.text = "₹ ${walletAmount.toString()}"
                println("----walletAmount$walletAmount")


                if (cartlist.size < 10) {
                    bin.tvsizeCart.text = "0" + cartlist.size + " Item in Cart"
                } else {
                    bin.tvsizeCart.text = cartlist.size.toString()
                }
                cartlist.forEach {
                    cart_id_list.add(it._id)
                    println("---cart_Id_list$cart_id_list")
                }
                val model = CartIdModel(cart_id_list)
                println("---cart_Id_listsave${Gson().toJson(model)}")
                PrefrencesHelper.saveCartIDList(this, Gson().toJson(model))

                if (cartlist.isEmpty()) {
                    bin.cartJson.visibility = View.VISIBLE
                    bin.textView142.visibility = View.GONE
                    bin.textView146.visibility = View.GONE
                    bin.textView148.visibility = View.GONE
                    bin.textView143.visibility = View.GONE
                    bin.textView145.visibility = View.GONE
                    bin.scrollView2.visibility = View.GONE
                    bin.bottom.visibility = View.GONE
                    bin.tvsizeCart.visibility = View.GONE
                    bin.ivInfoPack.visibility = View.GONE
                    bin.ivInfoVaccine.visibility = View.GONE
                    return@observe
                } else {
                    bin.cartJson.visibility = View.GONE
                    bin.scrollView2.visibility = View.VISIBLE
                    bin.bottom.visibility = View.VISIBLE
                    bin.tvsizeCart.visibility = View.VISIBLE
                    bin.textView142.visibility = View.VISIBLE
                }

                if (vaccine_final_Result > 0) {
                    bin.textView146.visibility = View.VISIBLE
                    bin.textView148.visibility = View.VISIBLE
                    bin.ivInfoVaccine.visibility = View.VISIBLE
                } else {
                    bin.textView146.visibility = View.GONE
                    bin.textView148.visibility = View.GONE
                    bin.ivInfoVaccine.visibility = View.GONE
                }
                if (pacakge_final_price > 0) {
                    bin.textView143.visibility = View.VISIBLE
                    bin.textView145.visibility = View.VISIBLE
                    bin.ivInfoPack.visibility = View.VISIBLE
                } else {
                    bin.textView143.visibility = View.GONE
                    bin.textView145.visibility = View.GONE
                    bin.ivInfoPack.visibility = View.GONE
                }

                bin.rcyMycart.layoutManager =
                    LinearLayoutManager(this)
                adpterMyCart = MyCartAdapter(this, cartlist, this)
                bin.rcyMycart.adapter = adpterMyCart
                adpterMyCart!!.notifyDataSetChanged()

            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseDeleteCart.observe(this) {
            if (it?.code == 200) {
                btm_total_price = 0.0
                pacakge_final_price = 0.0
                vaccine_final_Result = 0
                totalCstVacine = 0
                totalHomeVaccine = 0
                total_package_price = 0.0
                vaccine_price = 0
                noofDose = 0
                totalVaccinePrice = 0
                home_vaccination_free_price = 0
                total_home_vaccination_free_price = 0
                noti_offer_price = 0.0


                if (cartlist.size > 0) {
                    bin.tvsizeCart.text = (cartlist.size - 1).toString() + "Item in Cart"

                } else {
                    bin.tvsizeCart.text = "0 Item in Cart"

                }
                cartlist?.removeAt(positionDelete)
                adpterMyCart?.notifyItemRemoved(positionDelete)
                adpterMyCart?.notifyItemRangeChanged(positionDelete, cartlist?.size ?: 0)
                apiCallingForCartList()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.responseCompanyList.observe(this) {
            if (it?.code == 200) {
                if (companyList.size > 0) {
                    companyList.clear()
                }
                companyList.add(0, "Company Name")
                it.result.forEach {
                    companyList.add(it.companyName)
                    companyHashMapID.put(it.companyName, it._id)
                }
                spinnerForCompanyList(companyList)
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseGetDiscount.observe(this) {
            if (it?.code == 200) {
                Toast.makeText(this, "Discount Coupon Successfully Applied!!", Toast.LENGTH_SHORT)
                    .show()
                get_discount_value = it.discountValue.toDouble()
                println("-----diss$get_discount_value")
                bin.tvCoOperate.text = "${get_discount_value} %"
                discamount = btm_total_price * get_discount_value / 100
                bin.discountText.text = "₹${discamount}"
                val total1 = btm_total_price - discamount!!
                bin.textView160.text = "₹$total1"
                status_discount = true

            } else {
                Toast.makeText(this, "Coupon not applicable!!", Toast.LENGTH_SHORT).show()
                bin.tvCrossCoOperate.visibility = View.GONE
                bin.checkBoxCoOperate.isChecked = false
                bin.walletConstraint.visibility = View.VISIBLE
                bin.tvCoOperate.visibility = View.GONE
                bin.discount.visibility = View.GONE
                bin.discountText.visibility = View.GONE
                bin.textView160.text = "₹$btm_total_price"
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)
        }
    }

    private fun priceCalculation() {
        totalCstVacine = 0
        totalHomeVaccine = 0
        for (data in cartlist) {
            if (data.cartType.equals("VACCINE")) {
                noofDose = data.noOfDose.toInt()
                vaccine_price = data.vaccinePrice.toInt()
                totalVaccinePrice = noofDose * vaccine_price
                totalCstVacine += totalVaccinePrice
                home_vaccination_free_price = data.homeVaccinationFee.toInt()
                val nodose = data.noOfDose.toInt()
                total_home_vaccination_free_price = nodose * home_vaccination_free_price
                totalHomeVaccine += total_home_vaccination_free_price
            } else {
                total_package_price = data.offerPrice.toDouble()
                pacakge_final_price += total_package_price
            }
        }
        // set package data...
        bin.textView145.text = "₹" + pacakge_final_price
        btm_total_price = pacakge_final_price + vaccine_final_Result
        bin.textView160.text = "₹$btm_total_price"

        // set vaccine data...
        vaccine_final_Result = totalCstVacine + totalHomeVaccine
        val hh = vaccine_final_Result - noti_offer_price
        bin.textView148.text = "₹" + hh
        btm_total_price = pacakge_final_price + hh
        bin.textView160.text = "₹$btm_total_price"
        if (intent.hasExtra("coupenCode")) {
            bin.discount.visibility = View.VISIBLE
            bin.discountText.visibility = View.VISIBLE
            bin.ivCrossCart.visibility = View.VISIBLE
            bin.applyCoupen.visibility = View.GONE
            bin.cardViewCoOperate.visibility = View.GONE
            coupenCode = intent.getStringExtra("coupenCode").toString()
            discountPercentage = intent.getStringExtra("discount")!!.toDouble()
            discountCalc()
        } else {
            bin.ivCrossCart.visibility = View.GONE
            bin.applyCoupen.visibility = View.VISIBLE
            bin.discount.visibility = View.GONE
            bin.discountText.visibility = View.GONE
        }
        bin.ivCrossCart.setOnClickListener {
            bin.discount.visibility = View.GONE
            bin.discountText.visibility = View.GONE
            bin.coupenCode.text = "Coupon Code"
            bin.applyCoupen.visibility = View.VISIBLE
            bin.ivCrossCart.visibility = View.GONE
            bin.cardViewCoOperate.visibility = View.VISIBLE
            // set package data...
            bin.textView145.text = "₹" + pacakge_final_price
            btm_total_price = pacakge_final_price + vaccine_final_Result
            bin.textView160.text = "₹$btm_total_price"

            // set vaccine data...
            vaccine_final_Result = totalCstVacine + totalHomeVaccine
            val jj = vaccine_final_Result - noti_offer_price
            bin.textView148.text = "₹" + jj
            btm_total_price = pacakge_final_price + jj
            bin.textView160.text = "₹$btm_total_price"
        }

    }

    private fun apiCallingForCartList() {
        viewModel.onCartList(this)
    }

    private fun apiCallingForDeleteCart() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.onDeleteCart(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kcartId] = deleteId
        return hashMap
    }

    /*override fun onResume() {
        super.onResume()
        apiCallingForCartList()
    }*/

    private fun deletePopup() {
        val dialog = this.let { Dialog(this) }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)

        val btDismiss = dialog.findViewById<TextView>(R.id.dialog_newcancel)
        val btSubmit = dialog.findViewById<TextView>(R.id.dialog_ok)

        btSubmit.setOnClickListener {
            dialog.dismiss()
            apiCallingForDeleteCart()

        }

        btDismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }

    var deleteId: String = ""
    override fun onDeleteCart(msg: AskedData, position: Int) {
        deleteId = msg._id
        positionDelete = position
        deletePopup()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
