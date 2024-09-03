package com.example.ice2currencyapp

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var editTextAmount: EditText
    lateinit var textViewResult: TextView
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.exchangerate-api.com/v4/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(CurrencyApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editTextAmount = findViewById(R.id.edittext_amount)
        textViewResult = findViewById(R.id.textview_result)
        val apikey = "c7b71c4e429aaa98316d1bc0390426592ad9a3e0"

        apiService.getExchangeRates("USD",apikey).enqueue(object:Callback<CurrencyResponse>{
            override fun onResponse(call: Call<CurrencyResponse>, response: Response<CurrencyResponse>){
                if (response.isSuccessful){
                    val currencyResponse = response.body()
                    val targetCurrency = "EUR"
                    val amount = editTextAmount.text.toString().toDouble()
                    val rate = currencyResponse?.rates?.get(targetCurrency)?:1.0
                    val convertedAmount = amount*rate

                    textViewResult.text = convertedAmount.toString()
                }
                else{

                }
            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}