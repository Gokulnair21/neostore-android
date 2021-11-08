package com.example.neostore_android

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neostore_android.repositories.PreferenceRepository

class LauncherActivity : AppCompatActivity() {

    private val preferenceRepository: PreferenceRepository by lazy {
        (application as NeoStoreApplication).preferenceRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (preferenceRepository.accessToken.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}