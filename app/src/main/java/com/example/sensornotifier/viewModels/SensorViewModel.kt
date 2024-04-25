package com.example.sensornotifier.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensornotifier.utilities.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SensorViewModel : ViewModel() {

    private lateinit var sensorData: SensorData

     fun retrieveData(url: String): SensorData {
        viewModelScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            var temperature = 48.0
            var humidity = 50.0
            var currentTime = "default"
            client.newCall(request).enqueue(object : Callback {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call, response: Response) {

                    // Handling the response from server
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        val doc: Document = Jsoup.parse(responseData)

                        // extracting data
                        val tempTxt = doc.select("span:containsOwn(Temperature)").first()?.
                        nextElementSibling()?.nextElementSibling()?.text()?.substring(0,5)
                        temperature = tempTxt?.toDouble()!!
                        val humTxt = doc.select("span:containsOwn(Humidity)").first()?.
                        nextElementSibling()?.nextElementSibling()?.text()?.substring(0,5)
                        humidity = humTxt?.toDouble()!!
                        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                        currentTime = LocalDateTime.now().format(formatter)
//                        currentTime = doc.select("p#time").text()
                        Log.d("ExtractTime", currentTime)
                    }
                    sensorData = SensorData(
                        temp = temperature,
                        humidity = humidity,
                        time = currentTime
                    )
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("serverDataError", e.toString())
                }
            })
        }
        return sensorData
    }
}