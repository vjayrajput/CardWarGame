package com.app.war.common.network

suspend fun <T : Any> safeApiCall(call: suspend () -> T): Result<T> {
    return try {
        Result.success(call())
    } catch (exception: Throwable) {
        Result.failure(ApiErrorMapper().map(exception))
    }
}
