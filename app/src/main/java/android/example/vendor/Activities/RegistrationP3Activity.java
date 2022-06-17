package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.vendor.Classes.Vendor;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationP3Activity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_p3);

        //intent info from previous activity
        Intent previntent=getIntent();
        String businessname=previntent.getStringExtra("businessname");
        String username=previntent.getStringExtra("username");
        String phonenumber=previntent.getStringExtra("phonenumber");

        EditText getproducttype=findViewById(R.id.getothers);

        Button submitbutton=findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup=findViewById(R.id.getproducttyperadiogroup);
                int radioid=radioGroup.getCheckedRadioButtonId();       //"radioid" is different for every radio button and is -1 if none of the radio buttons is selected
                //if no option is selected
                if(radioid==-1){
                    Toast.makeText(RegistrationP3Activity.this, "Select type", Toast.LENGTH_SHORT).show();
                }
                //when an option is selected
                else{
                    RadioButton radioButton=findViewById(radioid);      //connect this new radiobutton with above selected radiobutton
                    // if fruit was selected
                    if (radioButton.getText().equals("Fruits") || radioButton.getText().equals("फल")){
                        //pass data to fruits activity
                        Intent intent=new Intent(getApplicationContext(), FruitsMenuActivity.class);
                        intent.putExtra("businessname", businessname);
                        intent.putExtra("username", username);
                        intent.putExtra("phonenumber", phonenumber);
                        intent.putExtra("producttype", "fruits");
                        startActivity(intent);

                        database=FirebaseDatabase.getInstance();
                        reference=database.getReference("vendor").child(phonenumber);
                        reference.setValue(new Vendor(businessname,username,phonenumber,"fruits",true));
                    }
                    // if vegetables was selected
                    else if (radioButton.getText().equals("Vegetables") || radioButton.getText().equals("सब्ज़ियाँ")){
                        //pass data to vegetable activity
                        Intent intent=new Intent(getApplicationContext(), VegetablesMenuActivity.class);
                        intent.putExtra("businessname", businessname);
                        intent.putExtra("username", username);
                        intent.putExtra("phonenumber", phonenumber);
                        intent.putExtra("producttype", "vegetables");
                        startActivity(intent);

                        database=FirebaseDatabase.getInstance();
                        reference=database.getReference("vendor").child(phonenumber);
                        reference.setValue(new Vendor(businessname,username,phonenumber,"vegetables",true));
                    }
                }
            }
        });


    }

    //when a radio button is clicked
//    public void onradioclick(View view){
//        RadioGroup radioGroup=findViewById(R.id.producttype);
//        int radioid=radioGroup.getCheckedRadioButtonId();
//        RadioButton radioButton=findViewById(radioid);
//        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
//    }
}