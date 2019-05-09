package transferwise.test.technical.restaurants.injection.module

import android.content.Context
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.util.Log
import org.jetbrains.anko.connectivityManager
import java.io.File


class ApiModuleContext(private val context: Context) : ApiModule.Context {

    override fun getCacheDir(): File {
        return context.cacheDir
    }

    override fun isConnected(): Boolean {
        try {
            val connection = context.connectivityManager
            val activeNetwork = connection.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            Log.w(ApiModuleContext::class.java.name, e.toString())
        }

        return false
    }

    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getModule(): ApiModule {
        return ApiModule(this)
    }

    override val ioThread: Scheduler
        get() = Schedulers.io()
    override val androidThread: Scheduler
        get() = AndroidSchedulers.mainThread()
}