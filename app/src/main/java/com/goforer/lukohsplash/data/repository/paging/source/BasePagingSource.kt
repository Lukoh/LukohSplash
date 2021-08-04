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

package com.goforer.lukohsplash.data.repository.paging.source

import androidx.paging.PagingSource
import com.goforer.lukohsplash.data.repository.paging.PagingErrorMessage
import com.goforer.lukohsplash.data.source.network.api.RestAPI
import com.goforer.lukohsplash.presentation.vm.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class BasePagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {
    protected lateinit var pagingList: MutableList<Value>

    protected var errorMessage = PagingErrorMessage.ERROR_MESSAGE_PAGING_EMPTY

    protected lateinit var query: Query

    @Inject
    internal lateinit var restAPI: RestAPI

    abstract fun setData(query: Query, value: MutableList<Value>)
}