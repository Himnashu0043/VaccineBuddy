package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.AllCategories

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityAllCategoriesBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.CategoryAdapter

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.CategoryList.AskedData
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.Kids.KidsActivity
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
import com.catalyist.helper.CustomLoader
import com.catalyist.helper.ErrorUtil
import java.util.*
import kotlin.collections.ArrayList

class AllCategoriesActivity : AppCompatActivity(), CategoryAdapter.OnClickItem {
    private lateinit var binding: ActivityAllCategoriesBinding
    private lateinit var viewModel: ViewModalLogin
    private var arrayList = ArrayList<AskedData>()
    private var categoryAdapter: CategoryAdapter? = null
    private var category_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)

    }

    private fun initView() {
        binding.allCategoriesToolbar.tvTittle.text = "All Categories"
        viewModel.onCategoryList(this)
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
                            it.categoryName.lowercase(Locale.ROOT)
                                .contains(text.lowercase(Locale.ROOT))
                        }
                        binding.rcyAllCategory.layoutManager =
                            GridLayoutManager(this@AllCategoriesActivity, 3)
                        categoryAdapter = CategoryAdapter(
                            this@AllCategoriesActivity,
                            tempFilterList as ArrayList<AskedData>, this@AllCategoriesActivity
                        )
                        binding.rcyAllCategory.adapter = categoryAdapter
                        categoryAdapter!!.notifyDataSetChanged()
                        if (tempFilterList.isEmpty()) {
                            Toast.makeText(
                                this@AllCategoriesActivity,
                                "Data Not Found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    binding.rcyAllCategory.layoutManager =
                        GridLayoutManager(this@AllCategoriesActivity, 3)
                    categoryAdapter = CategoryAdapter(
                        this@AllCategoriesActivity,
                        arrayList,
                        this@AllCategoriesActivity
                    )
                    binding.rcyAllCategory.adapter = categoryAdapter
                    categoryAdapter!!.notifyDataSetChanged()

                }
            }
        })
        binding.allCategoriesToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        CustomLoader.showLoader(this)
        viewModel.responseCategoryList.observe(this) {
            if (it?.code == 200) {
                //binding.lottie.visibility = View.GONE
                CustomLoader.hideLoader()
                arrayList.clear()
                arrayList.addAll(it.askedData)
                if (arrayList.isEmpty()) {
                    Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
                }
                binding.rcyAllCategory.layoutManager =
                    GridLayoutManager(this, 3)
                categoryAdapter = CategoryAdapter(this, arrayList, this)
                binding.rcyAllCategory.adapter = categoryAdapter
                categoryAdapter!!.notifyDataSetChanged()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun clickOn(msgItem: AskedData, position: Int) {
        category_id = msgItem._id
        if (msgItem.subCategoryCount.isEmpty()) {
            startActivity(
                Intent(
                    this,
                    PackageActivity::class.java
                ).putExtra("categoryId", category_id)
            )
        } else {
            startActivity(
                Intent(this, KidsActivity::class.java).putExtra(
                    "categoryId",
                    category_id
                )
            )
        }

    }
}





