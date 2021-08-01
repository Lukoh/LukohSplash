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

package com.goforer.base.extension

inline fun <reified T> T?.isNull(
    crossinline doOnIsNull: () -> Unit,
    crossinline doOnIsNotNull: (T) -> Unit) {
    this ?: doOnIsNull.invoke()
    this?.let(doOnIsNotNull)
}

suspend inline fun <reified T> T?.isNullOnFlow(
    crossinline doOnIsNull: suspend () -> Unit,
    crossinline doOnIsNotNull: suspend (T) -> Unit) {
    this ?: doOnIsNull.invoke()
    this?.let {
        doOnIsNotNull.invoke(it)
    }
}