package transferwise.test.technical.restaurants.data.model

import com.squareup.moshi.Json
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Reviews : RealmObject() {

    @PrimaryKey
    @Transient
    var restaurantId: String = ""

    @Json(name = "reviews")
    var reviews: RealmList<Review> = RealmList()
}