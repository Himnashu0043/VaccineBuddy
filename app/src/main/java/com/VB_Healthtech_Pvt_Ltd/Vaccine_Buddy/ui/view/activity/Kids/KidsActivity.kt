package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Kids

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityKidsBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.KidsAdapter
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.SubCategoryList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Package.PackageActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.catalyist.helper.ErrorUtil
import java.util.*
import kotlin.collections.ArrayList

class KidsActivity : AppCompatActivity(), KidsAdapter.SubCategory {
    private lateinit var binding: ActivityKidsBinding
    private lateinit var viewModel: ViewModalLogin
    private var kidsAdapter: KidsAdapter? = null
    private var arrayList = ArrayList<AskedData>()
    var category_id: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKidsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)


    }

    private fun initView() {
        category_id = intent.getStringExtra("categoryId").toString()
        println("-----kidiiiiii${category_id}")
        apiforSubCategoryList(category_id)
    }

    private fun lstnr() {
        binding.seach.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                var text = p0.toString().trim()
                if (text.isNotEmpty()) {
                    if (text.length > 0) {

                        var tempFilterList = arrayList.filter {
                            it.subCategoryName.lowercase(Locale.ROOT)
                                .contains(text.lowercase(Locale.ROOT))
                        }
                        binding.rcyAllCategory.layoutManager =
                            GridLayoutManager(this@KidsActivity, 3)
                        kidsAdapter =
                            KidsAdapter(this@KidsActivity, tempFilterList as ArrayList<AskedData>,this@KidsActivity)
                        binding.rcyAllCategory.adapter = kidsAdapter
                        kidsAdapter!!.notifyDataSetChanged()
                        if (tempFilterList.isEmpty()) {
                            Toast.makeText(
                                this@KidsActivity,
                                "Data Not Found!!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    binding.rcyAllCategory.layoutManager =
                        GridLayoutManager(this@KidsActivity, 3)
                    kidsAdapter = KidsAdapter(this@KidsActivity, arrayList,this@KidsActivity)
                    binding.rcyAllCategory.adapter = kidsAdapter
                    kidsAdapter!!.notifyDataSetChanged()

                }
            }
        })
        binding.allCategoriesToolbar.ivBack.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseSubcategoryList.observe(this) {
            if (it?.code == 200) {
                arrayList.clear()
                arrayList.addAll(it.askedData)
                if (arrayList.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Data Not Found!!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    binding.allCategoriesToolbar.tvTittle.text = "--"
                }
                binding.allCategoriesToolbar.tvTittle.text = it.askedData.get(0).categoryName
                binding.rcyAllCategory.layoutManager =
                    GridLayoutManager(this, 3)
                kidsAdapter = KidsAdapter(this, arrayList, this)
                binding.rcyAllCategory.adapter = kidsAdapter
                kidsAdapter!!.notifyDataSetChanged()
                //  Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    private fun apiforSubCategoryList(categoryId: String) {
        viewModel.onSubcategoryList(this, categoryId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    var subCategory_id = ""
    override fun onsubCategory_id(msg: AskedData, pos: Int) {
        subCategory_id = msg._id
        println("-----subCategory_id$subCategory_id")
        startActivity(
            Intent(this, PackageActivity::class.java).putExtra(
                "subCategory_id",
                subCategory_id
            )
        )

    }


}