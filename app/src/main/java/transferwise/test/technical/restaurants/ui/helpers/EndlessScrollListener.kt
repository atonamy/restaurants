package transferwise.test.technical.restaurants.ui.helpers

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class EndlessScrollListener(private val linearLayoutManager: LinearLayoutManager,
                            private val listener: EndlessScrollListener.ScrollToBottomListener) : RecyclerView.OnScrollListener() {

    private var loading = true
    private val visibleThreshold = 3
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var adjuster: Int = 1
    private var previousTotal = -adjuster
    private var page: Long = 1

    var hasFooter = true
    set(value) {
        field = value
        adjuster = if(value) 1 else 0
    }

    fun onRefresh() {
        previousTotal = -adjuster
        page = 1
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = linearLayoutManager.itemCount
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount-adjuster > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            loading = true
            Log.d(EndlessScrollListener::class.java.simpleName, "onScroll")
            this.listener.onScrollToBottom(page)
            page++
        }
    }

    interface ScrollToBottomListener {
        fun onScrollToBottom(page: Long)
    }
}