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

public class VegetablesMenuActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Product> allproductslist=new ArrayList<Product>();
    private RecyclerView recyclerView;
    String phonenumber=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables_menu);

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

        recyclerView=findViewById(R.id.allproductsrecyclerviewveg);
        setAdapter();

        Button confirmbutton=findViewById(R.id.cartbutton1veg);
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
                    Toast.makeText(VegetablesMenuActivity.this, "Select atleast 1 item", Toast.LENGTH_SHORT).show();
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

    private void setAdapter() {
        recyclerAdapterforMenu adapter=new recyclerAdapterforMenu(allproductslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void setproductslist() {
        allproductslist.add(new Product("Asparagus","एस्परैगस",getResources().getIdentifier("asparagus","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Beetroot","चुकंदर",getResources().getIdentifier("beetroot","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Bitter Gourd","करेला",getResources().getIdentifier("bittergourd","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Bottle Gourd","लौकी",getResources().getIdentifier("bottlegourd","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Brinjal","बैंगन",getResources().getIdentifier("brinjal","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Broccoli","ब्रॉकली",getResources().getIdentifier("broccoli","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Cherry Tomato","चेरी टमाटर",getResources().getIdentifier("cherrytomato","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Chilly","मिर्च",getResources().getIdentifier("chilly","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Coriander","धनिया",getResources().getIdentifier("coriander","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Corn","मक्का",getResources().getIdentifier("corn","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Cucumber","खीरा",getResources().getIdentifier("cucumber","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Drumsticks","सहजन",getResources().getIdentifier("drumsticks","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Fenugreek","मेंथी",getResources().getIdentifier("fenugreek","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Garlic","लहसुन",getResources().getIdentifier("garlic","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Tinda","टिंडा",getResources().getIdentifier("tinda","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Jackfruit","कटहल",getResources().getIdentifier("jackfruit","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Kale","गोभी",getResources().getIdentifier("kale","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Lettuce","सलाद पत्ता",getResources().getIdentifier("lettuce","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Lotus Stem","कमल का तना",getResources().getIdentifier("lotusstem","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Olive","जैतून",getResources().getIdentifier("olive","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Onion","प्याज़",getResources().getIdentifier("onion","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Peas","मटर",getResources().getIdentifier("peas","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Potato","आलू",getResources().getIdentifier("potato","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Pumpkin","कद्दू",getResources().getIdentifier("pumpkin","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Red Bell Pepper","लाल शिमला मिर्च",getResources().getIdentifier("redbellpepper","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Ridge Gourd","तोरई",getResources().getIdentifier("ridgegourd","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Saag Bathua","साग बथुआ",getResources().getIdentifier("saagbathua","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Snake Cucumber","नाग खीरा",getResources().getIdentifier("snakecucumber","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Sweet Potato","शकरकंद",getResources().getIdentifier("sweetpotato","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Tomato","टमाटर",getResources().getIdentifier("tomato","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Turnip","शलजम",getResources().getIdentifier("turnip","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("White Radish","मूली",getResources().getIdentifier("whiteradish","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Yellow Bell Pepper","पीली बेल मिर्च",getResources().getIdentifier("yellowbellpepper","drawable",getPackageName()),10,1,false,"vegetables"));

        allproductslist.add(new Product("Beans","फलियां",getResources().getIdentifier("beans","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Cabbage","पत्ता गोभी",getResources().getIdentifier("cabbage","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Carrot","गाजर",getResources().getIdentifier("carrot","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Cauliflower","फूलगोभी",getResources().getIdentifier("cauliflower","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Ladyfinger","भिन्डी",getResources().getIdentifier("ladyfinger","drawable",getPackageName()),10,1,false,"vegetables"));
        allproductslist.add(new Product("Spinach","पालक",getResources().getIdentifier("spinach","drawable",getPackageName()),10,1,false,"vegetables"));
    }
}