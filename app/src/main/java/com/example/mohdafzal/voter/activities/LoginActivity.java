package com.example.mohdafzal.voter.activities;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohdafzal.voter.R;
import com.example.mohdafzal.voter.utils.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        if (checkPermission()) {
            //Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        } else {
            requestPermission();
        }
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                name.clearFocus();
                pass.requestFocus();
                return true;
            }
        });
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (name.getText().toString().matches("")) {
                    name.setError("please enter username");
                } else if (pass.getText().toString().matches("")) {
                    pass.setError("please enter password");
                } else {
                    /*Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);*/
                    sendreq();
                }
                return true;
            }
        });


        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().matches("")) {
                    name.setError("please enter username");
                } else if (pass.getText().toString().matches("")) {
                    pass.setError("please enter password");
                } else {
                   /* Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);*/
                    if (NetworkUtil.isNetworkAvailable(LoginActivity.this)) {

                        sendreq();
                    } else {
                        Toast.makeText(LoginActivity.this, "Not Connetced to internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
            {
                CALL_PHONE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
            }, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (CameraPermission) {


                    } else {

                    }
                }

                break;
        }
    }

    private void sendreq() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        String s = "http://electionapp.uxservices.in/Web_Services/login.asmx/AdminLogin?users=" + name.getText().toString() + "&pwd=" + pass.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                System.out.println("first" + response);
                String newString = response.replace("http://electionapp.uxservices.in", "");
                String newString1 = newString.replace("<string xmlns=\"\">", "");
                String newString2 = newString1.replace("</string>", "");
                String newstring3 = newString2.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
                System.out.println("dasdxaws" + newstring3);

                try {
                    JSONArray jsonArray = new JSONArray(newstring3);
                    System.out.println("dqws" + jsonArray);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if (jsonObject.getString("status").matches("false")) {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                    int userid = jsonObject1.getInt("E_User_id");
                    int user_option = jsonObject1.getInt("E_User_Area");
                    String name = jsonObject1.getString("E_User_Eng_FName") + " " + jsonObject1.getString("E_User_Eng_LName");


                    System.out.println("edsxz" + userid);
                    SharedPreferences.Editor editor = getSharedPreferences("userid", MODE_PRIVATE).edit();
                    editor.putInt("user_id", userid);
                    editor.putInt("user_option", user_option);
                    editor.putString("user_name", name);

                    editor.apply();
                    SharedPreferences.Editor editor1 = getSharedPreferences("Loginstatus", MODE_PRIVATE).edit();
                    editor1.putBoolean("loginstatus", true);
                    editor1.apply();


                    String s1 = jsonObject.getString("msg");
                    if (jsonObject.getJSONArray("data").getJSONObject(0).get("E_Election_Access").equals(null)) {
                        SharedPreferences.Editor editor2 = getSharedPreferences("userid", MODE_PRIVATE).edit();
                        editor2.putBoolean("election_access", false);
                        editor2.apply();

                    } else {
                        SharedPreferences.Editor editor2 = getSharedPreferences("userid", MODE_PRIVATE).edit();
                        editor2.putBoolean("election_access", true);
                        editor2.apply();
                    }
                    if (jsonObject.getJSONArray("data").getJSONObject(0).get("E_Report_Access").equals(null)) {
                        SharedPreferences.Editor editor2 = getSharedPreferences("userid", MODE_PRIVATE).edit();
                        editor2.putBoolean("report_access", false);
                        editor2.apply();

                    } else {
                        SharedPreferences.Editor editor2 = getSharedPreferences("userid", MODE_PRIVATE).edit();
                        editor2.putBoolean("report_access", true);
                        editor2.apply();
                    }
                    Toast.makeText(LoginActivity.this, s1, Toast.LENGTH_SHORT).show();
                    if (s1.matches("Login Successfully")) {
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        finish();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent, ActivityOptions
                                .makeSceneTransitionAnimation(LoginActivity.this).toBundle());
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "please check internet settings", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;

    }
}

