package com.gunay.countriesapp.service

import android.database.Observable
import com.gunay.countriesapp.model.Country
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    suspend fun getCountries() : Response<List<Country>>

}