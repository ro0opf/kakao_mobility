package com.ro0opf.pokemon.ui.search

import android.os.Bundle
import androidx.fragment.app.commit
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.common.base.BaseActivity
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class SearchActivity : BaseActivity(R.layout.activity_search) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, SearchFragment())
        }
    }
}
