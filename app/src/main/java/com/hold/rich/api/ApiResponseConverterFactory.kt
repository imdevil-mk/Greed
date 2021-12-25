package com.hold.rich.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
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
    ): Converter<ResponseBody, *> {
        val adapter: TypeAdapter<Any> = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<Any>
        return ApiResponseConverter(gson, adapter)
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

class ApiResponseConverter<T>(
    private val gson: Gson,
    private val adapter: TypeAdapter<T>,
) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T? {
        Log.d(TAG, "convert: ")
        val jsonReader = gson.newJsonReader(value.charStream())
        jsonReader.beginObject()
        var code = -1
        var msg = ""
        var data: T? = null
        while (jsonReader.hasNext()) {
            when (jsonReader.nextName()) {
                "code" -> code = jsonReader.nextInt()
                "msg" -> msg = jsonReader.nextString()
                "data" -> data = adapter.read(jsonReader)
            }
        }
        jsonReader.endObject()
        return ApiSuccessResponse(code, msg, data) as T
    }
}