package com.example.retrofit


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private const val BASE_URL2 =
    "http://10.0.2.2:5000"

val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

val restapi = retrofit.create(RestApi::class.java)


val retrofit2 = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL2)
    .build()

val restapi2 = retrofit2.create(RestApi2::class.java)


interface RestApi {
    @GET("photos")
    suspend fun getPhotos(): String
}


interface RestApi2 {
    @GET("mensagem")
    suspend fun getMensagem(): String

    @GET("contador")
    suspend fun getContagem(): Int
}