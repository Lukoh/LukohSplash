/*
 * Copyright (C)  2020 Blue-Ocean
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.goforer.lukohsplash.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Singleton

/**
 * Executes business logic in its execute method and keep posting updates to the result as
 * [Result<R>].
 */

@Singleton
abstract class UseCase<in Param, Resource> {
    companion object {
        internal const val CORRECTION_Y_VALUE = 356
    }
    abstract fun run(viewModelScope: CoroutineScope, params: Param): SharedFlow<Resource>
}