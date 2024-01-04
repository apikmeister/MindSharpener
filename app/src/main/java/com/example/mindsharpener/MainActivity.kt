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

        // Resource identifier for interface
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val answerText = findViewById<EditText>(R.id.answerText)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val resetButton = findViewById<Button>(R.id.resetButton)

        // This is to generate question based on radio default value when first enter the app
        val defaultRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        level = when (defaultRadioButton.text) {
            "i3" -> 10
            "i5" -> 100
            "i7" -> 1000
            else -> 0
        }
        generateQuestion()

        // This to generate new question when the radiobutton is clicked
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            level = when (findViewById<RadioButton>(checkedId).text) {
                "i3" -> 10
                "i5" -> 100
                "i7" -> 1000
                else -> 0
            }
            generateQuestion()
        }

        // check button listener to check the answer if it is true or not
        checkButton.setOnClickListener {
            // check if the textEdit is empty, show toast so user can answer the question and not implement the logic to checkanswer
            if (answerText.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Please answer the question given in the answer field.", Toast.LENGTH_SHORT).show()

            } else {
                // get and parse user answer to check and generate a new question
                userAnswer = answerText.text.toString().toInt()
                checkAnswer()
                generateQuestion()
            }
        }

        // reset button listener to reset score to 0
        resetButton.setOnClickListener {
            resetScore()
        }
    }

    // generate new question using random based on selected radio
    private fun generateQuestion() {
        val firstNumberTextView = findViewById<TextView>(R.id.firstNumberQ)
        val operatorTextView = findViewById<TextView>(R.id.operatorQ)
        val secondNumberTextView = findViewById<TextView>(R.id.secondNumberQ)

        val random = Random()
        firstNumber = random.nextInt(level)
        secondNumber = random.nextInt(level)
        operator = random.nextInt(4)

        // Display the question on the screen
        firstNumberTextView.text = "$firstNumber"
        operatorTextView.text = getOperatorSymbol(operator)
        secondNumberTextView.text = "$secondNumber"
    }

    // Compare and check the user answer with calculated answer
    private fun checkAnswer() {
        val scoreText = findViewById<TextView>(R.id.scoreText)

        // calculate correctAnswer based on operator number and symbol
        val correctAnswer = when (operator) {
            0 -> firstNumber + secondNumber
            1 -> firstNumber - secondNumber
            2 -> firstNumber * secondNumber
            3 -> firstNumber / secondNumber
            else -> 0
        }
        // add score by 1 if userAnswer is same as correct answer
        if (userAnswer == correctAnswer) {
            score++
        } else {
            // Deduct score by 1 if the answer is wrong
            score--
        }
        // Display the score on screen in realtime
        scoreText.text = "$score"
    }

    // get operator symbol to show as question
    private fun getOperatorSymbol(operator: Int): String {
        // map the number into operator symbol
        return when (operator) {
            0 -> "+"
            1 -> "-"
            2 -> "*"
            3 -> "/"
            else -> ""
        }
    }

    // Reset logic for reset button to reset score to 0
    private fun resetScore() {
        val scoreText = findViewById<TextView>(R.id.scoreText)
        score = 0
        scoreText.text = "$score"
    }
}

