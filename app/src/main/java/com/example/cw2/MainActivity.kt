package com.example.cw2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.cw2.R //possibly delete
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    fun parser(meal: String): Meal {

        val mealJ = JSONObject(meal)

       // println(mealJ.getString("Meal"))

        val ingredients = mutableListOf<String>()
        val measurements = mutableListOf<String>()

        val name = mealJ.getString("Meal")

        val drink = mealJ.getString("DrinkAlternate")
        val category = mealJ.getString("Category")
        val area = mealJ.getString("Area")
        val instructions = mealJ.getString("Instructions")
        val youtube = mealJ.getString("Youtube")
        val tags = mealJ.getString("Tags")
        val mealthumb = mealJ.getString("MealThumb")

        var num = 0
        while (true) {

            num++
            try {
                ingredients.add(mealJ.getString("Ingredient" + num.toString()))
                measurements.add(mealJ.getString("Measure" + num.toString()))

            } catch (e: Exception) {
                break
            }
        }
        var list1 = ingredients.joinToString(separator = ", ")
        var list2 = measurements.joinToString(separator = ", ")

        println("parser ended")

        if (mealJ.has("Source")) {
            val source = mealJ.getString("Source")
            val imagesource = mealJ.getString("ImageSource")
            val creativecommonsconfirmed = mealJ.getString("CreativeCommonsConfirmed")
            val datemodified = mealJ.getString("dateModified")

            return Meal(name,
                drink,
                category,
                area,
                instructions,
                youtube,
                mealthumb,
                tags,
                list1,
                list2,
                source,
                imagesource,
                creativecommonsconfirmed,
                datemodified)
        }

        return Meal(
                name,
                drink,
                category,
                area,
                instructions,
                youtube,
                mealthumb,
                tags,
                list1,
                list2,
            null,
            null,
            null,
            null
            )

    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val tv = findViewById<TextView>(R.id.textview)
//        tv.setText("")
// create the database
        val db = AppDatabase.getDatabase(this)
        val mealDao = db.mealDao()


        val startButton = findViewById<Button>(R.id.start)
        val searchBI = findViewById<Button>(R.id.SBI)
        val search = findViewById<Button>(R.id.search)

        runBlocking {
            launch {
                mealDao.clearTable()
            }
        }
        startButton.setOnClickListener {
            runBlocking {
            launch {
                //mealDao.clearTable()
                val meal1 = "{\"Meal\":\"Sweet and Sour Pork\",\n" +
                        "\"DrinkAlternate\":null,\n" +
                        "\"Category\":\"Pork\",\n" +
                        "\"Area\":\"Chinese\",\n" +
                        "\"Instructions\":\"Preparation\\r\\n1. Crack the egg into a bowl. Separate the egg white and yolk.\\r\\n\\r\\nSweet and Sour Pork\\r\\n2. Slice the pork tenderloin into ips.\\r\\n\\r\\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\\r\\n\\r\\n4. Marinade the pork ips for about 20 minutes.\\r\\n\\r\\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\\r\\n\\r\\nSweet and Sour Pork\\r\\nCooking Inuctions\\r\\n1. Pour the cooking oil into a wok and heat to 190\\u00b0C (375\\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\\r\\n\\r\\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\\r\\n\\r\\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\\r\\n\\r\\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\\r\\n\\r\\n5. Serve on a plate and add some coriander for decoration.\",\n" +
                        "\"MealThumb\":\"https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/1529442316.jpg\",\n" +
                        "\"Tags\":\"Sweet\",\n" +
                        "\"Youtube\":\"https:\\/\\/www.youtube.com\\/watch?v=mdaBIhgEAMo\",\n" +
                        "\"Ingredient1\":\"Pork\",\n" +
                        "\"Ingredient2\":\"Egg\",\n" +
                        "\"Ingredient3\":\"Water\",\n" +
                        "\"Ingredient4\":\"Salt\",\n" +
                        "\"Ingredient5\":\"Sugar\",\n" +
                        "\"Ingredient6\":\"Soy Sauce\",\n" +
                        "\"Ingredient7\":\"Starch\",\n" +
                        "\"Ingredient8\":\"Tomato Puree\",\n" +
                        "\"Ingredient9\":\"Vinegar\",\n" +
                        "\"Ingredient10\":\"Coriander\",\n" +
                        "\"Measure1\":\"200g\",\n" +
                        "\"Measure2\":\"1\",\n" +
                        "\"Measure3\":\"Dash\",\n" +
                        "\"Measure4\":\"1\\/2 tsp\",\n" +
                        "\"Measure5\":\"1 tsp \",\n" +
                        "\"Measure6\":\"10g\",\n" +
                        "\"Measure7\":\"10g\",\n" +
                        "\"Measure8\":\"30g\",\n" +
                        "\"Measure9\":\"10g\",\n" +
                        "\"Measure10\":\"Dash\"}"+
                        "\"Source\":\"https:\\/\\/www.bbcgoodfood.com\\/recipes\\/3146682\\/chicken-marengo\",\n" +
                        "\"ImageSource\":null,\n" +
                        "\"CreativeCommonsConfirmed\":null,\n" +
                        "\"dateModified\":null}"


                val meal2 = "{\"Meal\":\"Chicken Marengo\",\n" +
                        "\"DrinkAlternate\":null,\n" +
                        "\"Category\":\"Chicken\",\n" +
                        "\"Area\":\"French\",\n" +
                        "\"Instructions\":\"Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\\r\\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \\u2013 you shouldn\\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.\",\n" +
                        "\"MealThumb\":\"https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/qpxvuq1511798906.jpg\",\n" +
                        "\"Tags\":null,\n" +
                        "\"Youtube\":\"https:\\/\\/www.youtube.com\\/watch?v=U33HYUr-0Fw\",\n" +
                        "\"Ingredient1\":\"Olive Oil\",\n" +
                        "\"Ingredient2\":\"Mushrooms\",\n" +
                        "\"Ingredient3\":\"Chicken Legs\",\n" +
                        "\"Ingredient4\":\"Passata\",\n" +
                        "\"Ingredient5\":\"Chicken Stock Cube\",\n" +
                        "\"Ingredient6\":\"Black Olives\",\n" +
                        "\"Ingredient7\":\"Parsley\",\n" +
                        "\"Measure1\":\"1 tbs\",\n" +
                        "\"Measure2\":\"300g\",\n" +
                        "\"Measure3\":\"4\",\n" +
                        "\"Measure4\":\"500g\",\n" +
                        "\"Measure5\":\"1\",\n" +
                        "\"Measure6\":\"100g \",\n" +
                        "\"Measure7\":\"Chopped\",\n" +
                        "\"Source\":\"https:\\/\\/www.bbcgoodfood.com\\/recipes\\/3146682\\/chicken-marengo\",\n" +
                        "\"ImageSource\":null,\n" +
                        "\"CreativeCommonsConfirmed\":null,\n" +
                        "\"dateModified\":null}"


                val meal3 = "{\"Meal\":\"Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber\",\n" +
                        "\"DrinkAlternate\":null,\n" +
                        "\"Category\":\"Beef\",\n" +
                        "\"Area\":\"Vietnamese\",\n" +
                        "\"Instructions\":\"Add'l ingredients: mayonnaise, siracha\\r\\n\\r\\n1\\r\\n\\r\\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\\r\\n\\r\\n2\\r\\n\\r\\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\\r\\n\\r\\n3\\r\\n\\r\\nIn a medium bowl, combine cucumber, juice from half the lime, \\u00bc tsp sugar (\\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\\u2019d like. Season with salt and pepper.\\r\\n\\r\\n4\\r\\n\\r\\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\\r\\n\\r\\n5\\r\\n\\r\\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.\",\n" +
                        "\"MealThumb\":\"https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/z0ageb1583189517.jpg\",\n" +
                        "\"Tags\":null,\n" +
                        "\"Youtube\":\"\",\n" +
                        "\"Ingredient1\":\"Rice\",\n" +
                        "\"Ingredient2\":\"Onion\",\n" +
                        "\"Ingredient3\":\"Lime\",\n" +
                        "\"Ingredient4\":\"Garlic Clove\",\n" +
                        "\"Ingredient5\":\"Cucumber\",\n" +
                        "\"Ingredient6\":\"Carrots\",\n" +
                        "\"Ingredient7\":\"Ground Beef\",\n" +
                        "\"Ingredient8\":\"Soy Sauce\",\n" +
                        "\"Ingredient9\":\"\",\n" +
                        "\"Measure1\":\"White\",\n" +
                        "\"Measure2\":\"1\",\n" +
                        "\"Measure3\":\"1\",\n" +
                        "\"Measure4\":\"3\",\n" +
                        "\"Measure5\":\"1\",\n" +
                        "\"Measure6\":\"3 oz \",\n" +
                        "\"Measure7\":\"1 lb\",\n" +
                        "\"Measure8\":\"2 oz \",\n" +
                        "\"Source\":\"\",\n" +
                        "\"ImageSource\":null,\n" +
                        "\"CreativeCommonsConfirmed\":null,\n" +
                        "\"dateModified\":null}"


//
                val meal4 = "{\"Meal\":\"Leblebi Soup\",\n" +
                        "\"DrinkAlternate\":null,\n" +
                        "\"Category\":\"Vegetarian\",\n" +
                        "\"Area\":\"Tunisian\",\n" +
                        "\"Instructions\":\"Heat the oil in a large pot. Add the onion and cook until translucent.\\r\\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\\r\\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\\r\\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\\r\\nSeason to taste with salt, pepper and lemon juice and serve hot.\",\n" +
                        "\"MealThumb\":\"https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/x2fw9e1560460636.jpg\",\n" +
                        "\"Tags\":\"Soup\",\n" +
                        "\"Youtube\":\"https:\\/\\/www.youtube.com\\/watch?v=BgRifcCwinY\",\n" +
                        "\"Ingredient1\":\"Olive Oil\",\n" +
                        "\"Ingredient2\":\"Onion\",\n" +
                        "\"Ingredient3\":\"Chickpeas\",\n" +
                        "\"Ingredient4\":\"Vegetable Stock\",\n" +
                        "\"Ingredient5\":\"Cumin\",\n" +
                        "\"Ingredient6\":\"Garlic\",\n" +
                        "\"Ingredient7\":\"Salt\",\n" +
                        "\"Ingredient8\":\"Harissa Spice\",\n" +
                        "\"Ingredient9\":\"Pepper\",\n" +
                        "\"Ingredient10\":\"Lime\",\n" +
                        "\"Measure1\":\"2 tbs\",\n" +
                        "\"Measure2\":\"1 medium finely diced\",\n" +
                        "\"Measure3\":\"250g\",\n" +
                        "\"Measure4\":\"1.5L\",\n" +
                        "\"Measure5\":\"1 tsp \",\n" +
                        "\"Measure6\":\"5 cloves\",\n" +
                        "\"Measure7\":\"1\\/2 tsp\",\n" +
                        "\"Measure8\":\"1 tsp \",\n" +
                        "\"Measure9\":\"Pinch\",\n" +
                        "\"Measure10\":\"1\\/2 \",\n" +
                        "\"Source\":\"http:\\/\\/allrecipes.co.uk\\/recipe\\/43419\\/leblebi--tunisian-chickpea-soup-.aspx\",\n" +
                        "\"ImageSource\":null,\n" +
                        "\"CreativeCommonsConfirmed\":null,\n" +
                        "\"dateModified\":null}"

               // var test = parser(meal4)
                mealDao.insertMeals(parser(meal1))
                mealDao.insertMeals(parser(meal2))
                mealDao.insertMeals(parser(meal3))
                mealDao.insertMeals(parser(meal4))
//
                }
            }
        }

        searchBI.setOnClickListener {
            val intent = Intent(this,searchByIngredient::class.java)
            startActivity(intent)
        }

        search.setOnClickListener {
            val intent = Intent(this,Search::class.java)
            startActivity(intent)
        }

    }
}