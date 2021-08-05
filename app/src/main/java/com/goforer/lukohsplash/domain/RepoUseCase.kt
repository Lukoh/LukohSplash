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

package com.goforer.lukohsplash.domain

import com.goforer.lukohsplash.data.repository.Repository
import com.goforer.lukohsplash.data.source.network.response.Resource
import com.goforer.lukohsplash.presentation.vm.Params
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
abstract class RepoUseCase(open val repository: Repository<Resource>) :
    UseCase<Params, Resource>() {
    override fun run(lifecycleScope: CoroutineScope, params: Params) =
        repository.doWork(lifecycleScope, params.query)
}