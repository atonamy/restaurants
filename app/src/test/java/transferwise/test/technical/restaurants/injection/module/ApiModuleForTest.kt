package transferwise.test.technical.restaurants.injection.module

import io.reactivex.Observable
import retrofit2.Retrofit
import transferwise.test.technical.restaurants.api.RestaurantsApi
import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.model.Reviews
import transferwise.test.technical.restaurants.utils.ApiUtils


@Suppress("unused")
class ApiModuleForTest constructor(context: Context): ApiModule(context) {

    override fun providePostApi(retrofit: Retrofit): RestaurantsApi {

        return object : RestaurantsApi {
            override fun getRestaurants(latitude: Double, longitude: Double, offset: Long, limit: Int, radius: Int, sort: String): Observable<JsonResponse> {
                return Observable.fromCallable { ApiUtils.getUrl<JsonResponse>("restaurants.json") }
            }

            override fun getRestaurant(restaurantId: String): Observable<RestaurantDetails> {
                return Observable.fromCallable { ApiUtils.getUrl<RestaurantDetails>("restaurant-gifOfLjjWd8RM-EIQNfcpQ.json") }
            }

            override fun getRestaurantReviews(restaurantId: String): Observable<Reviews> {
                return Observable.fromCallable { ApiUtils.getUrl<Reviews>("restaurant-gifOfLjjWd8RM-EIQNfcpQ-reviews.json") }
            }
        }
    }

    override fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder().baseUrl("https://fake.api/").build()
    }

}