package com.jet2traveltech.article.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.jet2traveltech.article.R
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about_us -> findNavController(R.id.nav_host_fragment).navigate(R.id.AboutUsFragment)

        }
        return super.onOptionsItemSelected(item)
    }
}