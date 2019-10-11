package com.ngarvey.cctrivia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.highscore_layout.*
import java.io.File
import java.io.FileNotFoundException

class ScoreActivity: AppCompatActivity() {

    companion object {
        private const val SCORES_FILEPATH = "highscore.json"
        private const val SCORE_KEY = "score"

        fun setNewScore(context: Context, score: Int) : Intent {
            return Intent(context, ScoreActivity::class.java).putExtra(SCORE_KEY, score)
        }
    }

    lateinit var highScores: ArrayList<HighScore>
    lateinit var highScoreTextViews: ArrayList<TextView>
    private var newScore: Int = -1
    private var madeHighScores = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_layout)

        // Get previous high scores

        val json = try {
            File(this.filesDir, SCORES_FILEPATH).readText()
        } catch (e: FileNotFoundException) {
            "[]"
        }

        val gson = Gson()

        highScores = gson.fromJson(json, object : TypeToken<ArrayList<HighScore>>() {}.type)

        // Get current high score
        if (intent.getIntExtra(SCORE_KEY, -1) == -1) {
            // This should not happen
            finish()
        }

        // Get TextView Displays
        highScoreTextViews = arrayListOf(high_score_1, high_score_2,
            high_score_3, high_score_4, high_score_5)

        // Display current high scores
        for ((i, textView) in highScoreTextViews.withIndex()) {
            if (i < highScores.size) {
                textView.text = getString(R.string.score_listing,
                    highScores[i].rank,
                    highScores[i].name,
                    highScores[i].score)
            } else {
                // Make textView invisible
                textView.visibility = TextView.INVISIBLE
            }
        }

        submit_button.setOnClickListener {
            if (name_entry.text.toString() != "") {
                submitScore(name_entry.text.toString())
            } else {
                Toast.makeText(this, R.string.name_error, Toast.LENGTH_SHORT).show()
            }
        }


        newScore = intent.getIntExtra(SCORE_KEY, 0)
        name_label.text = getString(R.string.new_score, newScore)

    }

    private fun submitScore(name: String) {
        var scoreAdded = false
        for ((i, listing) in highScores.withIndex()) {
            if (newScore >= listing.score!!) {
                val newHighScore = HighScore(i + 1, name, newScore)
                highScores.add(i, newHighScore)
                madeHighScores = i < 5
                scoreAdded = true
                break
            }
        }

        if (highScores.size < 5 && !scoreAdded){
            highScores.add(HighScore(1, name, newScore))
            scoreAdded = true
        }

        if (scoreAdded) {
            for (index in 0 until highScores.size) {
                highScores[index] =
                    HighScore(index + 1, highScores[index].name, highScores[index].score)
            }
            val gson = Gson()
            val json = gson.toJson(highScores, object : TypeToken<ArrayList<Question>>() {}.type)
            this.openFileOutput(SCORES_FILEPATH, 0).use {
                it.write(json.toByteArray())
            }
        }

        Toast.makeText(this, R.string.submitted, Toast.LENGTH_LONG).show()
        finish()
    }

}