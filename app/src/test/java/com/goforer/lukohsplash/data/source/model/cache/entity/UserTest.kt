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