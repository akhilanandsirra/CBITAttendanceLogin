package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.john.waveview.WaveView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    WaveView waveView;
    AppCompatTextView Result,classAttended,classesTotal,
            dayDate,period1,period2,period3,period4,period5,period6,
            dayDateStatus,period1Status,period2Status,period3Status,period4Status,period5Status,period6Status;
    ArrayList<String> timeTable = new ArrayList<>();
    ArrayList<String> dayWiseTable = new ArrayList<>();
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Result=(AppCompatTextView)findViewById(R.id.percent);
        classAttended=(AppCompatTextView)findViewById(R.id.classes1);
        classesTotal=(AppCompatTextView)findViewById(R.id.classes2);

        dayDate=(AppCompatTextView)findViewById(R.id.row1column2);
        period1=(AppCompatTextView)findViewById(R.id.row2column2);
        period2=(AppCompatTextView)findViewById(R.id.row3column2);
        period3=(AppCompatTextView)findViewById(R.id.row4column2);
        period4=(AppCompatTextView)findViewById(R.id.row5column2);
        period5=(AppCompatTextView)findViewById(R.id.row6column2);
        period6=(AppCompatTextView)findViewById(R.id.row7column2);

        dayDateStatus=(AppCompatTextView)findViewById(R.id.table2row1column2);
        period1Status=(AppCompatTextView)findViewById(R.id.table2row2column2);
        period2Status=(AppCompatTextView)findViewById(R.id.table2row3column2);
        period3Status=(AppCompatTextView)findViewById(R.id.table2row4column2);
        period4Status=(AppCompatTextView)findViewById(R.id.table2row5column2);
        period5Status=(AppCompatTextView)findViewById(R.id.table2row6column2);
        period6Status=(AppCompatTextView)findViewById(R.id.table2row7column2);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent=getIntent();
        String result=intent.getStringExtra("percentage");
        float percent=Float.valueOf(result);

        String classesHeld=intent.getStringExtra("classes1");
        String classesAttended=intent.getStringExtra("classes2");

        timeTable=intent.getStringArrayListExtra("timetable");
        dayWiseTable=intent.getStringArrayListExtra("daywisetable");

        waveView=(WaveView)findViewById(R.id.wave_view);
        ProgressBarAnimation anim = new ProgressBarAnimation(waveView, 0, percent);
        anim.setDuration(1000);
        waveView.startAnimation(anim);
        Result.setText(String.format(getString(R.string.percentage),percent));
        classAttended.setText(String.format(getString(R.string.attendedString),classesAttended));
        classesTotal.setText(String.format(getString(R.string.totalString),classesHeld));

        //getDate
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
        String date = df.format(c);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        //setText for table1
        if(timeTable.isEmpty()){
            //if list is empty
            dayDate.setText(String.format(getString(R.string.table1Daydate),date,dayOfTheWeek));
            period1.setText("-");
            period2.setText("-");
            period3.setText("-");
            period4.setText("-");
            period5.setText("-");
            period6.setText("-");
        }

        else{
            String periodString1[]=timeTable.get(1).split("[\\(.*\\)]");
            String periodString2[]=timeTable.get(2).split("[\\(.*\\)]");
            String periodString3[]=timeTable.get(3).split("[\\(.*\\)]");
            String periodString4[]=timeTable.get(4).split("[\\(.*\\)]");
            String periodString5[]=timeTable.get(5).split("[\\(.*\\)]");
            String periodString6[]=timeTable.get(6).split("[\\(.*\\)]");

            dayDate.setText(String.format(getString(R.string.table1Daydate),date,timeTable.get(0)));
            period1.setText(Arrays.asList(periodString1).get(0));
            period2.setText(Arrays.asList(periodString2).get(0));
            period3.setText(Arrays.asList(periodString3).get(0));
            period4.setText(Arrays.asList(periodString4).get(0));
            period5.setText(Arrays.asList(periodString5).get(0));
            period6.setText(Arrays.asList(periodString6).get(0));
        }

        //setText for daywisetable

        String dayStatus[] = dayWiseTable.get(0).split(" ");

        String daydateStatus[]=Arrays.asList(dayStatus).get(0).split("[\\(.*\\)]");

        dayDateStatus.setText(String.format(getString(R.string.table1Daydate),Arrays.asList(daydateStatus).get(0),Arrays.asList(daydateStatus).get(1).toUpperCase()));
        period1Status.setText(Arrays.asList(dayStatus).get(1));
        period2Status.setText(Arrays.asList(dayStatus).get(2));
        period3Status.setText(Arrays.asList(dayStatus).get(3));
        period4Status.setText(Arrays.asList(dayStatus).get(4));
        period5Status.setText(Arrays.asList(dayStatus).get(5));
        period6Status.setText(Arrays.asList(dayStatus).get(6));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}