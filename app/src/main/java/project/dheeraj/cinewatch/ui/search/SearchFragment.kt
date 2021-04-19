package project.dheeraj.cinewatch.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
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
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
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

        searchAdapter = SearchRecyclerViewAdapter()
        binding.searchRecyclerView.adapter = searchAdapter

        binding.buttonBack.setOnClickListener {
            it.findNavController().navigateUp()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                getSearchResult()
            }

        })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getSearchResult()
                true
            }
            false
        }


    }

    fun getSearchResult() {
        if (!binding.searchEditText.text.isNullOrEmpty())
            viewModel.getSearchMovie(binding.searchEditText.text.toString())
                .observe(viewLifecycleOwner, Observer {
                    searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    e(it.toString())
                })
    }

    fun performSearch() {

        if (!binding.searchEditText.text.isNullOrEmpty())
            viewModel.searchMovie(binding.searchEditText.text.toString())
                .observe(requireActivity(), Observer { res ->

                    when(res.status) {
                        Status.LOADING -> {
                            showToast("Loading")
                        }
                        Status.SUCCESS -> {
                            searchResult.clear()
                            if (res.data != null && res.data.value != null)
                                searchAdapter.submitData(viewLifecycleOwner.lifecycle,
                                    res.data.value!!
                                )

                        }
                        Status.ERROR -> {}
                    }

                })

    }


}