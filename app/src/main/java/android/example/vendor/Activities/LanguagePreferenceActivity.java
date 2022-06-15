package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.vendor.Classes.LanguageManager;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LanguagePreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_preference);

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
                    }
                    else{
                        languageManager.updateResource("hi");
                    }
                }
                Intent intent=new Intent(getApplicationContext(),RegistrationP1Activity.class);
                startActivity(intent);
            }
        });
    }
}