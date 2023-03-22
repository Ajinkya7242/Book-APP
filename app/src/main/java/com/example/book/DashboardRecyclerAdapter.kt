package com.example.book

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context:Context,val itemList:ArrayList<Book>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewholder>() {

    class DashboardViewholder(view: View):RecyclerView.ViewHolder(view){
       val txtBookName:TextView=view.findViewById(R.id.txtrecyclerDashboard)
        val txtBookAuther:TextView=view.findViewById(R.id.txtBookAuther)
        val txtBookCost:TextView=view.findViewById(R.id.txtBookCost)
        val txtBookRating:TextView=view.findViewById(R.id.txtBookRating)
        val imgBookname:ImageView=view.findViewById(R.id.imgBookname)
        val liContent:RelativeLayout=view.findViewById(R.id.liContent)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewholder(view)
    }

    override fun getItemCount(): Int {
         return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewholder, position: Int) {
       val book=itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuther.text=book.bookAuthor
        holder.txtBookCost.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
       // holder.imgBookname.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.adventures_finn).into(holder.imgBookname);
        holder.liContent.setOnClickListener{
             val intent=Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
           context.startActivity(intent)
        }

    }
}