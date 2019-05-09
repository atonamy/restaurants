package transferwise.test.technical.restaurants.data.view_model


import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.view.View
import com.facebook.drawee.view.SimpleDraweeView

import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.data.model.Location
import transferwise.test.technical.restaurants.data.model.Restaurant




class RestaurantViewModel : AddressViewModal() {


    var onClickListener: () -> Unit = {}
    var onShareListener: () -> Unit = {}
    var onCallListener: () -> Unit = {}

    @get:Bindable
    var model: Restaurant = Restaurant()
    set(value) {
        field = value
        notifyChange()
    }

    override val location: Location?
        get() = model.location

    @get:Bindable
    val onClick: View.OnClickListener
        get() = View.OnClickListener {
            onClickListener()
        }

    @get:Bindable
    val onShare: View.OnClickListener
        get() = View.OnClickListener {
            onShareListener()
        }


    @get:Bindable
    val onCall: View.OnClickListener
        get() = View.OnClickListener {
            onCallListener()
        }


}

@BindingAdapter("imageUrl")
fun setFrescoImageUrl(view: SimpleDraweeView, imageUrl: String) {
    if(imageUrl.isEmpty()) {

        view.setActualImageResource(R.drawable.ic_no_photo)
    }
    else
        view.setImageURI(imageUrl)
}