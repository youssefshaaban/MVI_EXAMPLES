package com.example.mvi.add_number_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.add_number_example.MainIntent.AddNumber
import com.example.mvi.add_number_example.MainViewState.Error
import com.example.mvi.add_number_example.MainViewState.Idle
import com.example.mvi.add_number_example.MainViewState.Number
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel : ViewModel() {
  val intentChanal = Channel<MainIntent>(Channel.UNLIMITED)
  private val _viewState = MutableStateFlow<MainViewState>(Idle)
  val state: StateFlow<MainViewState> get() = _viewState
  private var number: Int = 0

  init {
    processIntent()
  }
  private fun processIntent() {
    viewModelScope.launch {
      intentChanal.consumeAsFlow().collect {
        when (it) {
          is AddNumber -> addNumber()
        }
      }
    }
  }

  private fun addNumber() {
    viewModelScope.launch {
      _viewState.value = try {
        Number(++number )
      } catch (e: Exception) {
        Error(e.message!!)
      }
    }
  }

}