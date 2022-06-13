package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.vendor.Classes.Vendor;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VegetableActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable);

        Intent previntent=getIntent();
        String businessname=previntent.getStringExtra("businessname");
        String username=previntent.getStringExtra("username");
        String phonenumber=previntent.getStringExtra("phonenumber");
        String producttype=previntent.getStringExtra("producttype");

        CheckBox potato=findViewById(R.id.potato);
        CheckBox carrot=findViewById(R.id.carrot);
        CheckBox spinach=findViewById(R.id.spinach);

        Button submitbutton=findViewById(R.id.submitbutton);

        ArrayList<String> vegetables=new ArrayList<String>();

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(potato.isChecked()){
                    vegetables.add("Potato");
                }
                else{
                    vegetables.remove("Potato");
                }
            }
        });

        carrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carrot.isChecked()){
                    vegetables.add("Carrot");
                }
                else{
                    vegetables.remove("Carrot");
                }
            }
        });

        spinach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinach.isChecked()){
                    vegetables.add("Spinach");
                }
                else{
                    vegetables.remove("Spinach");
                }
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //submit vendor info from P1 P2 P3 to database
                database=FirebaseDatabase.getInstance();                //"database" is set as root node of our json tree from database
                reference=database.getReference("vendor");          //"reference" is one of the child of this tree which was set by passing path ("vendor") of the child node
//                Vendor vendor=new Vendor(businessname,username,phonenumber,producttype,vegetables);     //new vendor object with vendor registration info which will be uploaded
//                reference.child(phonenumber).setValue(vendor);
                //reference=vendor     thus we first create a child node having phone number (which will always be unique bcz of the phone number) and then in this new child node we upload the vendor object.

                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
            }
        });

    }
}