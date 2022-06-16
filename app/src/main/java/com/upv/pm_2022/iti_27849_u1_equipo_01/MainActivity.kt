package com.upv.pm_2022.iti_27849_u1_equipo_01

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    var viewPager: ViewPager2? = null
    var tabLayout: TabLayout? = null
    companion object{
        lateinit var db: DbHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        db = DbHelper(applicationContext)

        setUpTabBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.about -> showDialogAbout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogAbout(){
        // Assign values
        val builder = AlertDialog.Builder(this)
        val viewDialog = layoutInflater.inflate(R.layout.about, null)
        // Pass view to builder
        builder.setView(viewDialog)
        // Create dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun setUpTabBar()
    {
        val adapter = TabPageAdapter(this, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                tabLayout!!.selectTab(tabLayout!!.getTabAt(position))
            }
        })

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab)
            {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}