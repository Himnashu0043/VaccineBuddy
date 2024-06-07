package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.AvenuesParams;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.Constants;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.LoadingDialog;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.RSAUtility;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService.ServiceUtility;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PaymentGatway.PaymentGatwayRes;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.CommonUtil;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class CCAvenueActivity extends AppCompatActivity {

    Context mContext;
    WebView webview;
    ProgressDialog progress;
    com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.orderDetails order;
    String order_id = "";
    String accessCode = "";
    String reUrl = "";
    String canUrl = "";
    String enCode = "";
    Intent mainIntent;
    String encVal;
    String vResponse;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ccavenue);
//        get_RSA_key(mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE), mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
//        ButterKnife.bind(this);
//        mContext = CCAvenueActivity.this;
//        progress = new ProgressDialog(this);
//        progress.setMessage("Loading...");
//        progress.setCanceledOnTouchOutside(false);
//        progress.setCancelable(true);
//        webview = (WebView) findViewById(R.id.webview);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.getSettings().setSupportMultipleWindows(true);
//        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
//        webview.setWebChromeClient(new WebChromeClient());
//        webview.setWebViewClient(new MyWebViewClient());

        /*order = getIntent().getParcelableExtra("order");*/
        order_id = getIntent().getStringExtra("order_id");
        accessCode = getIntent().getStringExtra("access_code");
        reUrl = getIntent().getStringExtra("redirect_url");
        canUrl = getIntent().getStringExtra("cancel_url");
        enCode = getIntent().getStringExtra("enc_val");
        System.out.println("---encode" + enCode);
        show();
        String data = "encRequest=" + enCode + "&access_code=" + accessCode;
        webview.postUrl(Constants.CCAVENUE_URL, data.getBytes());

    }




    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(webview, url);

            if (progress.isShowing())
                hide();

            if (url.equalsIgnoreCase(reUrl) || url.equalsIgnoreCase(canUrl)) {
                webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        }
    }

    String hh = "";
    String hh1 = "";

    @SuppressWarnings("unused")
    class MyJavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String html) {
            System.out.println("---hhhhh" + html);
            hh = String.valueOf(Html.fromHtml(html)).replace("=", "\":\"").replace("[", "{").replace("]", "}");

            System.out.println("---hhhhh1" + hh);
            PaymentGatwayRes res = new Gson().fromJson(hh, PaymentGatwayRes.class);
            hh1 = res.getData().getAmount();
            System.out.println("---hhhhh111" + hh1);
            // process the html source code to get final status of transaction
            String status = null;
            if (html.indexOf("Failure") != -1) {
                status = "Transaction Declined!";
            } else if (html.indexOf("Success") != -1) {
                status = "Transaction Successful!";
            } else if (html.indexOf("Aborted") != -1) {
                status = "Transaction Cancelled!";
            } else {
                status = "Status Not Known!";
            }
            /*Intent intent = new Intent(getApplicationContext(), PaymentStatusActivity.class);
            intent.putExtra("transStatus", status);
            intent.putExtra("orderId", order.getOrder_id());
            startActivity(intent);
            finish();*/
        }
    }


    public void show() {
        if (!((Activity) mContext).isFinishing())
            if (progress != null && !progress.isShowing())
                progress.show();

    }

    public void hide() {
        if (!((Activity) mContext).isFinishing())
            if (progress != null && progress.isShowing())
                progress.dismiss();

    }
//    private class RenderView extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (!ServiceUtility.chkNull(vResponse).equals("")
//                    && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
//                StringBuffer vEncVal = new StringBuffer("");
//                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
//                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
//                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);  //encrypt amount and currency
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Showing progress dialog
//            LoadingDialog.showLoadingDialog(getApplicationContext(), "Loading...");
//
//        }
//    }
//    private void get_RSA_key(final String ac, final String od) {
//        LoadingDialog.showLoadingDialog(getApplicationContext(), "Loading...");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
//                        LoadingDialog.cancelLoading();
//
//                        if (response != null && !response.equals("")) {
//                            vResponse = response;     ///save retrived rsa key
//                            if (vResponse.contains("!ERROR!")) {
//                                show_alert(vResponse);
//                            } else {
//                                new RenderView().execute();   // Calling async task to get display content
//                            }
//
//
//                        }
//                        else
//                        {
//                            show_alert("No response");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LoadingDialog.cancelLoading();
//                        //Toast.makeText(WebViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(AvenuesParams.ACCESS_CODE, ac);
//                params.put(AvenuesParams.ORDER_ID, od);
//                return params;
//            }
//
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//    }
//
//    public void show_alert(String msg) {
//        AlertDialog alertDialog = new AlertDialog.Builder(
//                getApplicationContext()).create();
//
//        alertDialog.setTitle("Error!!!");
//        if (msg.contains("\n"))
//            msg = msg.replaceAll("\\\n", "");
//
//        alertDialog.setMessage(msg);
//
//
//
//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//
//
//        alertDialog.show();
//    }

}