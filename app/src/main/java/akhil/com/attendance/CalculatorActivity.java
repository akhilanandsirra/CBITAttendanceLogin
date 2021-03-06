package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputLayout;
import com.john.waveview.WaveView;

public class CalculatorActivity extends AppCompatActivity {

    private TextInputLayout Classes_Attended,Classes_Conducted,Desired_Percentage;
    WaveView waveView;
    private AppCompatEditText editClasses_Attended,editClasses_Conducted,editDesired_Percentage;
    AppCompatButton CalculateButton;
    private AppCompatTextView result,result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        findViewById(R.id.calculate_activity).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View focusedView = getCurrentFocus();
                if(v!=null&&focusedView!=null){
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return true;
            }
        });

        Classes_Attended=(TextInputLayout) findViewById(R.id.classesAttended_TextInputLayout);
        Classes_Conducted=(TextInputLayout) findViewById(R.id.classesHeld_TextInputLayout);
        Desired_Percentage=(TextInputLayout) findViewById(R.id.desiredPercent_TextInputLayout);

        editClasses_Attended=(AppCompatEditText) findViewById(R.id.classesAttended_TextField);
        editClasses_Conducted=(AppCompatEditText) findViewById(R.id.classesHeld_TextField);
        editDesired_Percentage=(AppCompatEditText) findViewById(R.id.desiredPercent_TextField);

        Intent intent = getIntent();
        String conducted=intent.getStringExtra("attended");
        String Held=intent.getStringExtra("held");

        editClasses_Conducted.setText(Held);
        editClasses_Attended.setText(conducted);
        editDesired_Percentage.setText(getString(R.string.defaultPercentage));

        result=(AppCompatTextView)findViewById(R.id.resultView);
        result2=(AppCompatTextView)findViewById(R.id.resultView2);


        CalculateButton=(AppCompatButton)findViewById(R.id.calculate_button);
        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classAttended=Classes_Attended.getEditText().getText().toString().trim();
                String classConducted=Classes_Conducted.getEditText().getText().toString().trim();
                String desiredPercentage=Desired_Percentage.getEditText().getText().toString().trim();
                long classattended,classconducted,desiredpercentage;
                if(classAttended.isEmpty()||classConducted.isEmpty()||desiredPercentage.isEmpty())
                {
                    result.setText(getString(R.string.errorInput));
                    result2.setText(null);
                }
                else if(classConducted.equals("0")){
                    result.setText(getString(R.string.errorHeld));
                    result2.setText(null);
                }
                else
                {
                    classattended=Long.parseLong(classAttended);
                    classconducted=Long.parseLong(classConducted);
                    desiredpercentage=Long.parseLong(desiredPercentage);
                    double percent = ((double) classattended / (double) classconducted) * 100;
                    long samepercent=(classattended/classconducted)*100;
                    int attend = 0,bunk=0;
                    double y=0;
                    double d=0;
                    if(classattended<=classconducted&&desiredpercentage<100&&desiredpercentage>0) {
                        if(classattended>5000000||classconducted>5000000)
                        {
                            result.setText(getString(R.string.sizeError));
                            result2.setText(null);
                        }
                        else {
                            result.setText(String.format(getString(R.string.result_percent), percent));
                            waveView = (WaveView) findViewById(R.id.wave_view);
                            ProgressBarAnimation anim = new ProgressBarAnimation(waveView, 0, (float)percent);
                            anim.setDuration(1000);
                            waveView.startAnimation(anim);
                            if(percent==desiredpercentage){
                                result2.setText(null);
                            }

                            if (percent>desiredpercentage){
                                int i;
                                d = ((double) classattended/ (double) classconducted) * 100;
                                double z=(double) desiredpercentage;
                                for (i = 0; d >desiredpercentage; i++) {
                                    if(Math.ceil(d)==desiredpercentage)
                                        break;
                                    d = ((double) (classattended)/ (double) (classconducted + i)) * 100;
                                    bunk = i;
                                    if(d<desiredpercentage){
                                        bunk=bunk-1;
                                        break;}
                                    if(Math.ceil(d)==desiredpercentage)
                                        break;
                                }
                                result2.setText(String.format(getString(R.string.result_bunk),bunk));
                            }
                            if(percent<desiredpercentage){
                                int i;
                                for (i = 0; y <=desiredpercentage; i++) {
                                    if(y==desiredpercentage)
                                        break;
                                    y = ((double)(classattended + i )/ (double) (classconducted + i)) * 100;
                                    attend = i;
                                    if(y==desiredpercentage)
                                        break;
                                }
                                result2.setText(String.format(getString(R.string.result_attend),attend));
                            }
                        }

                    }
                    else {
                        if(desiredpercentage==100&&classattended!=classconducted)
                        {
                            result.setText(getString(R.string.errorHundred1));
                            result2.setText(getString(R.string.errorHundred2));
                        }
                        if(desiredpercentage==100&&classattended==classconducted){
                            result.setText(String.format(getString(R.string.result_percent), percent));
                            result2.setText(null);
                        }
                        if(desiredpercentage>100||desiredpercentage==0)
                        {
                            result.setText(R.string.errorpercent);
                            result2.setText(R.string.errorpercent2);
                        }
                        if(classattended>classconducted){
                            result.setText(getString(R.string.errorNumber));
                            result2.setText(getString(R.string.errorNumber2));}
                    }
                }
            }
        });
    }
}
