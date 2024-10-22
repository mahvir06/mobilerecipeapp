package com.example.cw2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Search  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchlayout)
        var display = findViewById<TextView>(R.id.results)
        display.text = ""
        var usertext = findViewById<EditText>(R.id.editText)
        val search = findViewById<Button>(R.id.button)

        val db = AppDatabase.getDatabase(this)
        val mealDao = db.mealDao()

        search.setOnClickListener {

            runBlocking {
                launch {

                    var searchterm = usertext.text.toString()

                    var obj = mealDao.findByName(searchterm)
//                    for (thing in obj){
//                        var test = thing.toString()
//                        display.text.append(test)
//                    }
                    display.text = obj.toString()

                    println("objs: " + obj + "\n")
                }
            }
        }
    }
}