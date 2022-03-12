package com.example.pushnotification

data class PushNotificationData(
    val data: NotificationData,
    val to: String
)