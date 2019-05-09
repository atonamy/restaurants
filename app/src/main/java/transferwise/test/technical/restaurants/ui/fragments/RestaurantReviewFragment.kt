package transferwise.test.technical.restaurants.ui.fragments


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryContract
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryDelegator
import transferwise.test.technical.restaurants.data.access.RestaurantsRepository
import transferwise.test.technical.restaurants.data.model.Reviews
import transferwise.test.technical.restaurants.data.view_model.ReviewsViewModel
import transferwise.test.technical.restaurants.databinding.ReviewLayoutBinding
import transferwise.test.technical.restaurants.extensions.showError
import transferwise.test.technical.restaurants.injection.module.ApiModuleContext
import transferwise.test.technical.restaurants.ui.adapters.ReviewsAdapter
import transferwise.test.technical.restaurants.ui.helpers.WindowMessages
import android.databinding.adapters.TextViewBindingAdapter.setText




class RestaurantReviewFragment : Fragment(),  RestaurantsRepositoryContract by RestaurantsRepositoryDelegator(), WindowMessages {

    private lateinit var repository: RestaurantsRepository
    private lateinit var recycleView: RecyclerView
    private val viewModel = ReviewsViewModel()
    private var restaurantId: String = ""
    var onLoad: (loading: Boolean) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (context != null) {
            repository = RestaurantsRepository(ApiModuleContext(context!!), this)
            arguments?.let {
                restaurantId = it.getString("restaurant_id", "")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<ReviewLayoutBinding>(
                inflater, R.layout.review_layout, container, false
        )
        restoreState(savedInstanceState)
        binding.viewModel = viewModel
        initRecycleView(binding)
        if (!restaurantId.isEmpty())
            repository.loadRestaurantReviews(restaurantId)
        return binding.root
    }

    private fun initRecycleView(binding: ReviewLayoutBinding) {
        recycleView = binding.reviewGrid
        recycleView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recycleView.setHasFixedSize(true)
        recycleView.itemAnimator = DefaultItemAnimator()
    }

    override fun onError(error: Throwable) {
        error.printStackTrace()
        context?.showError(error) {
            activity?.finish()
        }
    }

    override fun onRestaurantReviews(response: Reviews) {
        if (context != null)
            recycleView.adapter = ReviewsAdapter(context!!, response.reviews)
        viewModel.showNoReviews = isContentEmpty(recycleView.adapter.itemCount, response.reviews)
    }

    override fun onLoading(loading: Boolean) {
        onLoad(loading)
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.release()
    }

    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putString("restaurant_id", restaurantId)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if(savedInstanceState != null)
            restaurantId = savedInstanceState.getString("restaurant_id", "")
    }

}
