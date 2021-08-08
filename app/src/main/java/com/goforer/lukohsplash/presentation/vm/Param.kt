package com.goforer.lukohsplash.presentation.vm

object Param {
    private lateinit var params: Params

    fun setParams(params: Params) {
        this.params = params
    }

    fun getParams() = this.params
}