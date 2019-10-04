package com.ngarvey.cctrivia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.questions_layout.*
import java.io.File

class QuizActivity : AppCompatActivity() {

    val QUESTIONS_PATH = "./src/main/res/JSON"

    // Intent keys
    val CATEGORY_KEY = "category"
    val SCORE_KEY = "score"
    val QUESTION_NUMBER_KEY = "question_number"

    val CATEGORY_0 = "buildings.json"
    val CATEGORY_1 = "faculty.json"
    val CATEGORY_2 = "lore.json"

    lateinit var currentCategory: String
    lateinit var questions: List<Question>

    companion object {}

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

        // Load questions
        File("$QUESTIONS_PATH/$currentCategory").readText()


    }

    private fun gradeQuestion(choice: Int){

    }

}