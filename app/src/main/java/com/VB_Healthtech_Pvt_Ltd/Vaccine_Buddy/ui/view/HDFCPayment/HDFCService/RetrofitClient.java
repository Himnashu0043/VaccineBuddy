package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCService;


import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.TestNewResPay;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.HDFCModal.TestPostModel;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TrackingPostModel;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.TrackingRes;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.traking.Main1Code;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RetrofitClient {

    private static GitApiInterface gitApiInterface;

    private static String URL = Constants.MERCHANT_SERVER_URL;

    public static GitApiInterface Interface() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS).build();

        if (gitApiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(new ToStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            gitApiInterface = retrofit.create(GitApiInterface.class);
        }

        return gitApiInterface;
    }

    public interface GitApiInterface {

        /*@POST("MobPHPKit/india/init_payment.php")
        Call<String> initiatePayment(@Query("amount") String amount);*/
//        @POST("rest-api-php/items/create")
//        Call<TestResPay> initiatePayment(
//                @Body TestPostModel postModel);
        @POST("rest-api-php/items/rsakey")
        Call<TestNewResPay> initiatePayment(
                @Body TestPostModel postModel);

        @POST("rest-api-php/items/tracking")
        Call<Main1Code> tracking(
                @Body TrackingPostModel postModel);
    }
}