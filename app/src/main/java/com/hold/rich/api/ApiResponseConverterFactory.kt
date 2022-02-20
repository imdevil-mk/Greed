package com.hold.rich.api

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.hold.rich.api.bean.NetworkResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

private const val TAG = "Greedy-ApiResponseConverterFac"

object ApiResponseConverterFactory : Converter.Factory() {

    private var gson: Gson = Gson()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (getRawType(type) != NetworkResponse::class.java) {
            return null
        }
        return ApiResponseConverter(gson, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}

class ApiResponseConverter(
    private val gson: Gson,
    private val type: Type,
) : Converter<ResponseBody, Any> {
    override fun convert(value: ResponseBody): Any {
        val adapter = gson.getAdapter(TypeToken.get(type))
        val jsonReader = gson.newJsonReader(value.charStream())

        return value.use {
            val result: Any = adapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        }
    }
}