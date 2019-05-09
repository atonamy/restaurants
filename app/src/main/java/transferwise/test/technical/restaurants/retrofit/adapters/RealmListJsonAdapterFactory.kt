package transferwise.test.technical.restaurants.retrofit.adapters


import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.squareup.moshi.JsonAdapter
import io.realm.RealmList
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class RealmListJsonAdapterFactory : JsonAdapter.Factory {

    @Nullable
    override fun create(@NonNull type: Type, @NonNull annotations: Set<Annotation>, @NonNull moshi: Moshi): JsonAdapter<*>? {
        val rawType = Types.getRawType(type)
        if (!annotations.isEmpty()) return null
        return if (rawType == RealmList::class.java) {
            newRealmListAdapter<Any>(type, moshi).nullSafe()
        } else null
    }

    private fun <T : Any> newRealmListAdapter(type: Type, moshi: Moshi): RealmListAdapter<Any> {
        val elementType = Types.collectionElementType(type, RealmList::class.java)
        val elementAdapter = moshi.adapter<Any>(elementType)
        return RealmListAdapter(elementAdapter)
    }
}