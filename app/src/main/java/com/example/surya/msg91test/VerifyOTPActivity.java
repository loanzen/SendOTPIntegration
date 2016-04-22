package com.example.surya.msg91test;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.msg91.sendotp.library.Config;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

public class VerifyOTPActivity extends ActionBarActivity {
    Button button;
    EditText otp;
    private static final String TAG = VerifyOTPActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Log.i(TAG, "Phone number: " + phoneNumber);

        button = (Button) findViewById(R.id.btn_verify_otp);
        otp = (EditText) findViewById(R.id.text_verify_otp);

        VerificationListener verificationListener = new TestVerificationListener();
        Config config = SendOtpVerification.config().context(getApplicationContext())
                .build();
        final Verification mVerification = SendOtpVerification.createSmsVerification(config,
                phoneNumber, verificationListener, "91");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "Verify OTP");
                mVerification.verify(otp.getText().toString());
            }
        });
    }
}
