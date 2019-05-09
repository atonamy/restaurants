package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmObject

open class Category : RealmObject() {

    @Json(name = "alias")
    var alias: String? = ""

    @Json(name = "title")
    var title: String? = ""
}