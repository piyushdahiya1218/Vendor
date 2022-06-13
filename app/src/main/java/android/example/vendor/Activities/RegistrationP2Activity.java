package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationP2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_p2);

        EditText getAadhar=findViewById(R.id.getaadhar);
        Button submitaadharbutton=findViewById(R.id.submitaadharbutton);
        TextView enteropttextview=findViewById(R.id.enteropttextview);
        EditText getotp=findViewById(R.id.getotp);
        Button submitotpbutton=findViewById(R.id.submitotpbutton);

        //otp elements initially invisible
        enteropttextview.setVisibility(View.INVISIBLE);
        getotp.setVisibility(View.INVISIBLE);
        submitotpbutton.setVisibility(View.INVISIBLE);

        //when submit aadhar button is clicked
        submitaadharbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadharnumber=getAadhar.getText().toString();
                //aadhar number is of 12 digits
                //check if number is 12 digits
                if(aadharnumber.length()<12){
                    Toast.makeText(RegistrationP2Activity.this, "Enter correct Aadhar number", Toast.LENGTH_SHORT).show();
                    getAadhar.setText(null);
                }
                //aadhar number correct
                else{
                    //to hide keyboard after submit aadhar button is clicked
                    InputMethodManager imm=(InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view=getCurrentFocus();
                    imm.hideSoftInputFromWindow(view.getWindowToken(),0);

                    //now otp elements are visible
                    enteropttextview.setVisibility(View.VISIBLE);
                    getotp.setVisibility(View.VISIBLE);
                    submitotpbutton.setVisibility(View.VISIBLE);

                    //when submit otp button is clicked
                    submitotpbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String otp=getotp.getText().toString();
                            //if otp not entered
                            if(otp.isEmpty()){
                                Toast.makeText(RegistrationP2Activity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                            }
                            //otp enetered
                            else{
                                //when otp is correct
                                if(otp.equals("12345")){
                                    Toast.makeText(RegistrationP2Activity.this, "Correct otp", Toast.LENGTH_SHORT).show();

                                    //get intent information from previous activity
                                    Intent previntent=getIntent();
                                    String businessname=previntent.getStringExtra("businessname");
                                    String username=previntent.getStringExtra("username");
                                    String phonenumber=previntent.getStringExtra("phonenumber");

                                    //create new intent and pass information from previous activity to this new activity
                                    Intent intent=new Intent(getApplicationContext(), RegistrationP3Activity.class);
                                    intent.putExtra("businessname", businessname);
                                    intent.putExtra("username", username);
                                    intent.putExtra("phonenumber", phonenumber);
                                    startActivity(intent);
                                }
                                //when otp is not correct
                                else{
                                    Toast.makeText(RegistrationP2Activity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                                    getotp.setText(null);
                                }
                            }
                        }
                    });
                }
            }
        });


        //FOR TESTING
        Button testbutton=findViewById(R.id.testbutton);
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previntent=getIntent();
                String businessname=previntent.getStringExtra("businessname");
                String username=previntent.getStringExtra("username");
                String phonenumber=previntent.getStringExtra("phonenumber");

                Intent intent=new Intent(getApplicationContext(), RegistrationP3Activity.class);
                intent.putExtra("businessname", businessname);
                intent.putExtra("username", username);
                intent.putExtra("phonenumber", phonenumber);
                startActivity(intent);
            }
        });
    }
}