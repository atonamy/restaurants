package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class Location : RealmObject() {

    @Json(name = "address1")
    var addressOne: String? = ""

    @Json(name = "address2")
    var addressTwo: String? = ""

    @Json(name = "address3")
    var addressThree: String? = ""

    @Json(name = "city")
    var city: String? = ""

    @Json(name = "zip_code")
    var zipCode: String? = ""

    @Json(name = "country")
    var country: String? = ""

    @Json(name = "state")
    var state: String? = ""
}