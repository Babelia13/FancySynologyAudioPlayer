/*
 *   Copyright 2022 Babelia
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.babelia.fansyn.network

import android.content.Context
import com.babelia.fansyn.BuildConfig
import com.babelia.fansyn.utils.LocalDateAdapter
import com.babelia.fansyn.utils.LocalDateTimeAdapter
import com.babelia.fansyn.utils.LocalTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Suppress("UndocumentedPublicClass", "UndocumentedPublicFunction")
object NetworkModule {

    private const val HTTP_CLIENT_TIMEOUT_SECONDS = 60L
    private const val CACHE_SIZE = 300L * 1024 * 1024 // 300 MB

    const val SYNOLOGY_FAKE_API_TAG = "SYNOLOGY_FAKE_API_TAG"

    fun create() = DI.Module("NetworkModule") {

        bind<OkHttpClient>() with singleton {
            val context: Context = instance()

            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                Timber.tag("OkHttpClient").d(message)
            }.apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            OkHttpClient.Builder()
                .connectTimeout(HTTP_CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(HTTP_CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(HTTP_CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .cache(Cache(context.cacheDir, CACHE_SIZE))
                // TODO Change this when login module is implemented
                .addInterceptor(RequestInterceptor(BuildConfig.SYNOLOGY_LOGIN_API_SID))
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }

        bind<Retrofit>() with multiton { baseUrl: String ->
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(instance())
                .addConverterFactory(MoshiConverterFactory.create(instance()))
                .build()
        }

        bind<Moshi>() with singleton {
            Moshi.Builder()
                .add(LocalDateTimeAdapter())
                .add(LocalTimeAdapter())
                .add(LocalDateAdapter())
                // During conversion, Moshi will try to use the adapters in the order you added them. Only if it fails,
                // it will try the next adapter.KotlinJsonAdapterFactory is the most general one, hence should be called
                // last, after all of your custom and special cases were covered
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        bind<SynologyApi>() with singleton {
            val retrofit: Retrofit = instance(arg = BuildConfig.SYNOLOGY_API_BASE_URL)
            retrofit.create(SynologyApi::class.java)
        }

        bind<SynologyApi>(SYNOLOGY_FAKE_API_TAG) with singleton {
            SynologyFakeApi()
        }

    }
}