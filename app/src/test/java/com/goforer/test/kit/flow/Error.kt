package com.goforer.test.kit.flow

internal sealed class Error {
    object Empty : Error()
    data class Wrapped(val throwable: Throwable) : Error()
}