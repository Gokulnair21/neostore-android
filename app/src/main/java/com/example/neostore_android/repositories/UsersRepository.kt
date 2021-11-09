package com.example.neostore_android.repositories

import com.example.neostore_android.models.AccountResponse
import com.example.neostore_android.models.AuthResponse
import com.example.neostore_android.models.CommonPostResponse
import com.example.neostore_android.services.network.RetroFitService
import com.example.neostore_android.services.network.UserAPI
import com.example.neostore_android.utils.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UsersRepository : BaseRepository() {

    private val userRetrofitService: UserAPI = RetroFitService.getUserAPI()


    fun login(email: String, password: String): Flow<NetworkData<AuthResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse { userRetrofitService.login(email, password) })
    }.flowOn(Dispatchers.IO)


    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String,
        gender: String,
        phoneNumber: Number
    ): Flow<NetworkData<AuthResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse {
            userRetrofitService.register(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                gender = gender,
                phoneNumber = phoneNumber
            )
        })
    }.flowOn(Dispatchers.IO)


    fun forgotPassword(email: String): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse {
            userRetrofitService.forgotPassword(email)
        })
    }.flowOn(Dispatchers.IO)


    fun changePassword(
        accessToken: String,
        oldPassword: String,
        password: String,
        confirmPassword: String
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse {
            userRetrofitService.changePassword(
                accessToken = accessToken,
                oldPassword = oldPassword,
                password = password,
                confirmPassword = confirmPassword
            )
        })
    }.flowOn(Dispatchers.IO)


    fun updateAccountDetails(
        accessToken: String,
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: Number,
        profilePic: String,
        dob: String
    ): Flow<NetworkData<CommonPostResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse {
            userRetrofitService.updateAccountDetails(
                firstName = firstName,
                lastName = lastName,
                email = email,
                accessToken = accessToken,
                phoneNumber = phoneNumber,
                profilePic = profilePic,
                dob = dob
            )
        })
    }.flowOn(Dispatchers.IO)


    fun getUsersData(accessToken: String): Flow<NetworkData<AccountResponse>> = flow {
        emit(NetworkData.Loading())
        emit(getResponse {
            userRetrofitService.getUsersData(accessToken)
        })
    }.flowOn(Dispatchers.IO)


}