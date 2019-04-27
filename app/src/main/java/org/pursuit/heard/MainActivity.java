package org.pursuit.heard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.pursuit.heard.mainFragments.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    // TODO: PLEASE DELETE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Hello World");
        System.out.println("Hello World");

    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
}
