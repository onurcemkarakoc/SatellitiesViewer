package com.onurcemkarakoc.core.common.utils

class State(
    val status: Status,
    val message: String? = null,
) {
    companion object {
        fun success() = State(status = Status.SUCCESS)
        fun error(
            message: String? = null,
        ) = State(status = Status.ERROR, message)

        fun loading() =
            State(status = Status.LOADING)
    }
}

enum class Status {
    LOADING, SUCCESS, ERROR
}