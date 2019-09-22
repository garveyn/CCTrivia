package com.ngarvey.cctrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.category_selection_layout.*

class CategorySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_selection_layout)

        // Setup category navigation
        category0.setOnClickListener {
            categorySelection(1)
        }
        category1.setOnClickListener {
            categorySelection(2)
        }
        category2.setOnClickListener {
            categorySelection(3)
        }
        category3.setOnClickListener {
            categorySelection(4)
        }


    }

    fun categorySelection(categoryNumber: Int) {

    }
}
