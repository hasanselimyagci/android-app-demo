package com.example.sixt

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.sixt.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var cars: Array<Car>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val gson = Gson()

        val jsonFileString = getJsonDataFromAsset(applicationContext, "cars.json")
        val arrayTutorialType = object : TypeToken<Array<Car>>() {}.type
        cars = gson.fromJson<Array<Car>>(jsonFileString, arrayTutorialType)

        recyclerView = findViewById(R.id.list) as RecyclerView
        val Manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = Manager

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val adapter = CarsRecyclerViewAdapter(this@MapsActivity,cars, mMap)
        recyclerView.adapter = adapter

        cars.forEachIndexed  {
                idx, car ->
            createMarker(car.latitude!!, car.longitude!!, car.name!!)
        }

        val center = LatLng(48.134557, 11.576921)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 11f))

        mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            var index = 0
            cars.forEachIndexed{
                i, c ->
                if(marker.title.equals(c.name))
                    index = i
            }
            recyclerView.smoothScrollToPosition(index)
            false
        }

    }


    private fun createMarker(latitude: Double, longitude: Double,
                             name: String) {
        mMap.addMarker(MarkerOptions()
            .position(LatLng(latitude, longitude))
            .anchor(0.5f,0.5f)
            .title(name))
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}