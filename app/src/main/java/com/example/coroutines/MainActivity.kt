package com.example.coroutines

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception
import java.lang.Runnable

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var mainHandler: Handler

    private var coroutineJob: Job? = null
    private val updateText = object : Runnable{
        override fun run() {
            getAdvice()
            mainHandler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getAdvice()

        binding.btnGen.setOnClickListener {
           // getAdvice()
            onResume()
        }

        binding.btnCancel.setOnClickListener {
            onPause()
        }

        mainHandler = Handler(Looper.myLooper()!!)
    }

    private fun getAdvice() {


        val apiInterface = ApiClient().getClient()?.create(ApiInterface::class.java)

        coroutineJob = GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface?.getData()?.awaitResponse()

                if (response!!.isSuccessful) {
                    val data = response.body()!!

                    withContext(Dispatchers.Main) {

                        binding.tvView.text = data.slip.advice.toString()


                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                    Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }



    ///resume


    override fun onResume() {
        super.onResume()
        mainHandler.post(updateText)
    }


    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateText)
    }

}

//override fun onPause() {
// coroutineJob?.cancel()
// super.onPause()
// }


