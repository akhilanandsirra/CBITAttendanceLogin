package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.john.waveview.WaveView;

public class ResultActivity extends AppCompatActivity {
    WaveView waveView;
    AppCompatTextView Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Result=(AppCompatTextView)findViewById(R.id.percent);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent=getIntent();
        String result=intent.getStringExtra("percentage");
        float percent=Float.valueOf(result);

        waveView=(WaveView)findViewById(R.id.wave_view);
        ProgressBarAnimation anim = new ProgressBarAnimation(waveView, 0, percent);
        anim.setDuration(1000);
        waveView.startAnimation(anim);
        Result.setText(String.format(getString(R.string.percentage),percent));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}