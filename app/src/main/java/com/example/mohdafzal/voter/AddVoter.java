package com.example.mohdafzal.voter;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.mohdafzal.voter.navigationdrawer.BaseActivity;

import java.util.List;
import java.util.Locale;

public class AddVoter extends BaseActivity implements View.OnClickListener {
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private EditText dateofbith;
    private ImageView datepicker;
    private RadioGroup radiogroupmale;
    private LinearLayout femaleLayout, outoftownlayout, businesslayout;
    private RadioButton femalerad;
    private RadioButton outOfTownRadio;
    private Spinner selectProfessionSpinner;
    private int year;
    private int month;
    private int day;
    private Button googleLocation;
    private LocationManager locationManager;
    private Location location;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voter);
        getSupportActionBar().setTitle("Add New Voter");
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        set(navMenuTitles, navMenuIcons);
        initUI();
        femalerad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (femaleLayout.isShown()) {
                    femaleLayout.setVisibility(View.GONE);
                } else {
                    femaleLayout.setVisibility(View.VISIBLE);

                }
            }
        });
        outOfTownRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (outoftownlayout.isShown()) {
                    outoftownlayout.setVisibility(View.GONE);
                } else {
                    outoftownlayout.setVisibility(View.VISIBLE);

                }
            }
        });
        selectProfessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 2 | position == 3) {
                    businesslayout.setVisibility(View.VISIBLE);
                } else {
                    businesslayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initUI() {
        dateofbith = (EditText) findViewById(R.id.dateofbirth);
        datepicker = (ImageView) findViewById(R.id.datepicker);
        radiogroupmale = (RadioGroup) findViewById(R.id.radiogrupgender);
        femaleLayout = (LinearLayout) findViewById(R.id.feamlelayout);
        outoftownlayout = (LinearLayout) findViewById(R.id.outoftownlayout);
        businesslayout = (LinearLayout) findViewById(R.id.businesslayout);
        femalerad = (RadioButton) findViewById(R.id.rdfemale);
        address=(EditText)findViewById(R.id.address);
        outOfTownRadio = (RadioButton) findViewById(R.id.outOfTownRadio);
        selectProfessionSpinner = (Spinner) findViewById(R.id.selectProfessionSpinner);
        googleLocation = (Button) findViewById(R.id.googleLocation);
        datepicker.setOnClickListener(this);
        googleLocation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateofbirth:
                showDialog(0);
                break;
            case R.id.googleLocation:
                getCurrentAddress();

                break;
        }
    }
    public void getCurrentAddress() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                    return;

                }
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    4000000,
                    10000, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });
            } catch (Exception ex) {
                Log.i("msg", "fail to request location update, ignore", ex);
            }
            if (locationManager != null) {
                location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

                if (addresses.size() > 0) {
                    address.setText(addresses.get(0).getAddressLine(0).replace("null", ""));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            dateofbith.setText((selectedMonth + 1) + "/" + selectedDay + "/"
                + selectedYear);
        }
    };
}
