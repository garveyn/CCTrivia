package com.ngarvey.cctrivia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.category_selection_layout.*

class CategorySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_selection_layout)

        // Setup category navigation
        category0.setOnClickListener {
            categorySelection(0)
        }
        category1.setOnClickListener {
            categorySelection(1)
        }
        category2.setOnClickListener {
            categorySelection(2)
        }


    }

    private fun categorySelection(categoryNumber: Int) {
        val intent = QuizActivity.newIntent(this, categoryNumber)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

            // Returning from QuizActivity()
            if (requestCode == 0) {
                val score = QuizActivity.getScore(data)
                val intent = ScoreActivity.setNewScore(this, score)
                startActivity(intent)
            }
        }
    }
}
