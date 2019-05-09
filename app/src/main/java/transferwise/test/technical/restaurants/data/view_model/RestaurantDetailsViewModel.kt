package transferwise.test.technical.restaurants.data.view_model

import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.view.View
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ViewListener
import transferwise.test.technical.restaurants.BR
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.data.model.Location
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import java.util.*
import kotlin.collections.ArrayList

class RestaurantDetailsViewModel(private val context: ContextViewModel) : AddressViewModal() {

    private val carouselViews: MutableList<View> = ArrayList()

    var onBackListener: () -> Unit = {}

    @get:Bindable
    var model: RestaurantDetails = RestaurantDetails()
        set(value) {
            field = value
            populateCarouselViews()
            notifyChange()
        }

    override val location: Location?
        get() = model.location

    private fun populateCarouselViews() {
        carouselViews.clear()
        for(url in model.photos)
            carouselViews.add(context.getCarouselView(url))
        carouselPageCount = carouselViews.size
    }

    @get:Bindable
    val onBack: View.OnClickListener
        get() = View.OnClickListener {
            onBackListener()
        }

    @get:Bindable
    val open: String
        get() {
            return if(model.isClosed != null && !(model.isClosed!!)) context.getString(R.string.yes) else context.getString(R.string.no)
        }

    @get:Bindable
    val hours: String
        get() {
            val hours = model.hours
            if(hours.size > 0 && hours[0] != null &&
                    hours[0]!!.open.size == 7) {
                val workHours = hours[0]!!.open
                val calendar = Calendar.getInstance()
                val day = calendar.get(Calendar.DAY_OF_WEEK)-1
                val todayWorkHours = workHours[day]
                if(todayWorkHours != null) {
                    var start = todayWorkHours.start
                    var end = todayWorkHours.end
                    if(start != null && end != null && start.length > 2 && end.length > 2){
                        start = StringBuilder(start).insert(2, ":").toString()
                        end = StringBuilder(end).insert(2, ":").toString()
                        return "$start - $end"
                    }
                }
            }
            return ""
        }

    @get:Bindable
    val category: String
        get() {
            val categories = model.categories
            val result = StringBuilder("")
            if(categories.size > 0) {
                for (category in categories) {
                    val title = category.title
                    if(!title.isNullOrEmpty())
                        result.append(result.comma()+title!!)
                }
            }

            return result.toString()
        }

    @get:Bindable
    var distance: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.distance)
        }

    @get:Bindable
    var carouselPageCount: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.carouselPageCount)
        }

    @get:Bindable
    val carouselViewListner: ViewListener
            get() {
                return object: ViewListener {
                    override fun setViewForPosition(position: Int): View {
                        return carouselViews[position]
                    }

                }
            }

    private fun StringBuilder.comma(): String {
        if (!this.isEmpty())
            return ", "
        else
            return ""
    }
}

@BindingAdapter("pageCount")
fun setCarouselPageCount(view: CarouselView, numPages: Int) {
    view.pageCount = numPages
}

@BindingAdapter("viewListener")
fun setCarouselViewListner(view: CarouselView, listener: ViewListener) {
    view.setViewListener(listener)
}
