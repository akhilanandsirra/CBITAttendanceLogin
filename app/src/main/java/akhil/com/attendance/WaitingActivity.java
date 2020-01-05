package akhil.com.attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class WaitingActivity extends AppCompatActivity {
    String at,classesHeld,classesAttended,UserName,Password;
    ArrayList<String> classesTable = new ArrayList<>();
    AppCompatTextView percentage;
    ArrayList<String> timeTable = new ArrayList<>();
    ArrayList<String> dayWiseTable = new ArrayList<>();
    boolean rememberMe;
    private Content Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        percentage=(AppCompatTextView) findViewById(R.id.percent);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Task= (Content) new Content().execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to the website
                Intent intent=getIntent();

                UserName=intent.getStringExtra("Username");
                Password=intent.getStringExtra("Password");
                rememberMe=intent.getBooleanExtra("Remember",false);
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

                Document document = Jsoup.connect(url2)
                        .data("cookieexists", "false")
                        .data("txtPassword",Password)
                        .data("btnSubmit","Submit")
                        .data("__LASTFOCUS",lastFocus2)
                        .data("__EVENTTARGET",eventTarget2)
                        .data("__EVENTARGUMENT",eventArgument2)
                        .data("__VIEWSTATEGENERATOR",viewstateGenerator2)
                        .data("__VIEWSTATE", viewState2)
                        .data("__EVENTVALIDATION", eventValidation2)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0")
                        .get();

                    Element attendance = document.getElementById("ctl00_cpStud_lblTotalPercentage");
                    at=attendance.html();
                    at=at.replaceAll("[^\\.0123456789]","");

                    if(at.isEmpty()){
                        SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                        loginPrefsEditor.putBoolean("saveLogin", false);
                        loginPrefsEditor.clear();
                        loginPrefsEditor.apply();
                        throw new Exception("demo");
                    }

                Element table3=document.select("table").get(19);
                Elements rows3 = table3.select("tr");
                Element row = rows3.get(rows3.size()-1);
                Elements cols = row.select("td");
                for(int i=3;i<5;i++)
                    classesTable.add(cols.get(i).text());
                //System.out.println(classesTable);
                classesHeld=classesTable.get(0);
                classesAttended=classesTable.get(1);

                //Table1 Details

                Element timetable=document.select("table").get(17);
                Elements rows = timetable.select("tr");
                for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element rowTime = rows.get(i);
                    Elements colsTime = rowTime.select("td");
                    for(int j=0;j<colsTime.size();j++)
                        timeTable.add(colsTime.get(j).text());
                }

                //dayWise details

                Element daywisetable=document.select("table").get(24);
                Elements rows2 = daywisetable.select("tr");

                for (int i = 1; i < rows2.size(); i++) { //first row is the col names so skip it.
                    Element rowDaywise = rows2.get(i);
                    Elements colsDaywise = rowDaywise.select("tr");
                    for(int j=0;j<colsDaywise.size();j++)
                        dayWiseTable.add(colsDaywise.get(j).text());
                }

            }
            catch(NullPointerException e){

                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Password is Incorrect",Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
                finish();
            }

            catch(HttpStatusException e){

                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Username is Incorrect",Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
                finish();
            }

            catch(SocketTimeoutException e){

                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Session Timeout",Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
                finish();
            }

            catch (IOException e) {
                Toast.makeText(WaitingActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                runOnUiThread(new Runnable(){
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Percentage not available",Toast.LENGTH_LONG).show();
                    }
                });
                Task.cancel(true);
                finish();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Toast.makeText(WaitingActivity.this, at, Toast.LENGTH_LONG).show();
            //float percent=Float.valueOf(at);
            openResultativity();
        }
    }

    public void openResultativity() {
        Intent intent=new Intent(this, ResultActivity.class);
        intent.putExtra("percentage",at);
        intent.putExtra("classes1",classesHeld);
        intent.putExtra("classes2",classesAttended);
        intent.putStringArrayListExtra("timetable",timeTable);
        intent.putStringArrayListExtra("daywisetable",dayWiseTable);

        intent.putExtra("Username",UserName);
        intent.putExtra("Password",Password);
        intent.putExtra("Remember",rememberMe);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Task.cancel(true);
        Toast.makeText(WaitingActivity.this, "Process Aborted", Toast.LENGTH_LONG).show();
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Task.cancel(true);
        this.finish();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Task.cancel(true);
        this.finish();
    }

}
