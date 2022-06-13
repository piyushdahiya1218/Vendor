package android.example.vendor.Adapters;

import android.example.vendor.Classes.Product;
import android.example.vendor.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterforCart extends RecyclerView.Adapter<recyclerAdapterforCart.MyViewHolder> {

    private ArrayList<Product> cartproductslist;

    //constructor of this adapter
    public recyclerAdapterforCart(ArrayList<Product> cartproductslist){
        this.cartproductslist = cartproductslist;
    }

    //initialize textviews whose text have to be set by recycler view
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimage;
        private TextView productnameeng;
        private TextView productnamehindi;
        private TextView productprice;
        private TextView productquantity;
        private Button priceadd;
        private Button pricesub;
        private Button quantityadd;
        private Button quantitysub;
        public MyViewHolder(final View view){
            super(view);
            productimage=view.findViewById(R.id.productimage);
            productnameeng =view.findViewById(R.id.productnameeng);
            productnamehindi=view.findViewById(R.id.productnamehindi);
            productprice=view.findViewById(R.id.productprice);
            productquantity=view.findViewById(R.id.productquantity);
            priceadd=view.findViewById(R.id.priceadd);
            pricesub=view.findViewById(R.id.pricesub);
            quantityadd=view.findViewById(R.id.quantityadd);
            quantitysub=view.findViewById(R.id.quantitysub);
        }
    }

    //"list_item" is a view that shows how one row will look like.   Attach this view to our recyclerview
    @NonNull
    @Override
    public recyclerAdapterforCart.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_items,parent,false);
        return new MyViewHolder(itemview);
    }

    /*lets say recyclerview wants to generate first row.
    * For first row,  position=0
    * We fill first row by first element of arraylist. Second row by second element.
    * So for first row, recyclerview gets first element from arraylist (which is a string)
    * and that string is used to set value of productname textview.
    * Then hashmap is searched for that string, which gives us the corresponding price for that item.
    * We set this price as value of productprice textview*/
    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterforCart.MyViewHolder holder, int position) {
        holder.productimage.setImageResource(cartproductslist.get(holder.getAdapterPosition()).getImageRid());
        holder.productnameeng.setText(cartproductslist.get(holder.getAdapterPosition()).getProductnameeng());
        holder.productnamehindi.setText(cartproductslist.get(holder.getAdapterPosition()).getProductnamehindi());
        holder.productprice.setText(String.valueOf(cartproductslist.get(holder.getAdapterPosition()).getPrice()));
        holder.productquantity.setText(String.valueOf(cartproductslist.get(holder.getAdapterPosition()).getQuantity()));

        Product product=cartproductslist.get(holder.getAdapterPosition());
        holder.priceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.productprice.getText().toString()) != 200){
                    holder.productprice.setText(String.valueOf(Integer.parseInt(holder.productprice.getText().toString())+10));
                    product.setPrice(Integer.parseInt(holder.productprice.getText().toString()));
                    cartproductslist.set(holder.getAdapterPosition(),product);
                }
            }
        });
        holder.pricesub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.productprice.getText().toString()) != 10){
                    holder.productprice.setText(String.valueOf(Integer.parseInt(holder.productprice.getText().toString())-10));
                    product.setPrice(Integer.parseInt(holder.productprice.getText().toString()));
                    cartproductslist.set(holder.getAdapterPosition(),product);
                }
            }
        });
        holder.quantityadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.productquantity.getText().toString()) != 20){
                    holder.productquantity.setText(String.valueOf(Integer.parseInt(holder.productquantity.getText().toString())+1));
                    product.setQuantity(Integer.parseInt(holder.productquantity.getText().toString()));
                    cartproductslist.set(holder.getAdapterPosition(),product);
                }
            }
        });
        holder.quantitysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(holder.productquantity.getText().toString()) != 0){
                    holder.productquantity.setText(String.valueOf(Integer.parseInt(holder.productquantity.getText().toString())-1));
                    product.setQuantity(Integer.parseInt(holder.productquantity.getText().toString()));
                    cartproductslist.set(holder.getAdapterPosition(),product);
                }
            }
        });
    }

    //returns size of arraylist
    @Override
    public int getItemCount() {
        if(cartproductslist ==null){
            return 0;
        }
        return cartproductslist.size();
    }
}
