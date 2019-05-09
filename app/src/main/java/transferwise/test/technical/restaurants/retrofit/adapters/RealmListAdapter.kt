package transferwise.test.technical.restaurants.retrofit.adapters


import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import io.realm.RealmList
import java.io.IOException


internal class RealmListAdapter<T : Any>(private val elementAdapter: JsonAdapter<T>) : JsonAdapter<RealmList<T>>() {

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: RealmList<T>?) {
        writer.beginArray()
        if(value != null)
            for (element in value) {
                elementAdapter.toJson(writer, element)
            }
        writer.endArray()
    }

    @Nullable
    @Throws(IOException::class)
    override fun fromJson(@NonNull reader: JsonReader): RealmList<T>? {
        val result = RealmList<T>()
        reader.beginArray()
        while (reader.hasNext()) {
            result.add(elementAdapter.fromJson(reader))
        }
        reader.endArray()
        return result
    }
}