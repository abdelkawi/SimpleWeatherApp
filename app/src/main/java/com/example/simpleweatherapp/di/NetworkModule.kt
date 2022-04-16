package com.example.simpleweatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.simpleweatherapp.BuildConfig
import com.example.simpleweatherapp.data.remote.ApiService
import com.example.simpleweatherapp.data.local.LocalDataSource
import com.example.simpleweatherapp.data.remote.RemoteDataSource
import com.example.simpleweatherapp.data.local.AppDatabase
import com.example.simpleweatherapp.data.local.SearchHistoryDao
import com.example.simpleweatherapp.data.local.WeatherDao
import com.example.simpleweatherapp.data.repo.RepositoryImpl
import com.example.simpleweatherapp.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun getRepo(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repository =
        RepositoryImpl(remoteDataSource, localDataSource)


    @Singleton
    @Provides
    fun provideDb(@ApplicationContext applicationContext:Context) = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "weather"
    ).build()

    @Singleton
    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
    @Singleton
    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.searchDao()
    }

}