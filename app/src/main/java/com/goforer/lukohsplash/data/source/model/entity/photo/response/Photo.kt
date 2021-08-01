package com.goforer.lukohsplash.data.source.model.entity.photo.response

import android.os.Parcelable
import com.goforer.lukohsplash.data.source.model.entity.BaseEntity
import com.goforer.lukohsplash.data.source.model.entity.user.response.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val created_at: String?,
    val updated_at: String?,
    val width: Int?,
    val height: Int?,
    val color: String? = "#E0E0E0",
    val blur_hash: String?,
    val description: String?,
    val alt_description: String?,
    val views: Int?,
    val downloads: Int?,
    val likes: Int?,
    var liked_by_user: Boolean?,
    val exif: Exif?,
    val location: Location?,
    val tags: List<Tag>?,
    val urls: Urls,
    val links: Links?,
    val user: User?,
) : BaseEntity(), Parcelable {
    @Parcelize
    data class Exif(
        val make: String?,
        val model: String?,
        val exposure_time: String?,
        val aperture: String?,
        val focal_length: String?,
        val iso: Int?
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Location(
        val city: String?,
        val country: String?,
        val position: Position?
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Position(
        val latitude: Double?,
        val longitude: Double?
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Tag(
        val type: String?,
        val title: String?
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Links(
        val self: String,
        val html: String,
        val download: String,
        val download_location: String
    ) : BaseEntity(), Parcelable
}