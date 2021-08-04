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

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.goforer.base.extension.gone
import com.goforer.lukohsplash.databinding.DialogNormalBinding
import com.goforer.base.extension.setDefaultWindowTheme
import com.goforer.base.extension.show
import java.util.ArrayList

class NormalDialog(private val mContext: Context, private val isHorizontal: Boolean = true) :
    DialogFragment() {
    private var _binding: DialogNormalBinding? = null
    private val binding get() = _binding!!

    var mIsCloseButton = false
    var mTitle: CharSequence? = null
    var mMessage: CharSequence? = null
    var mCustomViews: ArrayList<View> = arrayListOf()

    var mPositive: CharSequence = ""
    var mPositiveButtonListener: DialogInterface.OnClickListener? = null
    var mNegative: CharSequence = ""
    var mNegativeButtonListener: DialogInterface.OnClickListener? = null

    var mOnDismissListener: DialogInterface.OnDismissListener? = null
    var mOnCancelListener: DialogInterface.OnCancelListener? = null

    var isShowing = false

    override fun onStart() {
        super.onStart()
        dialog?.setDefaultWindowTheme()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNormalBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            if (isHorizontal) {
                vButtonContainer.gone()
                hButtonContainer.show()
            } else {
                hButtonContainer.gone()
                vButtonContainer.show()
            }

            ivClose.setOnClickListener {
                dismiss()
            }
            backgroundContainer.setOnClickListener {
                dismiss()
            }
        }

        setCloseButton()
        setTitle()
        setMessage()
        setView()
        setPositiveButton()
        setNegativeButton()

        view.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun setCloseButton() {
        with(binding) {
            if (mIsCloseButton)
                ivClose.show()
            else
                ivClose.gone()
        }
    }

    fun setTitle() {
        with(binding) {
            if (mTitle != null) {
                tvTitle.show()
                tvTitle.text = mTitle
            } else {
                tvTitle.gone()
            }
        }
    }

    fun setMessage() {
        with(binding) {
            if (mMessage != null) {
                tvMessage.show()
                tvMessage.text = mMessage
            } else {
                tvMessage.gone()
            }
        }
    }

    fun setView() {
        for (view in mCustomViews) {
            view.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.contentsContainer.addView(view)
        }
    }

    fun setPositiveButton() {
        if (isHorizontal) {
            binding.tvHPositive
            binding.tvHPositive.text = mPositive
            binding.tvHPositive.setOnClickListener {
                mPositiveButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_POSITIVE)
                dismiss()
            }
        } else {
            binding.tvVPositive.text = mPositive
            binding.tvVPositive.setOnClickListener {
                mPositiveButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_POSITIVE)
                dismiss()
            }
        }

    }

    fun setNegativeButton() {
        if (isHorizontal) {
            if (mNegativeButtonListener != null) {
                binding.tvHNegative.show()
                binding.tvHNegative.text = mNegative
                binding.tvHNegative.setOnClickListener {
                    mNegativeButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_NEGATIVE)
                    dismiss()
                }
            } else {
                binding.tvHNegative.gone()
            }
        } else {
            if (mNegativeButtonListener != null) {
                binding.tvVNegative.show()
                binding.tvVNegative.text = mNegative
                binding.tvVNegative.setOnClickListener {
                    mNegativeButtonListener?.onClick(this.dialog, DialogInterface.BUTTON_NEGATIVE)
                    dismiss()
                }
            } else {
                binding.tvVNegative.gone()
            }
        }
    }

    private fun runMailChooser(mailTo: String) {
        val i = Intent(Intent.ACTION_SEND)

        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(mailTo))
        runCatching {
            startActivity(Intent.createChooser(i, "Choose Mail Client"))
        }.onFailure {
            Toast.makeText(
                this.mContext,
                "There is no email client installed.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnDismissListener?.onDismiss(dialog)
        mOnDismissListener = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        mOnCancelListener?.onCancel(dialog)
        mOnDismissListener = null
    }

    class Builder(val context: Context, isHorizontal: Boolean = true) {
        var dialog = NormalDialog(context, isHorizontal)

        fun setCloseButton(isCloseButton: Boolean): Builder {
            dialog.mIsCloseButton = isCloseButton
            if (dialog.isShowing)
                dialog.setCloseButton()

            return this
        }

        fun setTitle(text: CharSequence): Builder {
            dialog.mTitle = text

            if (dialog.isShowing)
                dialog.setTitle()

            return this
        }

        fun setTitle(@StringRes textId: Int): Builder {
            dialog.mTitle = context.getString(textId)

            if (dialog.isShowing)
                dialog.setTitle()

            return this
        }

        fun setMessage(text: CharSequence): Builder {
            dialog.mMessage = text
            if (dialog.isShowing)
                dialog.setMessage()

            return this
        }

        fun setMessage(@StringRes textId: Int): Builder {
            dialog.mMessage = context.getString(textId)

            if (dialog.isShowing)
                dialog.setMessage()

            return this
        }

        fun setView(view: View): Builder {
            dialog.mCustomViews.add(view)

            if (dialog.isShowing)
                dialog.setView()

            return this
        }

        fun setPositiveButton(
            text: CharSequence,
            listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.mPositive = text
            dialog.mPositiveButtonListener = DialogInterface.OnClickListener(listener)

            if (dialog.isShowing)
                dialog.setPositiveButton()

            return this
        }

        fun setPositiveButton(
            @StringRes textId: Int,
            listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.mPositive = context.getString(textId)
            dialog.mPositiveButtonListener = DialogInterface.OnClickListener(listener)

            if (dialog.isShowing)
                dialog.setPositiveButton()

            return this
        }

        fun setNegativeButton(
            text: CharSequence,
            listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.mNegative = text
            dialog.mNegativeButtonListener = DialogInterface.OnClickListener(listener)

            if (dialog.isShowing)
                dialog.setNegativeButton()

            return this
        }

        fun setNegativeButton(
            @StringRes textId: Int,
            listener: (DialogInterface, Int) -> Unit
        ): Builder {
            dialog.mNegative = context.getString(textId)
            dialog.mNegativeButtonListener = DialogInterface.OnClickListener(listener)

            if (dialog.isShowing)
                dialog.setNegativeButton()

            return this
        }

        fun setOnDismissListener(onDismiss: (DialogInterface) -> Unit): Builder {
            dialog.mOnDismissListener = DialogInterface.OnDismissListener(onDismiss)

            return this
        }

        fun setOnDismissListener(onDismiss: DialogInterface.OnDismissListener? = null): Builder {
            dialog.mOnDismissListener = onDismiss

            return this
        }

        fun setOnCancelListener(onCancel: (DialogInterface) -> Unit): Builder {
            dialog.mOnCancelListener = DialogInterface.OnCancelListener(onCancel)

            return this
        }

        fun setOnCancelListener(onCancel: DialogInterface.OnCancelListener? = null): Builder {
            dialog.mOnCancelListener = onCancel

            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            dialog.isCancelable = cancelable

            return this
        }

        fun show(manager: FragmentManager): NormalDialog {
            dialog.show(manager, null)
            dialog.isShowing = true

            return dialog
        }

        fun show(transaction: FragmentTransaction): NormalDialog {
            dialog.show(transaction, null)
            dialog.isShowing = true

            return dialog
        }
    }
}