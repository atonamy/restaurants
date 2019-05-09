package transferwise.test.technical.restaurants.data.view_model

import android.databinding.BaseObservable
import android.databinding.Bindable
import transferwise.test.technical.restaurants.data.model.Location


abstract class AddressViewModal : BaseObservable() {

    abstract val location: Location?

    @get:Bindable
    val address: String
        get() {
            var result = ""
            val l = location
            if(l != null) {
                if(!l.addressOne.isNullOrEmpty())
                    result += l.addressOne
                if(!l.addressTwo.isNullOrEmpty())
                    result += result.comma() + l.addressTwo
                if(!l.city.isNullOrEmpty())
                    result += result.comma() + l.city
                if(!l.zipCode.isNullOrEmpty())
                    result += result.comma() + l.zipCode
            }

            return result
        }

    private fun String.comma(): String {
        if (!this.isEmpty())
            return ", "
        else
            return ""
    }

}