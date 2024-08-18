package com.compumovil.taller_1.services

import android.content.res.AssetManager
import com.compumovil.taller_1.repositories.CountriesRepository
import com.compumovil.taller_1.services.entities.Country

class CountriesService {
    companion object{
        private var  instance: CountriesService? = null

        fun getInstance(): CountriesService{
            if(instance == null) instance = CountriesService(CountriesRepository())
            return instance!!
        }
    }

    lateinit var repository: IRepository

    private constructor(repository: IRepository){
        this.repository = repository
    }

    fun getCountries(assets: AssetManager):List<Country>{
        return repository.fetchCountries(assets)
    }

}