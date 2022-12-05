package com.sowa705.batteryview

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sowa705.batteryview.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Update(binding.root);
    }

    fun Update(view: View){
        // make a web request to get the battery level

        val queue = Volley.newRequestQueue(this)

        val url = "http://192.168.4.1/api/info"
        binding.button.text = "Loading..."
        binding.button.isEnabled = false

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                binding.button.text = "Update"
                binding.button.isEnabled = true
                binding.socText.text = (response.getDouble("soc")*100).toString() + " %";
                binding.whText.text = response.getString("energy") + " Wh";
                binding.tempText.text = response.getString("temperature") + " °C";
                binding.voltageText.text = response.getDouble("batteryVoltage").toString()+ " V";
                binding.currentText.text = response.getDouble("batteryCurrent").toString()+ " A";
            },
            { error ->
                binding.button.text = "Retry"
                binding.button.isEnabled = true
                binding.voltageText.text = error.toString();
            }
        )

        queue.add(jsonObjectRequest);
    }
}