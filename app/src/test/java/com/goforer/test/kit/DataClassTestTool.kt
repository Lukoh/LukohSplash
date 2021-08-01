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