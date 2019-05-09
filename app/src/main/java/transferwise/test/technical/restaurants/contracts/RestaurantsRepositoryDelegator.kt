package transferwise.test.technical.restaurants.contracts

import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.model.Reviews

class RestaurantsRepositoryDelegator : RestaurantsRepositoryContract {

    override fun onLoading(loading: Boolean) {

    }

    override fun onRestaurants(response: JsonResponse) {

    }

    override fun onRestaurant(response: RestaurantDetails) {

    }

    override fun onRestaurantReviews(response: Reviews) {

    }

    override fun onError(error: Throwable) {

    }
}