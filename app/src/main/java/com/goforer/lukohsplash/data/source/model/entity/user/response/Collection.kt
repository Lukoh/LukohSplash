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