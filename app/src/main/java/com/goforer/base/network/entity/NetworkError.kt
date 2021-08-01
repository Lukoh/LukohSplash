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

package com.bluewhale.base.network.entity

data class NetworkError(val error: ErrorBody) {
    data class ErrorBody(val code: String?, var message: String = ERROR_DEFAULT)

    companion object {
        const val ERROR_DEFAULT = "An unexpected error has occurred"
        const val ERROR_SERVICE_UNAVAILABLE = 503
        const val ERROR_SERVICE_BAD_GATEWAY = 502
    }
}