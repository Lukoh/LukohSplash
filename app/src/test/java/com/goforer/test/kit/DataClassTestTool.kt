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

import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

object DataClassTestTool {
    inline fun <reified T : Any> checkProperties(objectA: T, objectB: T): Boolean {
        T::class.declaredMemberProperties.forEach { kCallable ->
            if (readInstanceProperty<Any>(
                    objectA,
                    kCallable.name
                ) !=
                readInstanceProperty<Any>(
                    objectB,
                    kCallable.name
                )
            )
                return false

        }
        return true
    }

    @Suppress("UNCHECKED_CAST")
    fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
        val property = instance::class.declaredMemberProperties
            .first { it.name == propertyName } as KProperty1<Any, *>

        return property.get(instance) as R
    }
}