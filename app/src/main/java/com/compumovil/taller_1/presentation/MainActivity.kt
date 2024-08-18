package com.compumovil.taller_1.presentation

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.bumptech.glide.Glide
import com.compumovil.taller_1.R
import com.compumovil.taller_1.databinding.ActivityMainBinding
import com.compumovil.taller_1.services.CountriesService
import com.compumovil.taller_1.services.entities.Country
import org.w3c.dom.Text

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
        //get the countries
        val countries: List<Country> = CountriesService.getInstance().getCountries(assets)
        //get the linear layout
        val list: LinearLayout = binding.countriesList;

        //setting callback mmethods for search bar so it triggers the printing of the countries
        binding.searchBar.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                printCountries(countries, p0 , list)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                printCountries(countries, p0 , list)
                return false
            }
        })

        printCountries(countries, "", list)

    }

    fun printCountries(countries: List<Country>, searched : String?, list: LinearLayout) {
        //to re print from scratch and not accumulate
        list.removeAllViews()

        //create a card for each country
        for(c in countries){
            //to check if something was searched, and if it was, we will avoid the not coincidences
            if(searched != null &&
                !(      c.name.lowercase().contains(searched.lowercase()) ||
                        c.region.lowercase().contains(searched.lowercase()) ||
                        c.subRegion.lowercase().contains(searched.lowercase())
                        )) continue

            //create layout params to give margin to carfs
            val cardParams =  LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            cardParams.setMargins(20,10,20,10)

            //create card
            val card: CardView = CardView(this)
            card.layoutParams = cardParams
            card.radius = 20f
            card.cardElevation = 12f
            card.setContentPadding(20, 50, 20, 50)
            card.setCardBackgroundColor(ContextCompat.getColor(this, R.color.card))

            //create linear layout that will be inside card
            val cardContent : LinearLayout = LinearLayout(this)
            cardContent.orientation = LinearLayout.VERTICAL
            cardContent.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            cardContent.setPadding(16,16,16,16)

            //create text for country
            val countryName : TextView = TextView(this)
            countryName.text = c.name
            countryName.textSize = 24f
            countryName.setTextColor(Color.WHITE)
            countryName.gravity = Gravity.CENTER_HORIZONTAL

            //create text for region
            val countryRegion : TextView = TextView(this)
            countryRegion.text = "${c.subRegion}  -  ${c.region}"
            countryRegion.textSize = 20f
            countryRegion.setTypeface(null, Typeface.ITALIC)
            countryRegion.setPadding(20,20,0,0)
            countryRegion.setTextColor(Color.WHITE)



            //adding views to linear layout, linear layout to card, and card to list
            cardContent.addView(countryName)
            cardContent.addView(countryRegion)
            card.addView(cardContent)
            list.addView(card)

            card.setOnClickListener({
                val intent = Intent(this, CountryActivity::class.java)
                intent.putExtra("COUNTRY", c)
                startActivity(intent)
            })
        }
    }
}