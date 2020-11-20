package com.ro0opf.pokemon.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.ActivitySplashBinding
import com.ro0opf.pokemon.ui.search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 1500
    private lateinit var binding: ActivitySplashBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        startLoginActivity()
    }

    private fun startLoginActivity() {
        activityScope.launch {
            delay(SPLASH_TIME_OUT)
            startActivity(Intent(this@SplashActivity, SearchActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}