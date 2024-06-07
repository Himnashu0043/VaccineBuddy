package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.HDFCPayment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityPaymentStatusBinding;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity;


public class PaymentStatusActivity extends AppCompatActivity {
    Intent mIntent;
    @NonNull
    ActivityPaymentStatusBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPaymentStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mIntent = getIntent();

//        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        /*binding.textView1.setText(mIntent.getStringExtra("transStatus"));*/
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        afficher();
                    }
                }, 500L);


    }
    public void afficher()
    {
        Intent intent = new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
