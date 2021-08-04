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

package com.goforer.test.kit

import com.goforer.lukohsplash.presentation.vm.Query

object QueryTool {
    fun getQuery(vararg params: Any?): Query {
        val query = Query()
        params.forEachIndexed { index, any ->
            when (index) {
                0 -> query.firstParam = any ?: 0
                1 -> query.secondParam = any ?: 0
                2 -> query.thirdParam = any
                3 -> query.forthParam = any
            }
        }

        return query
    }
}