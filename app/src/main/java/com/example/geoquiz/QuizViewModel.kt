package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModal"

class QuizViewModel : ViewModel(){

    private var  questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa,false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true),
    )

    var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer:Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext()
    {
        currentIndex = (currentIndex + 1) % questionBank.size
        isCheater = false
    }

    fun moveToPrev()
    {
        if(currentIndex != 0) {
            currentIndex = (currentIndex - 1) % questionBank.size
        }else{
            currentIndex = 5
        }
        isCheater = false
    }

    init {
        Log.d(TAG, "ViewModal instance created")
    }

    override fun onCleared()
    {
        super.onCleared()

        Log.d(TAG, "ViewModal instance about to be destroyed")
    }
}