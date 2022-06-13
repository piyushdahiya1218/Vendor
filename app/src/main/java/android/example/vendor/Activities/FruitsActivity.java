package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.vendor.Classes.Vendor;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FruitsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        //intent data from previous activity
        Intent previntent=getIntent();
        String businessname=previntent.getStringExtra("businessname");
        String username=previntent.getStringExtra("username");
        String phonenumber=previntent.getStringExtra("phonenumber");
        String producttype=previntent.getStringExtra("producttype");

        //initializations
        CheckBox apple=findViewById(R.id.potato);
        CheckBox mango=findViewById(R.id.carrot);
        CheckBox orange=findViewById(R.id.spinach);
        Button submitbutton=findViewById(R.id.submitbutton);

        ArrayList<String> fruits=new ArrayList<String>();

        //will run when apple checkbox is clicked (inside the func we'll check whether after clicking this checkbox, it is not checked or unchecked)
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if it is now checked
                if(apple.isChecked()){
                    fruits.add("Apple");        //add this element to array list
                }
                //if it is now unchecked
                else{
                    fruits.remove("Apple");     //remove this element from array list
                }
            }
        });

        //will run mango checkbox is clicked
        mango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mango.isChecked()){
                    fruits.add("Mango");
                }
                else{
                    fruits.remove("Mango");
                }
            }
        });

        //will run when orange checkbox is clicked
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orange.isChecked()){
                    fruits.add("Orange");
                }
                else{
                    fruits.remove("Orange");
                }
            }
        });

        //on clicking submit button
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(FruitsActivity.this, "Registration Completed!", Toast.LENGTH_SHORT).show();
                //IF ELSE CONDITION ONLY FOR TESTING
                if(phonenumber!=null) {
                    //vendor info collected in all registration phases is now uploaded to database
                    // flow of intent info in activities (P1->P2->P3->fruitsactivity)
                    database = FirebaseDatabase.getInstance();          //"database" is set as root node of our json tree from database
                    reference = database.getReference("vendor");        //"reference" is one of the child of this tree which was set by passing path ("vendor") of the child node
//                    Vendor vendor = new Vendor(businessname, username, phonenumber, producttype, fruits);       //new vendor object with vendor registration info which will be uploaded
//                    reference.child(phonenumber).setValue(vendor);          //reference=vendor     thus we first create a child node having phone number (which will always be unique bcz of the phone number) and then in this new child node we upload the vendor object.

                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    intent.putExtra("phonenumber", phonenumber);
                    startActivity(intent);
                }
                else{
                    //THIS CODE IS ONLY FOR TESTING REMOVE LATER
                    Intent intent=new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}