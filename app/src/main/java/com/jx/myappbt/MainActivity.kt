package com.jx.myappbt

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView;
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
//		btAdpt = BluetoothAdapter.getDefaultAdapter()
//		btAdpt = BluetoothManager.getAdapter() // this does not work either

		val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
		val btAdpt: BluetoothAdapter? = bluetoothManager.getAdapter()

		val btStatusTv:TextView = findViewById(R.id.btStatusTv)
		val btPairedTv:TextView = findViewById(R.id.pairedTv)

		if (btAdpt==null) {
			btStatusTv.text = "Bluetooth is not available"
		} else {
			btStatusTv.text = "Bluetooth is available"
		}

		val btIv:ImageView = findViewById(R.id.btIconIv)
		if (btAdpt?.isEnabled==true) {
			btIv.setImageResource(R.drawable.ic_bt_on)
		} else {
			btIv.setImageResource(R.drawable.ic_bt_off)
		}

		val btnTurnOn:Button = findViewById(R.id.turnOnBtn)
		val btnTurnOff:Button = findViewById(R.id.turnOffBtn)
		val btnDisc:Button = findViewById(R.id.discovBtn)
		val btnPaired:Button = findViewById(R.id.pairedBtn)
		val btnPushNoti:Button = findViewById(R.id.notiBtn)


		btnTurnOn.setOnClickListener{
			if (btAdpt?.isEnabled==true) {
				Toast.makeText(this, "Already On!", Toast.LENGTH_LONG).show()
			} else {
				val enableBtIntent:Intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//				if (ActivityCompat.checkSelfPermission(
//						this,
//						Manifest.permission.BLUETOOTH_CONNECT
//					) != PackageManager.PERMISSION_GRANTED
//				) {
//					// TODO: Consider calling
//					//    ActivityCompat#requestPermissions
//					// here to request the missing permissions, and then overriding
//					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//					//                                          int[] grantResults)
//					// to handle the case where the user grants the permission. See the documentation
//					// for ActivityCompat#requestPermissions for more details.
//					return
//				}
				startActivityForResult(enableBtIntent, REQUEST_CODE_ENABLE_BT)
			}
		}
		btnTurnOff.setOnClickListener{
			if (btAdpt?.isEnabled==false) {
				Toast.makeText(this, "Already Off!", Toast.LENGTH_LONG).show()
			} else {
				btAdpt?.disable()
				btIv.setImageResource(R.drawable.ic_bt_off)
				Toast.makeText(this, "BT is Off!", Toast.LENGTH_LONG).show()
			}
		}

		btnDisc.setOnClickListener{
//			if(btAdpt.isDiscovering==false){
//				Toast.makeText(this, "Making this device discoverable!", Toast.LENGTH_LONG).show()
//				val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
//				startActivityForResult(intent, REQUEST_CODE_DISCOVER_BT)
//			}
		}

		btnPaired.setOnClickListener {
			if (btAdpt?.isEnabled==true){

				//check permission
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
					== PackageManager.PERMISSION_GRANTED) {
					// Permission is granted, you can list connected Bluetooth devices
				} else {
					// Permission is not granted, you need to request it
				}


//				val btConnect:Intent = Intent(BluetoothAdapter.action_co)


//				val pairedDevices: Set<BluetoothDevice>? = btAdpt?.bondedDevices
//				pairedDevices?.forEach { device ->
//					val deviceName = device.name
//					val deviceHardwareAddress = device.address // MAC address
//				}

				btPairedTv.text = "Paired devices"
				if (ActivityCompat.checkSelfPermission(
						this,
						Manifest.permission.BLUETOOTH_CONNECT
					) != PackageManager.PERMISSION_GRANTED
				) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding

					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					Toast.makeText(this, "Not granted", Toast.LENGTH_LONG).show()
				}
//				val devs = 	btAdpt.bondedDevices
//				for (dev in devs){
//					val devName = dev.name
//					val devAddr = dev
//					btPairedTv.append("\nDevice: $devName , $devAddr")
//				}
			}else{
				Toast.makeText(this, "Turn on BT first", Toast.LENGTH_LONG).show()
			}
		}

		btnPushNoti.setOnClickListener{

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

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
		val btAdpt: BluetoothAdapter? = bluetoothManager.getAdapter()
		val btPairedTv:TextView = findViewById(R.id.pairedTv)

		if(btAdpt!=null) {
			val devs = btAdpt.bondedDevices
			for (dev in devs) {
				val devName = dev.name
				val devAddr = dev
				btPairedTv.append("\nDevice: $devName , $devAddr")
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
	}
}