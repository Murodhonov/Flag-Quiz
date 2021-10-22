package com.example.flagquiz

import Models.Flag
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var flagArrayList: ArrayList<Flag>
    private var count = 0
    private var countryName = ""
    private lateinit var buttonArrayList: ArrayList<Button>
    private var pressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val intent = Intent(this,SplashScreen::class.java)
        startActivity(intent)

        image1.setOnClickListener {
           if (!pressed){
               pressed = true
               Toast.makeText(this, "On", Toast.LENGTH_SHORT).show()
           }else{
               pressed = false
               Toast.makeText(this, "Off", Toast.LENGTH_SHORT).show()
           }
        }

        buttonArrayList = ArrayList()

        obyektYaratish()
        btnJoylaCount()

    }
    
    private fun obyektYaratish() {
        flagArrayList = ArrayList()
        flagArrayList.add(Flag("uzbekistan", R.drawable.uzb))
        flagArrayList.add(Flag("russia", R.drawable.russian))
        flagArrayList.add(Flag("china", R.drawable.china))
        flagArrayList.add(Flag("usa", R.drawable.aqsh))
        flagArrayList.add(Flag("india", R.drawable.india))
    }

    private fun btnJoylaCount() {
        image1.setImageResource(flagArrayList[count].image  !!)
        lin_1_matn.removeAllViews()
        lin_2_btn1.removeAllViews()
        lin3_btn_2.removeAllViews()
        countryName = ""
        btnJoyla(flagArrayList[count].name)
    }

    private fun btnJoyla(countryName: String?) {
        val btnArray: ArrayList<Button> = randomBtn(countryName)
        for (i in 0..5) {
            customBtn(btnArray[i],15.0,"#D81E88E5","#FFFFFF","#000000","#FA0000",5.0)
            lin_2_btn1.addView(btnArray[i])
        }
        for (i in 6..11) {
            customBtn(btnArray[i],15.0,"#D81E88E5","#FFFFFF","#000000","#FA0000",5.0)
            lin3_btn_2.addView(btnArray[i])
        }
    }

    private fun randomBtn(countryName: String?): ArrayList<Button> {
        val array = ArrayList<Button>()
        val arrayText = ArrayList<String>()

        for (c in countryName!!) {
            arrayText.add(c.toString())//uzbekistan  = u , z , b , e , k , i , s , t , a , n
        }
        if (arrayText.size != 12) {
            val str = "ABCDEFGHIJKLMNOPQRSTUVXYZ"
            for (i in arrayText.size until 12) {
                val random = Random().nextInt(str.length)
                arrayText.add(str[random].toString())
            }
        }
        arrayText.shuffle()

        for (i in 0 until arrayText.size) {
            val button = Button(this)
            customBtn(button,15.0,"#D81E88E5","#FFFFFF","#000000","#FA0000",5.0)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            button.text = arrayText[i]
            button.setOnClickListener(this)
            array.add(button)
        }
        return array
    }

    override fun onClick(v: View?) {
        val button1 = v as Button
        customBtn(button1,15.0,"#D81E88E5","#FFFFFF","#000000","#FA0000",5.0)
        if (buttonArrayList.contains(button1)) {
            lin_1_matn.removeView(button1)
            var hasC = false
            lin_2_btn1.children.forEach { button ->
                if ((button as Button).text.toString() == button1.text.toString()) {
                    button.visibility = View.VISIBLE
                    countryName = countryName.substring(0, countryName.length - 1)
                    hasC = true
                }
            }

            lin3_btn_2.children.forEach { button ->

                if ((button as Button).text.toString() == button1.text.toString()) {

                    button.visibility = View.VISIBLE
                    if (!hasC) {
                        countryName = countryName.substring(0, countryName.length - 1)
                    }
                }
            }
        } else {
            button1.visibility = View.INVISIBLE
            if (button1.text.toString().lowercase(Locale.getDefault()) == "a" && pressed) {
                Toast.makeText(this, "A harfi bosildi", Toast.LENGTH_SHORT).show()
            }
            countryName += button1.text.toString().uppercase(Locale.getDefault())
            val button2 = Button(this)
            customBtn(button2,15.0,"#D81E88E5","#FFFFFF","#000000","#FA0000",5.0)
            button2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            button2.text = button1.text

            button2.setOnClickListener(this)
            buttonArrayList.add(button2)
            lin_1_matn.addView(button2)
            matnTogri()
        }
    }

    private fun matnTogri() {
        if (countryName == flagArrayList[count].name?.uppercase(Locale.getDefault())) {
            Toast.makeText(this@MainActivity, "Successful", Toast.LENGTH_SHORT).show()
            if (count == flagArrayList.size - 1) {
                count = 0
            } else {
                count++
            }
            btnJoylaCount()
        } else {
            if (countryName.length == flagArrayList[count].name?.length) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                lin_1_matn.removeAllViews()
                lin3_btn_2.removeAllViews()
                lin_2_btn1.removeAllViews()
                btnJoyla(flagArrayList[count].name)
                countryName = ""
            }
        }
    }
    private fun customBtn(view:View,radius:Double,a:String,b:String,c:String,d:String,s:Double){
        val style = GradientDrawable()
        style.cornerRadius = radius.toFloat()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            style.color =
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_pressed)
                    ), intArrayOf(Color.parseColor(a), Color.parseColor(b))
                )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            style.setStroke(
                s.toInt(),
                ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_pressed),
                        intArrayOf(android.R.attr.state_pressed)
                    ), intArrayOf(Color.parseColor(c), Color.parseColor(d))
                )
            )
        }
        view.background = style
    }
}