package de.thiems.cwironman;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private NotificationReceiver nReceiver;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothSocket bluetoothSocket;
	private BluetoothDevice bluetoothDevice;

	final int REQUEST_ENABLE_BT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			startActivity(new Intent(
					"android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
			nReceiver = new NotificationReceiver();
			IntentFilter filter = new IntentFilter();
			filter.addAction("de.thiems.cwironman.IronManService");
			registerReceiver(nReceiver, filter);

			TurnOnBluetooth();
		} catch (Exception e) {
			Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG)
					.show();
		}

	}

	private void TurnOnBluetooth() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (!bluetoothAdapter.isEnabled()) {
			Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(turnOn, 0);
			Toast.makeText(getApplicationContext(), "Bluetooth turned on",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), "Bluetooth already on",
					Toast.LENGTH_LONG).show();
		}
	}

	class NotificationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(MainActivity.this,
					intent.getStringExtra("notification_event"),
					Toast.LENGTH_LONG).show();
			new Thread(new ClientThread()).start();
		}
	}

	@Override
    protected void onStart() {
        super.onStart();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        RefreshDeviceList();
    }
	
	public void RefreshDeviceList() {
		final ArrayList<BluetoothDevice> list = new ArrayList<BluetoothDevice>(
				bluetoothAdapter.getBondedDevices());
		final BluetoothArrayAdapter adapter = new BluetoothArrayAdapter(
				this, android.R.layout.simple_list_item_1, list);

		ListView listview = (ListView) findViewById(R.id.lvBluetoothDevices);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final BluetoothDevice item = (BluetoothDevice) parent
						.getItemAtPosition(position);

				bluetoothDevice = item;
				Method m = null;
				try {
					m = item.getClass().getMethod("createRfcommSocket",
							new Class[] { int.class });
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}

				try {
					bluetoothSocket = (BluetoothSocket) m.invoke(
							bluetoothDevice, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					// Connect the device through the socket. This will block
					// until it succeeds or throws an exception
					bluetoothSocket.connect();
				} catch (IOException connectException) {
					// Unable to connect; close the socket and get out
					try {
						bluetoothSocket.close();
					} catch (IOException closeException) {
					}
					return;
				}
			}
		});
	}

	class ClientThread implements Runnable {

		public ClientThread() {

		}

		@Override
		public void run() {

		}

		public void cancel() {

		}

	}
}
