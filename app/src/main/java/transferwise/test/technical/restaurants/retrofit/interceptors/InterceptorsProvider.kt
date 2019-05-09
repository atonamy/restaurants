package transferwise.test.technical.restaurants.retrofit.interceptors

import android.util.Log
import okhttp3.Cache
import transferwise.test.technical.restaurants.injection.module.ApiModule
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.io.File
import java.util.concurrent.TimeUnit


class InterceptorsProvider(private val context: ApiModule.Context) {

    val HEADER_CACHE_CONTROL = "Cache-Control"
    val HEADER_PRAGMA = "Pragma"


    fun provideCache(): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.getCacheDir(), "http-cache"),
                        15 * 1024 * 1024)
        } catch (e: Exception) {
            Log.e(InterceptorsProvider::class.java.name, "Could not create Cache!")
        }


        return cache
    }

    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            val cacheControl: CacheControl

            if (context.isConnected()) {
                cacheControl = CacheControl.Builder()
                        .maxAge(0, TimeUnit.SECONDS)
                        .build()
            } else {
                cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
            }

            response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()

        }
    }

    fun provideOfflineCacheInterceptor(): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()

            if (!context.isConnected()) {
                val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
            }

            chain.proceed(request)
        }
    }

}