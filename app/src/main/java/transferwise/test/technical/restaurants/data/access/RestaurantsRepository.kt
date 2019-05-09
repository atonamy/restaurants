package transferwise.test.technical.restaurants.data.access

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import transferwise.test.technical.restaurants.api.RestaurantsApi
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryContract
import transferwise.test.technical.restaurants.injection.module.ApiModule
import javax.inject.Inject

class RestaurantsRepository(
        private val context: ApiModule.Context,
        private val contract: RestaurantsRepositoryContract
) : ApiInjector(context) {

    @Inject
    lateinit var postApi: RestaurantsApi
    private val disposables: Array<Disposable?> = arrayOf(null, null, null)


    fun loadRestaurants(latitude: Double, longitude: Double, offset: Long) {
        disposables[0] = handleCall(contract, postApi.getRestaurants(latitude, longitude, offset)) {
            contract.onRestaurants(it)
        }
    }

    fun loadRestaurantDetails(restaurantId: String) {
        disposables[1] = handleCall(contract, postApi.getRestaurant(restaurantId)) {
            contract.onRestaurant(it)
        }
    }

    fun loadRestaurantReviews(restaurantId: String) {
        disposables[2] = handleCall(contract, postApi.getRestaurantReviews(restaurantId)) {
            contract.onRestaurantReviews(it)
        }
    }

    fun release() {
        disposables.forEach {
            if(it != null && !it.isDisposed)
                it.dispose()
        }
    }

    private inline fun <T> handleCall(contract: RestaurantsRepositoryContract, observable: Observable<T>,
                              crossinline subscribe: (response: T) -> Unit): Disposable {
        contract.onLoading(true)
        return observable.observeOn(context.androidThread)
                .subscribeOn(context.ioThread)
                .doOnTerminate {
                    contract.onLoading(false)
                }
                .subscribe(
                        {
                            subscribe(it)
                        },
                        {
                            contract.onError(it)
                        }
                )
    }


}