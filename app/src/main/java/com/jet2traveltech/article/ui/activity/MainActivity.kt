package com.jet2traveltech.article.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jet2traveltech.article.databinding.ActivityMainBinding

/**
 * Main Activity to contain Article Fragment and its transition(s).
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}