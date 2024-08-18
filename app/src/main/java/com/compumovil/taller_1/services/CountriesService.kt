package com.compumovil.taller_1.services

import android.content.res.AssetManager
import com.compumovil.taller_1.repositories.CountriesRepository
import com.compumovil.taller_1.services.entities.Country

class CountriesService {
    companion object{
        private var  instance: CountriesService? = null

        fun getInstance(): CountriesService{
            //for this project since it's pretty basic we inject the dependency right here
            //but if we wanted to scale this then this would be taken out and we would use
            //something for dependency injection
            if(instance == null) instance = CountriesService(CountriesRepository())
            return instance!!
        }
    }

    val repository: IRepository

    private constructor(repository: IRepository){
        this.repository = repository
    }

    //it's basically innecesary since it does nothing, because there's no
    //business logic to implement, but if there were, then we would implement it here
    fun getCountries(assets: AssetManager):List<Country>{
        return repository.fetchCountries(assets)
    }

}