package transferwise.test.technical.restaurants.ui.fragments


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import transferwise.test.technical.restaurants.R
import transferwise.test.technical.restaurants.data.view_model.RestaurantInfoViewModel
import transferwise.test.technical.restaurants.databinding.InformationLayoutBinding


class RestaurantInfoFragment : Fragment() {

    private val viewModel = RestaurantInfoViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.hours = it.getString("hours", "")
            viewModel.category = it.getString("category", "")
            viewModel.open = it.getString("open", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<InformationLayoutBinding>(
                inflater, R.layout.information_layout, container, false)
        restoreState(savedInstanceState)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putString("hours", viewModel.hours)
        state.putString("category", viewModel.category)
        state.putString("open", viewModel.open)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if(savedInstanceState != null) {
            viewModel.hours = savedInstanceState.getString("hours", "")
            viewModel.category = savedInstanceState.getString("category", "")
            viewModel.open = savedInstanceState.getString("open", "")
        }
    }


}
