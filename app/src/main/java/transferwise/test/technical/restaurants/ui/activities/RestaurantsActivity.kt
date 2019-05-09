package transferwise.test.technical.restaurants.ui.activities


import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dinuscxj.refresh.RecyclerRefreshLayout
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton


import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.jetbrains.anko.*
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryContract
import transferwise.test.technical.restaurants.contracts.RestaurantsRepositoryDelegator
import transferwise.test.technical.restaurants.data.access.RestaurantsRepository
import transferwise.test.technical.restaurants.data.model.JsonResponse
import transferwise.test.technical.restaurants.databinding.ActivityRestaurantsBinding
import transferwise.test.technical.restaurants.extensions.requestLocation
import transferwise.test.technical.restaurants.injection.module.ApiModuleContext
import transferwise.test.technical.restaurants.ui.adapters.RestaurantsAdapter
import transferwise.test.technical.restaurants.ui.helpers.EndlessScrollListener
import transferwise.test.technical.restaurants.ui.helpers.PagesHelper
import transferwise.test.technical.restaurants.extensions.makeDial
import transferwise.test.technical.restaurants.extensions.showError
import transferwise.test.technical.restaurants.ui.helpers.WindowMessages


class RestaurantsActivity : AppCompatActivity(), RestaurantsRepositoryContract by RestaurantsRepositoryDelegator(),
        RecyclerRefreshLayout.OnRefreshListener, EndlessScrollListener.ScrollToBottomListener, WindowMessages {

    private lateinit var repository: RestaurantsRepository
    private lateinit var recycleView: RecyclerView
    private lateinit var refresher: RecyclerRefreshLayout
    private lateinit var adapter: RestaurantsAdapter
    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen
    private lateinit var scroller: EndlessScrollListener
    private lateinit var pages: PagesHelper
    private val location = object {
        var latitude: Double = .0
        var longitude: Double = .0
    }
    private var refreshing = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repository = RestaurantsRepository(ApiModuleContext(this), this)
        initAdapter()
        initRecycleView(
                DataBindingUtil.setContentView<ActivityRestaurantsBinding>(this, R.layout.activity_restaurants)
        ) {
            initRefresher(it)
        }
        initSkeleton()
        initiateLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.release()
    }

    private fun initAdapter() {
        adapter = RestaurantsAdapter(this)
        adapter.shareClickListener = {title, url ->
            if(!url.isNullOrEmpty() && !title.isNullOrEmpty())
                share(url!!, title!!)
        }
        adapter.callClickListener = {
            if(!it.isNullOrEmpty())
                makeDial(it!!)
        }
        adapter.restaurantClickListener = {
            startActivity(intentFor<RestaurantDetailsActivity>("restaurant_id" to it.id,
                    "distance" to it.distance).singleTop())
        }
    }

    private fun initiateLoad() {
        requestLocation({
            finish()
        }) {
            loadContent(it.latitude, it.longitude)
        }
    }

    private fun initSkeleton() {
        skeletonScreen = Skeleton.bind(recycleView)
                .adapter(adapter)
                .load(R.layout.list_item_skeleton_restaurants)
                .show()
    }

    private fun initRefresher(binding: ActivityRestaurantsBinding) {
        refresher = binding.refreshLayout
        refresher.setOnRefreshListener(this)

    }

    override fun onRefresh() {
        scroller.onRefresh()
        adapter.clearRestaurants()
        skeletonScreen.show()
        refreshing = true
        initiateLoad()
    }


    private fun initRecycleView(binding: ActivityRestaurantsBinding, done: (binding: ActivityRestaurantsBinding) -> Unit) {
        recycleView = binding.restaurantsRecycle
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        scroller = EndlessScrollListener(layoutManager, this)
        recycleView.layoutManager = layoutManager
        recycleView.setHasFixedSize(true)
        recycleView.itemAnimator = DefaultItemAnimator()
        recycleView.adapter = adapter
        recycleView.addOnScrollListener(scroller)
        done(binding)
    }

    override fun onScrollToBottom(page: Long) {
        val offset = pages.offset(page)
        if(offset != (-1).toLong())
            repository.loadRestaurants(location.latitude, location.longitude, offset)
    }

    private fun loadContent(latitude: Double, longitude: Double) {
        location.latitude = latitude
        location.longitude = longitude
        repository.loadRestaurants(latitude, longitude, 0)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onError(error: Throwable) {
        showError(error) {}
    }

    override fun onRestaurants(response: JsonResponse) {
        pages = PagesHelper(response.total, resources.getInteger(R.integer.items_per_page))
        adapter.addRestaurants(response.businesses)
        isContentEmptyPopup(adapter.itemCount, response.businesses, this)
    }

    override fun onLoading(loading: Boolean) {
        if(!loading && refreshing) {
            refreshing = false
            skeletonScreen.hide()
            refresher.setRefreshing(false)
        }
    }

}
