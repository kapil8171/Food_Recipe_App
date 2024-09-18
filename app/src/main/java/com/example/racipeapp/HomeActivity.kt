package com.example.racipeapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.racipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var rvAdapter: PopularAdaptor
    private lateinit var binding: ActivityHomeBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        setUpRecyclerView()
         binding.search.setOnClickListener {
             startActivity(Intent(this@HomeActivity,SearchActivity::class.java))
         }

         binding.salad.setOnClickListener {
             var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
              myIntent.putExtra("TITTLE", "Salad")
               myIntent.putExtra("CATEGORY", "Salad")
               startActivity(myIntent)
         }
         binding.mainDish.setOnClickListener {
             var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
             myIntent.putExtra("TITTLE", "Main Dish")
             myIntent.putExtra("CATEGORY", "Dish")
             startActivity(myIntent)
         }
         binding.drinks.setOnClickListener {
             var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
             myIntent.putExtra("TITTLE", "Drinks")
             myIntent.putExtra("CATEGORY", "Drinks")
             startActivity(myIntent)
         }
         binding.desserts.setOnClickListener {
             var myIntent = Intent(this@HomeActivity,CategoryActivity::class.java)
             myIntent.putExtra("TITTLE", "Desserts")
             myIntent.putExtra("CATEGORY", "Desserts")
             startActivity(myIntent)
         }

        binding.more.setOnClickListener {
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)

            dialog.show()

            dialog.window!!.setLayout(
                 ViewGroup.LayoutParams.MATCH_PARENT,
                       ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }


    }

    private fun setUpRecyclerView() {
        // Initialize an empty ArrayList to hold the data
        var dataList = ArrayList<Recipe>()

        // Set up the RecyclerView and adapter
        rvAdapter = PopularAdaptor(dataList, this)
        binding.rvPopular.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var db = Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        var recipes = daoObject.getAll()

        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                dataList.add(recipes[i]!!)
            }

            rvAdapter = PopularAdaptor(dataList,this)

            binding.rvPopular.adapter = rvAdapter
        }



    }



}
