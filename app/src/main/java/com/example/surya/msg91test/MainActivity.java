package com.example.surya.msg91test;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

class TestVerificationListener implements VerificationListener{
    private static final String TAG = TestVerificationListener.class.getCanonicalName();

    @Override
    public void onInitiated(String response){
        Log.i(TAG, "Initialized!");
    }

    @Override
    public void onInitiationFailed(Exception exception){
        Log.i(TAG, "Verification initialization failed: " + exception.getMessage());
    }

    @Override
    public void onVerified(String response){
        Log.i(TAG, "Verified!\n" + response);
    }

    @Override
    public void onVerificationFailed(Exception exception){
        Log.i(TAG, "Verification failed: " + exception.getMessage());
    }
}

public class MainActivity extends ActionBarActivity {

    Button button;
    EditText phoneNumber;
    private static final String TAG = MainActivity.class.getCanonicalName();
    public final static String EXTRA_MESSAGE = "com.example.surya.msg91test.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.send_otp);
        phoneNumber = (EditText) findViewById(R.id.text_otp);

        Log.i(TAG, "Works0");
        final Intent intent = new Intent(this, VerifyOTPActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "EditText: " + phoneNumber.getText().toString());
                try {
                    VerificationListener verificationListener = new TestVerificationListener();
                    Config config = SendOtpVerification.config().context(getApplicationContext())
                            .build();
                    Verification mVerification = SendOtpVerification.createSmsVerification(config,
                            phoneNumber.getText().toString(), verificationListener, "91");
                    mVerification.initiate();

                    intent.putExtra(EXTRA_MESSAGE, phoneNumber.getText().toString());

                    startActivity(intent);
                }catch(Exception e){
                    Log.i(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
