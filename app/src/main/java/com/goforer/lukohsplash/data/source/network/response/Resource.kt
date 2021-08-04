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

package com.goforer.lukohsplash.data.source.network.response

class Resource {
    private lateinit var status: Status

    private var message: String? = null

    private var data: Any? = null

    internal var errorCode: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as Resource?

        if (status !== resource?.status) {
            return false
        }

        if (if (message != null) message == resource.message else resource.message == null)
            if (if (data != null) data == resource.data else resource.data == null) return true

        return false
    }

    override fun hashCode(): Int {
        var result = status.hashCode()

        result = 31 * result + if (message != null) message!!.hashCode() else 0
        result = 31 * result + if (data != null) data?.hashCode()!! else 0

        return result
    }

    override fun toString(): String =
        "Resource{" +
            "status=" + status +
            ", message='" + message + '\''.toString() +
            ", data=" + data +
            '}'.toString()

    internal fun success(data: Any?): Resource {
        status = Status.SUCCESS
        this.errorCode = 0
        this.data = data
        message = null

        return this
    }

    internal fun error(msg: String?, errorCode: Int): Resource {
        status = Status.ERROR
        this.errorCode = errorCode
        this.data = null
        message = msg

        return this
    }

    internal fun loading(data: Any?): Resource {
        status = Status.LOADING
        this.errorCode = 0
        this.data = data
        message = null

        return this
    }

    internal fun getMessage(): String? = message
    internal fun getStatus(): Status = status
    internal fun getData(): Any? = data
}
