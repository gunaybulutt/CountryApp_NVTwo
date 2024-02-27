package com.gunay.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gunay.countriesapp.R
import com.gunay.countriesapp.model.Country
import com.gunay.countriesapp.databinding.RecyclerItemBinding
import java.io.FileNotFoundException
import com.gunay.countriesapp.util.downloadFromUrl
import com.gunay.countriesapp.util.placeholderProgressBar
import com.gunay.countriesapp.view.FeedFragmentDirections


class CountryAdapter(val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){



    class CountryViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) , CountryClickListener
    {
        override fun onCountryClicked(v: View) {

            //alternatif
            //val uuid = v.findViewById<TextView>(R.id.countryUuidText).text.toString().toInt()
            var uuid = binding.countryUuidText.text.toString().toInt()
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
            Navigation.findNavController(v).navigate(action)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)

        //eski data binding
        //val inflater = LayoutInflater.from(parent.context)
        //val view = DataBindingUtil.inflate<RecyclerItemBinding>(inflater, R.layout.recycler_item,parent,false)
        //return CountryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country = countryList[position]
        holder.binding.listener = holder




        /*try {
            holder.binding.name.text = countryList[position].countryName
            holder.binding.region.text = countryList[position].countryRegion
            holder.binding.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.binding.imageView.context))

            holder.binding.view.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
                Navigation.findNavController(it).navigate(action)
            }

        }catch (e: FileNotFoundException){
            System.out.println(e)
        }*/



    }

    fun updateCountryList(newCountryList: List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }



}

