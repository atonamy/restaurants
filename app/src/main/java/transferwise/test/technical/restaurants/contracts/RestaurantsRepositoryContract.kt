package transferwise.test.technical.restaurants.contracts

import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.model.Reviews

interface RestaurantsRepositoryContract {

    fun onLoading(loading: Boolean)
    fun onRestaurants(response: JsonResponse)
    fun onRestaurant(response: RestaurantDetails)
    fun onRestaurantReviews(response: Reviews)
    fun onError(error: Throwable)
}