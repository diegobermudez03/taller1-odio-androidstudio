package com.compumovil.taller_1.presentation

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.compumovil.taller_1.databinding.ActivityCountryBinding
import com.compumovil.taller_1.services.entities.Country
import org.w3c.dom.Text

class CountryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getting country selected
        val country: Country = intent.getSerializableExtra("COUNTRY") as Country

        //configuring app bar and back button
        setSupportActionBar(binding.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.appBar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        supportActionBar?.title = country.name

        //setting action to call button
        binding.callButton.setOnClickListener({
            val prefix = "tel:${country.numericCode}"
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse(prefix)
            })

        })

        //setting the flag image
        Glide.with(this)
            .load(country.flagPng)
            .into(binding.countryFlag)

        //creating all the other info to show
        //country name
        val countryName: TextView = TextView(this)
        countryName.text = "${country.name}  -  ${country.nativeName}"
        countryName.textSize = 30f
        countryName.setPadding(0,20,0,40)
        countryName.setTypeface(null, Typeface.BOLD_ITALIC)

        //country region and subregion
        val countryRegion : TextView = TextView(this)
        countryRegion.textSize = 24f
        countryRegion.setPadding(0,0,0,30)
        countryRegion.text = "From ${country.region}, Exactly in ${country.subRegion} "

        //country info
        val coordenates : TextView = TextView(this)
        coordenates.setPadding(0,40,0,30)
        coordenates.text = "LATITUDE: ${country.latitude}  LONGITUDE: ${country.longitude}  TOTAL AREA: ${country.area}"
        coordenates.textSize = 19f

        //currency info
        val currency : TextView = TextView(this)
        currency.textSize = 19f
        currency.text = "HOW TO PAY HERE?\nWell, you can use ${country.currency.name} ${country.currency.symbol}100.0"


        //adding to the view
        binding.content.addView(countryName)
        binding.content.addView(countryRegion)
        binding.content.addView(coordenates)
        binding.content.addView(currency)


    }


}