package com.example.book


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request

import com.example.book.util.ConnectionManager
import org.json.JSONException
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.POST
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


import com.squareup.picasso.Picasso
import org.json.JSONObject


class DescriptionActivity : AppCompatActivity() {
    lateinit var txtBookName:TextView
    lateinit var txtBookAuthor:TextView
    lateinit var txtBookPrice:TextView
    lateinit var txtBookRating:TextView
    lateinit var  imgBookImage:ImageView
    lateinit var txtBookDesc:TextView
    lateinit var btnAddtoFav:Button
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayoot:RelativeLayout
    var bookId:String?="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtBookName = findViewById(R.id.txtBookName)
        txtBookAuthor = findViewById(R.id.txtBookAuther)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookRating = findViewById(R.id.txtBookrating)
        imgBookImage = findViewById(R.id.imgBook)
        txtBookDesc = findViewById(R.id.txtBookDescription)
        btnAddtoFav = findViewById(R.id.button)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        progressBarLayoot = findViewById(R.id.progressLayout)
        progressBarLayoot.visibility = View.VISIBLE

        if (intent != null) {
            bookId = intent.getStringExtra("book_id").toString()
        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some Unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()
        }

        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id",bookId)
        Toast.makeText(this@DescriptionActivity,bookId,Toast.LENGTH_LONG).show()
        val jsonRequest=object: JsonObjectRequest(Request.Method.POST,url,jsonParams, Response.Listener {
            Toast.makeText(this@DescriptionActivity,"in",Toast.LENGTH_LONG).show()
             try{
                 val success=it.getBoolean("success")
                 if(success){
                     val bookJsonObject=it.getJSONObject("book_data")
                     progressBarLayoot.visibility=View.GONE
                     Picasso.get().load(bookJsonObject.getString("image")).error(R.drawable.img).into(imgBookImage)
                     txtBookName.text=bookJsonObject.getString("name")
                     txtBookAuthor.text=bookJsonObject.getString("author")
                     txtBookPrice.text=bookJsonObject.getString("price")
                     txtBookRating.text=bookJsonObject.getString("rating")
                     txtBookDesc.text=bookJsonObject.getString("description")
                 }else{
                     Toast.makeText(this@DescriptionActivity,"Error in else",Toast.LENGTH_LONG).show()
                 }
             }catch (e:Exception){
                 Toast.makeText(this@DescriptionActivity,"Error in catch",Toast.LENGTH_LONG).show()
             }


        },Response.ErrorListener {
            Toast.makeText(this@DescriptionActivity,"Error is $it",Toast.LENGTH_LONG).show()
        }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["Content-type"]="application/json"
                headers["token"]="2396bd71023d58"
                return headers
            }
        }

    }
}