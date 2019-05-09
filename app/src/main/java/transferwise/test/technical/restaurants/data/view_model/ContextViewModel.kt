package transferwise.test.technical.restaurants.data.view_model

import android.view.View

interface ContextViewModel {
    fun getString(resourseId: Int): String
    fun getCarouselView(url: String): View
}