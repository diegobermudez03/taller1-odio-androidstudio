package com.compumovil.taller_1.repositories

import android.content.res.AssetManager
import com.compumovil.taller_1.services.IRepository
import com.compumovil.taller_1.services.entities.Country
import com.compumovil.taller_1.services.entities.Currency
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.lang.reflect.Type

class CountriesRepository: IRepository {

    class CustomDeserializer: JsonDeserializer<Country> {
        private val currencies = HashMap<String, Currency>()
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Country {
            val jsonObject = json.asJsonObject
            var currency: Currency? = null
            val currencyCode: String = jsonObject.get("CurrencyCode").asString
            if(currencies.get(currencyCode) != null) currency = currencies.get(currencyCode)
            else{
                currency = Currency(
                    jsonObject.get("CurrencyCode").asString,
                    jsonObject.get("CurrencyName").asString,
                    jsonObject.get("CurrencySymbol").asString.getOrElse(0){' '}
                    );
                currencies.put(currency.code, currency)
            }
            return Country(
                jsonObject.get("Name").asString,
                jsonObject.get("Alpha2Code").asString,
                jsonObject.get("Alpha3Code").asString,
                jsonObject.get("NativeName").asString,
                jsonObject.get("Region").asString,
                jsonObject.get("SubRegion").asString,
                if(jsonObject.get("Latitude").asString.length > 0)  jsonObject.get("Latitude").asDouble else 0.0,
                if(jsonObject.get("Longitude").asString.length > 0) jsonObject.get("Longitude").asDouble else 0.0,
                if(jsonObject.get("Area") is JsonNull) 0.0 else jsonObject.get("Area").asDouble,
                if(jsonObject.get("NumericCode") is JsonNull) 0 else jsonObject.get("NumericCode").asInt,
                jsonObject.get("NativeLanguage").asString,
                currency!!,
                jsonObject.get("Flag").asString,
                jsonObject.get("FlagPng").asString,
            )
        }
    }

    override fun fetchCountries(assets: AssetManager): List<Country> {
        try {
            val inputStream = assets.open("countries/data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonData =  String(buffer, charset("UTF-8"))
            val jsonElement: JsonElement = JsonParser.parseString(jsonData)
            val jsonObject: JsonObject = jsonElement.asJsonObject
            val jsonArray = jsonObject.getAsJsonArray("Countries")
            val gson = GsonBuilder().registerTypeAdapter(Country::class.java, CustomDeserializer()).create()
            return gson.fromJson(jsonArray, Array<Country>::class.java).toList()
        }catch (e:Exception){
            println(e.toString())
        }
        return ArrayList<Country>()
    }
}