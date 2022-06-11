package com.example.project

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.project.databinding.ActivityMainBinding
import com.example.project.ui.home.AnalyseActivity
import com.example.project.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, AnalyseActivity::class.java)
        //binding.analyse.setOnClickListener{startActivity(intent)}

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //User Info의 질환명 spinner 목록 및 연동
        //var data = listOf("-선택하세요-", "비만", "거식증", "폭식증")
        //var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        //val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
        //setContentView(R.layout.fragment_home)
        //binding.disease_input.adapter = adapter
    }
}