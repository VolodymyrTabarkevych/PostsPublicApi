package ua.tabarkevych.postspublicapi.di

import androidx.viewbinding.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.tabarkevych.postspublicapi.network.PostApiService
import ua.tabarkevych.postspublicapi.network.model.PostNetworkMapper
import ua.tabarkevych.postspublicapi.util.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providePostsMapper(): PostNetworkMapper {
        return PostNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): PostApiService {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(5, 30, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(builder.build())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }
}