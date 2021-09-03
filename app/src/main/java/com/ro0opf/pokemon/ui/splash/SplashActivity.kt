package com.ro0opf.pokemon.ui.splash

import android.os.Bundle
import androidx.fragment.app.commit
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.common.base.BaseActivity
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, SplashFragment())
        }
    }
}
