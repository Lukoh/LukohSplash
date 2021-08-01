/*
 * Copyright (C) 2021 Lukoh Nam, goForer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.goforer.lukohsplash.di.module

import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bluewhale.base.network.entity.NetworkError
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.goforer.lukohsplash.LukohSplash
import com.goforer.lukohsplash.BuildConfig
import com.goforer.lukohsplash.R
import com.goforer.lukohsplash.data.source.network.api.RestAPI
import com.goforer.lukohsplash.data.source.network.factory.FlowCallAdapterFactory
import com.goforer.lukohsplash.data.source.network.factory.NullOnEmptyConverterFactory
import com.goforer.lukohsplash.presentation.Caller.preparingService
import com.goforer.lukohsplash.presentation.Caller
import com.goforer.base.network.NetworkErrorHandler
import com.goforer.base.utility.ConnectionUtils
import com.goforer.base.view.dialog.NormalDialog
import com.goforer.base.worker.download.DownloaderQuery
import com.goforer.base.worker.download.wrapper.DownloaderQueryWrapper
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * A module for Android-specific dependencies which require a [Context] or
 * [android.app.Application] to create.
 */
@Module
class AppModule {
    companion object {
        const val timeout_read = 30L
        const val timeout_connect = 30L
        const val timeout_write = 30L
    }

    @Provides
    @Singleton
    fun appContext(application: Application) : Context = application.applicationContext

    @Provides
    @Singleton
    fun provideGSon() : Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideDownloaderQueryWrapper(context: Context) : DownloaderQueryWrapper = DownloaderQuery(context)

    @Singleton
    @Provides
    fun provideNetworkErrorHandler(context: Context) = NetworkErrorHandler(context)

    @Singleton
    @Provides
    fun providePersistentCookieJar(context: Context) =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        cookieJar: PersistentCookieJar
    ): OkHttpClient {
        val ok = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .connectTimeout(timeout_connect, TimeUnit.SECONDS)
            .readTimeout(timeout_read, TimeUnit.SECONDS)
            .writeTimeout(timeout_write, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            if ("robolectric" != Build.FINGERPRINT)
                ok.addNetworkInterceptor(StethoInterceptor())

            val httpLoggingInterceptor =
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        if (isJSONValid(message))
                            Logger.json(message)
                        else
                            Timber.d("%s", message)
                    }

                    fun isJSONValid(jsonInString: String): Boolean {
                        try {
                            JSONObject(jsonInString)
                        } catch (ex: JSONException) {
                            try {
                                JSONArray(jsonInString)
                            } catch (ex1: JSONException) {
                                return false
                            }

                        }

                        return true
                    }

                })

            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            ok.addInterceptor(httpLoggingInterceptor)
        }

        ok.addInterceptor(interceptor)

        return ok.build()
    }

    @Provides
    @Singleton
    fun getRequestInterceptor(
        application: Application,
        context: Context
    ): Interceptor {
        return Interceptor {
            Timber.tag("PRETTY_LOGGER")

            if (!ConnectionUtils.isNetworkAvailable(context)) {
                if (application is LukohSplash) {
                    application.activity?.let { activity ->
                        activity as AppCompatActivity
                        activity.lifecycleScope.launch {
                            NormalDialog.Builder(context)
                                .setTitle(R.string.no_network)
                                .setMessage(R.string.network_disconnect)
                                .setPositiveButton(R.string.ok) { _: DialogInterface, _: Int ->
                                }.setOnDismissListener {
                                    Caller.closeApp(activity)
                                }.show(activity.supportFragmentManager)
                        }
                    }
                }
            }

            val original = it.request()

            //Log.e("pretty", "Interceptor.url.host: ${original.url.host}")
            //Log.e("pretty", "Interceptor.url.path: ${original.url}")
            val requested = with(original) {
                val builder = newBuilder()

//
                builder.header("Accept", "application/json")
                builder.header("Accept-Version", "v1")
                builder.header("mobileplatform", "android")
                Timber.d("mobileplatform: android")

                builder.header("versioncode", "${BuildConfig.VERSION_CODE}")
                Timber.d("versioncode: ${BuildConfig.VERSION_CODE}")

//                Timber.d("request cookies: ${cookieJar.loadForRequest(requested.url)}")
                builder.build()
            }

            val response = it.proceed(requested)
            val body = response.body
            var bodyStr = body?.string()
            Timber.d("**http-num: ${response.code}")
            Timber.d("**http-body: $body")

            if (!response.isSuccessful) {
                try {
                    when (response.code) {
                        NetworkError.ERROR_SERVICE_BAD_GATEWAY, NetworkError.ERROR_SERVICE_UNAVAILABLE -> {
                            preparingService(context)
                        }

                        else -> {
                            val networkError =
                                Gson().fromJson(bodyStr, NetworkError::class.java)
                            networkError.error.message =
                                original.url.encodedPath + "\n" + networkError.error.message
                            bodyStr = Gson().toJson(networkError)
                        }
                    }
                } catch (e: Exception) {
                    e.stackTrace
                    Timber.e(Throwable(e.toString()))
                }
            }

            val builder = response.newBuilder()

            builder.body(bodyStr?.toByteArray()?.toResponseBody(body?.contentType())).build()
        }
    }

    @Singleton
    @Provides
    fun provideRestAPI(gson: Gson, okHttpClient: OkHttpClient): RestAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .client(okHttpClient)
            .build()

        return retrofit.create(RestAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesInAppUpdateManager(application: Application): AppUpdateManager =
        AppUpdateManagerFactory.create(application)

}