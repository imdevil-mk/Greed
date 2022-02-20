package com.hold.rich.api.bean

data class NetworkResponse<T>(
    val code: Int,
    val data: List<T>,
    val msg: String,
)