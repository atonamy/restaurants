package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class Review : RealmObject() {

    @Json(name = "url")
    var url: String? = ""

    @Json(name = "text")
    var text: String? = ""

    @Json(name = "rating")
    var rating: Double? = .0

    @Json(name = "user")
    var user: User? = User()
}