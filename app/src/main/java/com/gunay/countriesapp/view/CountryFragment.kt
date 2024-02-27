package com.gunay.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gunay.countriesapp.databinding.FragmentCountryBinding
import com.gunay.countriesapp.util.downloadFromUrl
import com.gunay.countriesapp.util.placeholderProgressBar
import com.gunay.countriesapp.viewmodel.CountryVM


class CountryFragment : Fragment() {

    private lateinit var binding: FragmentCountryBinding
    private lateinit var viewModel : CountryVM
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryVM::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {

                binding.selectedCountry = it

                /*
                binding.countryName.text = country.countryName
                binding.countryCapital.text = country.countryCapital
                binding.CountryCurrency.text = country.countryCurrency
                binding.CountryLanguage.text = country.countryLanguage
                binding.countryRegion.text = country.countryRegion
                context?.let {
                    binding.countryImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))

                }*/
            }
        })
    }
}