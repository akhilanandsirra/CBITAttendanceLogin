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
    AppCompatTextView Result,classAttended,classesTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Result=(AppCompatTextView)findViewById(R.id.percent);
        classAttended=(AppCompatTextView)findViewById(R.id.classes1);
        classesTotal=(AppCompatTextView)findViewById(R.id.classes2);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent=getIntent();
        String result=intent.getStringExtra("percentage");
        float percent=Float.valueOf(result);

        String classesHeld=intent.getStringExtra("classes1");
        String classesAttended=intent.getStringExtra("classes2");

        waveView=(WaveView)findViewById(R.id.wave_view);
        ProgressBarAnimation anim = new ProgressBarAnimation(waveView, 0, percent);
        anim.setDuration(1000);
        waveView.startAnimation(anim);
        Result.setText(String.format(getString(R.string.percentage),percent));
        classAttended.setText(String.format(getString(R.string.attendedString),classesAttended));
        classesTotal.setText(String.format(getString(R.string.totalString),classesHeld));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}