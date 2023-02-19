package com.vkunitsyn.level4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.utils.FeatureFlags

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if (FeatureFlags.transactionsEnabled) {

                supportFragmentManager.commit {
                    add<AuthFragment>(R.id.fragment_container)
                    addToBackStack(null)
                }
            } else {
                val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
                supportFragmentManager.commit {
                    replace(R.id.fragment_container, navHostFragment)
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
        }
    }
}