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

import com.goforer.lukohsplash.data.source.model.entity.user.response.User

class UserTest {
    companion object {
        val mock by lazy {
            UserTest()
        }

        fun getInstance() = mock
    }

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

    internal val user = User(
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
}