package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json

class JsonResponse(
        @Json(name = "businesses")
        val businesses: Array<Restaurant>,
        @Json(name = "total")
        val total: Long
)