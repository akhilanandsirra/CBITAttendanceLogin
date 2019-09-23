package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.john.waveview.WaveView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class WaitingActivity extends AppCompatActivity {
    String at;
    WaveView waveView;
    AppCompatTextView percentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        percentage=(AppCompatTextView) findViewById(R.id.percent);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        new Content().execute();


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
                Document document = Jsoup.connect("https://erp.cbit.org.in/?__LASTFOCUS=&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwUKMTExMDk3MjkwOA9kFgICAQ9kFhQCBQ8PFgIeB1Zpc2libGVoZGQCCQ8PFgIfAGdkZAILDw8WBB4ISW1hZ2VVcmwFRH5cRVJQXEFkbWluXFN0dWRlbnRQaG90b3MuYXNweD9TdHVkSWQ9NzUwMiZDb2xDb2RlPTAwMDEmR3JwQ29kZT1DQklUHwBnZGQCDw8PFgYeBFRleHQFDDE2MDExNzczNzA4OR4HRW5hYmxlZGgfAGhkZAIRDw8WAh8AZ2RkAhMPDxYCHwJlZGQCFQ8PFgIfAGhkZAIXDw8WAh8AZ2RkAhsPDxYCHwBnZGQCHQ8PFgIfAGdkZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAgUHSW1nQmFjawUMSW1nVXNlclBob3RvK%2BH5kGAx0PSv8AMiMnYMoI4uyXiMiYM6TasaCds24ME%3D&__VIEWSTATEGENERATOR=C2EE9ABB&__EVENTVALIDATION=%2FwEdAAm3b5ZXqJt8X%2FJx%2BD4jDeHOBjpuGLkudYNkCAonVyADt%2B5PVNfdHmla7NuBu7%2FwrMMyrNXnRHAQ0i9tgVPzigLD0vlayIibJlZm2HEdhP9alnY2%2BMc6SrnAqio3oCKbxYY85pbWlDO2hADfoPXD%2F5tdKHfjJrmhuOJtNRn2ZOc35Gp8QtNk8V97ULE6OJFh19NI2AIBKsDmJMvEpIUF8lCQZyBbrZ31FlP1tUsnDSExTg%3D%3D&txtPassword=8055&btnSubmit=Submit").get();
                Element attendance = document.getElementById("ctl00_cpStud_lblTotalPercentage");
                at=attendance.text();
                at=at.replaceAll("[^\\.0123456789]","");


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(WaitingActivity.this, at, Toast.LENGTH_LONG).show();
            float percent=Float.valueOf(at);
            openResultativity();
            //percentage.setText(at);
        }
    }

    public void openResultativity() {
        Intent intent=new Intent(this, ResultActivity.class);
        intent.putExtra("percentage",at);
        startActivity(intent);
    }
}
