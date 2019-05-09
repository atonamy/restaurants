package transferwise.test.technical.restaurants.ui.activities

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.ethanhua.skeleton.ViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.carousel_view.view.*
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryContract
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryDelegator
import transferwise.test.technical.restaurants.data.access.RestaurantsRepository
import transferwise.test.technical.restaurants.data.model.RestaurantDetails
import transferwise.test.technical.restaurants.data.view_model.ContextViewModel
import transferwise.test.technical.restaurants.data.view_model.RestaurantDetailsViewModel
import transferwise.test.technical.restaurants.databinding.RestaurantDetailLayoutBinding
import transferwise.test.technical.restaurants.extensions.showError
import transferwise.test.technical.restaurants.injection.module.ApiModuleContext
import transferwise.test.technical.restaurants.ui.adapters.RestaurantPagerAdapter
import transferwise.test.technical.restaurants.ui.fragments.RestaurantInfoFragment
import transferwise.test.technical.restaurants.ui.fragments.RestaurantReviewFragment

class RestaurantDetailsActivity : AppCompatActivity(), RestaurantsRepositoryContract by RestaurantsRepositoryDelegator() {

    private lateinit var viewModel: RestaurantDetailsViewModel
    private lateinit var skeletonScreen: ViewSkeletonScreen
    private lateinit var repository: RestaurantsRepository
    private lateinit var viewPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = bindViewModel()
        initViewPager(binding)
        initSkeleton(binding)
        initiateLoad()
    }


    private fun initViewPager(binding: RestaurantDetailLayoutBinding) {
        viewPager = binding.viewPager
        binding.tabHost.setupWithViewPager(viewPager)
    }

    private fun initiateLoad() {
        val restaurantId = intent.getStringExtra("restaurant_id")
        if(restaurantId.isNullOrEmpty())
            finish()
        repository = RestaurantsRepository(ApiModuleContext(this), this)
        repository.loadRestaurantDetails(restaurantId)
    }

    private fun bindViewModel(): RestaurantDetailLayoutBinding  {
        viewModel = RestaurantDetailsViewModel(object: ContextViewModel {
            override fun getCarouselView(url: String): View {
                val view = layoutInflater.inflate(R.layout.carousel_view, null)
                view.res_image.setImageURI(url)
                return view
            }

            override fun getString(resourseId: Int): String {
                return this@RestaurantDetailsActivity.getString(resourseId)
            }

        })
        val binding = DataBindingUtil.setContentView<RestaurantDetailLayoutBinding>(this, R.layout.restaurant_detail_layout)
        binding.viewModel = viewModel
        viewModel.distance = intent.getStringExtra("distance")
        viewModel.onBackListener = {
            finish()
        }

        return binding
    }

    private fun setViewPager() {
        val adapter = RestaurantPagerAdapter(supportFragmentManager)
        adapter.addFragment(RestaurantInfoFragment().apply {
          arguments = Bundle().apply {
              putString("hours", viewModel.hours)
              putString("category", viewModel.category)
              putString("open", viewModel.open)
          }
        }, getString(R.string.info))
        adapter.addFragment(RestaurantReviewFragment().apply {
            arguments = Bundle().apply {
                putString("restaurant_id", viewModel.model.id)
            }
            onLoad = {
                if(it)
                    skeletonScreen.show()
                else
                    skeletonScreen.hide()
            }
        }, getString(R.string.reviews))
        viewPager.adapter = adapter

    }

    private fun initSkeleton(binding: RestaurantDetailLayoutBinding) {
        skeletonScreen = Skeleton.bind(binding.root)
                .load(R.layout.restaurant_details_skeleton_layout)
                .show()
    }


    override fun onError(error: Throwable) {
        error.printStackTrace()
        showError(error) {
            finish()
        }
    }

    override fun onRestaurant(response: RestaurantDetails) {
        viewModel.model = response
        setViewPager()
    }

    override fun onLoading(loading: Boolean) {
        if(!loading)
            skeletonScreen.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.release()
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

}
