package com.ngarvey.cctrivia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.questions_layout.*

class QuizActivity : AppCompatActivity() {


    private lateinit var currentCategory: String
    private var currentIndex: Int = -1 // So we can use nextQuestion() to set up properly
    private var score: Int = 0
    private lateinit var questions: ArrayList<Question>
    private lateinit var correctSound: MediaPlayer
    private lateinit var incorrectSound: MediaPlayer

    companion object {

        // Constants
        private const val QUESTIONS_PATH = "JSON"
        private const val MULTIPLE_CHOICE = 0
        private const val IMAGE_CHOICE = 1


        // Logcat
        private const val TAG = "QuizActivity"

        // Intent keys
        private const val CATEGORY_KEY = "category"
        private const val SCORE_KEY = "score"
        private const val QUESTION_NUMBER_KEY = "question_number"

        private const val CATEGORY_0 = "Buildings.json"
        private const val CATEGORY_1 = "Faculty.json"
        private const val CATEGORY_2 = "lore.json"

        fun newIntent(context: Context, category: Int) : Intent {
            val intent = Intent(context, QuizActivity::class.java)

            val intentCategory: String = when (category) {
                0 -> CATEGORY_0
                1 -> CATEGORY_1
                2 -> CATEGORY_2
                else -> {
                    Log.d(TAG, "Incorrect category number in newIntent() found: $category")
                    CATEGORY_0
                }
            }

            intent.putExtra(CATEGORY_KEY, intentCategory)

            return intent
        }

        fun getCategory(intent: Intent) : String {
            return intent.getStringExtra(CATEGORY_KEY)
        }

        fun getScore(intent: Intent) : Int {
            return intent.getIntExtra(SCORE_KEY, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions_layout)

        // If the extra is null, just go with cat_0
        currentCategory = intent.getStringExtra(CATEGORY_KEY)

        if (intent.getStringExtra(CATEGORY_KEY) == null) {
            Log.d(TAG, "intent did not pass Category key")
            currentCategory = savedInstanceState?.getString(CATEGORY_KEY) ?: CATEGORY_0
        }

        // If recreated
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SCORE_KEY, 0)
            currentIndex = savedInstanceState.getInt(QUESTION_NUMBER_KEY, 0)

        }


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

        loadQuestions("$QUESTIONS_PATH/$currentCategory")
        nextQuestion()

        // Setup sounds
        correctSound = MediaPlayer.create(this, R.raw.you_got_it)
        incorrectSound = MediaPlayer.create(this, R.raw.wrong)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(CATEGORY_KEY, currentCategory)
        outState?.putInt(SCORE_KEY, score)
        outState?.putInt(QUESTION_NUMBER_KEY, currentIndex)
    }

    private fun gradeQuestion(choice: Int){
        if (choice == questions[currentIndex].correctAnswer) {
            score++
            Toast.makeText(this, R.string.correct_response, Toast.LENGTH_SHORT).show()
            correctSound.start()
        } else {
            Toast.makeText(this, R.string.incorrect_response, Toast.LENGTH_SHORT).show()
            incorrectSound.start()
        }
        nextQuestion()
    }

    private fun nextQuestion() {
        // Quit if last question was answered
        if (currentIndex == questions.size - 1) {
            val result = Intent()
            result.putExtra(SCORE_KEY, score)
            result.putExtra(CATEGORY_KEY, currentCategory)
            setResult(Activity.RESULT_OK, result)
            finish()
            return
        }

        currentIndex++

        val currQuestion = questions[currentIndex]

        score_display.text = getString(R.string.score_label, score)
        questionNumber.text = getString(R.string.question_label, currentIndex + 1)
        questionPrompt.text =  currQuestion.question

        if (currQuestion.answerType == MULTIPLE_CHOICE) {
            if (currQuestion.answers != null) {
                choice0.text = currQuestion.answers[0]
                choice1.text = currQuestion.answers[1]
                choice2.text = currQuestion.answers[2]
                choice3.text = currQuestion.answers[3]
            }
        } else {
            // TODO image questions
        }
    }

    private fun loadQuestions(filepath: String) {


        for ( e in assets.list(QUESTIONS_PATH)!!) {
            Log.d(TAG, e)
        }

        val json = assets.open(filepath).bufferedReader().use {
            it.readText()
        }

        val gson = Gson()

        questions = gson.fromJson(json, object : TypeToken<ArrayList<Question>>() {}.type)

        Log.d(TAG, "${questions.size} items loaded into questions")
    }

}