package com.smart.keuneunong.di

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.smart.keuneunong.BuildConfig
import com.smart.keuneunong.data.network.DetailsApi
import com.smart.keuneunong.data.network.UsersApi
import com.smart.keuneunong.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    @WeatherApiRetrofit
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(
            JacksonConverterFactory.create(
                XmlMapper().registerKotlinModule()
            )
        )
        .baseUrl("https://data.bmkg.go.id/")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(@GithubApiRetrofit retrofit: Retrofit): UsersApi =
        retrofit.create(UsersApi::class.java)

    @Provides
    @Singleton
    fun provideUserDetailsService(@GithubApiRetrofit retrofit: Retrofit): DetailsApi =
        retrofit.create(DetailsApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiService(@WeatherApiRetrofit retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}