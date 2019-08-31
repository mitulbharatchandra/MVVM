package com.sevenpeakssoftware.mitul.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ContentService {
    @GET("article/get_articles_list")
    fun getContentlist(): Observable<NetworkContentContainer>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.apphusetreach.no/application/119267/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val contents = retrofit.create(ContentService::class.java)
}