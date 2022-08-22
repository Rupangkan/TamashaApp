package com.example.tamashaapp.view

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tamashaapp.R
import com.example.tamashaapp.model.Data

class EmployeesAdapter(private val data: List<Data>, private val context: Context): RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val arrowUp: ImageView = view.findViewById(R.id.arrowUp)
        val arrowDown: ImageView = view.findViewById(R.id.arrowDown)
        val id: TextView = view.findViewById(R.id.id)
        val name: TextView = view.findViewById(R.id.name)
        val salary: TextView = view.findViewById(R.id.salary)

    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.id.text = item.id.toString()
        holder.name.text = item.employee_name
        holder.salary.text = "$ " + item.employee_salary.toString()
        Glide.with(context).load(R.drawable.doge).centerCrop().into(holder.image)
        holder.arrowDown.setOnClickListener{
            holder.arrowDown.visibility = View.GONE
            holder.salary.visibility = View.VISIBLE
            holder.arrowUp.visibility = View.VISIBLE
        }
        holder.arrowUp.setOnClickListener{
            holder.arrowUp.visibility = View.GONE
            holder.salary.visibility = View.GONE
            holder.arrowDown.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(context).inflate(R.layout.single_card_view, parent, false)
        return ViewHolder(adapterLayout)
    }
}