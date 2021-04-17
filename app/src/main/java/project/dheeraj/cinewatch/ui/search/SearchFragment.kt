package project.dheeraj.cinewatch.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import project.dheeraj.cinewatch.R
import project.dheeraj.cinewatch.data.model.Movie
import project.dheeraj.cinewatch.data.model.Status
import project.dheeraj.cinewatch.databinding.FragmentHomeBinding
import project.dheeraj.cinewatch.databinding.FragmentSearchBinding
import project.dheeraj.cinewatch.ui.adapter.SearchRecyclerViewAdapter
import project.dheeraj.cinewatch.utils.showToast
import timber.log.Timber.e

@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    private lateinit var searchAdapter: SearchRecyclerViewAdapter
    private var searchResult : ArrayList<Movie> = ArrayList()
    private var query = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchAdapter = SearchRecyclerViewAdapter()
        binding.searchRecyclerView.adapter = searchAdapter

        binding.buttonBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchResult()
                true
            }
            false
        }


    }

    fun getSearchResult() {
        viewModel.getSearchMovie(binding.searchEditText.text.toString())
            .observe(viewLifecycleOwner, Observer {
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                e(it.toString())
            })
    }

    fun performSearch() {

        viewModel.searchMovie(binding.searchEditText.text.toString())
                .observe(requireActivity(), Observer { res ->

                    when(res.status) {
                        Status.LOADING -> {
                            showToast("Loading")
                        }
                        Status.SUCCESS -> {
                            searchResult.clear()
                            showToast(res.data!!.total_results.toString())
                            searchResult.addAll(res.data.results)
                            searchAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {}
                    }

                })

    }


}