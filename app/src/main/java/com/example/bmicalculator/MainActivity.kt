package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightInput = findViewById<EditText>(R.id.weightInput)
        val heightInput = findViewById<EditText>(R.id.heightInput)
        val genderInput = findViewById<EditText>(R.id.genderInput)
        val resultText = findViewById<TextView>(R.id.resultText)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        calculateButton.setOnClickListener {
            val weightText = weightInput.text.toString()
            val heightText = heightInput.text.toString()
            val gender = genderInput.text.toString()

            if (weightText.isNotEmpty() && heightText.isNotEmpty()) {
                val weight = weightText.toDoubleOrNull()
                val height = heightText.toDoubleOrNull()

                if (weight != null && height != null) {
                    val user = User(weight, height, gender)
                    val bmi = user.calculateBMI()
                    val result = BMIResult.getBMIResult(bmi)

                    resultText.text = "BMI: %.2f\n%s".format(bmi, result.message)
                    resultText.setTextColor(
                        if (result == BMIResult.NORMAL) android.graphics.Color.GREEN
                        else android.graphics.Color.RED
                    )
                } else {
                    resultText.text = "Please enter valid numbers for weight and height."
                    resultText.setTextColor(android.graphics.Color.RED)
                }
            } else {
                resultText.text = "Please fill in all fields."
                resultText.setTextColor(android.graphics.Color.RED)
            }
        }
    }
}

// BMI Enum Class
enum class BMIResult(val range: String, val message: String) {
    UNDERWEIGHT("< 18.5", "You are underweight"),
    NORMAL("18.5 - 24.9", "Your weight is normal"),
    OVERWEIGHT("> 24.9", "You are overweight");

    companion object {
        fun getBMIResult(bmi: Double): BMIResult {
            return when {
                bmi < 18.5 -> UNDERWEIGHT
                bmi <= 24.9 -> NORMAL
                else -> OVERWEIGHT
            }
        }
    }
}

// User Data Class
class User(private val weight: Double, private val height: Double, val gender: String) {

    fun calculateBMI(): Double {
        return weight / (height * height)
    }
}
