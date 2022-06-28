package br.com.zup.sharedpreferencesapp.model

data class MainModel(
    var user : String,
    var password : String,
    var accessAuth : Boolean = false
)
