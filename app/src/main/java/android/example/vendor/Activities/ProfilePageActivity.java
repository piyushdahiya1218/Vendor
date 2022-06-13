package android.example.vendor.Activities;

import android.content.Intent;
import android.example.vendor.Classes.Vendor;
import android.example.vendor.R;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePageActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        TextView businessnametextview=findViewById(R.id.businessname);
        TextView usernametextview=findViewById(R.id.username);
        TextView phonenumbertextview=findViewById(R.id.phonenumber);
        TextView producttypetextview=findViewById(R.id.producttype);
        TextView productlisttextview=findViewById(R.id.products);

        //get intent data from previous activity
        Intent intent=getIntent();
        String phonenumber=intent.getStringExtra("phonenumber");

        //vendor object data is extracted for the particular phonenumber (number that the user has currently registered with)
        //After getting the vendor object from firebase, values are set
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("vendor").child(phonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Vendor vendor=snapshot.getValue(Vendor.class);
                    if(vendor!=null){
                        businessnametextview.setText(vendor.getBusinessname());
                        usernametextview.setText(vendor.getUsername());
                        phonenumbertextview.setText(vendor.getPhonenumber());
                        producttypetextview.setText(vendor.getProducttype());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get list of products for particular vendor
        reference=database.getReference("vendorcart").child(phonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String productsliststring=null;
                    for(DataSnapshot vendorcart : snapshot.getChildren()){
                        if(vendorcart!=null){
                            if(productsliststring==null){
                                productsliststring=vendorcart.child("productnameeng").getValue().toString();
                            }
                            else{
                                productsliststring = productsliststring + ", " + vendorcart.child("productnameeng").getValue().toString();
                            }
                        }
                    }
                    productlisttextview.setText(productsliststring);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}