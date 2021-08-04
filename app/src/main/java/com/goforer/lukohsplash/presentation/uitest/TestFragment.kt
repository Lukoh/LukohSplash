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

package com.goforer.lukohsplash.presentation.uitest

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.databinding.FragmentTestBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestBinding
        get() = FragmentTestBinding::inflate

    private lateinit var listener: PermissionCallback

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val grantedPermission = HashMap<Int, TestFragment.Permission>()
            permissions.entries.forEachIndexed { index, item ->
                grantedPermission[index] = Permission(item.key, item.value)
            }

            if (grantedPermission[0]?.isGranted!! && grantedPermission[1]?.isGranted!!) {
                listener.onPermissionGranted()
            } else {
                val showRationale =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        homeActivity, grantedPermission[0]?.permissionName!!
                    ) || ActivityCompat.shouldShowRequestPermissionRationale(
                        homeActivity, grantedPermission[1]?.permissionName!!
                    )
                if (!showRationale) {
                    createDialogForStoragePermission()
                }
            }
        }

    interface PermissionCallback {
        fun onPermissionGranted()
    }

    inner class Permission(val permissionName: String, val isGranted: Boolean)

    internal fun setupPermission(callback: PermissionCallback) {
        listener = callback
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        )
    }

    private fun createDialogForStoragePermission() {
        NormalDialog.Builder(homeActivity.applicationContext)
            .setMessage(R.string.request_permission)
            .setPositiveButton(R.string.ok) { _, _ ->
                startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:${activity?.packageName}"))
                )
            }.setNegativeButton(R.string.cancel) { _, _ ->
            }.show(childFragmentManager)
    }
}