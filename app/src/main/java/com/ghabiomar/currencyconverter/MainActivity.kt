package com.ghabiomar.currencyconverter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ghabiomar.currencyconverter.Main.MainViewModule
import com.ghabiomar.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
   private val viewModule : MainViewModule by viewModels()
   private lateinit var input :EditText
   private lateinit var output :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        input = findViewById(R.id.input)
        output= findViewById(R.id.output)
         spinnerSetup()
        textChanged()
        lifecycleScope.launchWhenStarted {
            viewModule.conversion.collect { event ->
                when(event){
                    is MainViewModule.CurrencyEvent.Success -> {
                        binding.progressBar.isVisible = false
                        binding.output.setTextColor(Color.GREEN)
                        binding.output.text = event.resultText
                    }
                    is MainViewModule.CurrencyEvent.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.output.setTextColor(Color.RED)
                        binding.output.text = event.errorText
                    }
                    is MainViewModule.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true

                    }
                    else -> Unit
                }

            }
        }
    }
    private fun converter(){
        viewModule.convert(
            binding.input.text.toString(),
            binding.spinner1.selectedItem.toString(),
            binding.spinner2.selectedItem.toString(),
        )
    }
    private fun textChanged() {
       input.addTextChangedListener(object :TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
              Log.d("main","Before Text Changed")
           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               Log.d("main","On Text Changed")
           }

           override fun afterTextChanged(s: Editable?) {
              converter()
           }
       })
    }
    private fun spinnerSetup() {

        val adapterSpinner1= CountryArrayAdapter(this,Countries.list!!)
        spinner1.adapter = adapterSpinner1


        val adapterSpinner2= CountryArrayAdapter(this,Countries.list!!)
        spinner2.adapter = adapterSpinner2

    }
    fun onNumberClick(view: View) {
        (view as Button).let {
            onAppend(it.text)
        }
    }
    private fun onAppend(char: CharSequence) {
        input.append(char)
    }
    fun onDeleteClick(view: View) {
        var str: String = input.text.toString()
        if (str != "") {
            str = str.substring(0, str.length - 1)
            input.setText(str)
        }
    }

}