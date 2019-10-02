package akhil.com.attendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class WaitingActivity extends AppCompatActivity {
    String at,at2;
    AppCompatTextView percentage;
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

                String url="https://erp.cbit.org.in/?__LASTFOCUS=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwUKMTExMDk3MjkwOA9kFgICAQ9kFgICBQ8PFgIeB1Zpc2libGVoZGRkApSadXV8hBd7qi9M9MQf24gQFo1JDdpv3rqIRSVoR5Y%3D&__VIEWSTATEGENERATOR=C2EE9ABB&__EVENTVALIDATION=%2FwEdAAUo8HF9hHYWKGGF3Et0JGNxBjpuGLkudYNkCAonVyADt%2B5PVNfdHmla7NuBu7%2FwrMNjemWCTRgEB59HPczIGVNwgWOkgugWB5Cq9dYD7toQNEwZfb2PCk9YCZQ7UhXsjSWufILYgZp8zPh7f7XDtu2a&txtUserName=160117737089&btnNext=Next";
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
                        .data("txtPassword","8055")
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
                at=attendance.text();
                at=at.replaceAll("[^\\.0123456789]","");

            } catch (IOException e) {
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
