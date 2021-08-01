package com.goforer.lukohsplash.data.source.model.entity.user.response

import android.os.Parcelable
import com.goforer.lukohsplash.data.source.model.entity.BaseEntity
import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collection(
    val id: String,
    val title: String,
    val description: String?,
    val published_at: String?,
    val updated_at: String?,
    val curated: Boolean?,
    val featured: Boolean?,
    val total_photos: Int,
    val private: Boolean?,
    val share_key: String?,
    val tags: List<Photo.Tag>?,
    val cover_photo: Photo?,
    val preview_photos: List<Photo>?,
    val user: User?,
    val links: Links?
) : BaseEntity(), Parcelable

@Parcelize
data class Links(
    val self: String,
    val html: String,
    val photos: String
) : BaseEntity(), Parcelable