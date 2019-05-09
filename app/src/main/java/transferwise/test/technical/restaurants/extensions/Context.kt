package transferwise.test.technical.restaurants.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.okButton
import transferwise.test.technical.restaurants.R


fun Context.getDecimal(resourseId: Int): Float {
    val outValue = TypedValue()
    resources.getValue(resourseId, outValue, true)
    return outValue.float
}

fun Context.makeDial(number: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

inline fun Context.showError(error: Throwable, crossinline  done: () -> Unit) {
    alert(Appcompat, "${getString(R.string.error)} ${error.message}" ){
        okButton {done()}
        onCancelled {
            done()
        }
    }.show()
}

fun Context.showEmpty() {
    alert(Appcompat, "${getString(R.string.empty)}" ){
        okButton {}
    }.show()
}