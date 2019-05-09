package transferwise.test.technical.restaurants.data.view_model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.data.model.Review

class ReviewViewModel : BaseObservable() {

    @get:Bindable
    var model: Review = Review()
        set(value) {
            field = value
            notifyChange()
        }

}

