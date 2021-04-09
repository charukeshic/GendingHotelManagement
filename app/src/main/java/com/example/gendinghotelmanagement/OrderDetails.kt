package com.example.gendinghotelmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class OrderDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        //get the spinner from the xml.
        val dropdown = findViewById<Spinner>(R.id.extraService)
        //create a list of items for the spinner.
//create a list of items for the spinner.
        val items = arrayOf("Include Breakfast", "Laundry Services", "Airport Pick Up/ Drop Off")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        dropdown.adapter = adapter
    }
}