package transferwise.test.technical.restaurants.data.view_model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.view.View
import transferwise.test.technical.restaurants.BR

class ReviewsViewModel : BaseObservable() {

    @get:Bindable
    var showNoReviews = View.GONE
    set(value) {
        field = value
        notifyPropertyChanged(BR.showNoReviews)
    }
}