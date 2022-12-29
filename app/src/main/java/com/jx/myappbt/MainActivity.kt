package com.jx.myappbt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

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

		val btnTurnOn:Button = findViewById(R.id.turnOnBtn)
		val btnTurnOff:Button = findViewById(R.id.turnOffBtn)
		val btnDisc:Button = findViewById(R.id.discovBtn)

		btnTurnOn.setOnClickListener{
			if (btAdpt.isEnabled)
			{
				Toast.makeText(this, "Already On!", Toast.LENGTH_LONG).show()
			}
			else
			{
				var intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
				ActivityResultContracts.StartActivityForResult
			}
		}
		btnTurnOff.setOnClickListener{}
		btnDisc.setOnClickListener{}

	}
}