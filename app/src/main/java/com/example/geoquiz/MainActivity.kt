package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_CHEATER = "cheater"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private var score:Int = 0


    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModal: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "On Create(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        val isCheater = savedInstanceState?.getBoolean(KEY_CHEATER, false) ?: false
        Log.d(TAG, "$isCheater")
        quizViewModal.currentIndex = currentIndex
        quizViewModal.isCheater = isCheater

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener{
            quizViewModal.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener{
            quizViewModal.moveToPrev()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            //start cheat activity
            val answerIsTrue = quizViewModal.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity,answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK){
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            quizViewModal.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onStart(){
        super.onStart()

        Log.d(TAG, "onStart() Called")
    }

    override fun onResume(){
        super.onResume()

        Log.d(TAG, "onResume() Called")
    }

    override fun onPause(){
        super.onPause()

        Log.d(TAG, "onPause() Called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        Log.d(TAG, "${quizViewModal.isCheater}")
        savedInstanceState.putBoolean(KEY_CHEATER, quizViewModal.isCheater)
        savedInstanceState.putInt(KEY_INDEX, quizViewModal.currentIndex)
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy() called")
    }


    private fun updateQuestion(){
        Log.d(TAG, "update qs called")
        val questionTextResId = quizViewModal.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean)
    {
        val correctAnswer: Boolean = quizViewModal.currentQuestionAnswer
        Log.d(TAG, "check answer called")
        Log.d(TAG, "${quizViewModal.isCheater}")
        val messageResId = when{
            quizViewModal.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }
}