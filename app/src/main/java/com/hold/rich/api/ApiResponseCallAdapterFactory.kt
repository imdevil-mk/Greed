package com.hold.rich.api

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private const val TAG = "Greedy-ApiResponseCallAdapterF"

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        val innerType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawInnerType = getRawType(innerType)
        if (rawInnerType != ApiResponse::class.java) {
            return null
        }
        if (innerType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

        val bodyType = getParameterUpperBound(0, innerType)

        return ApiResponseCallAdapter<Any>(bodyType)
    }
}

class ApiResponseCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<ApiResponse<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<ApiResponse<R>> {
        return ApiResponseCall(call)
    }
}

class ApiResponseCall<R>(
    private val delegate: Call<R>,
) : Call<ApiResponse<R>> {
    override fun enqueue(callback: Callback<ApiResponse<R>>) {
        return delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body is ApiSuccessResponse<*>) {
                        if (body.code == 0) {
                            callback.onResponse(
                                this@ApiResponseCall,
                                Response.success(
                                    ApiSuccessResponse(
                                        body.code,
                                        body.errorMessage,
                                        body.data as R
                                    )
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@ApiResponseCall,
                                Response.success(ApiBizErrorResponse(body.code, body.errorMessage))
                            )
                        }
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiErrorResponse("Unkown error"))
                        )
                    }
                } else {
                    val t = HttpException(response)
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiErrorResponse(t.toString()))
                    )
                }
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                Log.d(TAG, "onFailure: ")
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiErrorResponse(t.toString()))
                )
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<R>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}