package com.goforer.test.kit.flow

import kotlin.reflect.KClass

/**
 * Computes eagerly flow values to allow testing, works with both cold/finite and hot/infinite streams.
 */
interface FlowTestCollector<T> {

    // region getters

    /**
     * @return true if the given flow is completed or cancelled.
     */
    suspend fun isCompleted(): Boolean

    /**
     * @return the value at the given index
     */
    suspend fun valueAt(index: Int): T

    /**
    * @return the given value
    */
    suspend fun value(): T

    /**
     * @return all the values emitted from the given flow.
     */
    suspend fun values(): List<T>

    /**
     * @return the number of values emitted from given flow
     */
    suspend fun valuesCount(): Int

    /**
     * @return the error, if any
     */
    suspend fun error(): Throwable?

    // endregion

    // region assertions

    /**
     * Assert that the given flow is completed or cancelled
     * @return this
     */
    suspend fun assertComplete(): FlowTestCollector<T>

    /**
     * Assert that the given flow is not completed or cancelled
     * @return this
     */
    suspend fun assertNotComplete(): FlowTestCollector<T>

    /**
     * Assert that the given flow has not received any errors.
     * @return this
     */
    suspend fun assertNoErrors(): FlowTestCollector<T>

    /**
     * Asserts that the given flow received an error which is an
     * instance of the specified errorClass class.
     * @return this
     */
    suspend fun assertError(javaClass: Class<out Throwable>): FlowTestCollector<T>

    // since 1.4.2
    /**
     * Asserts that the given flow received an error which is an
     * instance of the specified errorClass class.
     * @return this
     */
    suspend fun assertError(kotlinClass: KClass<out Throwable>): FlowTestCollector<T>

    /**
     * Asserts that the given flow received an error for which
     * the provided predicate returns true.
     * @return this
     */
    suspend fun assertError(predicate: (Throwable) -> Boolean): FlowTestCollector<T>

    /**
     * Assert that the given flow received exactly one value.
     * @return this
     */
    suspend fun assertValue(value: T): FlowTestCollector<T>

    /**
     * Assert that the given flow received exactly one value for which
     * the provided predicate returns true.
     * @return this
     */
    suspend fun assertValue(predicate: (T) -> Boolean): FlowTestCollector<T>

    /**
     * Assert that the given flow received exactly one value and this equals to null
     * @since 1.1.0
     * @return this
     */
    suspend fun assertValueIsNull(): FlowTestCollector<T>

    /**
     * Asserts that the given flow received exactly one value at the given index
     * which is equal to the given value.
     * @return this
     */
    suspend fun assertValueAt(index: Int, value: T): FlowTestCollector<T>

    /**
     * Asserts that the given flow received a value at the given index for which
     * the provided predicate returns true.
     * @return this
     */
    suspend fun assertValueAt(index: Int, predicate: (T) -> Boolean): FlowTestCollector<T>

    /**
     * Assert that the given flow received only the specified values in the specified order.
     * @return this
     */
    suspend fun assertValues(vararg values: T): FlowTestCollector<T>

    /**
     * Assert that the given flow received the specified number of values.
     * @return this
     */
    suspend fun assertValueCount(count: Int): FlowTestCollector<T>

    /**
     * Assert that the given flow has not received any value.
     * @return this
     */
    suspend fun assertNoValues(): FlowTestCollector<T>

    /**
     * Assert that the error has the given message.
     * @param message the message expected
     * @return this
     */
    suspend fun assertErrorMessage(message: String): FlowTestCollector<T>

    // endregion

}