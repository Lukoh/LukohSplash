package com.goforer.base.view.dialog

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.databinding.DialogLoadingBinding
import com.goforer.base.extension.setDefaultWindowTheme

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

        dialog?.setCancelable(false)

        val animationDrawable = binding.ivLoading.drawable as AnimationDrawable

        animationDrawable.start()
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