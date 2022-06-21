package android.example.vendor.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.example.vendor.Adapters.recyclerAdapterforOrderRequest;
import android.example.vendor.Classes.CustomerCart;
import android.example.vendor.Classes.Product;
import android.example.vendor.Classes.RequestFrom;
import android.example.vendor.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderRequestActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    FirebaseDatabase database;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    String customerphonenumber=null;
    String vendorphonenumber=null;
    Marker customermarker;
    Marker vendormarker;
    CustomerCart customerCart;
    boolean customerfirsttime =true;
    boolean vendorfirsttime=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request);

        Intent previntent=getIntent();
        customerphonenumber=previntent.getStringExtra("customerphonenumber");
        Log.i(customerphonenumber,customerphonenumber);
        vendorphonenumber=previntent.getStringExtra("vendorphonenumber");
        String currentlang=previntent.getStringExtra("currentlang");

        database=FirebaseDatabase.getInstance();

        //activity wont run and will be returned to previous activity if "accepted==0"
        reference=database.getReference("vendorrequests").child(vendorphonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("accepted").getValue(String.class).equals("0")){
                        Toast.makeText(OrderRequestActivity.this, "Request declined", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        

        // Obtain the SupportMapFragment and get notified when the map is ready to be used. When map is ready to be used, "onMapReady" function will be called
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerView=findViewById(R.id.orderrequestrecyclerview);

        //get customer cart list and set adapter
        reference=database.getReference("customercart").child(customerphonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    customerCart=snapshot.getValue(CustomerCart.class);
                    setAdapter(customerCart.getProducts());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //when clicked on Accept
        Button acceptbutton=findViewById(R.id.acceptbutton);
        if(currentlang.equals("Hindi")){
            acceptbutton.setText("स्वीकार");
        }
        else{
            acceptbutton.setText("Accept");
        }
        acceptbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference=database.getReference("vendorrequests").child(vendorphonenumber);
                RequestFrom requestFrom=new RequestFrom(customerphonenumber,"1");
                reference.setValue(requestFrom);

                //finish() bcz this same activity is opening when making changes to firebase. So finish() to nullify the effect
//                finish();

                reference=database.getReference("customerlocation").child(customerphonenumber);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String customerlat=String.valueOf(snapshot.child("latitude").getValue(Double.class));
                            String customerlng=String.valueOf(snapshot.child("longitude").getValue(Double.class));
                            Intent mapintent=new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+customerlat+","+customerlng+"&mode=w"));
                            mapintent.setPackage("com.google.android.apps.maps");
                            if(mapintent.resolveActivity(getPackageManager())!=null){
                                startActivity(mapintent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //when clicked on Declined
        Button declinebutton=findViewById(R.id.declinebutton);
        if(currentlang.equals("Hindi")){
            declinebutton.setText("अस्वीकार");
        }
        else{
            declinebutton.setText("Decline");
        }
        declinebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference=database.getReference("vendorrequests").child(vendorphonenumber);
                RequestFrom requestFrom=new RequestFrom(customerphonenumber,"0");
                reference.setValue(requestFrom);
                //finish() bcz this same activity is opening when making changes to firebase. So finish() to nullify the effect
                finish();
            }
        });

    }

    private void setAdapter(ArrayList<Product> cartproductslist) {
        recyclerAdapterforOrderRequest adapter=new recyclerAdapterforOrderRequest(cartproductslist);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        int height=100;
        int width=100;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.customericon);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);


        //for vendor
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("vendorlocation").child(vendorphonenumber);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    double vendorlatitude=snapshot.child("latitude").getValue(Double.class);
                    double vendorlongitude=snapshot.child("longitude").getValue(Double.class);
                    LatLng vendorlatlng=new LatLng(vendorlatitude,vendorlongitude);
                    if(vendorfirsttime){
                        vendormarker=map.addMarker(new MarkerOptions().position(vendorlatlng).title("You"));
                        vendorfirsttime=false;
                    }
                    else{
                        vendormarker.setPosition(vendorlatlng);
                    }

                    //for customer
                    reference=database.getReference("customerlocation").child(customerphonenumber);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                double customerlatitude=snapshot.child("latitude").getValue(Double.class);
                                double customerlongitude=snapshot.child("longitude").getValue(Double.class);
                                LatLng customerlatlng=new LatLng(customerlatitude,customerlongitude);
                                if(customerfirsttime){
                                    customermarker=map.addMarker(new MarkerOptions().position(customerlatlng).title("Customer").icon(smallMarkerIcon));
                                    customerfirsttime =false;
                                }
                                else{
                                    customermarker.setPosition(customerlatlng);
                                }

                                if(customermarker!=null && vendormarker!=null){
                                    LatLngBounds.Builder builder=new LatLngBounds.Builder();
                                    builder.include(customermarker.getPosition());
                                    builder.include(vendormarker.getPosition());
                                    LatLngBounds bounds=builder.build();
                                    int width = getResources().getDisplayMetrics().widthPixels;
                                    int height = getResources().getDisplayMetrics().heightPixels;
                                    int padding = (int) (height * 0.20); // offset from edges of the map 10% of screen
                                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                                    map.animateCamera(cu);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}