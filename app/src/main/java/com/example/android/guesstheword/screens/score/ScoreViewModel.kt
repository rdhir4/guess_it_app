package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import android.util.Log
class ScoreViewModel(finalScore:Int) :ViewModel(){
    init{
        Log.i("ScoreViewModel","score is $finalScore")
    }
}