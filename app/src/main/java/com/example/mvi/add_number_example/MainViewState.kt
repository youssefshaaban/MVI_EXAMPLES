package com.example.mvi.add_number_example

sealed class MainViewState {

  object Idle : MainViewState()

  // number
  data class Number(val number: Int) : MainViewState()

  // error
  data class Error(val error: String) : MainViewState()
}
