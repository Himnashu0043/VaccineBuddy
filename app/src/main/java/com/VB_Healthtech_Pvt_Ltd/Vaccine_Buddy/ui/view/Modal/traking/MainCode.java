package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.traking;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCode {
    @Nullable
    @SerializedName("order_status ")
    @Expose
    private String orderStatus;
    @SerializedName("reference_no ")
    @Expose
    private String referenceNo;
    @Nullable
    @SerializedName("error_desc ")
    @Expose
    private String error_desc;

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

}
