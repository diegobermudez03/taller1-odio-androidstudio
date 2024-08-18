package com.compumovil.taller_1.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.compumovil.taller_1.R
import com.compumovil.taller_1.databinding.ActivityMainBinding
import com.compumovil.taller_1.services.CountriesService
import com.compumovil.taller_1.services.entities.Country

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.appTitle.text = R.string.title.toString()
        binding.appTitle.text = "aaaaaaaaaaaaa"
        //get the countries
        val countries: List<Country> = CountriesService.getInstance().getCountries(assets)
        //get the linear layout
        val list: LinearLayout = binding.countriesList;
        //create a card for each country
        for(c in countries){
            //create layout params to give margin to carfs
            val cardParams =  LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(20,10,20,10)
            //create card
            val card: CardView = CardView(this)
            //assign params and other card style
            card.layoutParams = cardParams
            card.radius = 20f
            card.cardElevation = 12f
            card.setContentPadding(20, 50, 20, 50)
            card.setCardBackgroundColor(Color.parseColor("#FFEBEE"))
            //create text for country
            val countryName : TextView = TextView(this)
            //assign value and other style
            countryName.text = c.name
            countryName.textSize = 24f
            countryName.setTextColor(Color.BLACK)
            countryName.gravity = Gravity.CENTER_HORIZONTAL

            //add view to card and add card to linear layout
            card.addView(countryName)
            list.addView(card)
            //hi how are you
        }

    }
}