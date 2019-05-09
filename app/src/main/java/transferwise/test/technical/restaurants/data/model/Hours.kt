package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmList
import io.realm.RealmObject

open class Hours : RealmObject() {
    @Json(name = "open")
    var open: RealmList<Hour> = RealmList()

}