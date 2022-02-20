package com.hold.rich.api

sealed class ApiResponse<T>

data class ApiSuccessResponse<T>(val code: Int, val errorMessage: String, val data: List<T>) :
    ApiResponse<T>()

data class ApiBizErrorResponse<T>(val code: Int, val errorMessage: String) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()