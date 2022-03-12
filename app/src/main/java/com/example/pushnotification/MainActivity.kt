package com.example.pushnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                FirebaseService.token = token
                findViewById<EditText>(R.id.etToken).setText(token)
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        setClickListeners()
    }

    private fun setClickListeners() {
        findViewById<Button>(R.id.btnSend).setOnClickListener {
            val title = findViewById<EditText>(R.id.etTitle).text.toString()
            val message = findViewById<EditText>(R.id.etMessage).text.toString()
            val token = findViewById<EditText>(R.id.etToken).text.toString()
            if (title.isNotEmpty() && message.isNotEmpty() && token.isNotEmpty()) {
                PushNotificationData(
                    NotificationData(title, message),
                    token
                ).also {
                    viewModel?.sendNotivication(it)
                }
            }
        }
    }
}