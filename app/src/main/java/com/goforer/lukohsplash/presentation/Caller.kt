package com.goforer.lukohsplash.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.goforer.lukohsplash.presentation.ui.HomeActivity

object Caller {
    const val SERVICE_PREPARING = "SERVICE_PREPARING"

    internal fun closeApp(activity: Activity) {
        activity.finishAffinity()
        activity.moveTaskToBack(true)
    }

    internal fun preparingService(context: Context) {
        val intent = Intent(context, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(SERVICE_PREPARING, true)
        }

        context.startActivity(intent)
    }
}