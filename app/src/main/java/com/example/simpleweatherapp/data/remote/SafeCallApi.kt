package com.example.simpleweatherapp.data.remote

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception



suspend inline fun <reified T> safeApiCall(
    noinline call: suspend () -> Response<T>
): Result<T> {
    return try {
        val res = call()
        return when (res.isSuccessful) {
            true -> {
                Result.Success(
                    res.body() as T
                )
            }
            false -> {
                Result.Error(
                    res.errorBody()?.toError()!!.message
                )
            }
        }
    } catch (e: Exception) {
        Result.Error(e.toString())
    }
}

inline fun ResponseBody.toError(): Error {
    return Gson().fromJson(this.charStream(), Error::class.java)
}