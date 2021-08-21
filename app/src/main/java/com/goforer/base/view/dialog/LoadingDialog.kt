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

package com.goforer.base.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.goforer.base.extension.setDefaultWindowTheme
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.databinding.DialogLoadingBinding

open class LoadingDialog : DialogFragment() {
    private var _binding: DialogLoadingBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setDefaultWindowTheme()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.LoadingDialogTransparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLoadingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loLoading.playAnimation()

        dialog?.setCancelable(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun isShowing(): Boolean {
        return if (dialog != null) {
            dialog?.isShowing ?: false
        } else {
            dismiss()
            false
        }
    }
}
