package com.ro0opf.pokemon.ui.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ro0opf.pokemon.R
import com.ro0opf.pokemon.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        binding.edtSearch.addTextChangedListener {
            text ->
            run {
                Log.e("123123", text.toString())
            }
        }
    }
}