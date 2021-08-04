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

package com.goforer.lukohsplash.data.source.model.entity.user.response

import android.os.Parcelable
import com.goforer.lukohsplash.data.source.model.entity.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val updated_at: String?,
    val username: String?,
    val name: String?,
    val first_name: String?,
    val last_name: String?,
    val instagram_username: String?,
    val twitter_username: String?,
    val portfolio_url: String?,
    val bio: String?,
    val location: String?,
    val total_likes: Int?,
    val total_photos: Int?,
    val total_collections: Int?,
    val followed_by_user: Boolean?,
    val followers_count: Int?,
    val following_count: Int?,
    val downloads: Int?,
    val profile_image: ProfileImage?,
    val badge: Badge?,
    val links: Links?,
) : BaseEntity(), Parcelable {
    @Parcelize
    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Badge(
        val title: String?,
        val primary: Boolean?,
        val slug: String?,
        val link: String?
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Links(
        val self: String,
        val html: String,
        val photos: String,
        val likes: String,
        val portfolio: String,
        val following: String,
        val followers: String
    ) : BaseEntity(), Parcelable
}