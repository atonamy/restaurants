package transferwise.test.technical.restaurants.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.realm.RealmList
import transferwise.test.technical.restaurants.data.model.Review
import transferwise.test.technical.restaurants.data.view_model.ReviewViewModel
import transferwise.test.technical.restaurants.databinding.UserRatingInfoLayoutBinding

class ReviewsAdapter (private  val context: Context,
private val restaurants: MutableList<Review> = RealmList(),
private val inflater: LayoutInflater = LayoutInflater.from(context)): RecyclerView.Adapter<ReviewsAdapter.RatingViewHolder>() {

    class RatingViewHolder(private val binding: UserRatingInfoLayoutBinding,
                           var viewModel: ReviewViewModel): RecyclerView.ViewHolder(binding.root)  {

        fun bindRestaurantView() {
            binding.viewModel = viewModel
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        return RatingViewHolder(UserRatingInfoLayoutBinding.inflate(inflater, parent, false), ReviewViewModel())
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
        holder.viewModel.model = restaurants[position]
        holder.bindRestaurantView()
    }


}