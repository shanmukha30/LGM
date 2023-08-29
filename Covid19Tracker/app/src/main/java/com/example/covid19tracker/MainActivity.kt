package com.example.covid19tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val modelList = mutableListOf<Model>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        fetchData()
    }

    private fun fetchData() {
        val url = "https://data.covid19india.org/v4/min/data.min.json"

        val cityNames = arrayOf(arrayOf("AP","Chittoor"), arrayOf("BR","Patna"), arrayOf("HR","Gurugram"), arrayOf("CH","Chandigarh"), arrayOf("CT","Bastar"))

        val request = StringRequest(Request.Method.GET, url, { response ->
            try {
                for(i in 0..cityNames.size-1)
                {
                    val objectMain = JSONObject(response)
                    val object1 = objectMain.getJSONObject(cityNames[i][0])
                    val object2 = object1.getJSONObject("districts")
                    val object3 = object2.getJSONObject(cityNames[i][1])
                    val object4 = object3.getJSONObject("total")
                    val object5 = object3.getJSONObject("delta")

                    val active = object4.getString("tested")
                    val confirmed = object4.getString("confirmed")
                    val deceased = object4.getString("deceased")
                    val recovered = object4.getString("recovered")
                    var confDec : String? = "0"
                    val confInc = object5.getString("confirmed")
                    try {
                        confDec = object5.getString("deceased")
                    }
                    catch (e: JSONException)
                    {
                        e.printStackTrace()
                    }

                    val confRec = object5.getString("recovered")

                    val model = Model(cityNames[i][1], confirmed, deceased, recovered, active, confInc, confDec, confRec)
                    modelList.add(model)
                }


                // Similar logic for other locations...

                listView.adapter = modelAdapter(this@MainActivity,modelList)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
        })

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }
}