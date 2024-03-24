package com.kisahcode.fundamentalandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kisahcode.fundamentalandroid.databinding.ActivitySmsReceiverBinding


/**
 * An activity responsible for displaying details of received SMS messages.
 * It shows the sender's phone number and the message content.
 */
class SmsReceiverActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }

    private var binding: ActivitySmsReceiverBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmsReceiverBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set the title of the activity
        title = getString(R.string.incoming_message)

        // Set a click listener for the close button to finish the activity
        binding?.btnClose?.setOnClickListener {
            finish()
        }

        // Retrieve sender's phone number and message from intent extras
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)

        // Set sender's phone number and message to corresponding TextViews
        binding?.tvFrom?.text = getString(R.string.from, senderNo)
        binding?.tvMessage?.text = senderMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}