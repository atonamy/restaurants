package transferwise.test.technical.restaurants.injection.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.api.RestaurantsApi
import okhttp3.logging.HttpLoggingInterceptor
import transferwise.test.technical.restaurants.retrofit.adapters.RealmListJsonAdapterFactory
import transferwise.test.technical.restaurants.retrofit.interceptors.InterceptorsProvider
import java.io.File


@Module
@Suppress("unused")
open class ApiModule constructor(private val context: Context) {

    interface Context {
        fun getString(id: Int): String
        fun getModule(): ApiModule
        fun isConnected(): Boolean
        fun getCacheDir(): File
        val ioThread: Scheduler
        val androidThread: Scheduler
    }

    private val client: OkHttpClient
        get() {
            val provider = InterceptorsProvider(context)
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor {
                val request = it.request().newBuilder().addHeader("Authorization", context.getString(R.string.auth_token)).build()
                it.proceed(request)
            }
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor(provider.provideOfflineCacheInterceptor())
            httpClient.addNetworkInterceptor(provider.provideCacheInterceptor())
            httpClient.cache(provider.provideCache())
            return httpClient.build()
        }

    private val moshi: Moshi
            get() = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .add(RealmListJsonAdapterFactory())
                    .build()

    @Provides
    @Singleton
    protected open fun providePostApi(retrofit: Retrofit): RestaurantsApi {
        return retrofit.create(RestaurantsApi::class.java)
    }

    @Provides
    @Singleton
    protected open fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

}