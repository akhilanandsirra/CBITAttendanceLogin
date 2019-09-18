package akhil.com.attendance;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.john.waveview.WaveView;

public class ProgressBarAnimation extends Animation {
    private WaveView progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(WaveView progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}