package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.example.vendor.Classes.Vendor;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class RegistrationP1Activity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_p1);

        EditText getbusinessname=findViewById(R.id.getbusinessname);
        EditText getusername=findViewById(R.id.getusername);
        EditText getphonenumber=findViewById(R.id.getphonenumber);
        Button nextbutton=findViewById(R.id.nextbutton);

        //when next button is clicked
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String businessname=getbusinessname.getText().toString();
                String username=getusername.getText().toString();
                String phonenumber=getphonenumber.getText().toString();

                //passing vendor registration info to next activity
                Intent intent=new Intent(getApplicationContext(), RegistrationP2Activity.class);
                intent.putExtra("businessname", businessname);
                intent.putExtra("username", username);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);

                //passing info to shared preference
                String sharedprefFile="file1";
                SharedPreferences sharedPreferences=getSharedPreferences(sharedprefFile,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("businessname",businessname);
                editor.putString("username",username);
                editor.putString("phonenumber",phonenumber);
                editor.apply();
            }
        });
    }
}