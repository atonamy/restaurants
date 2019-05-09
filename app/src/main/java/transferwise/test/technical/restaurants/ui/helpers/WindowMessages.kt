package transferwise.test.technical.restaurants.ui.helpers

import android.content.Context
import android.view.View
import transferwise.test.technical.restaurants.extensions.showEmpty

interface WindowMessages {
    fun <T> isContentEmptyPopup(count: Int, items: Array<T>, context: Context) {
        if(items.isEmpty() && count == 0)
            context.showEmpty()
    }

    fun <T> isContentEmpty(count: Int, items: List<T>): Int {
        if(items.isEmpty() && count == 0)
            return View.VISIBLE

        return View.GONE
    }
}