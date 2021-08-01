package com.goforer.lukohsplash.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.goforer.lukohsplash.databinding.FragmentSettingBinding
import com.goforer.lukohsplash.presentation.ui.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding
        get() = FragmentSettingBinding::inflate
}