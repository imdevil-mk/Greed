package com.hold.rich.api

import com.hold.rich.api.bean.NetworkResponse
import com.hold.rich.utils.TypeUtils
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

        val dataType = getParameterUpperBound(0, innerType)

        val responseType =
            TypeUtils.ParameterizedTypeImpl(null, NetworkResponse::class.java, dataType)
        return ApiResponseCallAdapter(responseType)
    }
}

class ApiResponseCallAdapter(private val responseType: Type) :
    CallAdapter<NetworkResponse<Any>, Call<ApiResponse<Any>>> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<NetworkResponse<Any>>): Call<ApiResponse<Any>> {
        return ApiResponseCall(call)
    }
}

class ApiResponseCall(
    private val delegate: Call<NetworkResponse<Any>>,
) : Call<ApiResponse<Any>> {

    override fun enqueue(callback: Callback<ApiResponse<Any>>) {
        return delegate.enqueue(object : Callback<NetworkResponse<Any>> {
            override fun onResponse(
                call: Call<NetworkResponse<Any>>,
                response: Response<NetworkResponse<Any>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body is NetworkResponse<Any>) {
                        if (body.code == 0) {
                            callback.onResponse(
                                this@ApiResponseCall,
                                Response.success(
                                    ApiSuccessResponse(
                                        body.code,
                                        body.msg,
                                        body.data
                                    )
                                )
                            )
                        } else {
                            callback.onResponse(
                                this@ApiResponseCall,
                                Response.success(ApiBizErrorResponse(body.code, body.msg))
                            )
                        }
                    } else {
                        callback.onResponse(
                            this@ApiResponseCall,
                            Response.success(ApiErrorResponse("Unknown error"))
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

            override fun onFailure(call: Call<NetworkResponse<Any>>, t: Throwable) {
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

    override fun execute(): Response<ApiResponse<Any>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}