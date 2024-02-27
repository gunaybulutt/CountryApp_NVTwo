package com.gunay.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gunay.countriesapp.model.Country
import com.gunay.countriesapp.service.CountryApiService
import com.gunay.countriesapp.service.CountryDatabase
import com.gunay.countriesapp.util.CustomSharedPreferences
import kotlinx.coroutines.launch

class FeedVM(application: Application) : AndroidViewModel(application) {

    private val countryApiService = CountryApiService()
    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    fun refleshData(){

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromApi()
        }

    }


    public fun getDataFromApi(){

            viewModelScope.launch {
                try {
                    val response = countryApiService.getData()

                    if (response.isSuccessful){
                        response.body()?.let {
                            storeInSQLite(it)
                            Toast.makeText(getApplication(),"Countries from Api", Toast.LENGTH_LONG).show()
                    }
                } else {
                    println("Error: ${response.message()}")
                    System.out.println("hata_one")
                }


                } catch (e: Exception) {
                    println("Error: ${e.localizedMessage}")
                    System.out.println("hata_two")
                }
            }

    }

    fun getDataFromSQLite(){
        viewModelScope.launch {
            val countries = CountryDatabase(getApplication()).CountryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    private fun storeInSQLite(list: List<Country>){
        viewModelScope.launch {
            val dao = CountryDatabase(getApplication()).CountryDao()
            dao.deleteAllCountries()

            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = listLong[i].toInt()
                i = i +1
            }

            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

}

