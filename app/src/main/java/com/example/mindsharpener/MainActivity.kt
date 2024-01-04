package com.example.mindsharpener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {
    
    private var level = 0
    private var score = 0
    private var firstNumber = 0
    private var secondNumber = 0
    private var operator = 0
    private var userAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val answerText = findViewById<EditText>(R.id.answerText)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val resetButton = findViewById<Button>(R.id.resetButton)

        val defaultRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        level = when (defaultRadioButton.text) {
            "i3" -> 10
            "i5" -> 100
            "i7" -> 1000
            else -> 0
        }
        generateQuestion()

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            level = when (findViewById<RadioButton>(checkedId).text) {
                "i3" -> 10
                "i5" -> 100
                "i7" -> 1000
                else -> 0
            }
            generateQuestion()
        }

        checkButton.setOnClickListener {
            if (answerText.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Please answer the question given in the answer field.", Toast.LENGTH_SHORT).show()

            } else {
                userAnswer = answerText.text.toString().toInt()
                checkAnswer()
                generateQuestion()
            }
        }

        resetButton.setOnClickListener {
            resetScore()
        }
    }

    private fun generateQuestion() {
        val firstNumberTextView = findViewById<TextView>(R.id.firstNumberQ)
        val operatorTextView = findViewById<TextView>(R.id.operatorQ)
        val secondNumberTextView = findViewById<TextView>(R.id.secondNumberQ)

        val random = Random()
        firstNumber = random.nextInt(level)
        secondNumber = random.nextInt(level)
        operator = random.nextInt(4)

        firstNumberTextView.text = "$firstNumber"
        operatorTextView.text = getOperatorSymbol(operator)
        secondNumberTextView.text = "$secondNumber"
    }

    private fun checkAnswer() {
        val scoreText = findViewById<TextView>(R.id.scoreText)

        val correctAnswer = when (operator) {
            0 -> firstNumber + secondNumber
            1 -> firstNumber - secondNumber
            2 -> firstNumber * secondNumber
            3 -> firstNumber / secondNumber
            else -> 0
        }
        if (userAnswer == correctAnswer) {
            score++
        } else {
            score--
        }
        scoreText.text = "$score"
    }

    private fun getOperatorSymbol(operator: Int): String {
        return when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> ""
        }
    }

    private fun resetScore() {
        val scoreText = findViewById<TextView>(R.id.scoreText)
        score = 0
        scoreText.text = "$score"
    }
}

