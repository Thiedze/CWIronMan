package de.thiems.cwironman;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BluetoothArrayAdapter extends ArrayAdapter<BluetoothDevice> {
	
	public BluetoothArrayAdapter(Context context, int resource,
			List<BluetoothDevice> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
       BluetoothDevice device = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       if (convertView == null) {
          convertView = LayoutInflater.from(getContext()).inflate(R.layout.bluetooth_device, parent, false);
       }
       // Lookup view for data population
       TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
       TextView tvHome = (TextView) convertView.findViewById(R.id.tvMacAddress);
       // Populate the data into the template view using the data object
       tvName.setText(device.getName());
       tvHome.setText(device.getAddress());
       // Return the completed view to render on screen
       return convertView;
   }
}
