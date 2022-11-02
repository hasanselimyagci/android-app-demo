package com.example.sixt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


class CarsRecyclerViewAdapter(val context: Context, val cars: Array<Car>, val map: GoogleMap) :
    RecyclerView.Adapter<CarsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CarsRecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return ViewHolder(v);
    }
    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(viewHolder: CarsRecyclerViewAdapter.ViewHolder, position: Int) {
        viewHolder.name.text = cars[position].name

        Glide.with(context)
            .load(cars[position].carImageUrl)
            .into(viewHolder.icon)

        viewHolder.card.setOnClickListener(View.OnClickListener {
            val center = LatLng(cars[position].latitude!!, cars[position].longitude!!)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(center, 15f))
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.name)
        val icon = view.findViewById<ImageView>(R.id.icon)
        val card = view.findViewById<CardView>(R.id.card)
    }
}

