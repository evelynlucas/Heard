package org.pursuit.heard.animation;

import android.view.animation.Interpolator;

public class BounceInterpolator implements Interpolator {

    private double myAmp = 1;
    private double myFreq = 10;


    public BounceInterpolator(double myAmp, double myFreq) {
        this.myAmp = myAmp;
        this.myFreq = myFreq;
    }

    @Override
    public float getInterpolation(float time) {
        return (float)(-1*Math.pow(Math.E, -time/myAmp)*Math.cos(myFreq*time)+1);
    }
}
