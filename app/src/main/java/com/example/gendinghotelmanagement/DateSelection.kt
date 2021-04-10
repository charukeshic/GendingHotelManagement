package com.example.gendinghotelmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class DateSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_selection)

        //get the spinner from the xml.
        val dropdown = findViewById<Spinner>(R.id.ddlRoomType)
        //create a list of items for the spinner.
//create a list of items for the spinner.
        val items = arrayOf("1", "2", "three")
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
//set the spinners adapter to the previously created one.
//set the spinners adapter to the previously created one.
        dropdown.adapter = adapter

        val dropdown1 = findViewById<Spinner>(R.id.ddlTimeRange)
        val items1 = arrayOf("1", "2", "three")
        val adapter1 = ArrayAdapter (this,android.R.layout.simple_spinner_dropdown_item,items1)
        dropdown.adapter = adapter1

        val dropdown2 = findViewById<Spinner>(R.id.roomType)
        val items2 = arrayOf("1", "2", "three")
        val adapter2 =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items2)
        dropdown.adapter = adapter2
    }


}