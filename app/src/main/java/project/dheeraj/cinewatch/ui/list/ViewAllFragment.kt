package project.dheeraj.cinewatch.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Status

@ExperimentalCoroutinesApi
class ViewAllFragment : Fragment() {

    companion object {
        fun newInstance() = ViewAllFragment()
    }

    private lateinit var viewModel: ViewAllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_all, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewAllViewModel::class.java)
        // TODO: Use the ViewModel

    }

    fun fetchPopular() {
        viewModel.fetchPopular().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }

        })
    }

    fun fetchTopRated() {

        viewModel.fetchTopRated().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }

        })

    }

    fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(requireActivity(), Observer { res ->

            when(res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }

        })

    }

}