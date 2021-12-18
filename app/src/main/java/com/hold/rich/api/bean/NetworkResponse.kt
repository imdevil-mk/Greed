package com.hold.rich.api.bean

data class NetworkResponse<T>(
    val code: String,
    val data: List<T>,
    val msg: String,
)