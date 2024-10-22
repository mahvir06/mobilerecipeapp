package com.example.cw2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



class searchByIngredient : AppCompatActivity() {
    lateinit var finalList: MutableList<Meal>

    fun clean(dirty:JSONObject) : JSONObject{

        dirty.remove("idMeal")
        dirty.remove("strSource")
        dirty.remove("strImageSource")
        dirty.remove("strCreativeCommonsConfirmed")
        dirty.remove("dateModified")
        dirty.remove("strMealThumb")

        var array = dirty.keys().asSequence().toList()
        for (i in 0 until array.size){

            if (array[i].contains("str")){
                dirty.put(array[i].substring(3), dirty.getString(array[i]))
                dirty.remove(array[i])
            }
        }
        //println(dirty.toString())
        return dirty
    }

    fun transfer(DBObject: String) : Meal{

        val mealJSON = JSONObject(DBObject)

        val names = mealJSON.getString("Meal")
        val tags = mealJSON.getString("Tags")
        val category = mealJSON.getString("Category")
        val area = mealJSON.getString("Area")
        val instruc = mealJSON.getString("Instructions")
        val YT = mealJSON.getString("Youtube")

        val ingredients = mutableListOf<String>()
        val measurements = mutableListOf<String>()
        var num = 0
        while (true) {

            num++
            try {
                ingredients.add(mealJSON.getString("Ingredient" + num.toString()))
                measurements.add(mealJSON.getString("Measure" + num.toString()))

            } catch (e: Exception) {
                break
            }
        }
        return Meal(
            names,
            null,
            category,
            area,
            instruc,
            YT,
            null,
            tags,
            ingredients.joinToString(","),
            measurements.joinToString(","),
            null,
            null,
            null,
            null
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchbyingredient)
        finalList = mutableListOf()
        var usertext = findViewById<EditText>(R.id.editText)
        var display = findViewById<TextView>(R.id.results)

        val retrieve = findViewById<Button>(R.id.retrieve)
        val save = findViewById<Button>(R.id.save)

        val db = AppDatabase.getDatabase(this)
        val mealDao = db.mealDao()

        retrieve.setOnClickListener {
            finalList.clear()
            var searchterm = usertext.text.toString()
            searchterm = searchterm.replace(" ", "_")

            var stb = StringBuilder()
            val url_string = "https://www.themealdb.com/api/json/v1/1/filter.php?i=$searchterm"
            val url = URL(url_string)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            var output = ""

            runBlocking {
                launch {
// run the code of the coroutine in a new thread
                    withContext(Dispatchers.IO) {
                        var bf = BufferedReader(InputStreamReader(con.inputStream))
                        var line: String? = bf.readLine()
                        while (line != null) {
                            stb.append(line + "\n")
                            line = bf.readLine()
                        }

                        output = JSONObject(stb.toString()).getString("meals")
                        //println(output)
                        // parseJSON(stb)
                    }
                }
            }

            var arrayofmeals = JSONArray(output)

            for (i in 0 until arrayofmeals.length()){
                var mealID = arrayofmeals.getJSONObject(i).getString("idMeal")
               // println(mealID)
                var stb = StringBuilder()
                val url_string = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealID"
                val url = URL(url_string)
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                var output = ""

                runBlocking {
                    launch {
// run the code of the coroutine in a new thread
                        withContext(Dispatchers.IO) {
                            var bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                stb.append(line + "\n")
                                line = bf.readLine()
                            }

                            output = JSONObject(stb.toString()).getString("meals")
                            var added = clean(JSONArray(output).getJSONObject(0))

                           // var final = transfer(added.toString())
                            //println(added)
                            finalList.add(transfer(added.toString()))


                           // println(transfer(added.toString()))
                        // parseJSON(stb)
                        }
                    }
                }
            }

            runBlocking {
                launch {
                    display.text = finalList.toString()

                }
            }

        }

        save.setOnClickListener {
            for(meal in finalList) {
                runBlocking {
                    launch{
                        mealDao.insertMeals(meal)
                    }
                }
            }

//            runBlocking {
//                launch{
//                    var test = mealDao.getAll()
//                    display.text = test.toString()
//                    print("test" + test)
//
//                }
//            }
        }
    }
}