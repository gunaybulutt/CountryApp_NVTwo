package com.gunay.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gunay.countriesapp.model.Country
import com.gunay.countriesapp.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryVM (application: Application) : AndroidViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        viewModelScope.launch {
            val dao = CountryDatabase(getApplication()).CountryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }



}