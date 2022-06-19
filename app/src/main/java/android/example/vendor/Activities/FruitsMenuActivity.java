package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.example.vendor.Adapters.recyclerAdapterforMenu;
import android.example.vendor.Classes.Product;
import android.example.vendor.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FruitsMenuActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Product> allproductslist=new ArrayList<Product>();
    private RecyclerView recyclerView;
    String phonenumber=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_menu);

        SharedPreferences sharedPreferences=getSharedPreferences("file1",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        if(sharedPreferences.getString("phonenumber","").equals("")){
            //intent info from previous activity
            Intent previntent=getIntent();
            phonenumber=previntent.getStringExtra("phonenumber");
        }
        else{
            phonenumber=sharedPreferences.getString("phonenumber","");
        }

        setproductslist();

        recyclerView=findViewById(R.id.allproductsrecyclerview);

        setAdapter();

        Button confirmbutton=findViewById(R.id.cartbutton1);
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> productArrayList =new ArrayList<Product>();
                for(int i=0;i<allproductslist.size();i++){
                    if(allproductslist.get(i).isSelected()){
                        String productnameeng=allproductslist.get(i).getProductnameeng();
                        String productnamehindi=allproductslist.get(i).getProductnamehindi();
                        int productprice=allproductslist.get(i).getPrice();
                        int productquantity=allproductslist.get(i).getQuantity();
                        int imageRid=allproductslist.get(i).getImageRid();
                        String producttype=allproductslist.get(i).getProducttype();
                        productArrayList.add(new Product(productnameeng,productnamehindi,imageRid,productprice,productquantity,true,producttype));
                    }
                }
                if(productArrayList.isEmpty()){
                    Toast.makeText(FruitsMenuActivity.this, "Select atleast 1 item", Toast.LENGTH_SHORT).show();
                }
                else{
                    database=FirebaseDatabase.getInstance();
                    reference=database.getReference("vendorcart");
                    reference.child(phonenumber).setValue(productArrayList);

                    if(sharedPreferences.getBoolean("menufirsttime",true)==true || sharedPreferences.getBoolean("cartfirsttime",true)==true){
                        Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                        intent.putExtra("phonenumber",phonenumber);
                        startActivity(intent);

                        editor.putBoolean("menufirsttime",false);
                        editor.apply();
                    }
                    else if(sharedPreferences.getBoolean("menufirsttime",true)==false && sharedPreferences.getBoolean("cartfirsttime",true)==false){
                        finish();
                    }
                }
            }
        });

    }

    private void setproductslist() {
        allproductslist.add(new Product("Apricot","खुबानी",getResources().getIdentifier("apricot","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Avocado","एवोकाडो",getResources().getIdentifier("avocado","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Blueberry","ब्लूबेरी",getResources().getIdentifier("blueberry","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Coconut","नारियल",getResources().getIdentifier("coconut","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Custard Apple","शरीफा",getResources().getIdentifier("custardapple","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Date","खजूर",getResources().getIdentifier("date","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Dragon Fruit","ड्रैगन फल",getResources().getIdentifier("dragonfruit","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Grape Fruit","चकोतरा",getResources().getIdentifier("grapefruit","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Grapes","अंगूर",getResources().getIdentifier("grapes","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Guava","अमरूद",getResources().getIdentifier("guava","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Kiwi","कीवी",getResources().getIdentifier("kiwi","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Lemon","नींबू",getResources().getIdentifier("lemon","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Lychee","लीची",getResources().getIdentifier("lychee","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Mandarin","मंदारिन फल",getResources().getIdentifier("mandarin","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Mango","आम",getResources().getIdentifier("mango","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Melon","खरबूज",getResources().getIdentifier("melon","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Mulberry","शहतूत",getResources().getIdentifier("mulberry","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Muskmelon","खरबूजा",getResources().getIdentifier("muskmelon","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Papaya","पपीता",getResources().getIdentifier("papaya","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Peach","आडू",getResources().getIdentifier("peach","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Pear","नाशपाती",getResources().getIdentifier("pear","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Pineapple","अनानास",getResources().getIdentifier("pineapple","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Plum","आलूबुखारा",getResources().getIdentifier("plum","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Pomegranate","अनार",getResources().getIdentifier("pomegranate","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Quince","श्रीफल",getResources().getIdentifier("quince","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Raspberry","रसभरी",getResources().getIdentifier("raspberry","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Starfruit","स्टार फल",getResources().getIdentifier("starfruit","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Strawberry","स्ट्रॉबेरी",getResources().getIdentifier("strawberry","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Sweet Lime","मीठा नींबू",getResources().getIdentifier("sweetlime","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Tendor Coconut","कच्चा नारियल",getResources().getIdentifier("tendorcoconut","drawable",getPackageName()),10,1,false,"fruits"));

        allproductslist.add(new Product("Black Grapes","काले अंगूर",getResources().getIdentifier("blackgrapes","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Apple","सेब",getResources().getIdentifier("apple","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Orange","संतरा",getResources().getIdentifier("orange","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Banana","केला",getResources().getIdentifier("banana","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Cherry","चेरी",getResources().getIdentifier("cherry","drawable",getPackageName()),10,1,false,"fruits"));
        allproductslist.add(new Product("Watermelon","तरबूज",getResources().getIdentifier("watermelon","drawable",getPackageName()),10,1,false,"fruits"));
    }

    private void setAdapter() {
        recyclerAdapterforMenu adapter=new recyclerAdapterforMenu(allproductslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}