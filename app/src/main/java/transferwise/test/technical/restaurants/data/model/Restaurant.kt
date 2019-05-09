package transferwise.test.technical.restaurants.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import com.squareup.moshi.Json

open class Restaurant : RealmObject() {

        @PrimaryKey
        @Json(name = "id")
        var id: String = ""

        @Json(name = "name")
        var name: String? = ""

        @Json(name = "image_url")
        var imageUrl: String? = ""

        @Json(name = "is_closed")
        var isClosed: Boolean? = true

        @Json(name = "url")
        var url: String? = ""

        @Json(name = "rating")
        var rating: Double? = 0.0

        @Json(name = "price")
        var price: String? = ""

        @Json(name = "location")
        var location: Location? = Location()

        @Json(name = "phone")
        var phone: String? = ""

        @Json(name = "display_phone")
        var displayPhone: String? = ""

        @Json(name = "distance")
        var distance: String? = ""
}