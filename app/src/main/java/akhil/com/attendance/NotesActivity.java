package akhil.com.attendance;

import androidx.appcompat.app.AppCompatActivity;
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

public class NotesActivity extends AppCompatActivity {
    AppCompatSpinner Spinner1,Spinner2;
    String spinnerText1,spinnerText2, pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

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
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(NotesActivity.this, R.array.defaultYear,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                }
                else if(position==10){
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(NotesActivity.this, R.array.pg,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                    Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerText2 = parent.getItemAtPosition(position).toString();
                            pdf=null;
                            if(spinnerText2.equals("MTech CSE")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME CIVIL")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME CAD CAM")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME THERMAL Engg.")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME EEE (PSPE)")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME ECE (Comm.Engg.)")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("ME ECE (ES & VLSI)")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("MTech IT (CNIS)")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("MCA I and II Semesters")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("MCA III and IV Semesters")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("MCA V and VI Semesters")){
                                pdf="notAvailable";
                            }

                            if(spinnerText2.equals("MBA I and II Semesters")){
                                pdf="https://drive.google.com/folderview?id=1uZ2FeEWjKsdzSMUwte1uviMoHsyajdLL";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                else{
                    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(NotesActivity.this, R.array.year,android.R.layout.simple_spinner_dropdown_item);
                    Spinner2.setAdapter(adapter);
                    Spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            spinnerText2 = parent.getItemAtPosition(position).toString();
                            pdf=null;
                            if(spinnerText1.equals("Bio-Tech – BOT")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="https://drive.google.com/folderview?id=1MPY2FkQoE_95ABOKhF9VCjyirAM24Irg";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="https://drive.google.com/folderview?id=1MPY2FkQoE_95ABOKhF9VCjyirAM24Irg";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Computer Sci. -CSE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="https://drive.google.com/folderview?id=1RYuY4gcI40ba-LcMsdKmsfgTtrkuvtw8";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }


                            if(spinnerText1.equals("Info. Tech – ITD")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="https://drive.google.com/folderview?id=1MS7wHEES8LN_bebsLIMeHY7kL5cxCo6b";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="https://drive.google.com/folderview?id=1Raixm3-n9gvXzF4MbpHvX2Uq2zi9Vu_I";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Electronics – ECE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Civil – CED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Chemical – CHE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="https://drive.google.com/folderview?id=1ETTdt3D26W65YWwjG5QKYOKYQT5RimNl";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="https://drive.google.com/folderview?id=1HLxvJdxYmCNkYW6CAfp-WzGPFLRhhuFa";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Electrical – EEE")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="https://drive.google.com/folderview?id=1mWvLcK-MPi_nRVs3RgR0Y4mEQuKjq6AH";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="https://drive.google.com/folderview?id=1X36redWYlqgIOgwSukzIYgPCgrwIWkhd";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Mechanical – MED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
                                }
                            }

                            if(spinnerText1.equals("Production – PED")){
                                if(spinnerText2.equals("Sem 1 & 2")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 3 & 4")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 5 & 6")){
                                    pdf="notAvailable";
                                }
                                if(spinnerText2.equals("Sem 7 & 8")){
                                    pdf="notAvailable";
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

    public void notesDrive(View v){
        if(v!=null){
            if(pdf!=null) {
                if (pdf.equals("notAvailable")){
                    Toast.makeText(NotesActivity.this, "Notes not available. If you wish to list your respective department notes here,\nE-mail your notes drive link to admin at akiraplaystore9999@gmail.com", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(NotesActivity.this, "Opening Drive Notes", Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf));
                    startActivity(browserIntent);
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