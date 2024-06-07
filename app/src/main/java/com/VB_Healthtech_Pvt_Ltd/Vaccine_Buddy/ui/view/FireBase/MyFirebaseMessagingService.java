package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.FireBase;

import static android.content.ContentValues.TAG;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PushNotiModal;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails.OrderDetailsActivity;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper;
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by akaMahesh on 23/3/17.
 * Copyright to Mobulous Technology Pvt. Ltd.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    ArrayList<DoseInfo> listdata = new ArrayList<>();

    @Override
    public void onNewToken(String refreshedToken) {
        Log.e("NEW_TOKEN", refreshedToken);
        PrefrencesHelper.INSTANCE.getSaveDeviceID(getApplicationContext(), refreshedToken);

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("MyFirebaseMessagingService", "onMessageReceived:data" + remoteMessage.getData());

        Map<String, String> data = remoteMessage.getData();
        String response = remoteMessage.getData().get("doseInfo");
        String type = remoteMessage.getData().get("type");


        PrefrencesHelper.INSTANCE.getType(this, type);

        String t = PrefrencesHelper.INSTANCE.setType(this);
        System.out.println("----remm" + remoteMessage.getData());
        System.out.println("----tpe" + t);
        if (response != null) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String doseNumber = jsonObject.getString("doseNumber");
                    String timePeriod = jsonObject.getString("timePeriod");
                    listdata.add(new DoseInfo(doseNumber, timePeriod));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


        String id = "";
        if (remoteMessage.getData().get("packageId") != null) {
            id = remoteMessage.getData().get("packageId");
        } else if (remoteMessage.getData().get("orderId") != null) {
            id = remoteMessage.getData().get("orderId");
        } else {
            id = remoteMessage.getData().get("vaccineId");
        }
        sendNotification(data, type, id, listdata);
    }

    /**
     * Create and show a custom notification containing the received FCM message.
     * <p>
     * //     * @param notification FCM notification payload received.
     *
     * @param data FCM data payload received.
     */
    private void sendNotification(Map<String, String> data, String type, String id, ArrayList<DoseInfo> pushNoti) {

        Intent intent = null;

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if (type ==null){
            type="";
        }
        if (type.equalsIgnoreCase("Custom Package")) {
            intent = new Intent(this, PackageDetailsActivity.class);
            intent.putExtra("packageId", id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (  type.equalsIgnoreCase("Custom Vaccine")) {
            intent = new Intent(this, VaccineDetailsActivity.class);
            intent.putExtra("notiType", "Custom Vaccine");
            intent.putExtra("vaccineIdList", pushNoti);
            intent.putExtra("vaccineId", id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (type.equalsIgnoreCase("ASSIGN")) {
            intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("orderId", id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (type.equalsIgnoreCase("COMPLETE")) {
            intent = new Intent(this, OrderDetailsActivity.class);
            intent.putExtra("orderId", id);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

//        if(data.get("data").equalsIgnoreCase("user_notification")){
//            intent = new Intent(this, ActivityCashWallet.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        } else  if(data.get("data").equalsIgnoreCase("chips")){
//            intent = new Intent(this, ActivityMyChips.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        } else  if(data.get("data").equalsIgnoreCase("payment_notification")){
//            intent = new Intent(this, ActivityCashWallet.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }else  if(data.get("data").equalsIgnoreCase("bliss_bundle")){
//            intent = new Intent(this, ActivityBlissBundle.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }else  if(data.get("data").equalsIgnoreCase("friend_list")){
//            intent = new Intent(this, ActivitySocialConnect.class);
//            intent.putExtra(kFrom,"Notification");
//           // startActivity(ActivityUserDetailsSecound.Companion.getIntent(this, "AdapterComments", data.get("id")).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }
//        else {
//            intent = new Intent(this, ActivityHomeMain.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        Log.e(TAG, "sendNotification: " + Build.VERSION_CODES.M);

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setContentInfo(data.get("title"))
                    .setLargeIcon(icon)
                    .setColor(getColor(R.color.darknavyblue2))
                    .setLights(Color.RED, 1000, 300)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(getNotificationIcon());
        }
*/



        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(data.get("title"))
                .setContentText(data.get("body"))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setContentInfo(data.get("title"))
                .setLargeIcon(icon)
                .setColor(getColor(R.color.darknavyblue2))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(getNotificationIcon());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        } else {
            notificationManager.notify(getNotificationId(), notificationBuilder.build());
        }

    }

    private static int getNotificationId() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(9000);
    }


    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_small_notification : R.drawable.ic_small_notification;
    }
}
