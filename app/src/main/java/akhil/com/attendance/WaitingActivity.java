package akhil.com.attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    String at,classesHeld,classesAttended;
    ArrayList<String> classesTable = new ArrayList<>();
    AppCompatTextView percentage;
    ArrayList<String> timeTable = new ArrayList<>();
    ArrayList<String> dayWiseTable = new ArrayList<>();
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

                String UserName=intent.getStringExtra("Username");
                String Password=intent.getStringExtra("Password");
                String url="https://erp.cbit.org.in/?__LASTFOCUS=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwUKMTExMDk3MjkwOA9kFgICAQ9kFgICBQ8PFgIeB1Zpc2libGVoZGRkApSadXV8hBd7qi9M9MQf24gQFo1JDdpv3rqIRSVoR5Y%3D&__VIEWSTATEGENERATOR=C2EE9ABB&__EVENTVALIDATION=%2FwEdAAUo8HF9hHYWKGGF3Et0JGNxBjpuGLkudYNkCAonVyADt%2B5PVNfdHmla7NuBu7%2FwrMNjemWCTRgEB59HPczIGVNwgWOkgugWB5Cq9dYD7toQNEwZfb2PCk9YCZQ7UhXsjSWufILYgZp8zPh7f7XDtu2a&txtUserName="+UserName+"&btnNext=Next";
                String url2="https://erp.cbit.org.in/";
                Connection.Response loginForm = Jsoup.connect(url)
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

                    Document document = Jsoup.connect(url2)
                            .data("cookieexists", "false")
                            .data("txtPassword",Password)
                            .data("btnSubmit","Submit")
                            .data("__LASTFOCUS",lastFocus)
                            .data("__EVENTTARGET",eventTarget)
                            .data("__EVENTARGUMENT",eventArgument)
                            .data("__VIEWSTATEGENERATOR",viewstateGenerator)
                            .data("__VIEWSTATE", viewState)
                            .data("__EVENTVALIDATION", eventValidation)
                            .cookies(loginForm.cookies())
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0")
                            .get();

                    Element attendance = document.getElementById("ctl00_cpStud_lblTotalPercentage");
                    at=attendance.html();
                    at=at.replaceAll("[^\\.0123456789]","");

                Element table3=document.select("table").get(19);
                Elements rows3 = table3.select("tr");
                Element row = rows3.get(rows3.size()-1);
                Elements cols = row.select("td");
                for(int i=3;i<5;i++)
                    classesTable.add(cols.get(i).text());
                System.out.println(classesTable);
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(WaitingActivity.this, at, Toast.LENGTH_LONG).show();
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
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Task.cancel(true);
        Toast.makeText(WaitingActivity.this, "Process Aborted", Toast.LENGTH_LONG).show();
        super.onBackPressed();
        this.finish();
    }
}
