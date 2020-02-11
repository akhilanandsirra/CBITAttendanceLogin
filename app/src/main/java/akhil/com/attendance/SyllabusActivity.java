package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SyllabusActivity extends AppCompatActivity{
    AppCompatSpinner Spinner1,Spinner2;
    String spinnerText1,spinnerText2, pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Spinner1=(AppCompatSpinner) findViewById(R.id.year);
        Spinner2=(AppCompatSpinner) findViewById(R.id.semester);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.branch,android.R.layout.simple_spinner_dropdown_item);
        Spinner1.setAdapter(adapter);
        Spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerText1 = parent.getItemAtPosition(position).toString();
                if(position==0){
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SyllabusActivity.this, R.array.defaultYear,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                }
                else if(position==10){
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SyllabusActivity.this, R.array.pg,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                    Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerText2 = parent.getItemAtPosition(position).toString();
                            pdf=null;
                            if(spinnerText2.equals("MTech CSE")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MTech-CSE-Model-Curriculum-07-12-2019-1.pdf";
                            }

                            if(spinnerText2.equals("ME CIVIL")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Civil-PG-AICTE-MODEL-7.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("ME CAD CAM")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ME-MODEL-CURRICULUM-CAD-CAM-I-to-IV-sem-7.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("ME THERMAL Engg.")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ME-MODEL-CURRICULUM-THERMAL-ENGG-I-to-IV-sem-7.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("ME EEE (PSPE)")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MEpspe-syllabus-book_updated_02012020.pdf";
                            }

                            if(spinnerText2.equals("ME ECE (Comm.Engg.)")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ME-ECE-CE-Syllabus-7.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("ME ECE (ES & VLSI)")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ME-ECE-ES-VLSID-Syllabus-7.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("MTech IT (CNIS)")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MTech-CNIS-AICTE-MC-Scheme-and-Syllabus-07.12.2019-1.pdf";
                            }

                            if(spinnerText2.equals("MCA I and II Semesters")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MCA_CBCS_MCA_I_II-Semesters-Syllabus2020.pdf";
                            }

                            if(spinnerText2.equals("MCA III and IV Semesters")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MCA_CBCS_MCA_III_IV-Semesters-Syllabus_Jan2020.pdf";
                            }

                            if(spinnerText2.equals("MCA V and VI Semesters")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MCASyll_0.pdf";
                            }

                            if(spinnerText2.equals("MBA I and II Semesters")){
                                pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MBA-I-II-Sem-Syllabus-2019-2020.pdf";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else{
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(SyllabusActivity.this, R.array.year,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                    Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerText2 = parent.getItemAtPosition(position).toString();
                            pdf=null;
                            if(spinnerText1.equals("Bio-Tech – BOT")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/Biotech_1.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BIOTECH-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Biotech_5th6thsem.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Bio-Tech-CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }

                            if(spinnerText1.equals("Computer Sci. -CSE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-CSE-MC-23-01-2020.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-CSE-MC-23-01-2020.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/CSESyllabus-Vsem.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/CSE-CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }


                            if(spinnerText1.equals("Info. Tech – ITD")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/IT_0.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/IT-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/ITSyll.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/IT-CBCS-VII-VIII-Syllabus-2019-05-09.pdf";
                                }
                            }

                            if(spinnerText1.equals("Electronics – ECE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/ECE_0.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ECE-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/ECESyll.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/ECE-CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }

                            if(spinnerText1.equals("Civil – CED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/Civil_0.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/CIVIL-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/civilsylfinal.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Civil-CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }

                            if(spinnerText1.equals("Chemical – CHE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/Chemical_1.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/CHEMICAL-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/CamichalSyll_0.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Chemical-CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }

                            if(spinnerText1.equals("Electrical – EEE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/EEE_0.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/EEE-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/Syllabus_EEE_5th-6th-sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/EEE_CBCS-Syllabus-VII-VIII-Semesters-2019-20.pdf";
                                }
                            }

                            if(spinnerText1.equals("Mechanical – MED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/Mech.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/MECH-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-5-and-6-SEM-MECH-2018-19-CBCS.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-CBCS-MECH-VII-and-VIII-sem-Scheme-and-Syllabus-MODIFIED.pdf";
                                }
                            }

                            if(spinnerText1.equals("Production – PED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="http://www.cbit.ac.in//wp-content/uploads/2019/04/Prod_1.pdf";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/PROD-MODEL-CURRICULUM-III-Sem-to-IV-Sem.pdf";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-5-and-6-SEM-PROD-2018-19-CBCS.pdf";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="http://www.cbit.ac.in/wp-content/uploads/2019/04/BE-CBCS-PROD-VII-and-VIII-sem-Scheme-and-Syllabus-MODIFIED.pdf";
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void syllabusPDF(View v){
        if(v!=null){
            if(pdf!=null) {
                try{
                    Toast.makeText(SyllabusActivity.this, "Opening Syllabus", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setDataAndType(Uri.parse(pdf), "application/pdf");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("http://docs.google.com/gview?embedded=true&url=" + pdf), "text/html");
                    startActivity(intent);
                }
            }
        }
    }


    //public void syllabus(View v) {
        /*
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("http://docs.google.com/gview?embedded=true&url=" + pdf), "text/html");
        startActivity(intent);*/
    //}
}