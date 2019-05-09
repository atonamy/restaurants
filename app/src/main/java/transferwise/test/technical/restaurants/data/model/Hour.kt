package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class Hour : RealmObject() {

    @Json(name = "is_overnight")
    var isOveright: Boolean? = false

    @Json(name = "start")
    var start: String? = ""

    @Json(name = "end")
    var end: String? = ""

    @Json(name = "day")
    var day: Int? = 0
}