package com.example.foodconnect

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.isNotEmpty().let {
            Log.d("FCM_Data", "Received: ${remoteMessage.data}")

            val foodId = remoteMessage.data["foodId"]
            val location = remoteMessage.data["location"]
            val quantity = remoteMessage.data["quantity"]
            val foodName = remoteMessage.data["foodName"]
            val hotelName = remoteMessage.data["hotelName"]
            val preparationTime = remoteMessage.data["preparationTime"]
            val expiryTime = remoteMessage.data["expiryTime"]
            val contactNumber = remoteMessage.data["contactNumber"]
            val specialNotes = remoteMessage.data["specialNotes"]

            sendNotification(
                foodId, location, quantity, foodName, hotelName,
                preparationTime, expiryTime, contactNumber, specialNotes
            )
        }
    }

    private fun sendNotification(
        foodId: String?, location: String?, quantity: String?,
        foodName: String?, hotelName: String?, preparationTime: String?,
        expiryTime: String?, contactNumber: String?, specialNotes: String?
    ) {
        val intent = Intent(this, VolunteerFrontpage::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("openFragment", "VolunteerNotificationFragment")
            putExtra("foodId", foodId)
            putExtra("location", location)
            putExtra("quantity", quantity)
            putExtra("foodName", foodName)
            putExtra("hotelName", hotelName)
            putExtra("preparationTime", preparationTime)
            putExtra("expiryTime", expiryTime)
            putExtra("contactNumber", contactNumber)
            putExtra("specialNotes", specialNotes)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, "your_channel_id")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("New Food Available!")
            .setContentText("Food: $foodName, Location: $location")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}




