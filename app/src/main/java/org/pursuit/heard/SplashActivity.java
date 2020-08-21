package org.pursuit.heard;

import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.pursuit.heard.animation.BounceInterpolator;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;
    private TextView logText;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_fragment);

        logText = findViewById(R.id.logo_text);
        signInButton = findViewById(R.id.sign_in_button);
        signUpButton = findViewById(R.id.sign_up_button);
        signInButton.setVisibility(View.GONE);
        signUpButton.setVisibility(View.GONE);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        org.pursuit.heard.animation.BounceInterpolator bounceInterpolator = new BounceInterpolator(0.2, 20);
        animation.setInterpolator(bounceInterpolator);
        logText.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                signInButton.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.VISIBLE);
         //       Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
         //       startActivity(mainIntent);
         //       finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
