package com.goforer.lukohsplash.data.source.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseResult(
    val result: String
) : BaseEntity(), Parcelable