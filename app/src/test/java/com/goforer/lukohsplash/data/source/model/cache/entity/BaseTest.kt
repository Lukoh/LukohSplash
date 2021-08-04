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

import com.goforer.lukohsplash.data.source.model.entity.ResponseResult
import com.goforer.test.kit.DataClassTestTool
import org.junit.Assert
import org.junit.Test

class BaseTest {
    companion object {
        val mock by lazy {
            BaseTest()
        }

        fun getInstance() = mock
    }

    val responseResult0 = ResponseResult("OK")
    private val responseResult1 = ResponseResult("OK")

    @Test
    fun responseResultTest() {
        Assert.assertTrue(DataClassTestTool.checkProperties(responseResult0, responseResult1))
    }
}