package org.wit.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.wit.livedive.models.DiveModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface DiveService {
    @GET("/dives")
    fun getall(): Call<List<DiveModel>>

    @GET("/dives/{id}")
    fun get(@Path("id") id: String): Call<DiveModel>

    @DELETE("/dives/{id}")
    fun delete(@Path("id") id: String): Call<DiveWrapper>

    @POST("/dives")
    fun post(@Body donation: DiveModel): Call<DiveWrapper>

    @PUT("/dives/{id}")
    fun put(@Path("id") id: String,
            @Body donation: DiveModel
    ): Call<DiveWrapper>

    companion object {

        val serviceURL = "https://livediveapi.herokuapp.com/"

        fun create() : DiveService {

            val gson = GsonBuilder().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(DiveService::class.java)
        }
    }
}