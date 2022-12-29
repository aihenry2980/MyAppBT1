package com.jx.myappbt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView;

class MainActivity : AppCompatActivity() {




	lateinit var btAdpt:BluetoothAdapter




	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		//bt stuff
		btAdpt = BluetoothAdapter.getDefaultAdapter()
//		btAdpt = BluetoothManager.getAdapter() // this does not work either

		val btStatusTv:TextView = findViewById(R.id.btStatusTv)
		if (btAdpt==null)
		{
			btStatusTv.text = "Bluetooth is not available"
		}
		else
		{
			btStatusTv.text = "Bluetooth is available"
		}

		val btIv:ImageView = findViewById(R.id.btIconIv)
		if (btAdpt.isEnabled)
		{
			btIv.setImageResource(R.drawable.ic_bt_on)
		}
		else
		{
			btIv.setImageResource(R.drawable.ic_bt_off)
		}

	}
}