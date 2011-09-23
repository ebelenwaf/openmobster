/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openmobster.app;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;

import org.openmobster.core.mobileCloud.android_native.framework.ViewHelper;

/**
 *
 * @author openmobster@gmail.com
 */
public class GetLocationAddressTask extends AsyncTask<Map<String,Object>,Integer,Map<String,Object>>
{
	private Context context;
	private ProgressDialog progressDialog;
	
	public GetLocationAddressTask(Context context)
	{
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() 
	{
		super.onPreExecute();
		
		//Setup the progress dialog
		this.progressDialog = new ProgressDialog(this.context);
		this.progressDialog.setTitle("");
		this.progressDialog.setMessage("Processing....");
		this.progressDialog.setCancelable(false);
	}

	
	@Override
	protected Map<String, Object> doInBackground(Map<String, Object>... params) 
	{
		try
		{
			Map<String,Object> result = new HashMap<String,Object>();
			
			//Show the Progress Dialog
			this.publishProgress(0);
			
			//Use the LocationFinder to find a location from the hardware
			LocationFinder locationFinder = new LocationFinder();
			locationFinder.startFind(this.context);
			Location location = locationFinder.endFind();
			
			//Using the location find its address
			if(location != null)
			{
				result.put("location", location);
				
				//Using a Geocoder to find the address of this location
				GeoFinder geoFinder = new GeoFinder();
				
				List<String> addresses = geoFinder.geocode(context, location);
				result.put("addresses", addresses);
			}
			
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace(System.out);
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) 
	{
		super.onProgressUpdate(values);
		
		//Show the progress dialog
		this.progressDialog.show();
	}

	@Override
	protected void onPostExecute(Map<String, Object> result) 
	{
		super.onPostExecute(result);
		
		//Dismiss the progress dialog
		this.progressDialog.dismiss();
		
		List<String> addresses = (List<String>)result.get("addresses");
		
		if(addresses != null)
		{
			StringBuilder buffer = new StringBuilder();
			
			for(String address:addresses)
			{
				buffer.append("\n"+address);
			}
			
			ViewHelper.getOkModal(this.context, "Geocoding", buffer.toString()).
			show();
		}
		else
		{
			String message = "Addresses Not Found!!!";
			ViewHelper.getOkModal(this.context, "Geocoding", message).
			show();
		}
	}
}