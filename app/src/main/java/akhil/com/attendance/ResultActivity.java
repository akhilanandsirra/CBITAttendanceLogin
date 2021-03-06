package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.animation.content.Content;
import com.john.waveview.WaveView;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
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
    String date,originalUrl,savedUrl;
    AppCompatButton websiteButton,calculatorButton,syllabusButton,notesButton;
    boolean rememberMe,refresh;
    boolean doublePressedBackExit = false;
    private Content Task;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    String classesHeld ,classesAttended;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Result = (AppCompatTextView) findViewById(R.id.percent);
        classAttended = (AppCompatTextView) findViewById(R.id.classes1);
        classesTotal = (AppCompatTextView) findViewById(R.id.classes2);

        dayDate = (AppCompatTextView) findViewById(R.id.row1column2);
        period1 = (AppCompatTextView) findViewById(R.id.row2column2);
        period2 = (AppCompatTextView) findViewById(R.id.row3column2);
        period3 = (AppCompatTextView) findViewById(R.id.row4column2);
        period4 = (AppCompatTextView) findViewById(R.id.row5column2);
        period5 = (AppCompatTextView) findViewById(R.id.row6column2);
        period6 = (AppCompatTextView) findViewById(R.id.row7column2);

        dayDateStatus = (AppCompatTextView) findViewById(R.id.table2row1column2);
        period1Status = (AppCompatTextView) findViewById(R.id.table2row2column2);
        period2Status = (AppCompatTextView) findViewById(R.id.table2row3column2);
        period3Status = (AppCompatTextView) findViewById(R.id.table2row4column2);
        period4Status = (AppCompatTextView) findViewById(R.id.table2row5column2);
        period5Status = (AppCompatTextView) findViewById(R.id.table2row6column2);
        period6Status = (AppCompatTextView) findViewById(R.id.table2row7column2);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        if(height<1921){
            LinearLayout linearLayout1=findViewById(R.id.LinearLayout1);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayout1.getLayoutParams();
            params.setMargins(0, 105, 0, 0);
            linearLayout1.setLayoutParams(params);
        }

        if(width<900){
            LinearLayout linearLayout1=findViewById(R.id.LinearLayout1);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayout1.getLayoutParams();
            params.setMargins(0, 65, 0, 0);
            linearLayout1.setLayoutParams(params);
        }

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Intent intent = getIntent();
        String result = intent.getStringExtra("percentage");
        float percent = Float.valueOf(result);


        rememberMe = intent.getBooleanExtra("Remember", false);

        SharedPreferences loginPreferences2 = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        refresh = loginPreferences2.getBoolean("saveLogin", false);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        savedUrl= loginPreferences.getString("url", null);

        classesHeld = intent.getStringExtra("classes1");
        classesAttended = intent.getStringExtra("classes2");
        //originalUrl=intent.getStringExtra("url");

        timeTable = intent.getStringArrayListExtra("timetable");
        dayWiseTable = intent.getStringArrayListExtra("daywisetable");

        waveView = (WaveView) findViewById(R.id.wave_view);
        ProgressBarAnimation anim = new ProgressBarAnimation(waveView, 0, percent);
        anim.setDuration(1000);
        waveView.startAnimation(anim);

        Result.setText(String.format(getString(R.string.percentage), percent));

        /*ValueAnimator valueAnimatorPercent = ValueAnimator.ofFloat(0, percent);
        valueAnimatorPercent.setDuration(500);
        valueAnimatorPercent.setInterpolator(new DecelerateInterpolator());
        valueAnimatorPercent.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DecimalFormat df = new DecimalFormat("#0.00");
                df.setRoundingMode(RoundingMode.DOWN);
                Result.setText(String.format(getString(R.string.percentage),(df.format((float)valueAnimator.getAnimatedValue()))));
            }
        });

        valueAnimatorPercent.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                return (startValue + (endValue - startValue) * fraction);
            }
        });

        valueAnimatorPercent.start();*/

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, Integer.parseInt(classesAttended));
        ValueAnimator valueAnimator2 = ValueAnimator.ofInt(0, Integer.parseInt(classesHeld));
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator2.setDuration(1000);
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                classAttended.setText(String.format(getString(R.string.attendedString),valueAnimator.getAnimatedValue().toString()));
            }
        });

        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                classesTotal.setText(String.format(getString(R.string.totalString),valueAnimator.getAnimatedValue().toString()));
            }
        });

        valueAnimator.start();
        valueAnimator2.start();

        //classAttended.setText(String.format(getString(R.string.attendedString), classesAttended));
        //classesTotal.setText(String.format(getString(R.string.totalString), classesHeld));

        //getDate
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
        date = df.format(c);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        //setText for table1
        if(timeTable.isEmpty()){
            //if list is empty
            dayDate.setText(String.format(getString(R.string.table1Daydate),date,dayOfTheWeek.toUpperCase()));
            period1.setText("-");
            period2.setText("-");
            period3.setText("-");
            period4.setText("-");
            period5.setText("-");
            period6.setText("-");
        }

        else{
            String periodString1[];
            if(timeTable.size()>1)
                if (timeTable.get(1).isEmpty())
                    periodString1= new String[]{"-"};
                else
                    periodString1=timeTable.get(1).split("[\\(.*\\)]");
            else
                periodString1= new String[]{"-"};

            String periodString2[];
            if(timeTable.size()>2)
                if (timeTable.get(2).isEmpty())
                    periodString2= new String[]{"-"};
                else
                    periodString2=timeTable.get(2).split("[\\(.*\\)]");
            else
                periodString2= new String[]{"-"};

            String periodString3[];
            if(timeTable.size()>3)
                if (timeTable.get(3).isEmpty())
                    periodString3= new String[]{"-"};
                else
                    periodString3=timeTable.get(3).split("[\\(.*\\)]");
            else
                periodString3= new String[]{"-"};

            String periodString4[];
            if(timeTable.size()>4)
                if (timeTable.get(4).isEmpty())
                    periodString4= new String[]{"-"};
                else
                    periodString4=timeTable.get(4).split("[\\(.*\\)]");
            else
                periodString4= new String[]{"-"};

            String periodString5[];
            if(timeTable.size()>5)
                if (timeTable.get(5).isEmpty())
                    periodString5= new String[]{"-"};
                else
                    periodString5=timeTable.get(5).split("[\\(.*\\)]");
            else
                periodString5= new String[]{"-"};

            String periodString6[];
            if(timeTable.size()>6)
                if (timeTable.get(6).isEmpty())
                    periodString6= new String[]{"-"};
                else
                    periodString6=timeTable.get(6).split("[\\(.*\\)]");
            else
                periodString6= new String[]{"-"};


            dayDate.setText(String.format(getString(R.string.table1Daydate),date,timeTable.get(0).toUpperCase()));
            period1.setText(Arrays.asList(periodString1).get(0));
            period2.setText(Arrays.asList(periodString2).get(0));
            period3.setText(Arrays.asList(periodString3).get(0));
            period4.setText(Arrays.asList(periodString4).get(0));
            period5.setText(Arrays.asList(periodString5).get(0));
            period6.setText(Arrays.asList(periodString6).get(0));
        }

        //setText for daywisetable

        if(dayWiseTable.isEmpty()){
            dayDateStatus.setText("-\n-");
            period1Status.setText("-");
            period2Status.setText("-");
            period3Status.setText("-");
            period4Status.setText("-");
            period5Status.setText("-");
            period6Status.setText("-");
        }

        else{
            String dayStatus[] = dayWiseTable.get(0).split(" ");

            String daydateStatus[] = Arrays.asList(dayStatus).get(0).split("[\\(.*\\)]");

            dayDateStatus.setText(String.format(getString(R.string.table1Daydate), Arrays.asList(daydateStatus).get(0), Arrays.asList(daydateStatus).get(1).toUpperCase()));
            if(Arrays.asList(dayStatus).size()>1) {
                period1Status.setText(Arrays.asList(dayStatus).get(1));
            }
            else{
                period1Status.setText("-");
            }
            if(Arrays.asList(dayStatus).size()>2) {
                period2Status.setText(Arrays.asList(dayStatus).get(2));
            }
            else{
                period2Status.setText("-");
            }
            if(Arrays.asList(dayStatus).size()>3) {
                period3Status.setText(Arrays.asList(dayStatus).get(3));
            }
            else{
                period3Status.setText("-");
            }
            if(Arrays.asList(dayStatus).size()>4) {
                period4Status.setText(Arrays.asList(dayStatus).get(4));
            }
            else{
                period4Status.setText("-");
            }
            if(Arrays.asList(dayStatus).size()>5) {
                period5Status.setText(Arrays.asList(dayStatus).get(5));
            }
            else{
                period5Status.setText("-");
            }
            if(Arrays.asList(dayStatus).size()>6) {
                period6Status.setText(Arrays.asList(dayStatus).get(6));
            }
            else{
                period6Status.setText("-");
            }
        }

        if(savedUrl!=null){
            websiteButton=(AppCompatButton)findViewById(R.id.websiteButton);
            websiteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ResultActivity.this, "Opening Website", Toast.LENGTH_SHORT).show();
                    Uri uriUrl = Uri.parse(savedUrl);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);                }
            });
        }

        calculatorButton=(AppCompatButton)findViewById(R.id.calculatorButton);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ResultActivity.this, CalculatorActivity.class);
                //intent.putExtra("Username",userName);
                //intent.putExtra("Password",Password);
                intent.putExtra("attended",classesAttended);
                intent.putExtra("held",classesHeld);
                startActivity(intent);
            }
        });

        syllabusButton=(AppCompatButton)findViewById(R.id.syllabus_button);
        syllabusButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Syllabus Book" ,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        notesButton=(AppCompatButton)findViewById(R.id.notes_button);
        notesButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Notes Section" ,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Task= (Content) new Content().execute();
        if(savedUrl!=null){
            Task.cancel(true);
        }
    }


    @SuppressLint("StaticFieldLeak")
    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ResultActivity.this, "Initiating Website", Toast.LENGTH_SHORT).show();
            if(!isConnected()){
                Task.cancel(true);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to the website
                Intent intent=getIntent();

                String UserName=intent.getStringExtra("Username");
                String Password=intent.getStringExtra("Password");
                String url2="https://erp.cbit.org.in/";
                Connection.Response loginForm = Jsoup.connect(url2)
                        .method(Connection.Method.GET)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0")
                        .execute();

                Document doc = loginForm.parse();
                Element e = doc.select("input[id=__VIEWSTATE]").first();
                String viewState = e.attr("value");
                e = doc.select("input[id=__EVENTVALIDATION]").first();
                String eventValidation = e.attr("value");
                e = doc.select("input[id=__LASTFOCUS]").first();
                String lastFocus = e.attr("value");
                e = doc.select("input[id=__EVENTTARGET]").first();
                String eventTarget = e.attr("value");
                e = doc.select("input[id=__VIEWSTATEGENERATOR]").first();
                String viewstateGenerator = e.attr("value");
                e = doc.select("input[id=__EVENTARGUMENT]").first();
                String eventArgument = e.attr("value");

                Document document2 = Jsoup.connect(url2)
                        .data("cookieexists", "false")
                        .data("txtUserName",UserName)
                        .data("btnNext","Next")
                        .data("__LASTFOCUS",lastFocus)
                        .data("__EVENTTARGET",eventTarget)
                        .data("__EVENTARGUMENT",eventArgument)
                        .data("__VIEWSTATEGENERATOR",viewstateGenerator)
                        .data("__VIEWSTATE", viewState)
                        .data("__EVENTVALIDATION", eventValidation)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0")
                        .get();


                Element e2 = document2.select("input[id=__VIEWSTATE]").first();
                String viewState2 = e2.attr("value");
                e2 = document2.select("input[id=__EVENTVALIDATION]").first();
                String eventValidation2 = e2.attr("value");
                e2 = document2.select("input[id=__LASTFOCUS]").first();
                String lastFocus2 = e2.attr("value");
                e2 = document2.select("input[id=__EVENTTARGET]").first();
                String eventTarget2 = e2.attr("value");
                e2 = document2.select("input[id=__VIEWSTATEGENERATOR]").first();
                String viewstateGenerator2 = e2.attr("value");
                e2 = document2.select("input[id=__EVENTARGUMENT]").first();
                String eventArgument2 = e2.attr("value");

                originalUrl = Jsoup.connect(url2)
                        .data("cookieexists", "false")
                        .data("txtPassword",Password)
                        .data("btnSubmit","Submit")
                        .data("__LASTFOCUS",lastFocus2)
                        .data("__EVENTTARGET",eventTarget2)
                        .data("__EVENTARGUMENT",eventArgument2)
                        .data("__VIEWSTATEGENERATOR",viewstateGenerator2)
                        .data("__VIEWSTATE", viewState2)
                        .data("__EVENTVALIDATION", eventValidation2)
                        .followRedirects(true)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0") //to follow redirects
                        .execute().url().toExternalForm();
            }
            catch (IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
            }
            catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            loginPrefsEditor = loginPreferences.edit();
            if(savedUrl==null){
                savedUrl=originalUrl;
                loginPrefsEditor.putString("url", savedUrl);
                loginPrefsEditor.apply();
            }
            Toast.makeText(ResultActivity.this, "Website Initiated", Toast.LENGTH_SHORT).show();
            websiteButton=(AppCompatButton)findViewById(R.id.websiteButton);
            websiteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUrl();
                }
            });
        }
    }

    public void openUrl(){
        Toast.makeText(ResultActivity.this, "Opening Website", Toast.LENGTH_SHORT).show();
        Uri uriUrl = Uri.parse(originalUrl);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public void onBackPressed() {
        if(doublePressedBackExit){
            if(rememberMe){
                SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                loginPrefsEditor.putBoolean("saveLogin", false);
                loginPrefsEditor.clear();
                loginPrefsEditor.apply();

                Task.cancel(true);
                //for remember me
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else
            {
                SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                loginPrefsEditor.putBoolean("saveLogin", false);
                loginPrefsEditor.clear();
                loginPrefsEditor.apply();

                Task.cancel(true);
                //for remember me
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else{
            this.doublePressedBackExit = true;
            Toast.makeText(getApplicationContext(), "Press again to logout", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doublePressedBackExit = false;
                }
            }, 2000);
        }
    }

    public void refresh(View v) {
        if(isConnected()){
            if(refresh){
                Task.cancel(true);
                this.finish();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else{
                Toast.makeText(ResultActivity.this, "Refresh is only available for saved login", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(ResultActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void syllabus(View v) {
        Intent intent=new Intent(ResultActivity.this, SyllabusActivity.class);
        startActivity(intent);
    }

    public void notes(View v) {
        Intent intent=new Intent(ResultActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    /*public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                return networkInfo!=null && networkInfo.isConnected();
            }
        }
        return false;
    }*/

    public boolean isConnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo!=null && networkInfo.isConnected();
    }
}