package br.com.zup.sharedpreferencesapp.repository

import br.com.zup.sharedpreferencesapp.model.MainModel

class MainRepository {
    fun authenticate (login : MainModel) : MainModel{

        login.accessAuth = (login.user == "usuario" && login.password == "1234") ||
                (login.user == "" && login.password == "")

        return login
    }
}