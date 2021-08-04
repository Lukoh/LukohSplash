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

package com.goforer.test.kit.flow

import kotlinx.coroutines.flow.Flow
import org.junit.Assert
import java.util.*
import kotlin.reflect.KClass

internal class FlowTestCollectorImpl<T>(
    flow: Flow<T>
) : BaseTestFlowCollector<T>(flow) {

    // region getters

    override suspend fun isCompleted(): Boolean {
        return hasCompletedInternal()
    }

    override suspend fun valueAt(index: Int): T {
        val actualValues = flowValues()
        if (index !in 0..actualValues.lastIndex) {
            Assert.fail("IndexOutOfBoundsException: cannot access index [$index], list has [${actualValues.size}] items")
        }
        return actualValues[index]
    }

    override suspend fun values(): List<T> {
        return Collections.unmodifiableList(flowValues())
    }

    override suspend fun value(): T {
        return flowValue()
    }

    override suspend fun valuesCount(): Int {
        return flowValues().size
    }

    override suspend fun error(): Throwable? {
        return when (val error = errorInternal()) {
            is Error.Wrapped -> error.throwable
            is Error.Empty -> null
        }
    }

    // endregion

    // region assertions

    override suspend fun assertComplete(): FlowTestCollector<T> {
        Assert.assertTrue("Expected [Complete], but was [Not Complete]", isCompleted())
        return this
    }

    override suspend fun assertNotComplete(): FlowTestCollector<T> {
        Assert.assertFalse("Expected [Not Complete], but was [Complete]", isCompleted())
        return this
    }

    override suspend fun assertNoErrors(): FlowTestCollector<T> {
        when (val error = errorInternal()) {
            is Error.Wrapped -> Assert.fail(
                "Expected [No Errors], but failed with [${error.throwable}]"
            )
        }
        return this
    }

    override suspend fun assertError(javaClass: Class<out Throwable>): FlowTestCollector<T> {
        assertError { it::class.java == javaClass }
        return this
    }

    override suspend fun assertError(kotlinClass: KClass<out Throwable>): FlowTestCollector<T> {
        assertError { it::class.java == kotlinClass.java }
        return this
    }

    override suspend fun assertError(predicate: (Throwable) -> Boolean): FlowTestCollector<T> {
        val error = errorInternal()

        Assert.assertTrue(
            "Expected [Exception], but no Exception was thrown",
            error is Error.Wrapped
        )
        require(error is Error.Wrapped)
        Assert.assertTrue(
            "Predicate doesn't match, error [${error.throwable}]",
            predicate(error.throwable)
        )
        return this
    }

    override suspend fun assertErrorMessage(message: String): FlowTestCollector<T> {
        val error = errorInternal()
        require(error is Error.Wrapped)
        Assert.assertTrue(
            "Expected error message [${message}], but was [${error.throwable.message}]",
            message == error.throwable.message
        )
        Assert.assertEquals(message, error.throwable.message)
        return this
    }

    override suspend fun assertValue(value: T): FlowTestCollector<T> {
        if (flowValues().size != 1) {
            Assert.fail("Expected exactly 1 value [$value], but values were ${flowValues()}")
        }
        val first = valueAt(0)
        if (value != first) {
            Assert.fail("Expected [$value], but was [$first]")
        }
        return this
    }

    override suspend fun assertValue(predicate: (T) -> Boolean): FlowTestCollector<T> {
        if (flowValues().size != 1) {
            Assert.fail("Expected exactly 1 value, but values were ${flowValues()}")
        }

        val first = valueAt(0)
        Assert.assertTrue("Predicate doesn't match", predicate(first))
        return this
    }

    override suspend fun assertValueIsNull(): FlowTestCollector<T> {
        if (flowValues().size != 1) {
            Assert.fail("Expected exactly 1 value [null], but values were ${flowValues()}")
        }
        val first = valueAt(0)
        Assert.assertTrue("Expected [null], but was [$first]", first == null)
        return this
    }

    override suspend fun assertValueAt(index: Int, value: T): FlowTestCollector<T> {
        val actualValue = valueAt(index)
        if (value != actualValue) {
            Assert.fail("Expected [$value], but was [$actualValue]")
        }
        return this
    }

    override suspend fun assertValueAt(
        index: Int,
        predicate: (T) -> Boolean
    ): FlowTestCollector<T> {
        val actualValue = valueAt(index)
        if (!predicate(actualValue)) {
            Assert.fail("Predicate doesn't match for [$actualValue]")
        }
        return this
    }

    override suspend fun assertValues(vararg values: T): FlowTestCollector<T> {
        val expectedValues = values.toList()
        val actualValues = flowValues()
        if (expectedValues != actualValues) {
            Assert.fail("Expected $expectedValues, but was $actualValues")
        }
        return this
    }

    override suspend fun assertNoValues(): FlowTestCollector<T> {
        val actualValues = flowValues()
        if (actualValues.isNotEmpty()) {
            Assert.fail("Expected no values, but was $actualValues")
        }
        return this
    }

    override suspend fun assertValueCount(count: Int): FlowTestCollector<T> {
        val actualSize = flowValues().size
        if (actualSize != count) {
            Assert.fail("Expected [$count] items, but was [$actualSize]")
        }
        return this
    }
}