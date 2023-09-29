package com.anonimeact.moviest.models

import java.io.Serializable

data class ApiHandlerModel<out A, out B>(
    val data: A,
    val message: B
) : Serializable {
    override fun toString(): String = "($data, $message)"
}