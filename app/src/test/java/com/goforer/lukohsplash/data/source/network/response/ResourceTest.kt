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

package com.goforer.lukohsplash.data.source.network.response

import com.goforer.lukohsplash.data.source.model.entity.ResponseResult
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
@RunWith(MockitoJUnitRunner::class)
class ResourceTest : TestWatcher() {
    lateinit var resource: Resource

    @Before
    fun setup() {
        resource = Resource()
    }

    @Test
    fun successTest() {
        val success = resource.success(ResponseResult("OK"))

        Assert.assertEquals(
            success.toString(),
            "Resource{status=SUCCESS, message='null', data=ResponseResult(result=OK)}"
        )

        Assert.assertEquals(success.getMessage(), null)
        Assert.assertEquals(success.getStatus(), Status.SUCCESS)
        Assert.assertEquals(success.getData(), ResponseResult("OK"))

        Assert.assertEquals(
            success.hashCode(),
            resource.success(ResponseResult("OK")).hashCode()
        )
    }

    @Test
    fun errorTest() {
        val success = resource.error("error", 400)

        Assert.assertEquals(
            success.toString(),
            "Resource{status=ERROR, message='error', data=null}"
        )
    }

    @Test
    fun loadingTest() {
        val success = resource.loading(ResponseResult("OK"))

        Assert.assertEquals(
            success.toString(),
            "Resource{status=LOADING, message='null', data=ResponseResult(result=OK)}"
        )
    }

    @Test
    fun equalsTest() {
        val success = resource.success(ResponseResult("OK"))

        Assert.assertTrue(success == success)
        Assert.assertFalse(success.equals(null))
        Assert.assertFalse(success == Resource().error("error", 400))
        Assert.assertTrue(success == Resource().success(ResponseResult("OK")))
    }
}