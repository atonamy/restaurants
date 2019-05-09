package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class Coordinates : RealmObject() {

    @Json(name = "latitude")
    var latitude: Double? = 0.0

    @Json(name = "longitude")
    var longitude: Double? = 0.0
}