package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.FireBase

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.Notification.DoseInfo
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper.getSaveDeviceID
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper.getType
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.MainActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.OrderDetails.OrderDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.PackageDetails.PackageDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity.VaccineDetails.VaccineDetailsActivity
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.utils.Enums
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MyFireBaseMessagingServices : FirebaseMessagingService() {
    var listdata = ArrayList<DoseInfo>()

    override fun onNewToken(p0: String) {
        Log.e("Device Token  ==>>", p0)
        getSaveDeviceID(applicationContext, p0)

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "notification_data:===>>" + remoteMessage.data.toString())
        Log.e(TAG, "notification_data:===>>" + remoteMessage.rawData.toString())
        Log.e(TAG, "notification_data:===>>" + remoteMessage.messageType.toString())

        var intent: Intent? = null
        val type = remoteMessage.data["type"].toString()
        val title = remoteMessage.data["title"].toString()
        var message = remoteMessage.data["body"].toString()
        val response = remoteMessage.data["doseInfo"].toString()
        getType(this, type)

        if (message =="null"){
            message=remoteMessage.data["content"].toString()
        }
        if (response != "null") {
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val doseNumber = jsonObject.getString("doseNumber")
                    val timePeriod = jsonObject.getString("timePeriod")
                    listdata.add(DoseInfo(doseNumber, timePeriod))
                }
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }
        }


        var id: String? = ""
        id = if (remoteMessage.data["packageId"] != null) {
            remoteMessage.data["packageId"]
        } else if (remoteMessage.data["orderId"] != null) {
            remoteMessage.data["orderId"]
        } else {
            remoteMessage.data["vaccineId"]
        }

        val typeChange=type.trim().lowercase().replace(" ","_")
        when (typeChange) {
            Enums.custom_package.toString() -> {
                intent = Intent(applicationContext, PackageDetailsActivity::class.java)
                intent.putExtra("packageId", id)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                showNotification(intent, title, message)
            }
            Enums.custom_vaccine.toString() -> {
                intent = Intent(this, VaccineDetailsActivity::class.java)
                intent.putExtra("notiType", "Custom Vaccine")
                intent.putExtra("vaccineIdList", listdata)
                intent.putExtra("vaccineId", id)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                showNotification(intent, title, message)
            }
            Enums.assign.toString() -> {
                intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("orderId", id)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                showNotification(intent, title, message)
            }
            Enums.complete.toString() -> {
                intent = Intent(this, OrderDetailsActivity::class.java)
                intent.putExtra("orderId", id)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                showNotification(intent, title, message)
            }
            else -> {
                intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                showNotification(intent, title, message)
            }
        }


    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(intent: Intent, title: String, message: String) {

        val uniqueId = getUniqueId()
        try {
            val mNotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            Log.e(TAG, "onMessageReceived:ttt ")

            val paddingIntentRedirect = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
                Log.e(TAG, "showNotification: ")
                PendingIntent.getActivity(
                    this,
                    uniqueId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            } else {
                Log.e(TAG, "showNotification:22 ")
                val stackBuilder = TaskStackBuilder.create(this)
                    .addParentStack(MainActivity::class.java)
                    .addNextIntent(intent)
                stackBuilder
                    .getPendingIntent(getUniqueId(), PendingIntent.FLAG_IMMUTABLE)
            }
            val bigText = NotificationCompat.BigTextStyle()
            bigText.bigText(title)
            bigText.setBigContentTitle(title)
            val mBuilder = NotificationCompat.Builder(
                this,
                getString(R.string.default_notification_channel_id)
            )
            mBuilder.setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.circle_app_icon))
                .setSmallIcon(R.drawable.circle_app_icon)
                .setContentIntent(paddingIntentRedirect)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    getString(R.string.default_notification_channel_id),
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                mNotificationManager.createNotificationChannel(channel)
            }

            mNotificationManager.notify(uniqueId, mBuilder.build())


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    companion object {
        private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())
        private val TAG = MyFireBaseMessagingServices::class.java.simpleName
    }

}


