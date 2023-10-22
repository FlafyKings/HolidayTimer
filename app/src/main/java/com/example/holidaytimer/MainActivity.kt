package com.example.holidaytimer

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import io.paperdb.Paper
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.math.floor
import android.content.SharedPreferences




class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var string : String = 'a'.toString()


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val button = findViewById<Button>(R.id.containedButton)

        val textView3 = findViewById<TextView>(R.id.textView3)

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.ENGLISH)

        val mPrefs = getSharedPreferences("label", 0)
        val mString = mPrefs.getString("tag", "2021 11 02 19:00:00")

        cal.time = sdf.parse(mString)


        button.setOnClickListener(){

            val dpd = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                textView3.text = "$year ${month + 1} $dayOfMonth 11:40:00"
                //string = "$year ${month + 1} $dayOfMonth 00:00:00"
            }, year, month, day)


            dpd.show()
        }


    fun countdown( cal : Calendar) {

        val dayLeft = findViewById<TextView>(R.id.dayLeftValue)
        val hourLeft = findViewById<TextView>(R.id.hourLeftValue)
        val minuteLeft = findViewById<TextView>(R.id.minuteLeftValue)
        val secondLeft = findViewById<TextView>(R.id.secondLeftValue)

        var currentDate = Calendar.getInstance().timeInMillis
        var timeLeftAll = cal.timeInMillis - currentDate

        if(textView3.text.toString().length != 1 && cal.time.toString() != textView3.text.toString()) {
            cal.time = sdf.parse(textView3.text.toString())
            val mEditor = mPrefs.edit()
            mEditor.putString("tag", textView3.text.toString()).commit()
        }

        currentDate = Calendar.getInstance().timeInMillis
        timeLeftAll = cal.timeInMillis - currentDate


        dayLeft.text = floor(((timeLeftAll / 1000 / 3600 / 24).toDouble())).toInt().toString()
        hourLeft.text = floor(((timeLeftAll / 1000 / 3600) % 24).toDouble()).toInt().toString()
        minuteLeft.text = floor((((timeLeftAll / 1000 / 60) % 60).toDouble())).toInt().toString()
        secondLeft.text = floor(((timeLeftAll / 1000) % 60).toDouble()).toInt().toString()

    }

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                countdown(cal)
                mainHandler.postDelayed(this, 1000)
            }
        })
    }


}