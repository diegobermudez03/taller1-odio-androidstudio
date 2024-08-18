package com.compumovil.taller_1.services

import android.content.res.AssetManager
import com.compumovil.taller_1.services.entities.Country

interface IRepository {

    fun fetchCountries(assets : AssetManager): List<Country>
}