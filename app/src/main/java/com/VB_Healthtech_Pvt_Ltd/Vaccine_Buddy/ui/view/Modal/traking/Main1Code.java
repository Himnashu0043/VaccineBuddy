package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.traking;

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main1Code {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<MainCode> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MainCode> getData() {
        return data;
    }

    public void setData(List<MainCode> data) {
        this.data = data;
    }
}
