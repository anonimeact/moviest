package com.anonimeact.moviest.services

import android.content.Context
import androidx.room.Room
import com.anonimeact.moviest.services.localdb.MoviestDb
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object BaseProvider {
    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    /** Room */

    @Provides
    fun provideLocalDB(@ApplicationContext context: Context) : MoviestDb {
        return Room.databaseBuilder(
            context,
            MoviestDb::class.java, "moviestdb"
        ).build()
    }

    /** Networking */

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(client).build()
    }

    @Provides
    fun provideApiServices(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain: Interceptor.Chain ->
                val origin = chain.request()
                val token : String = context.assets.open("token.txt").bufferedReader().use { it.readText() }
                val requestBuilder = origin.newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer $token")
                chain.proceed(requestBuilder.build())

            }.build()
    }
}