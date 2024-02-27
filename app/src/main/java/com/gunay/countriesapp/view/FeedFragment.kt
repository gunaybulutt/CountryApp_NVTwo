package com.gunay.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gunay.countriesapp.adapter.CountryAdapter
import com.gunay.countriesapp.databinding.FragmentFeedBinding
import com.gunay.countriesapp.viewmodel.FeedVM


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var viewModel : FeedVM
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedVM::class.java)
        viewModel.refleshData()


        binding.FeedRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.FeedRecyclerView.adapter = countryAdapter


        binding.swipeRefleshLayout.setOnRefreshListener {
            binding.FeedRecyclerView.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refleshData()
            binding.swipeRefleshLayout.isRefreshing = false
        }



        observeLiveData()
    }



    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.FeedRecyclerView.visibility = View.VISIBLE
                binding.countryError.visibility = View.GONE
                binding.countryLoading.visibility = View.GONE
                countryAdapter.updateCountryList(it)

            }
        })

        //error varmı yokmu onu kontrol ediyoruz
        viewModel.countryError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.countryError.visibility = View.VISIBLE
                }else{
                    binding.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryError.visibility = View.GONE
                    binding.FeedRecyclerView.visibility = View.GONE
                }else{
                    binding.countryLoading.visibility = View.GONE
                }
            }
        })

    }

}























