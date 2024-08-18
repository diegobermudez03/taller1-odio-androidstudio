package com.compumovil.taller_1.services

import android.content.res.AssetManager
import com.compumovil.taller_1.services.entities.Country

//interface only for good architecture of project in case of scability, but it's really not
//neccesary for this project
interface IRepository {

    fun fetchCountries(assets : AssetManager): List<Country>
}