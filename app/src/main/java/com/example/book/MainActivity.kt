package com.example.book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawable_layout:DrawerLayout
    lateinit var  coordinatorLaout:CoordinatorLayout
    lateinit var  toolbar:Toolbar
    lateinit var frameLayout:FrameLayout
    lateinit var navigationview:NavigationView
    var previousMenuItem:MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawable_layout=findViewById(R.id.drawable_layout)
        coordinatorLaout=findViewById(R.id.coordinator_layout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame_layout)
        navigationview=findViewById(R.id.navbar)
        setupToolbar()
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawable_layout,R.string.open_drawer,R.string.close_drawer)
        drawable_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        openDashboard()

        navigationview.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            when(it.itemId){
                R.id.dashboard->{
                    openDashboard()
                }
                R.id.favourites->{
                    Toast.makeText(this,"Fav",Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,FavouritesFragment()).commit()
                    drawable_layout.closeDrawers()
                    supportActionBar?.title="Favourites"
                }
                R.id.profile->{
                    Toast.makeText(this,"profile",Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,profileFragment()).commit()
                    drawable_layout.closeDrawers()
                    supportActionBar?.title="Profile"
                }
                R.id.about->{
                    Toast.makeText(this,"About",Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,AbountFragment()).commit()
                    drawable_layout.closeDrawers()
                    supportActionBar?.title="About"
                }

            }
            return@setNavigationItemSelectedListener true }
    }

    fun setupToolbar(){
        setSupportActionBar(toolbar);
        supportActionBar?.title="Book Hub"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawable_layout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard(){
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,DashboardFragment()).commit()
        drawable_layout.closeDrawers()
        supportActionBar?.title="Dashboard"
        navigationview.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
       val frag=supportFragmentManager.findFragmentById(R.id.frame_layout);

        when(frag){
            !is DashboardFragment->openDashboard()
            else -> super.onBackPressed()
        }
    }
}