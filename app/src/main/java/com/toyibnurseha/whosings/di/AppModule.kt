package com.toyibnurseha.whosings.di

import android.content.Context
import com.toyibnurseha.whosings.BuildConfig
import com.toyibnurseha.whosings.api.ApiService
import com.toyibnurseha.whosings.db.WhoSingsDAO
import com.toyibnurseha.whosings.db.WhoSingsDatabase
import com.toyibnurseha.whosings.manager.TimerManager
import com.toyibnurseha.whosings.repository.WhoSingRepository
import com.toyibnurseha.whosings.api.ApiMapper
import com.toyibnurseha.whosings.utils.Constant.Companion.BASE_URL
import com.toyibnurseha.whosings.utils.Constant.Companion.MAX_TIMER
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
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient
            .Builder()
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiHelper(service: ApiService) = ApiMapper(service)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideGameRepository(api: ApiMapper, dao : WhoSingsDAO) = WhoSingRepository(api, dao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = WhoSingsDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideDAO(db: WhoSingsDatabase) = db.whoSingsDAO()

    @Provides
    fun provideTimer() = TimerManager(MAX_TIMER)

}