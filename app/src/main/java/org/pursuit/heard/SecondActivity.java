package org.pursuit.heard;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class SecondActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();

        createPagerFragments();
    }

    private void createPagerFragments() {

    }
}
