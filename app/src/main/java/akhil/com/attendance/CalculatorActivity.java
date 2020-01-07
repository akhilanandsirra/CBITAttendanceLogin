package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputLayout;

public class CalculatorActivity extends AppCompatActivity {

    private TextInputLayout Classes_Attended,Classes_Conducted,Desired_Percentage;
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
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        Classes_Attended=(TextInputLayout) findViewById(R.id.classesAttended_TextInputLayout);
        Classes_Conducted=(TextInputLayout) findViewById(R.id.classesHeld_TextInputLayout);
        Desired_Percentage=(TextInputLayout) findViewById(R.id.desiredPercent_TextInputLayout);

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
                            if(percent==desiredpercentage){
                                result2.setText(null);
                            }

                            if (percent>desiredpercentage){
                                int i;
                                d = ((double) classattended/ (double) classconducted) * 100;
                                double z=(double) desiredpercentage;
                                for (i = 1; d >desiredpercentage; i++) {
                                    if(Math.ceil(d)==desiredpercentage)
                                        break;
                                    d = ((double) (classattended)/ (double) (classconducted + i)) * 100;
                                    bunk = i;
                                    if(Math.ceil(d)==desiredpercentage)
                                        break;
                                }
                                result2.setText(String.format(getString(R.string.result_bunk),bunk));
                            }
                            if(percent<desiredpercentage){
                                int i;
                                for (i = 1; y <=desiredpercentage; i++) {
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
