package com.example.mohdafzal.mvoter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.CALL_PHONE;

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
         name=(EditText)findViewById(R.id.username);
         pass=(EditText)findViewById(R.id.password);
        if(checkPermission()){

            //Toast.makeText(MainActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

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
                if(name.getText().toString().matches("")){
                    name.setError("please enter username");
                }
                else if(pass.getText().toString().matches("")){
                    pass.setError("please enter password");
                }
                else{
                    sendreq();
                }                return true;
            }
        });


        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().matches("")){
                    name.setError("please enter username");
                }
               else if(pass.getText().toString().matches("")){
                    pass.setError("please enter password");
                }
                else{
                    sendreq();
                }
            }
        });

    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {
                        CALL_PHONE
                }, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;


                    if (CameraPermission ) {


                    }
                    else {

                    }
                }

                break;
        }
    }
    private void sendreq() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        String s="http://electionapp.uxservices.in/Web_Services/login.asmx/AdminLogin?users="+name.getText().toString()+"&pwd="+pass.getText().toString();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                System.out.println("first"+response);
                String newString = response.replace("http://electionapp.uxservices.in", "");
                String newString1 = newString.replace("<string xmlns=\"\">", "");
                String newString2 = newString1.replace("</string>", "");
                String newstring3=newString2.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","");
                System.out.println("dasdxaws"+newstring3);

                try {
                        JSONArray jsonArray = new JSONArray(newstring3);
                    System.out.println("dqws"+jsonArray);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    JSONArray jsonArray1=jsonObject.getJSONArray("data");
                    JSONObject jsonObject1=jsonArray1.getJSONObject(0);
                   int userid= jsonObject1.getInt("E_User_id");
                    System.out.println("edsxz"+userid);
                    Toast.makeText(LoginActivity.this, String.valueOf(userid), Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=getSharedPreferences("userid",MODE_PRIVATE).edit();
                    editor.putInt("user_id",userid);
                    editor.apply();
                    SharedPreferences.Editor editor1=getSharedPreferences("Loginstatus",MODE_PRIVATE).edit();
editor1.putBoolean("loginstatus",true);
                    editor1.apply();


                    String s1=jsonObject.getString("msg");
                    Toast.makeText(LoginActivity.this, s1, Toast.LENGTH_SHORT).show();
if (s1.matches("Login Successfully")){
    Intent intent=new Intent(LoginActivity.this,DashBoard.class);
    finish();
    startActivity(intent);
}
else{
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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED ;

    }
    }

