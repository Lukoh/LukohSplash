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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withTimeout

internal abstract class BaseTestFlowCollector<T>(
    private val flow: Flow<T>
) : FlowTestCollector<T> {

    private var _hasCompleted: Boolean? = null
    private var _flowValues: List<T>? = null
    private var _flowValue: T? = null
    private var _error: Error? = null

    private fun isValuesNotInitialized(): Boolean {
        return _flowValues == null ||
            _hasCompleted == null ||
            _error == null
    }

    private fun isValueNotInitialized(): Boolean {
        return _flowValue == null ||
            _hasCompleted == null ||
            _error == null
    }

    private suspend fun setup() {
        if (isValuesNotInitialized()) {
            initializeValues()
        }

        if (isValueNotInitialized()) {
            initializeValue()
        }

        require(_hasCompleted != null)
        require(_flowValues != null)
        require(_error != null)
    }

    private suspend fun initializeValues() {
        val values = mutableListOf<T>()

        runCatching {
            withTimeout(Long.MAX_VALUE) { // needed in order to work with delay()
                flow.onCompletion { _hasCompleted = true }
                    .catch { _error = Error.Wrapped(it) }
                    .collect { values.add(it) }

                if (_error !is Error.Wrapped) {
                    _error = Error.Empty
                }
            }
        }.onFailure { e ->
            _hasCompleted = false
            _error = Error.Empty
        }

        _flowValues = values
    }

    private suspend fun initializeValue() {
        var value: T? = null

        runCatching {
            withTimeout(Long.MAX_VALUE) { // needed in order to work with delay()
                flow.onCompletion { _hasCompleted = true }
                    .catch { _error = Error.Wrapped(it) }
                    .collect { value = it }

                if (_error !is Error.Wrapped) {
                    _error = Error.Empty
                }
            }
        }.onFailure { e ->
            _hasCompleted = false
            _error = Error.Empty
        }

        _flowValue = value
    }

    protected suspend fun flowValues(): List<T> {
        setup()
        return _flowValues!!
    }

    protected suspend fun flowValue(): T {
        setup()

        return _flowValue!!
    }


    protected suspend fun hasCompletedInternal(): Boolean {
        setup()
        return _hasCompleted!!
    }

    protected suspend fun errorInternal(): Error {
        setup()
        return _error!!
    }
}