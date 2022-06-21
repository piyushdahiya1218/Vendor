package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.vendor.Classes.LanguageManager;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LanguagePreferenceActivity extends AppCompatActivity {

    String sharedprefFile="file1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences=getSharedPreferences(sharedprefFile, MODE_PRIVATE);

        //check if shared preference file exists
        //if exists, skip registration activities
        if(!sharedPreferences.getString("language","").equals("")){
            LanguageManager languageManager=new LanguageManager(LanguagePreferenceActivity.this);
            if(sharedPreferences.getString("language","").equals("English")){
                languageManager.updateResource("en");
            }
            else if(sharedPreferences.getString("language","").equals("Hindi")){
                languageManager.updateResource("hi");
            }

            Intent intent=new Intent(getApplicationContext(),CartActivity.class);
            startActivity(intent);
        }
        else{
            setContentView(R.layout.activity_language_preference);

            SharedPreferences.Editor editor=sharedPreferences.edit();

            Button nextbutton=findViewById(R.id.nextbutton);
            nextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioGroup radioGroup=findViewById(R.id.langprefradiogroup);
                    int radioid=radioGroup.getCheckedRadioButtonId();
                    if(radioid==-1){
                        Toast.makeText(LanguagePreferenceActivity.this, "Select a language\nभाषा चुनें", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        LanguageManager languageManager=new LanguageManager(LanguagePreferenceActivity.this);
                        RadioButton radioButton=findViewById(radioid);
                        if(radioButton.getText().toString().equals("English")){
                            languageManager.updateResource("en");
                            editor.putString("language", "English");
                        }
                        else{
                            languageManager.updateResource("hi");
                            editor.putString("language", "Hindi");
                        }
                        editor.apply();
                    }
                    Intent intent=new Intent(getApplicationContext(),RegistrationP1Activity.class);
                    startActivity(intent);
                }
            });

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences=getSharedPreferences("file1",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("exitapp",false)==true){
            finish();
        }
    }
}