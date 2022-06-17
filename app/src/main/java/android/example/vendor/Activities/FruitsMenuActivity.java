package android.example.vendor.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_with_pics);

        //intent info from previous activity
        Intent previntent=getIntent();
        String phonenumber=previntent.getStringExtra("phonenumber");

        setproductslist();

        recyclerView=findViewById(R.id.allproductsrecyclerview);
        setAdapter();

        Button cartbutton=findViewById(R.id.cartbutton1);
        cartbutton.setOnClickListener(new View.OnClickListener() {
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
                        productArrayList.add(new Product(productnameeng,productnamehindi,imageRid,productprice,productquantity,producttype));
                    }
                }
                if(productArrayList.isEmpty()){
                    Toast.makeText(FruitsMenuActivity.this, "Select atleast 1 item", Toast.LENGTH_SHORT).show();
                }
                else{
                    database=FirebaseDatabase.getInstance();
                    reference=database.getReference("vendorcart");
                    reference.child(phonenumber).setValue(productArrayList);

                    Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                    intent.putExtra("phonenumber",phonenumber);
                    startActivity(intent);
                }
            }
        });

    }

    private void setproductslist() {
        allproductslist.add(new Product("Grapes","अंगूर",getResources().getIdentifier("grapes","drawable",getPackageName()),10,1,false,"fruits"));
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