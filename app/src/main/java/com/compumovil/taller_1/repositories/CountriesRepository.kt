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

    //custom deserializer, it's how we will deserialize each object inside the
    //json array, since, we won't simply translate the data to an exact model of that object
    //but we will apply some logic for the deserialization, for instance, separating currency and
    //country
    class CustomDeserializer: JsonDeserializer<Country> {
        //map to keep track of the currencies so that if two countries manage the same currency
        //then they will reference the same one
        private val currencies = HashMap<String, Currency>()

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): Country {
            val jsonObject = json.asJsonObject
            var currency: Currency? = null
            val currencyCode: String = jsonObject.get("CurrencyCode").asString
            //checking if the currency already exists, if it does, reference that, if not, create a new one
            if(currencies.get(currencyCode) != null) currency = currencies.get(currencyCode)
            else{
                currency = Currency(
                    jsonObject.get("CurrencyCode").asString,
                    jsonObject.get("CurrencyName").asString,
                    jsonObject.get("CurrencySymbol").asString.getOrElse(0){' '}
                    );
                currencies.put(currency.code, currency)
            }

            //deserialization, for some fields, which in the json where either null or empty we had
            //to apply some validation
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

    //method for fetching countries
    override fun fetchCountries(assets: AssetManager): List<Country> {
        try {
            //opening the json file and getting all the bytes into a inputStream
            val inputStream = assets.open("countries/data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonData =  String(buffer, charset("UTF-8"))

            //first we will get only the countries attribute, the array one, it's the one we will work with
            val jsonElement: JsonElement = JsonParser.parseString(jsonData)
            val jsonObject: JsonObject = jsonElement.asJsonObject
            val jsonArray = jsonObject.getAsJsonArray("Countries")

            //once we get the json array, we apply the deserializacion with Gson and the custom deserializer, and we return that data
            val gson = GsonBuilder().registerTypeAdapter(Country::class.java, CustomDeserializer()).create()
            return gson.fromJson(jsonArray, Array<Country>::class.java).toList()
        }catch (e:Exception){
            println(e.toString())
        }
        return ArrayList<Country>()
    }
}