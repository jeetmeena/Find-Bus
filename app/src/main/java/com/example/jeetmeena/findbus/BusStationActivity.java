package com.example.jeetmeena.findbus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class BusStationActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, OnMapReadyCallback, View.OnClickListener
        , LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LoaderManager.LoaderCallbacks<Cursor> {
    GoogleMap mMap;
    LocationRequest mLocationRequest;
    static int REQUEST_LOCATION_PERMISSION;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
   GoogleApiClient mGoogleApiClient;
    private SettingsClient mSettingsClient;

    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    Context context;
    final int REQUEST_CHECK_SETTINGS = 1;
    final int REQUEST_LOCATION = 2;
    static Location mLastLocation;
    static boolean mRequestingLocationUpdates = false;
    TextView showitemList;
    ViewGroup viewGroup;
    public Boolean locUpdates;
    public Boolean useGPS;  // pref: use_device_location
    //private Boolean hasLocPermissions;
    SharedPreferences preferences;
    boolean bool = true;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frameLayout;
    RecyclerView busListRecyclerView;
    String[] arrayList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout linearBottomSheet;
    boolean addMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station);

        showitemList = findViewById(R.id.showlistItems);
        busListRecyclerView = findViewById(R.id.busLIstRecyclerview);
        linearBottomSheet = findViewById(R.id.map_bottom_sheet);
        arrayList = new String[]{"Aweesdfe", "Sfvseerfa", "Gadssweeff", "Hweeqew", "QEEEddf", "Bfgaafwe", "Nagargra", "aDFRfegr",
                "Aweesdfe", "Sfvseerfa", "Gadssweeff", "Hweeqew", "QEEEddf", "Bfgaafwe", "Nagargra", "aDFRfegr"};

        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BusListRecyclerView(arrayList, BusStationActivity.this);
        busListRecyclerView.setLayoutManager(mLayoutManager);
        busListRecyclerView.setAdapter(mAdapter);

        // start location services, including permissions checks, etc.
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        useGPS = preferences.getBoolean("use_device_location", false);
        locUpdates = false;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mSettingsClient = LocationServices.getSettingsClient(context);
        //createLocationCallback();
        //createLocationRequest();
       // buildLocationSettingsRequest();
        googleApiClient();
        //getLastKnowLocation();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
// The View with the BottomSheetBehavior
        View bottomSheet = coordinatorLayout.findViewById(R.id.map_bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);


        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {

                    case BottomSheetBehavior.STATE_DRAGGING:
                        //if (showFAB)
                        //   fab.startAnimation(shrinkAnimation);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        // showFAB = true;
                        // fab.setVisibility(View.VISIBLE);
                        // fab.startAnimation(growAnimation);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        //showFAB = false;
                        break;


                }

            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {

            }
        });


    }


    public void googleApiClient(){
        GoogleApiClient.Builder client=new GoogleApiClient.Builder(this);
                                  client.addConnectionCallbacks(this)
                                          .addOnConnectionFailedListener(this)
                                          .addApi(LocationServices.API)
                                          .addApi(Places.GEO_DATA_API)
                                          .enableAutoManage(this,this)
                                          .build();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showlistItems:

                if (bool) {
                    fragmentManager = getSupportFragmentManager(); // or getFragmentManager()
                    fragmentTransaction = fragmentManager.beginTransaction();
                    // fragmentTransaction.setCustomAnimations(R.animator.fade_out, R.animator.slide_in);
                    fragmentTransaction.replace(R.id.farmeListView, new ListBusItemFragment(context));
                    // fragmentTransaction.commit();

                    //  int fragmentManager=   getSupportFragmentManager().beginTransaction().add(R.id.farmeListView, new ListBusItemFragment(context)).commit();
                    Toast.makeText(BusStationActivity.this, "Location Supporting Frament", Toast.LENGTH_LONG).show();
                    bool = false;

                } else {
                    bool = true;
                    //  getSupportFragmentManager().beginTransaction().setCustomAnimations().commit();
                    fragmentTransaction.remove(new ListBusItemFragment(context)).commit();
                    checkPermissions();

                }


        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkPermissions();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Toast.makeText(BusStationActivity.this, "Location !PERMISSION_GRANTED0", Toast.LENGTH_LONG).show();
        mMap.getUiSettings();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.resetMinMaxZoomPreference();
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);



        }


            @Override
            public void onLocationChanged(Location location) {
                   LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());

                MarkerOptions marke=  new MarkerOptions().position(latLng).title("Marker in Sydney");

                mMap.addMarker(marke);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }



    // @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (useGPS && !locUpdates) {
            lastKnowLocationUpdatesz();
        }
    }

    private void lastKnowLocationUpdatesz() {
        if (ActivityCompat.checkSelfPermission(  this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
              // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    // @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
            Toast.makeText(BusStationActivity.this, "Location !PERMISSION_GRANTED1", Toast.LENGTH_LONG).show();
        } else {
            startLocationUpdates();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted
                    startLocationUpdates();
                } else {
                    // permission denied
                    useGPS = false;
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putBoolean("use_device_location", false);
                    edit.apply();
                    Toast.makeText(this,
                            "location_permission_denied",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void startLocationUpdates() {
        Toast.makeText(BusStationActivity.this, "Location !PERMISSION_GRANTED2", Toast.LENGTH_LONG).show();
        showitemList.setVisibility(View.VISIBLE);
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        builder.setAlwaysShow(true);
        mSettingsClient = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task = mSettingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

                //  getLocation();
                locUpdates = true;
                if (ActivityCompat.checkSelfPermission(BusStationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BusStationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Toast.makeText(BusStationActivity.this, "Location !PERMISSION_GRANTED3", Toast.LENGTH_LONG).show();
                //  mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                //   mLocationCallback,
                //Looper.myLooper() /* Looper */);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int stetusCod = ((ApiException) e).getStatusCode();
                switch (stetusCod) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Toast.makeText(BusStationActivity.this, "Location !PERMISSION_GRANTED3", Toast.LENGTH_LONG).show();
                            // show the dialog by calling startResolutionForResult(), and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(BusStationActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:


                        Toast.makeText(BusStationActivity.this, "Location Services Unavailable", Toast.LENGTH_LONG).show();
                        useGPS = false;
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("use_device_location", false);
                        edit.apply();
                        break;


                }
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        Toast.makeText(BusStationActivity.this, "getLocation: onFailure.",
                                Toast.LENGTH_SHORT).show();
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(BusStationActivity.this,
                                REQUEST_CHECK_SETTINGS);
                        // getLocation();
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:

                        if (ActivityCompat.checkSelfPermission(BusStationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(BusStationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(BusStationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        } else {
                            // Start location updates
                            // Note - in emulator location appears to be null if no other app is using GPS at time.
                            // So if just turning on device's location services getLastLocation will likely not return anything
                            // mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null /*looper*/);
                            locUpdates = true;
                        }


                        break;
                    case RESULT_CANCELED:

                        useGPS = false;
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("use_device_location", false);
                        edit.apply();
                        break;


                }


                break;


        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    private void stopLocationUpdates() {
        locUpdates = false;
        mFusedLocationClient.removeLocationUpdates(mLocationCallback).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });
    }

    private void buildLocationSettingsRequest() {
        // get current locations settings of user's device
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void getStartLocationUpdatInInterval() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                Looper.myLooper() /* Looper */);

                  }

                  public void createLocationCallback(){

                      mLocationCallback = new LocationCallback() {
                          @Override
                          public void onLocationResult(LocationResult locationResult) {
                              super.onLocationResult(locationResult);
                              if (locationResult == null) {
                                  Toast.makeText(BusStationActivity.this, "getLocation:Location== Null" ,
                                          Toast.LENGTH_SHORT).show();
                                  return;
                              }
                              mLastLocation=locationResult.getLastLocation();
                              setLatLong(mCurrentLocation);


                              for (Location location : locationResult.getLocations()) {
                                  // Update UI with location data
                                  // ...
                              }
                          }

                          ;
                      };

                  }

    // Set new latitude and longitude based on location results
                public void setLatLong(Location location) {
                    double lastLat = location.getLatitude();
                    double lastLong = location.getLongitude();
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString("last_gps_lat", String.valueOf(lastLat));
                    edit.putString("last_gps_long", String.valueOf(lastLong));
                    edit.apply();
                    Toast.makeText(BusStationActivity.this, "getLocation:"+lastLat+lastLong,
                            Toast.LENGTH_SHORT).show();

                }




                                        private void getLocation() {
                                            if (ActivityCompat.checkSelfPermission(this,
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(this, new String[]
                                                                {android.Manifest.permission.ACCESS_FINE_LOCATION},
                                                        REQUEST_LOCATION_PERMISSION);
                                            } else {

                                                Toast.makeText(BusStationActivity.this, "getLocation: permissions granted.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }





    public void getLastKnowLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLastLocation = location;
                            //mLocationTextView.setText(
                            //getString(R.string.location_text,
                            //    mLastLocation.getLatitude(),
                            //    mLastLocation.getLongitude(),
                            // mLastLocation.getTime()));
                        } else {
                            // mLocationTextView.setText(R.string.no_location);
                        }
                    }


                });


    }


    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(context,"frgmenttuch",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Toast.makeText(context," onShowPress",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Toast.makeText(context,"onSingleTapUp",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Toast.makeText( context,"onScroll",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(context,"onLongPress",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Toast.makeText(context,"onFling",Toast.LENGTH_SHORT).show();
        return true;
    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }


    private class BusListRecyclerView extends RecyclerView.Adapter<BusListRecyclerView.ViewHolder> implements View.OnClickListener {
                String[] arrylit;
                Context context;

                public BusListRecyclerView(String[] arrayList, BusStationActivity busStationActivity) {
                    arrylit=arrayList;
                    context=busStationActivity;
                    linearBottomSheet.setVisibility(View.VISIBLE);
                }

                @Override
                public BusListRecyclerView.ViewHolder onCreateViewHolder(  ViewGroup parent, int viewType) {
                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.busitemlist,parent,false);
                    ViewHolder viewHolder=new ViewHolder(view);


                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(ViewHolder holder, int position) {
                   holder.mTextView.setText(arrylit[position]);

                }


                @Override
                public int getItemCount() {
                    return arrylit.length;
                }

                @Override
                public void onClick(View v) {
                    if(R.id.nameOfBus==v.getId()){
                        Toast.makeText( context,"name",Toast.LENGTH_SHORT).show();

                    }

                }


                public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                    public TextView mTextView;
                    ImageView button;
                    public ViewHolder(View itemView) {
                        super(itemView);
                        mTextView=itemView.findViewById(R.id.nameOfBus);
                        button =findViewById(R.id.destenas);
                       // button.setTag(R.integer.btn_plus_view, itemView);

                       // button.setOnClickListener(this);
                        itemView.setOnClickListener(this);
                    }

                    @Override
                    public void onClick(View v) {
                        if(R.id.nameOfBus==v.getId()){
                            Toast.makeText( context,"name",Toast.LENGTH_SHORT).show();

                        }
                        else if(button.getId()==v.getId()){
                            Toast.makeText( context,"button",Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText( context,"item",Toast.LENGTH_SHORT).show();
                    }
                }
            }


}
