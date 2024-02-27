package com.gunay.countriesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gunay.countriesapp.R
import com.gunay.countriesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }



}