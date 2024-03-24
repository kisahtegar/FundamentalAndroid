package com.kisahcode.fundamentalandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

/**
 * A BroadcastReceiver responsible for receiving and processing incoming SMS messages.
 * When an SMS is received, it extracts the sender's phone number and message content,
 * and starts the SmsReceiverActivity to display the details.
 */
class SmsReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }

    /**
     * Called when a new SMS message is received.
     * Extracts sender's phone number and message content from the intent,
     * and starts the SmsReceiverActivity to display the details.
     */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (message in messages) {
                val senderNum = message.originatingAddress
                val body = message.messageBody
                Log.d(TAG, "senderNum: $senderNum; message: $message")
                val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, body)
                context.startActivity(showSmsIntent)
            }
        }
    }
}