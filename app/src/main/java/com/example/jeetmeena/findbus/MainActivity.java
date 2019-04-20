package com.example.jeetmeena.findbus;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formattable;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.xpath.XPath;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener {
  int   PLACE_AUTOCOMPLETE_REQUEST_CODE=5;
    String PREFERENCES_FILE = "com.example.jeetmeena.findbus";
    static MainActivity mainActivity;
    ViewPager mViewPager;
    ImageView indacter_0, indacter_1, indacter_2, indacter_3, indacter_4;
    ImageButton mNextBtn;
    Button mFinishBtn, mSkip;
    private PagerAdapter mPagerAdapter;
    static int NUM_PAGES = 5;
    static int count = 0;
    int[] colorList;
    Button findBus,busStation;
    FirebaseAuth mAuths;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ImageButton headerImageButton,calenderImageView;
    NavigationView navigationView;
    FirebaseUser user;
    HorizontalScrollView horizontalScrollView;
    TextView dateTextView;
    static  String date;
    ViewPager viewPagerOffer;
    Timer  timer;
    int[] offerScreen;
    final int REQUEST_LOCATION = 2;
    AutoCompleteTextView currentLocation,destination;
    static Uri picUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          mainActivity = this;

           findBus=findViewById(R.id.findBuss);
           busStation=findViewById(R.id.busStationButton);
           drawerLayout =findViewById(R.id.drawerLayout);
           navigationView=findViewById(R.id.navigation);
          //toolbar =findViewById(R.id.app_bar);
          //   setSupportActionBar(toolbar);

        offerScreen=new int[] {R.layout.offera,R.layout.offerb,R.layout.offerc,R.layout.offerd,R.layout.offere};
                    dateTextView=findViewById(R.id.dateTextView);
                viewPagerOffer = findViewById(R.id.viewPagerOfferShow);
               currentLocation =findViewById(R.id.editView);
               destination=findViewById(R.id.editView2);
               calenderImageView=findViewById(R.id.calanderimageView);
               calenderImageView.setOnClickListener(this);
               viewPagerOffer.setAdapter(new OfferForOrder(getSupportFragmentManager(),offerScreen));
               viewPagerOffer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                   @Override
                   public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                   }

                   @Override
                   public void onPageSelected(int position) {

                   }

                   @Override
                   public void onPageScrollStateChanged(int state) {

                   }
               });






        PlaceAutocompleteFragment mPlaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager()
                .findFragmentById(R.id.place_autocomplete);
       // currentLocation.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
                     mPlaceAutocompleteFragment= (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
                        mPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                            @Override
                            public void onPlaceSelected(Place place) {
                                LatLng latLng=place.getLatLng();
                                Toast.makeText(MainActivity.this,"On location  "+place,Toast.LENGTH_LONG).show();
                               // goToThisLocation(1,latLng.latitude,latLng.longitude,15);
                            }

                            @Override
                            public void onError(Status status) {

                            }
                        });

                             mPlaceAutocompleteFragment.setBoundsBias(new LatLngBounds(
                                    new LatLng(-33.880490, 151.184363),
                                    new LatLng(-33.858754, 151.229596)));
     //  destination.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));

                mPlaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        LatLng latLng=place.getLatLng();
                        Toast.makeText(MainActivity.this,"On location  "+place,Toast.LENGTH_LONG).show();
                        //goToThisLocation(1,latLng.latitude,latLng.longitude,15);
                    }

                    @Override
                    public void onError(Status status) {

                    }
                });

          Date c = Calendar.getInstance().getTime();

             SimpleDateFormat d3 = new SimpleDateFormat("dd-MM-yyyy");
              date=d3.format(c);
                   navigationView.setNavigationItemSelectedListener(this);

                 View header=navigationView.getHeaderView(0);
                 headerImageButton=header.findViewById(R.id.headerImageButton);

                //TextView textView=navigationView.findViewById(R.id.headerEmail);
                   // textView.setText(user.getEmail());
                  /*View header=navigationView.inflateHeaderView(R.layout.drawer_header);
                  TextView textView=header.findViewById(R.id.headerEmail);
                              textView.setText(user.getEmail());*/
                   headerImageButton.setOnClickListener(this);
                   drawerLayout.addDrawerListener(this);
                   ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Drawer_Open,R.string.Drawer_Close);
                   drawerLayout.addDrawerListener(toggle);
                   toggle.syncState();
                   dateTextView.setOnClickListener(this);
                   try{//Intent intent=getIntent();
                      // date=intent.getStringExtra("date");

                   }
                    catch (Exception e){}

                   dateTextView.setText(date);
                   mAuths=FirebaseAuth.getInstance();
                   user=mAuths.getCurrentUser();
                if (getsharedPerfernces("User_First_Time","true").equals("true")) {
                    Intent intent = new Intent(this, PromoteActivity.class);
                    startActivity(intent);



                     }
                    else {

                    setmAuthds();
                    }
                   // horizontalScrollView.findViewById(R.id.horizontalScroll);
                   // horizontalScrollView.setSmoothScrollingEnabled(true);

                  //setupAutoPager();
             // setPic( mainActivity.getsharedPerfernces("pic_uri","asdf") );

        Toast.makeText(MainActivity.this, " pic"+picUris,
                Toast.LENGTH_SHORT).show();

      }


    private int currentPage = 0;
    private void setupAutoPager()
    {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run()
            {

                mViewPager.setCurrentItem(currentPage, true);
                if(currentPage == Integer.MAX_VALUE)
                {
                    currentPage = 0;
                }
                else
                {
                    ++currentPage ;
                }
            }
        };


      timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 2500);
    }


      public void permission(){

           // if(ContextCompat.checkSelfPermission(this,Manifest.a))

       }

    private Bitmap setPic(String uripath) {
        // Get the dimensions of the View
        int targetW = headerImageButton.getWidth();
        int targetH =headerImageButton.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        // Get the dimensions of the bitmap
        try {
            BitmapFactory.decodeFile( uripath, bmOptions);
        }catch (Exception e){}


        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        Bitmap bitmap=null;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        try {
           bitmap = BitmapFactory.decodeFile(uripath, bmOptions);
        }catch (Exception e){}


        headerImageButton.setImageBitmap(bitmap);
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);



        return true;

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.firstItem:

                Toast.makeText(MainActivity.this, "first",
                        Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.Seconditem:
                drawerLayout.closeDrawers();
                item.setChecked(true);
                break;
            case R.id.thirdItem:
                drawerLayout.closeDrawers();
                item.setChecked(true);
                break;
            case R.id.fourthItem:
                drawerLayout.closeDrawers();
                item.setChecked(true);
                break;
            case R.id.fifthItem:
                drawerLayout.closeDrawers();
                item.setChecked(true);
                break;
            default :  Toast.makeText(MainActivity.this, "SomeThing wrong",
                    Toast.LENGTH_SHORT).show();



        }

        return false;

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
          {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, UdserFrofileActivity.class);
                intent.putExtra("userEmail",user.getEmail());
                startActivity(intent);
                break;
            case R.id.logOut: mAuths.signOut();
                break;
            default :  Toast.makeText(MainActivity.this, "SomeThing wrong",
                  Toast.LENGTH_SHORT).show();

          }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
               // Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
            //    Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


    public static MainActivity getMainActivity() {
        return mainActivity;
    }



            public void savesharedPerferns(String key, String value) {
                SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Toast.makeText(MainActivity.this, " Save ",
                        Toast.LENGTH_SHORT).show();
                editor.putString(key, value);
                editor.apply();


            }

            public String getsharedPerfernces(String key, String difa) {
                SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);

                return sharedPreferences.getString(key, difa);
            }


          public void setPicUri(Uri p) {
           picUris=p;
           headerImageButton.setImageURI(picUris);

           }



    @Override
    protected void onStart()
      {
          super.onStart();

         }



    public void setmAuthds() {

      if(getsharedPerfernces("LogedIn","false").equals("false")){
          Intent intent = new Intent(MainActivity.this, AuthActivity.class);
          startActivity(intent);
          }
          else {

          if (ContextCompat.checkSelfPermission(MainActivity.this,
                  android.Manifest.permission.ACCESS_FINE_LOCATION)
                  != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                  Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {

              // Permission is not granted
              // Should we show an explanation?
              if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                      Manifest.permission.ACCESS_FINE_LOCATION ) && ActivityCompat.checkSelfPermission(MainActivity.this,
                      Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED) {

                      showAlertMessage();
                  // Show an explanation to the user *asynchronously* -- don't block
                  // this thread waiting for the user's response! After the user
                  // sees the explanation, try again to request the permission.
              } else {
                  // No explanation needed, we can request the permission.
                  ActivityCompat.requestPermissions(this, new String[]
                                  {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                          REQUEST_LOCATION);

                  // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                  // app-defined int constant. The callback method gets the
                  // result of the request.
              }
          } else {
              // Permission has already been granted
          }
          Toast.makeText(MainActivity.this, "LogedIn."+user.getEmail()+user.getUid(),
                  Toast.LENGTH_SHORT).show();
      }

       }






    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            // for each permission check if the user granted/denied them
            // you may want to group the rationale in a single dialog,
            // this is just an example
            for (int i = 0, len = permissions.length; i < len; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = shouldShowRequestPermissionRationale( permission );
                    if (! showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                    }




                    else if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permission)) {
                        showRationale(permission, "We want to pervaded  you tracking feature through the Device Location ,GPS and WIFI ");
                        // user did NOT check "never ask again"
                        // this is a good place to explain the user
                        // why you need the permission and ask if he wants
                        // to accept it (the rationale)
                    } //else if ( /* possibly check more permissions...*/ ) {
                    //}
                }
            }
        }
    }



                private void showAlertMessage(){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Warning");
                    builder.setIcon(R.drawable.ic_warning_black_24dp);
                    builder.setMessage("Allow to ");
                    // Add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                            {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_LOCATION);

                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    // Set other dialog properties

                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    builder.show();
                }

                private void showRationale(String permission, String s) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Warning");
                    builder.setIcon(R.drawable.ic_warning_black_24dp);
                    builder.setMessage(s);
                    // Add the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Set other dialog properties

            // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                }


    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "LogedIn."+user.getEmail()+user.getUid(),
                Toast.LENGTH_SHORT).show();
        if(R.id.dateTextView ==v.getId()){

            startActivity(new Intent(MainActivity.this,CelendarActivity.class));

        }
        else if(R.id.calanderimageView==v.getId()){
            startActivity(new Intent(MainActivity.this,CelendarActivity.class));

        }
        else if(R.id.findBuss==v.getId()){
            startActivity(new Intent(MainActivity.this,FindBusActivity.class));

        }

        else if(R.id.busStationButton==v.getId()){
            startActivity(new Intent(MainActivity.this,BusStationActivity.class));

        }

        else  if(R.id.headerImageButton==v.getId()){
            Intent intent = new Intent(MainActivity.this, UdserFrofileActivity.class);
            intent.putExtra("userEmail",user.getEmail());
            startActivity(intent);

        }

    }





                  @Override
                  public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                   }

                  @Override
                  public void onDrawerOpened(@NonNull View drawerView) {

                  }

                  @Override
                  public void onDrawerClosed(@NonNull View drawerView) {

                  }

                  @Override
                  public void onDrawerStateChanged(int newState) {

                  }

      public void logOut() {
        mAuths.signOut();

        savesharedPerferns("LogedIn","false");
      }



                        private class OfferForOrder extends PagerAdapter {
                         int[] offerScren;
                            public OfferForOrder(FragmentManager supportFragmentManager, int[] offerScreen) {
                            offerScren=offerScreen;
                            }

                            @Override
                            public int getCount() {
                                return offerScreen.length;
                            }
                               @Override
                               public void destroyItem(ViewGroup container, int position, Object object) {
                                View v = (View) object;
                                container.removeView(v);
                                 }

                            @Override
                            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                                return view==object;
                            }
                            @NonNull
                            @Override
                            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                                LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v= inflater.inflate(offerScren[position],container,false);
                                container.addView(v);

                                return v;
                            }
                        }


}