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

package com.goforer.lukohsplash.data.source.model.cache.entity

import com.goforer.lukohsplash.data.source.model.entity.photo.response.Photo
import com.goforer.lukohsplash.data.source.model.entity.user.response.Collection
import com.goforer.lukohsplash.data.source.model.entity.user.response.Links
import com.goforer.lukohsplash.data.source.model.entity.user.response.User

class CollectionsTest {
    companion object {
        val mock by lazy {
            CollectionsTest()
        }

        fun getInstance() = mock
    }

    private val tag1 = Photo.Tag("search", "covid-19")
    private val tag2 = Photo.Tag("search", "pandemic")
    private val tag3 = Photo.Tag("search", "coronavirus")

    private val exif = Photo.Exif("SONY", "ILCE-6000", "1/400", "3.2", "19.0", 320)

    private val profileImage = User.ProfileImage(
        "https://images.unsplash.com/profile-1619218269295-96fcdeeb674cimage?ixlib=rb-1.2.1\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026cs=tinysrgb\\u0026fit=crop\\u0026h=32\\u0026w=32",
        "https://images.unsplash.com/profile-1619218269295-96fcdeeb674cimage?ixlib=rb-1.2.1\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026cs=tinysrgb\\u0026fit=crop\\u0026h=64\\u0026w=64",
        "https://images.unsplash.com/profile-1619218269295-96fcdeeb674cimage?ixlib=rb-1.2.1\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026cs=tinysrgb\\u0026fit=crop\\u0026h=128\\u0026w=128"
    )

    private val userLinks = User.Links(
        "https://api.unsplash.com/users/jimmydean",
        "https://unsplash.com/@jimmydean",
        "https://api.unsplash.com/users/jimmydean/photos",
        "https://api.unsplash.com/users/jimmydean/likes",
        "https://api.unsplash.com/users/jimmydean/portfolio",
        "https://api.unsplash.com/users/jimmydean/following",
        "https://api.unsplash.com/users/jimmydean/followers"
    )

    private val urls = Photo.Urls(
        "https://images.unsplash.com/photo-1606787364406-a3cdf06c6d0c?ixid=MnwyNDc1MTN8MXwxfGFsbHw0MXx8fHx8fDJ8fDE2MjcyNjczNjA\\u0026ixlib=rb-1.2.1",
        "https://images.unsplash.com/photo-1606787364406-a3cdf06c6d0c?crop=entropy\\u0026cs=srgb\\u0026fm=jpg\\u0026ixid=MnwyNDc1MTN8MXwxfGFsbHw0MXx8fHx8fDJ8fDE2MjcyNjczNjA\\u0026ixlib=rb-1.2.1\\u0026q=85",
        "https://images.unsplash.com/photo-1606787364406-a3cdf06c6d0c?crop=entropy\\u0026cs=tinysrgb\\u0026fit=max\\u0026fm=jpg\\u0026ixid=MnwyNDc1MTN8MXwxfGFsbHw0MXx8fHx8fDJ8fDE2MjcyNjczNjA\\u0026ixlib=rb-1.2.1\\u0026q=80\\u0026w=1080",
        "https://images.unsplash.com/photo-1606787364406-a3cdf06c6d0c?crop=entropy\\u0026cs=tinysrgb\\u0026fit=max\\u0026fm=jpg\\u0026ixid=MnwyNDc1MTN8MXwxfGFsbHw0MXx8fHx8fDJ8fDE2MjcyNjczNjA\\u0026ixlib=rb-1.2.1\\u0026q=80\\u0026w=400",
        "https://images.unsplash.com/photo-1606787364406-a3cdf06c6d0c?crop=entropy\\u0026cs=tinysrgb\\u0026fit=max\\u0026fm=jpg\\u0026ixid=MnwyNDc1MTN8MXwxfGFsbHw0MXx8fHx8fDJ8fDE2MjcyNjczNjA\\u0026ixlib=rb-1.2.1\\u0026q=80\\u0026w=200",
    )

    private val photoLinks = Photo.Links(
        "https://api.unsplash.com/photos/Jvw3pxgeiZw",
        "https://unsplash.com/photos/Jvw3pxgeiZw",
        "https://unsplash.com/photos/Jvw3pxgeiZw/download",
        "https://api.unsplash.com/photos/Jvw3pxgeiZw/download?ixid=MnwyNDc1MTN8MXwxfGFsbHwxfHx8fHx8Mnx8MTYyNzI1OTE1OQ"
    )

    private val links = Links(
        "https://api.unsplash.com/collections/FsLhhJL-NMs",
        "https://unsplash.com/collections/FsLhhJL-NMs/twillingate-newfoundland",
        "https://api.unsplash.com/collections/FsLhhJL-NMs/photos"
    )

    private val user = User(
        "_HiPM01EbXg",
        "2021-07-25T15:24:34-04:00",
        "jimmydean",
        "Jimmy Dean",
        "Jimmy",
        "Dean",
        "jimmydean",
        "jimmydean",
        "https://www.jimmydean.com/",
        "Today’s Your Day to Shine On",
        "USA Florida",
        150,
        250,
        80,
        false,
        50,
        35,
        100,
        profileImage,
        null,
        userLinks,
    )

    val photo0 = Photo(
        "Jvw3pxgeiZw",
        "2020-11-30T20:51:24-05:00",
        "2021-07-24T23:25:18-04:00",
        4480,
        6720,
        "#f3f3f3",
        "LXLq5|%K%gRP%3?wt8IA%g%gROtR",
        null,
        "woman in orange turtleneck sweater holding brown bread",
        150,
        100,
        200,
        false,
        exif,
        null,
        listOf(tag1, tag2, tag3),
        urls,
        photoLinks,
        user
    )

    val collection0 = Collection(
        "FsLhhJL-NMs",
        "Twillingate,Newfoundland",
        "Hello Good morning",
        "2021-06-14T12:18:40-04:00",
        "2021-07-26T12:10:09-04:00",
        curated = false,
        featured = false,
        total_photos = 11,
        private = false,
        share_key = "e51e9b25c8e2d898f25e9940e8bead3e",
        tags = listOf(tag1, tag2, tag3),
        cover_photo = photo0,
        preview_photos = listOf(photo0),
        user = user,
        links = links,
    )
}