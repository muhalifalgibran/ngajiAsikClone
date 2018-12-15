package id.alif.footbalmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.alif.footbalmatchschedule.R
import kotlinx.android.synthetic.main.activity_home.*
import id.alif.footbalmatchschedule.R.id.*
import id.alif.footbalmatchschedule.fragment.FavoriteFragment
import id.alif.footbalmatchschedule.fragment.LoadFavFragment
import id.alif.footbalmatchschedule.fragment.LoadMatchFragment
import id.alif.footbalmatchschedule.fragment.TeamsFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                matches -> {
                    loadMatchFragment(savedInstanceState)
                }
                teams ->{
                    loadTeamsFragment(savedInstanceState)
                }
                favorites ->{
                    loadFavFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    LoadMatchFragment(),
                    LoadMatchFragment()::class.java.simpleName)
                .commit()
        }
    }
    private fun loadTeamsFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    TeamsFragment(),
                    TeamsFragment()::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavFragment(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container,
                    LoadFavFragment(),
                    LoadFavFragment()::class.java.simpleName)
                .commit()
        }
    }


}
