package com.example.book

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.book.util.ConnectionManager
import org.json.JSONException


class DashboardFragment : Fragment() {
lateinit var recyclerDashboard: RecyclerView
lateinit var layoutManager:RecyclerView.LayoutManager
lateinit var btnCheckInternet:Button
var  bookInfoList= ArrayList<Book>()
    lateinit var progressBarLayout:RelativeLayout
    lateinit var progressBar: ProgressBar

    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=view.findViewById(R.id.recyclerDashboard);
        progressBar=view.findViewById(R.id.progress_bar)
        progressBarLayout=view.findViewById(R.id.progress_layout)
       progressBarLayout.visibility=View.VISIBLE

        layoutManager=LinearLayoutManager(activity)

        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if(ConnectionManager().checkConnecttivity(activity as Context)){
            val jsonObjectRequest=object : JsonObjectRequest(Request.Method.GET,url,null, Response.Listener {

               try{
                   progressBarLayout.visibility=View.GONE
                   println("Respomse is $it")
                   val success=it.getBoolean("success")
                   if(success){
                       val data=it.getJSONArray("data")
                       for(i in 0 until data.length()){
                           val bookJsonObject=data.getJSONObject(i)
                           val bookObject=Book(
                               bookJsonObject.getString("book_id"),
                               bookJsonObject.getString("name"),
                               bookJsonObject.getString("author"),
                               bookJsonObject.getString("rating"),
                               bookJsonObject.getString("price"),
                               bookJsonObject.getString("image"))

                           bookInfoList.add(bookObject)
                           recyclerAdapter=DashboardRecyclerAdapter(activity as Context,bookInfoList)
                           recyclerDashboard.adapter=recyclerAdapter
                           recyclerDashboard.layoutManager=layoutManager

                       }
                   }
                   else{
                       Toast.makeText(activity as Context,"ERROR",Toast.LENGTH_LONG).show()
                   }
               }catch (e:JSONException){
                   Toast.makeText(activity as Context,"Some Unexpected Error Occurred",Toast.LENGTH_LONG).show()
               }

            },Response.ErrorListener {
                println("Errorrr is $it")
                Toast.makeText(activity as Context,"Volly Error Occurred",Toast.LENGTH_LONG).show()
            }){

                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="2396bd71023d58"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)

        }else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Fail")
            dialog.setMessage("Internet Connection not Found")
            dialog.setPositiveButton("Open Settings"){text,listener->
                val settingsIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()

        }


        return view
    }


}

