package android.example.vendor.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.vendor.Adapters.recyclerAdapterforCart;
import android.example.vendor.Classes.Product;
import android.example.vendor.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Product> cartproductslist =new ArrayList<>();
    private RecyclerView recyclerView;
    String phonenumber=null;
    String language=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        SharedPreferences sharedPreferences=getSharedPreferences("file1",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        language=sharedPreferences.getString("language","");

        if(sharedPreferences.getString("phonenumber","").equals("")){
            //intent info from previous activity
            Intent previntent=getIntent();
            phonenumber=previntent.getStringExtra("phonenumber");
        }
        else{
            phonenumber=sharedPreferences.getString("phonenumber","");
        }

        recyclerView=findViewById(R.id.recyclerview);

        //we extract vendorcart from firebase
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("vendorcart").child(phonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    cartproductslist.clear();
                    for(DataSnapshot vendorcart : snapshot.getChildren()){
                        if(vendorcart!=null){
                            cartproductslist.add(vendorcart.getValue(Product.class));
                        }
                    }
                    //if arraylist has items, then set them to recyclerview adapter
                    if(cartproductslist !=null){
                        setAdapter();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ToggleButton toggleButton=findViewById(R.id.toggleButton);
        toggleButton.setChecked(sharedPreferences.getBoolean("togglebutton",true));
        //when toggle button is clicked
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reference=database.getReference("vendor").child(phonenumber);
                if (isChecked){
                    reference.child("active").setValue(true);
                    editor.putBoolean("togglebutton",true);
                }
                else{
                    reference.child("active").setValue(false);
                    editor.putBoolean("togglebutton",false);
                }
                editor.apply();
            }
        });

        //when edit cart button is clicked, go back to menu page so that they can add remove items
        Button editcartbutton=findViewById(R.id.editcartbutton);
        editcartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("cartfirsttime",false);
                editor.apply();

                if(sharedPreferences.getString("producttype","").equals("fruits")){
                    Intent intent=new Intent(getApplicationContext(),FruitsMenuActivity.class);
                    startActivity(intent);
                }
                else if(sharedPreferences.getString("producttype","").equals("vegetables")){
                    Intent intent=new Intent(getApplicationContext(),VegetablesMenuActivity.class);
                    startActivity(intent);
                }


//                if(sharedPreferences.getBoolean("cartfirsttime",true)==true){
//                    editor.putBoolean("cartfirsttime",false);
//                    editor.apply();
//                    finish();
//                }
//                else if(sharedPreferences.getBoolean("cartfirsttime",true)==false){
//                }
            }
        });

        //when profile button is clicked, open Profile page
        Button profilebutton=findViewById(R.id.profilebutton);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileintent=new Intent(getApplicationContext(), ProfilePageActivity.class);
                profileintent.putExtra("phonenumber", phonenumber);
                startActivity(profileintent);
            }
        });

        //when home button is clicked, open Home page
        Button homebutton=findViewById(R.id.homebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=FirebaseDatabase.getInstance();
                reference=database.getReference("vendorcart");
                reference.child(phonenumber).setValue(cartproductslist);

                cartproductslist.clear();

                Intent homeintent=new Intent(getApplicationContext(), HomePageActivity.class);
                homeintent.putExtra("phonenumber", phonenumber);
                homeintent.putExtra("currentlang",ConfigurationCompat.getLocales(getResources().getConfiguration()).get(0).getDisplayLanguage());
                startActivity(homeintent);
            }
        });
    }

    //this method is called to set the data from our arraylist to recycler view adapter
    private void setAdapter() {
        recyclerAdapterforCart adapter=new recyclerAdapterforCart(cartproductslist);       //passing arraylist and hashmap as argument so that adapter can map every item in arraylist with its price using hashmap
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!language.equals("")){
            SharedPreferences sharedPreferences=getSharedPreferences("file1",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("exitapp",true);
            editor.apply();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(cartproductslist.size()==0){
            Log.i("empty","empty");
            finish();
            startActivity(getIntent());
        }
        else{
            Log.i("not empty","not empty");
            for(int i=0;i<cartproductslist.size();i++){
                Log.i("names "+String.valueOf(i),cartproductslist.get(i).getProductnameeng());
            }
        }
    }
}