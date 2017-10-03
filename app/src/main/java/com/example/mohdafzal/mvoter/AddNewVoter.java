package com.example.mohdafzal.mvoter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddNewVoter extends AppCompatActivity implements View.OnClickListener {

    private EditText fname,lname,mname,gln,aadhar;
    private ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_voter);
         logout=(ImageView)findViewById(R.id.logout) ;
        logout.setOnClickListener(this);
         fname=(EditText)findViewById(R.id.fname);
         lname=(EditText)findViewById(R.id.lname);
         mname=(EditText)findViewById(R.id.mname);
         gln=(EditText)findViewById(R.id.gln);
         aadhar=(EditText)findViewById(R.id.aadharcard);
        fname.setOnClickListener(this);
        lname.setOnClickListener(this);
        mname.setOnClickListener(this);
        gln.setOnClickListener(this);
        aadhar.setOnClickListener(this);
        fname.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            fname.clearFocus();
                            if(fname.getText().toString().matches("")){
                                fname.setError("please enter firstname");
                            }
                            else {
                                mname.requestFocus();
                            }
                                                     return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        mname.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            fname.clearFocus();
                            lname.requestFocus();

                                                    return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        }); lname.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            lname.clearFocus();
                            if(lname.getText().toString().matches("")){
                                lname.setError("please enter firstname");
                            }
                            else {
                                gln.requestFocus();
                            }
                                                    return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        }); gln.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            gln.clearFocus();
                            if(fname.getText().toString().matches("")){
                                fname.setError("please enter firstname");
                            }
                            else {
                                aadhar.requestFocus();
                            }

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        aadhar.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            aadhar.clearFocus();
                            if(aadhar.getText().toString().matches("")){
                                aadhar.setError("please enter aadhar");
                            }

                                                     else{
                                Toast.makeText(AddNewVoter.this, "All Values Are Right", Toast.LENGTH_SHORT).show();                            }                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                Intent intent=new Intent(this,LoginActivity.class);
                startActivity(intent);


        }

    }
}
