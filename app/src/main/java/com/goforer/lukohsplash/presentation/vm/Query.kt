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