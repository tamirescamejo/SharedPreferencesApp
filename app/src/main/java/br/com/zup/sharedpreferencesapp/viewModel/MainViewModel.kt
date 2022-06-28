package br.com.zup.sharedpreferencesapp.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.sharedpreferencesapp.PREFERENCE_KEY
import br.com.zup.sharedpreferencesapp.SAVE_USER_PASS_FLAG_KEY
import br.com.zup.sharedpreferencesapp.USER_NAME_LOGIN_KEY
import br.com.zup.sharedpreferencesapp.USER_PASSWORD_LOGIN_KEY
import br.com.zup.sharedpreferencesapp.model.MainModel
import br.com.zup.sharedpreferencesapp.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()
    private var _response: MutableLiveData<MainModel> = MutableLiveData()
    val response: LiveData<MainModel> = _response

    private val _savedData : MutableLiveData<MainModel> = MutableLiveData()
    val savedData : LiveData<MainModel> = _savedData

    private  val _saveDataFlag : MutableLiveData<Boolean> = MutableLiveData()
    val saveDataFlag : LiveData<Boolean> = _saveDataFlag

    private val pref = application.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE)
    private val prefEditor = pref.edit()

    fun getSavedData(){
        try {
            val user = pref.getString(USER_NAME_LOGIN_KEY, "").toString()
            val password = pref.getString(USER_PASSWORD_LOGIN_KEY, "").toString()
            val savedUser = MainModel(user, password)
            _savedData.value = savedUser
            _saveDataFlag.value = pref.getBoolean(SAVE_USER_PASS_FLAG_KEY, false)
        }catch (e:Exception){
            Log.i("Error", "------> ${e.message}")
        }
    }

    fun authentication (login : MainModel, flagSaveData:Boolean){
        try {
            prefEditor.putBoolean(SAVE_USER_PASS_FLAG_KEY, flagSaveData)
            if(flagSaveData){
                prefEditor.putString(USER_NAME_LOGIN_KEY, login.user)
                prefEditor.putString(USER_PASSWORD_LOGIN_KEY, login.password)
                prefEditor.apply()
            }else{
                prefEditor.remove(USER_NAME_LOGIN_KEY)
                prefEditor.remove(USER_PASSWORD_LOGIN_KEY)
                prefEditor.apply()
            }
            _response.value = repository.authenticate(login)
        }catch (ex: Exception){
            Log.i("Error", "------> ${ex.message}")
        }
    }
}