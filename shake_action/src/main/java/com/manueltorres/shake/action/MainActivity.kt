package com.manueltorres.shake.action

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(),  ShakeDetector.OnShakeListener {



    private var mSensorManager: SensorManager? = null
    private var shakeDetector = ShakeDetector();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shakeDetector.setOnShakeListener(this)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?;
        mSensorManager!!.registerListener(shakeDetector, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }



    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(shakeDetector, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }


    override fun onShake(count: Int) {
     Toast.makeText(this, "onShake: $count", Toast.LENGTH_LONG).show()
    }
}
