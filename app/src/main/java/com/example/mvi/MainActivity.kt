package com.example.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.mvi.add_number_example.AddNumberViewModel
import com.example.mvi.add_number_example.MainIntent
import com.example.mvi.add_number_example.MainViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
  lateinit var button: Button
  lateinit var textView: TextView
  private val viewModel: AddNumberViewModel by lazy {
    ViewModelProviders.of(this)[AddNumberViewModel::class.java]
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    textView=findViewById(R.id.numberVallue)
    button=findViewById(R.id.addNumber)

    // send
    button.setOnClickListener {
      lifecycleScope.launch {
        viewModel.intentChanal.send(MainIntent.AddNumber)
      }
    }
    render()
    // render
  }

  private fun render(){
    lifecycleScope.launch {
      viewModel.state.collect {
        when(it){
          is MainViewState.Idle->{textView.text="idle"}
          is MainViewState.Number->{
            textView.text=it.number.toString()
          }
          is MainViewState.Error->{
            textView.text=it.error
          }
        }
      }
    }
  }
}