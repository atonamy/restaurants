package transferwise.test.technical.restaurants.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.model.Reviews

interface RestaurantsApi {
    @GET("search?categories=restaurants")
    fun getRestaurants(
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double,
            @Query("offset") offset: Long,
            @Query("limit") limit: Int = 25,
            @Query ("radius") radius: Int = 15000,
            @Query("sort_by") sort: String = "distance"
    ): Observable<JsonResponse>

    @GET("{restaurant_id}")
    fun getRestaurant(
            @Path("restaurant_id") restaurantId: String
    ): Observable<RestaurantDetails>

    @GET("{restaurant_id}/reviews")
    fun getRestaurantReviews(
            @Path("restaurant_id") restaurantId: String
    ): Observable<Reviews>
}