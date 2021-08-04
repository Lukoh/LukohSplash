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

@file:Suppress("KDocUnresolvedReference")

package com.goforer.lukohsplash.presentation.vm

import javax.inject.Singleton

/**
 * set Queries to input the parameters to connect with REST API
 *
 * @param firstParam if the firstParam is -1, it means there is no first parameter to input REST API
 * @param secondParam if the secondParam is -1, it means there is no second parameter to input REST API
 */
@Singleton
class Query {
    lateinit var firstParam: Any
    lateinit var secondParam: Any
    var thirdParam: Any? = null
    var forthParam: Any? = null
}