package transferwise.test.technical.restaurants.data.view_model

import android.databinding.BaseObservable
import android.databinding.Bindable
import transferwise.test.technical.restaurants.BR

class RestaurantInfoViewModel : BaseObservable() {

    @get:Bindable
    var open: String = ""
        set(value) {
            field = value
           notifyPropertyChanged(BR.open)
        }

    @get:Bindable
    var hours: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hours)
        }

    @get:Bindable
    var category: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.category)
        }
}