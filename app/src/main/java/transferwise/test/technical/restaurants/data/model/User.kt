package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class User : RealmObject() {

    @Json(name = "profile_url")
    var profileUrl: String? = ""

    @Json(name = "image_url")
    var imageUrl: String? = ""

    @Json(name = "name")
    var name: String? = ""
}