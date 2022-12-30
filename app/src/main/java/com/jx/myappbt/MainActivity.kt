package com.jx.myappbt

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {




	lateinit var btAdpt:BluetoothAdapter
	private val REQUEST_CODE_ENABLE_BT:Int = 1
	private val REQUEST_CODE_DISCOVER_BT:Int = 2



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val v = android.os.Build.VERSION.SDK_INT

		Toast.makeText(this, "Android API Level: $v", Toast.LENGTH_LONG).show()

		//bt stuff
		btAdpt = BluetoothAdapter.getDefaultAdapter()
//		btAdpt = BluetoothManager.getAdapter() // this does not work either

		val btStatusTv:TextView = findViewById(R.id.btStatusTv)
		val btPairedTv:TextView = findViewById(R.id.pairedTv)
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
		val btnPaired:Button = findViewById(R.id.pairedBtn)

		btnTurnOn.setOnClickListener{
			if (btAdpt.isEnabled)
			{
				Toast.makeText(this, "Already On!", Toast.LENGTH_LONG).show()
			}
			else
			{
				val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
				startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
			}
		}
		btnTurnOff.setOnClickListener{
			if (!btAdpt.isEnabled)
			{
				Toast.makeText(this, "Already Off!", Toast.LENGTH_LONG).show()
			} else
			{
				btAdpt.disable()
				btIv.setImageResource(R.drawable.ic_bt_off)
				Toast.makeText(this, "BT is Off!", Toast.LENGTH_LONG).show()
			}
		}

		btnDisc.setOnClickListener{
			if(!btAdpt.isDiscovering){
				Toast.makeText(this, "Making this device discoverable!", Toast.LENGTH_LONG).show()
				val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
				startActivityForResult(intent, REQUEST_CODE_DISCOVER_BT)
			}
		}

		btnPaired.setOnClickListener {
			if (btAdpt.isEnabled){
				btPairedTv.text = "Paired devices"
				val devs = btAdpt.bondedDevices
				for (dev in devs){
					val devName = dev.name
					val devAddr = dev
					btPairedTv.append("\nDevice: $devName , $devAddr")
				}
			}else{
				Toast.makeText(this, "Turn on BT first", Toast.LENGTH_LONG).show()
			}
		}


	}


	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		when(requestCode){
			REQUEST_CODE_ENABLE_BT ->
				if (resultCode== Activity.RESULT_OK){
					val btIv:ImageView = findViewById(R.id.btIconIv)
					btIv.setImageResource(R.drawable.ic_bt_on)
					Toast.makeText(this, "Bluetooth is On!", Toast.LENGTH_LONG).show()
				}else{
					Toast.makeText(this, "Cannot turn on BT!", Toast.LENGTH_LONG).show()
				}
		}

		super.onActivityResult(requestCode, resultCode, data)
	}

}