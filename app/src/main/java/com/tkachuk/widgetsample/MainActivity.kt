package com.tkachuk.widgetsample

import android.annotation.TargetApi
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val REQUEST_BIND_WIDGET = 1

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mAppWidgetManager: AppWidgetManager = getSystemService(AppWidgetManager::class.java)
        val myWidgetProviderInfo = AppWidgetProviderInfo();
        val myProvider = myWidgetProviderInfo.provider

        if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the widget to be pinned. Note that, if the pinning
            // operation fails, your app isn't notified.
            val intent: Intent = Intent(this, MainActivity::class.java)

            // Configure the intent so that your app's broadcast receiver gets
            // the callback successfully. This callback receives the ID of the
            // newly-pinned widget (EXTRA_APPWIDGET_ID).
            val  successCallback: PendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0)

            mAppWidgetManager.requestPinAppWidget(myProvider, null, successCallback);
        }


        textView.setOnClickListener {
//            if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
//                Toast.makeText(this, "isRequestPinAppWidgetSupported", Toast.LENGTH_SHORT).show()
//                mAppWidgetManager.requestPinAppWidget(myProvider, null, null)
//            }
             Toast.makeText(this, "send broadcast", Toast.LENGTH_SHORT).show()

            val intent = Intent()
            intent.action = "com.android.launcher3.action.AUTOADDWIDGET"

            //where to sent
            intent.component = ComponentName("com.android.launcher3", "com.android.launcher3.AutoAddWidgetReceiver")

            //wich widget to sent
            intent.putExtra("packageName", "com.tkachuk.widgetsample")
            intent.putExtra("className", "com.tkachuk.widgetsample.NewAppWidget")

            sendBroadcast(intent)
        }
    }
}
