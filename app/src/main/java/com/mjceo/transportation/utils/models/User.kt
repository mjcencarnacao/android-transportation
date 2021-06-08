package com.mjceo.transportation.utils.models

data class User(val email: String, val password: String) {
    var card: String = "Default Viva Card Field"
    lateinit var name: String
    var image: String? = null
}




