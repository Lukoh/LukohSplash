/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.lukohsplash.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.goforer.lukohsplash.presentation.ui.HomeActivity

object Caller {
    private const val SERVICE_PREPARING = "SERVICE_PREPARING"

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