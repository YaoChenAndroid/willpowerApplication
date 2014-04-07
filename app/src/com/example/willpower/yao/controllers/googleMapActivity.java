package com.example.willpower.yao.controllers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.willpower.models.friendLoc;

import com.example.willpower.controllers.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class googleMapActivity extends FragmentActivity implements LocationListener,
	ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener{
	  private String username = "YaoChen";
	  
	  //parse data
	  private static final int MAX_POST_SEARCH_DISTANCE = 100;
	  private static final int MAX_POST_SEARCH_RESULTS = 10;

	  
	  //google location 
	  private GoogleMap map;
	  private String selectedObjectId;	  
	  private final Map<String, Marker> mapMarkers = new HashMap<String, Marker>();
	  private Location currentLocation;
	  
	  //google service
	  private LocationRequest locationRequest;
	  private LocationClient locationClient;
	  private Location lastLocation;
	
	  //google static data
	  // Milliseconds per second
	  private static final int MILLISECONDS_PER_SECOND = 1000;
	  // The update interval
	  private static final int UPDATE_INTERVAL_IN_SECONDS = 5;

	  // A fast interval ceiling
	  private static final int FAST_CEILING_IN_SECONDS = 1;
	  // Update interval in milliseconds
	  private static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
	      * UPDATE_INTERVAL_IN_SECONDS;
	  // A fast ceiling of update intervals, used when the app is visible
	  private static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND
	      * FAST_CEILING_IN_SECONDS;
	  private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	//activity method
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_google_map_yao);
		//initial service, the service used to get current location
		locationRequest = LocationRequest.create();
		locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);
		
		//local client
		locationClient = new LocationClient(this, this, this);
		
	    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	            .getMap();
	    map.setMyLocationEnabled(true);
	    map.setOnCameraChangeListener(new OnCameraChangeListener()
	    {

			@Override
			public void onCameraChange(CameraPosition arg0) {
				// TODO Auto-generated method stub
				doMapQuery();
			}	    	
	    });
	    if(GooglePlayServicesUtil.isGooglePlayServicesAvailable( getApplicationContext() ) == ConnectionResult.SUCCESS)
	    	Log.i("YaoChen", "Successful!");
	    else
	    {
	    	int n = GooglePlayServicesUtil.isGooglePlayServicesAvailable( getApplicationContext() );
	    	Log.e("YaoChen", String.valueOf(n));
	    }
	    
	    ///test
//	        Marker hamburg = map.addMarker(new MarkerOptions().position(YAOCHEN)
//	            .title("YaoChen"));
//	        Marker kiel = map.addMarker(new MarkerOptions()
//	            .position(YUXIN)
//	            .title("Zhong")
//	            .snippet("Zhong established the willpower")
//	            .icon(BitmapDescriptorFactory
//	                .fromResource(R.drawable.ic_launcher)));
//	        map.addMarker(new MarkerOptions().position(LAI)
//		            .title("LaiWang"));
//	        // Move the camera instantly to hamburg with a zoom of 15.
//	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(YAOCHEN, 15));
//
//	        // Zoom in, animating the camera.
//	        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
	        
//	        dataExchange temp = new dataExchange(this);
//	        String[] data = {"YaoChen", "40.608268", "-73.931750"};
//	        temp.saveData(data, dataExchange.DATA_TYPE_MAP);


	        
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		doMapQuery();
	}

	//parse data related method
	private ParseGeoPoint geoPointFromLocation(Location loc)
	{
		return new ParseGeoPoint(loc.getLatitude(), loc.getLongitude());
	}
	private void cleanUpMarkers(Set<String> toKeep) {
		// TODO Auto-generated method stub
		Set<String> temp = mapMarkers.keySet();
		for(String objId : new HashSet<String>(temp))
		{
			if(!toKeep.contains(objId))
			{
				Marker marker = mapMarkers.get(objId);
				marker.remove();
				mapMarkers.get(objId).remove();
				mapMarkers.remove(objId);
			}
		}
	}
	//get other users in certain distance from current user
	private void doMapQuery() {
		// TODO Auto-generated method stub
        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;

        
		if(myLoc == null)
		{
			cleanUpMarkers(new HashSet<String>());
			return;
		}
		final ParseGeoPoint myPoint = geoPointFromLocation(myLoc);
		ParseQuery<friendLoc> locQuery = friendLoc.getQuery();
		locQuery.whereWithinKilometers("location", myPoint, MAX_POST_SEARCH_DISTANCE);
		locQuery.include("user");
		locQuery.orderByAscending("createdAt");
		locQuery.setLimit(MAX_POST_SEARCH_RESULTS);
		//download position data
		locQuery.findInBackground(new FindCallback<friendLoc>(){

			@Override
			public void done(List<friendLoc> objects, ParseException arg1) {
				// TODO Auto-generated method stub
				if(null == objects) return;
				Set<String> toKeep = new HashSet<String>();
				String id;
				for(friendLoc loc: objects)
				{
					id = loc.getObjectId();
					toKeep.add(id);
					Marker oldMarker = mapMarkers.get(id);
					MarkerOptions markerOpts = 
							new MarkerOptions().position(new LatLng(loc.getLocation().getLatitude(), loc.getLocation().getLongitude()));
					if(oldMarker != null)
					{
						if(oldMarker.getSnippet() == null)
							continue;
						else
						{
							oldMarker.remove();
						}
					}
					markerOpts = 
							markerOpts.title(loc.getText())
							.icon(BitmapDescriptorFactory.defaultMarker(
									BitmapDescriptorFactory.HUE_ROSE));
					
					Marker marker = map.addMarker(markerOpts);
					mapMarkers.put(id, marker);
					if(id.equals(selectedObjectId))
					{
						marker.showInfoWindow();
						selectedObjectId = null;
					}
				}

				cleanUpMarkers(toKeep);
			}			
		}
		
		);
		
	}

	private void saveUserLocation() 
	{
        //save current location to parse
        ParseQuery<friendLoc> locQuery = friendLoc.getQuery();   
        locQuery.whereEqualTo("text", username);
        locQuery.findInBackground(new FindCallback<friendLoc>(){

			@Override
			public void done(List<friendLoc> arg0, ParseException e) {
				// TODO Auto-generated method stub
				if(null != e)
					return;
		        Location myLoc = (currentLocation == null) ? lastLocation : currentLocation;
		        if (myLoc == null) {
		          Toast.makeText(googleMapActivity.this,
		              "Please open the GPS service and try again after your location appears on the map.", Toast.LENGTH_LONG).show();
		          return;
		        }
		        final ParseGeoPoint myPoint = geoPointFromLocation(myLoc);
		        if(arg0.size() == 1){//if current user has record in the server, update
		        	arg0.get(0).setLocation(myPoint);
		        	arg0.get(0).saveInBackground(new SaveCallback(){
						@Override
						public void done(ParseException arg0) {
							// TODO Auto-generated method stub
							doMapQuery();
						}
			        	
			        });
		        }
		        else
		        {//if not, insert a new user into server
			        friendLoc loc = new friendLoc();
			        loc.setLocation(myPoint);
			        loc.setText(username);
			        ParseACL acl = new ParseACL();
			        acl.setPublicReadAccess(true);
			        loc.setACL(acl);
			        //Updata the location data
			        loc.saveInBackground(new SaveCallback(){
						@Override
						public void done(ParseException arg0) {
							// TODO Auto-generated method stub
							doMapQuery();
						}
			        	
			        });
		        }

		        LatLng latLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
		        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
		        map.animateCamera(cameraUpdate);
			}
        	
        });
	}
	//google server method
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		// TODO Auto-generated method stub
		if(connectionResult.hasResolution())
		{
			try
			{
				connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch(IntentSender.SendIntentException e)
			{
				
			}
		}else
		{
			showErrorDialog(connectionResult.getErrorCode());
		}
	}
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		currentLocation = getLocation();
		startPeriodicUpdata();		
		saveUserLocation();
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
	        Log.d("Disconnected from location services", getResources().getString(R.string.app_name));
	}
	@Override
	public void onLocationChanged(Location location)
	{
		currentLocation = location;
		if(lastLocation != null
				&& geoPointFromLocation(location).distanceInKilometersTo(geoPointFromLocation(lastLocation)) < 0.01)
			return;
		doMapQuery();
	}
	@Override
	public void onStop()
	{
		saveUserLocation();
		if(locationClient.isConnected())
		{
			stopPeriodicUpdates();
		}
		super.onStop();
	}
	@Override
	public void onStart()
	{
		super.onStart();
		locationClient.connect();

	}
	private void startPeriodicUpdata()
	{
		locationClient.requestLocationUpdates(locationRequest, this);
	}
	private void stopPeriodicUpdates()
	{
		locationClient.removeLocationUpdates(this);
	}
	private Location getLocation()
	{
		if(servicesConnected())
		{
			return locationClient.getLastLocation();
		}
		else
		{
			return null;
		}
	}
	private boolean servicesConnected() {
		// TODO Auto-generated method stub
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(ConnectionResult.SUCCESS == resultCode){
			return true;
		}else{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
			if(dialog != null){
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(), getResources().getString(R.string.app_name));
			}
		}
		return false;
	}
	  private void showErrorDialog(int errorCode) {
		    // Get the error dialog from Google Play services
		    Dialog errorDialog =
		        GooglePlayServicesUtil.getErrorDialog(errorCode, this,
		            CONNECTION_FAILURE_RESOLUTION_REQUEST);

		    // If Google Play services can provide an error dialog
		    if (errorDialog != null) {

		      // Create a new DialogFragment in which to show the error dialog
		      ErrorDialogFragment errorFragment = new ErrorDialogFragment();

		      // Set the dialog in the DialogFragment
		      errorFragment.setDialog(errorDialog);

		      // Show the error dialog in the DialogFragment
		      errorFragment.show(getSupportFragmentManager(), getResources().getString(R.string.app_name));
		    }
		  }
	public static class ErrorDialogFragment extends DialogFragment{
		private Dialog mDialog;
		public ErrorDialogFragment(){
			super();
			mDialog = null;
		}
		public void setDialog(Dialog dialog){
			mDialog = dialog;
		}
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState){
			return mDialog;
		}
	}
}
