package com.ro0opf.pokemon.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.ActivitySplashBinding
import com.ro0opf.pokemon.ui.search.SearchActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 1500
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        startLoginActivity()
    }

    private fun startLoginActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SearchActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, SPLASH_TIME_OUT)
    }
}