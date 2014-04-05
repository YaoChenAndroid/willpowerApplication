package com.example.willpower.yao.controllers;

import com.example.willpower.controllers.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import android.app.Activity;
import android.os.Bundle;

public class googleMapActivity extends Activity{
	  static final LatLng YAOCHEN = new LatLng(40.608268, -73.931750);
	  static final LatLng YUXIN = new LatLng(40.694299, -73.986928);
	  static final LatLng LAI = new LatLng(40.806317, -73.944127);
	  private GoogleMap map;
	  
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_google_map_yao);
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	        Marker hamburg = map.addMarker(new MarkerOptions().position(YAOCHEN)
	            .title("YaoChen"));
	        Marker kiel = map.addMarker(new MarkerOptions()
	            .position(YUXIN)
	            .title("Zhong")
	            .snippet("Zhong established the willpower")
	            .icon(BitmapDescriptorFactory
	                .fromResource(R.drawable.ic_launcher)));
	        map.addMarker(new MarkerOptions().position(LAI)
		            .title("LaiWang"));
	        // Move the camera instantly to hamburg with a zoom of 15.
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(YAOCHEN, 15));

	        // Zoom in, animating the camera.
	        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	}
}
