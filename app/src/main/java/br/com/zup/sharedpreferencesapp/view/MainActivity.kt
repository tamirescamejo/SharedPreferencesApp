package br.com.zup.sharedpreferencesapp.view

import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.zup.sharedpreferencesapp.R
import br.com.zup.sharedpreferencesapp.databinding.ActivityMainBinding
import br.com.zup.sharedpreferencesapp.model.MainModel
import br.com.zup.sharedpreferencesapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observers()
        viewModel.getSavedData()

        binding.bvLogin.setOnClickListener {
            authenticate()
        }
    }

    private fun authenticate() {
        val user = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val login = MainModel(user, password)

        viewModel.authentication(login, binding.swSaveData.isChecked)
    }

    private fun observers() {
        viewModel.response.observe(this) {
            if (it.accessAuth) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Usuario ou senha invalidos", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.savedData.observe(this) {
            binding.etUsername.setText(it.user)
            binding.etPassword.setText(it.password)
        }
        viewModel.saveDataFlag.observe(this) {
            binding.swSaveData.isChecked = it
        }
    }
}