package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel(){
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }
            private val _time = MutableLiveData<Long>()
                    val time : LiveData<Long>
                         get()= _time
                    val timeString = Transformations.map(time,{t->
                        DateUtils.formatElapsedTime(t/1000)
                    })
    private val timer :CountDownTimer
   private val _word = MutableLiveData<String>()
           val word : LiveData<String>
               get() = _word
    // The current score
    private val _score = MutableLiveData<Int>()
            val score  : LiveData<Int>
                get() = _score
    private val _eventGameFinish = MutableLiveData<Boolean>()
            val eventGameFinish  : LiveData<Boolean>
                get() = _eventGameFinish
    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>
    init{
        Log.i("GameViewModel","game view model called")
        _score.value = 0
        _word.value = ""
        _eventGameFinish.value = false
        timer = object:CountDownTimer(COUNTDOWN_TIME, ONE_SECOND)
        {
            override fun onTick(p0: Long) {
                 _time.value = p0
            }

            override fun onFinish() {
                _eventGameFinish.value = true
            }
        }
        timer.start()
        resetList()
        nextWord()

    }
    override fun onCleared() {
        super.onCleared()
      timer.cancel()
    }
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
            _word.value = wordList.removeAt(0)


    }
   fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

   fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun hasCompleted(){
        _eventGameFinish.value = false
    }
}
