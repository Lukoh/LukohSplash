package com.goforer.test.kit

import com.goforer.lukohsplash.presentation.vm.Query

object QueryTool {
    fun getQuery(vararg params: Any?): Query {
        val query = Query()
        params.forEachIndexed { index, any ->
            when (index) {
                0 -> query.firstParam = any ?: 0
                1 -> query.secondParam = any ?: 0
                2 -> query.thirdParam = any
                3 -> query.forthParam = any
            }
        }

        return query
    }
}