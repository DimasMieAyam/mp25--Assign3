package com.example.bmi

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.pow

class LandingActivity : AppCompatActivity() {
    companion object {
        val EXTRA_PARCEL = "USER_DATA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val user = intent.getParcelableExtra<User>(EXTRA_PARCEL)

        var etHeight = findViewById<TextInputEditText>(R.id.etHeight)
        var etWeight = findViewById<TextInputEditText>(R.id.etWeight)
        var logout = findViewById<Button>(R.id.logout)
        var btnCalculate = findViewById<Button>(R.id.btnCalculate)
        var landingName = findViewById<TextView>(R.id.landingName)
        var heightWarning = findViewById<TextView>(R.id.heightWarning)
        var weightWarning = findViewById<TextView>(R.id.weightWarning)
        var tvBmiNumber = findViewById<TextView>(R.id.tvBmiNumber)
        var tvBmiContext = findViewById<TextView>(R.id.tvBmiContext)
        var llResults = findViewById<LinearLayout>(R.id.llResults)

        landingName.text = user!!.username

        btnCalculate.setOnClickListener {
            val isInputValid = validateInput(
                etHeight.text.toString(),
                etWeight.text.toString(),
                heightWarning,
                weightWarning
            )

            if (isInputValid) {
                calculateBMI(
                    llResults,
                    landingName,
                    (etHeight.text.toString().toDouble() / 100),
                    etWeight.text.toString().toDouble(),
                    tvBmiNumber,
                    tvBmiContext
                )
            }
        }

        logout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun calculateBMI(
        llResults: LinearLayout,
        landingName: TextView,
        height: Double,
        weight: Double,
        number: TextView,
        context: TextView
    ) {
        val bmi = weight / (height.pow(2))

        val (category, color) = when {
            bmi < 18.5 -> Pair("Underweight", R.color.blue)
            bmi < 25 -> Pair("Normal", R.color.green)
            bmi < 30 -> Pair("Overweight", R.color.orange)
            else -> Pair("Obese", R.color.red)
        }

        landingName.setTextColor(resources.getColor(color, theme))

        llResults.visibility = View.VISIBLE
        number.text = "%.1f".format(bmi)
        number.setTextColor(resources.getColor(color, theme))

        context.text = "You are ${category}"
    }

    private fun validateInput(
        height: String,
        weight: String,
        heightWarning: TextView,
        weightWarning: TextView
    ): Boolean {
        var isValid = true

        // Validasi height
        if (height.isBlank() || height == "0") {
            heightWarning.text = "Tinggi badan tidak valid!"
            heightWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            heightWarning.visibility = View.GONE
        }

        // Validasi weight
        if (weight.isBlank() || weight == "0") {
            weightWarning.text = "Berat badan tidak valid!"
            weightWarning.visibility = View.VISIBLE
            isValid = false
        } else {
            weightWarning.visibility = View.GONE
        }

        return isValid
    }
}