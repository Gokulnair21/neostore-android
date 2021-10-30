package com.example.neostore_android.repositories

import com.example.neostore_android.utils.APIError
import com.example.neostore_android.utils.NetworkData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.lang.Exception

open class BaseRepository {

    protected suspend fun <T> getResponse(apiCall: suspend () -> Response<T>): NetworkData<T> {
        return try {
            val data = apiCall()
            return if (data.code() == 200) {
                NetworkData.Success(data.body()!!)
            } else {
                val gson = Gson()
                val type = object : TypeToken<APIError>() {}.type
                val errorResponse: APIError? = gson.fromJson(data.errorBody()!!.charStream(), type)
                NetworkData.Error(errorResponse)
            }
        } catch (e: Exception) {
            NetworkData.Error(
                error = APIError(
                    userMsg = "Error occurred,Try again",
                    message = e.message.toString(),
                    status = 600
                )
            )

        }
    }

}