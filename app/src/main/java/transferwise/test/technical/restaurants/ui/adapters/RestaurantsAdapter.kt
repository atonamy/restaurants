package transferwise.test.technical.restaurants.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.realm.RealmList
import transferwise.test.technical.restaurants.data.model.Restaurant
import transferwise.test.technical.restaurants.data.view_model.RestaurantViewModel
import transferwise.test.technical.restaurants.databinding.ListItemRestaurantBinding

class RestaurantsAdapter(private val context: Context,
                         private val restaurants: MutableList<Restaurant> = RealmList(),
                         private val inflater: LayoutInflater = LayoutInflater.from(context)):
        RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    var restaurantClickListener: (restaurant: Restaurant) -> Unit = { }
    var shareClickListener: (title: String?, url: String?) -> Unit = {_, _ ->  }
    var callClickListener: (phone: String?) -> Unit = { }

    class RestaurantViewHolder(private val binding: ListItemRestaurantBinding,
                          var viewModel: RestaurantViewModel): RecyclerView.ViewHolder(binding.root)  {

        fun bindRestaurantView() {
            binding.viewModel = viewModel
        }

    }

    fun addRestaurants(restaurants: Array<Restaurant>) {
        if(restaurants.isEmpty())
            return
        val position = this.restaurants.size
        this.restaurants.addAll(position, restaurants.toList())
        notifyItemRangeInserted(position, restaurants.size)
    }

    fun clearRestaurants() {
        restaurants.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsAdapter.RestaurantViewHolder {
        return RestaurantViewHolder(ListItemRestaurantBinding.inflate(inflater, parent, false),
                RestaurantViewModel())
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantsAdapter.RestaurantViewHolder, position: Int) {
        bindModel(holder.viewModel, position)
        holder.bindRestaurantView()
    }

    private fun bindModel(model: RestaurantViewModel, position: Int) {
        val restaurant = restaurants[position]
        model.model = restaurant
        model.onClickListener  = {
            restaurantClickListener(restaurant)
        }
        model.onCallListener = {
            callClickListener(restaurant.phone)
        }
        model.onShareListener = {
            shareClickListener(restaurant.name, restaurant.url)
        }
    }
}