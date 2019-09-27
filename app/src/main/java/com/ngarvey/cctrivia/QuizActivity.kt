package com.ngarvey.cctrivia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.questions_layout.*

class QuizActivity : AppCompatActivity() {

    // Intent keys
    val CATEGORY_KEY = "category"
    val SCORE_KEY = "score"
    val QUESTION_NUMBER_KEY = "question_number"

    val CATEGORY_0 = 0
    val CATEGORY_1 = 1
    val CATEGORY_2 = 2
    val CATEGORY_3 = 3

    lateinit var currentCategory: String

    companion object {
        fun getCategoryBundle(): Intent {
            val intent: Intent
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup choice buttons
        choice0.setOnClickListener {
            gradeQuestion(0)
        }
        choice1.setOnClickListener {
            gradeQuestion(1)
        }
        choice2.setOnClickListener {
            gradeQuestion(2)
        }
        choice3.setOnClickListener {
            gradeQuestion(3)
        }

    }

    private fun gradeQuestion(choice: Int){

    }

}