package com.goforer.lukohsplash.data.source.model.entity.photo

import android.os.Parcelable
import com.goforer.lukohsplash.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class SaveFileInfo(
    val url: String,
    val file: File,
    val fileName: String
) : BaseEntity(), Parcelable