package transferwise.test.technical.restaurants

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Test

import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryContract
import transferwise.test.technical.restaurants.data.access.RestaurantsRepository
import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.model.Reviews
import transferwise.test.technical.restaurants.injection.module.ApiModule
import transferwise.test.technical.restaurants.injection.module.ApiModuleForTest
import transferwise.test.technical.restaurants.utils.error
import transferwise.test.technical.restaurants.utils.log
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class RepositoryTest {

    private var shoudLoading = true
    private var success = true

    private val repository: RestaurantsRepository = RestaurantsRepository(object: ApiModule.Context {
        override fun isConnected(): Boolean {
            return true
        }

        override fun getCacheDir(): File {
            return File("")
        }

        override val ioThread: Scheduler
            get() =  Schedulers.trampoline()
        override val androidThread: Scheduler
            get() = Schedulers.trampoline()

        override fun getModule(): ApiModule {
            return ApiModuleForTest(this)
        }

        override fun getString(id: Int): String {
            return ""
        }

    }, object: RestaurantsRepositoryContract {
        override fun onRestaurant(response: RestaurantDetails) {

        }

        override fun onRestaurantReviews(response: Reviews) {

        }

        override fun onLoading(loading: Boolean) {
                Assert.assertEquals("Should Loading", shoudLoading, loading)
                shoudLoading = false
            }

            override fun onError(error: Throwable) {
                success = false
                "Test Failed".error()
                "${error.printStackTrace()}".error()
            }

            override fun onRestaurants(response: JsonResponse) {
                Assert.assertEquals("Should Have Result", 996, response.total)
            }

    })

    @Test
    fun testLoadRestaurants() {
        "[LoadRestaurants] Start Test".log()
        repository.loadRestaurants(.0, .0, 0)
        if(success)
            "[LoadRestaurants] Test Completed".log()
    }
}
