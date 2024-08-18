package com.compumovil.taller_1.services.entities

import java.io.Serializable

data class Country (
    val name: String,
    val alpha2Code : String,
    val alpha3Code : String,
    val nativeName : String,
    val region : String,
    val subRegion : String,
    val latitude : Double,
    val longitude : Double,
    val area : Double,
    val numericCode : Int,
    val nativeLanguage: String,
    val currency : Currency,
    val flag : String,
    val flagPng : String
    ): Serializable{
}