package com.abhijit.vivy_codingtest.di.modules

import android.content.Context
import androidx.room.Room
import com.abhijit.vivy_codingtest.BuildConfig
import com.abhijit.vivy_codingtest.data.local.dao.DoctorDao
import com.abhijit.vivy_codingtest.data.local.db.DoctorDatabase
import com.abhijit.vivy_codingtest.data.local.dbhelper.DatabaseHelper
import com.abhijit.vivy_codingtest.data.remote.api.ApiHelper
import com.abhijit.vivy_codingtest.data.remote.api.ApiHelperImpl
import com.abhijit.vivy_codingtest.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides

    fun provideDoctorDao(doctorDatabase: DoctorDatabase): DoctorDao {
        return doctorDatabase.doctorDao()
    }

    @Provides
    @Singleton
    fun provideDoctorDatabase(@ApplicationContext appContext: Context): DoctorDatabase {
        return Room.databaseBuilder(
            appContext,
            DoctorDatabase::class.java,
            "doctor-database"
        ).build()
        print("inside")
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelper: DatabaseHelper): DatabaseHelper = databaseHelper




}
