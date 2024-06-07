package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.SubCategory

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivitySubCategoryBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.adapter.SubCategory.SubCategoryAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.catalyist.helper.ErrorUtil
import java.util.*
import kotlin.collections.ArrayList

class SubCategoryActivity : AppCompatActivity(),SubCategoryAdapter.SubCategory{
    lateinit var bin: ActivitySubCategoryBinding
    private lateinit var viewModel: ViewModalLogin
    private var arrayList = ArrayList<AskedData>()
    private var subcategoryAdapter: SubCategoryAdapter? = null
    var categoryIdnew: String = ""
    var category_name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(bin.root)
        apiResponse()
        initView()
        lstnr()
        CommonUtil.themeSet(this, window)
    }

    private fun initView() {
        categoryIdnew = intent.getStringExtra("categoryId").toString()
        println("-----iiiiii${categoryIdnew}")
        apiforSubCategoryList(categoryIdnew)
    }

    private fun lstnr() {
        bin.allCategoriesToolbar.ivBack.setOnClickListener {
            onBackPressed()
        }
        bin.seach.addTextChangedListener(object : TextWatcher {
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
                        bin.rcyAllCategory.layoutManager =
                            StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
                        subcategoryAdapter = SubCategoryAdapter(
                            this@SubCategoryActivity,
                            tempFilterList as ArrayList<AskedData>,this@SubCategoryActivity
                        )
                        bin.rcyAllCategory.adapter = subcategoryAdapter
                        subcategoryAdapter!!.notifyDataSetChanged()
                        if (tempFilterList.isEmpty()) {
                            Toast.makeText(
                                this@SubCategoryActivity,
                                "Data Not Found!!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    bin.rcyAllCategory.layoutManager =
                        StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
                    subcategoryAdapter = SubCategoryAdapter(
                        this@SubCategoryActivity,
                        arrayList,this@SubCategoryActivity
                    )
                    bin.rcyAllCategory.adapter = subcategoryAdapter
                    subcategoryAdapter!!.notifyDataSetChanged()

                }
            }
        })

    }

    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseSubcategoryList.observe(this) {
            if (it?.code == 200) {
                arrayList.clear()
                arrayList.addAll(it.askedData)
                if (arrayList.isEmpty()) {
                    Toast.makeText(this@SubCategoryActivity, "Data Not Found!!", Toast.LENGTH_SHORT)
                        .show()
                    bin.allCategoriesToolbar.tvTittle.text = "--"
                }
                category_name = it.askedData.get(0).categoryName
                bin.allCategoriesToolbar.tvTittle.text = category_name
                println("----sunName$category_name")
                bin.rcyAllCategory.layoutManager =
                    StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
                subcategoryAdapter = SubCategoryAdapter(this, arrayList,this)
                bin.rcyAllCategory.adapter = subcategoryAdapter
                subcategoryAdapter!!.notifyDataSetChanged()

                //Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
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
