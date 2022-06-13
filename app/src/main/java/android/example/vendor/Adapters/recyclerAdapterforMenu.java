package android.example.vendor.Adapters;

import android.example.vendor.Classes.Product;
import android.example.vendor.R;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterforMenu extends RecyclerView.Adapter<recyclerAdapterforMenu.MyViewHolder> {

    private ArrayList<Product> allproductslist;

    public recyclerAdapterforMenu(ArrayList<Product> allproductslist){
        this.allproductslist=allproductslist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimage1;
        private TextView productnameeng1;
        private TextView productnamehindi1;
        private TextView productprice1;
        private TextView productquantity1;
        private Button priceadd1;
        private Button pricesub1;
        private Button quantityadd1;
        private Button quantitysub1;

        public MyViewHolder(final View view) {
            super(view);

            productimage1=view.findViewById(R.id.productimage1);
            productnameeng1 =view.findViewById(R.id.productnameeng1);
            productnamehindi1=view.findViewById(R.id.productnamehindi1);
            productprice1=view.findViewById(R.id.productprice1);
            productquantity1=view.findViewById(R.id.productquantity1);
            priceadd1=view.findViewById(R.id.priceadd1);
            pricesub1=view.findViewById(R.id.pricesub1);
            quantityadd1=view.findViewById(R.id.quantityadd1);
            quantitysub1=view.findViewById(R.id.quantitysub1);
        }
    }

    @NonNull
    @Override
    public recyclerAdapterforMenu.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_items,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterforMenu.MyViewHolder holder, int position) {
//        holder.productname1.setText(allproductslist.get(position).getNameeng()+"/"+allproductslist.get(position).getNamehindi());
        holder.productnameeng1.setText(allproductslist.get(holder.getAdapterPosition()).getProductnameeng());
        holder.productnamehindi1.setText(allproductslist.get(holder.getAdapterPosition()).getProductnamehindi());
        holder.productimage1.setImageResource(allproductslist.get(holder.getAdapterPosition()).getImageRid());
        holder.productprice1.setText(String.valueOf(allproductslist.get(holder.getAdapterPosition()).getPrice()));
        holder.productquantity1.setText(String.valueOf(allproductslist.get(holder.getAdapterPosition()).getQuantity()));

        Product product=allproductslist.get(holder.getAdapterPosition());
        holder.priceadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductslist.get(holder.getAdapterPosition()).isSelected()){
                    if(Integer.parseInt(holder.productprice1.getText().toString()) != 200){
                        holder.productprice1.setText(String.valueOf(Integer.parseInt(holder.productprice1.getText().toString())+10));
                        product.setPrice(Integer.parseInt(holder.productprice1.getText().toString()));
                        allproductslist.set(holder.getAdapterPosition(),product);
                    }
                }
            }
        });
        holder.pricesub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductslist.get(holder.getAdapterPosition()).isSelected()){
                    if(Integer.parseInt(holder.productprice1.getText().toString()) != 10){
                        holder.productprice1.setText(String.valueOf(Integer.parseInt(holder.productprice1.getText().toString())-10));
                        product.setPrice(Integer.parseInt(holder.productprice1.getText().toString()));
                        allproductslist.set(holder.getAdapterPosition(),product);
                    }
                }
            }
        });
        holder.quantityadd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductslist.get(holder.getAdapterPosition()).isSelected()){
                    if(Integer.parseInt(holder.productquantity1.getText().toString()) != 20){
                        holder.productquantity1.setText(String.valueOf(Integer.parseInt(holder.productquantity1.getText().toString())+1));
                        product.setQuantity(Integer.parseInt(holder.productquantity1.getText().toString()));
                        allproductslist.set(holder.getAdapterPosition(),product);
                    }
                }
            }
        });
        holder.quantitysub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductslist.get(holder.getAdapterPosition()).isSelected()){
                    if(Integer.parseInt(holder.productquantity1.getText().toString()) != 0){
                        holder.productquantity1.setText(String.valueOf(Integer.parseInt(holder.productquantity1.getText().toString())-1));
                        product.setQuantity(Integer.parseInt(holder.productquantity1.getText().toString()));
                        allproductslist.set(holder.getAdapterPosition(),product);
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allproductslist.get(holder.getAdapterPosition()).isSelected()){
                    allproductslist.get(holder.getAdapterPosition()).setSelected(false);
                    holder.itemView.setBackgroundResource(R.drawable.border_unselected);
                }
                else{
                    allproductslist.get(holder.getAdapterPosition()).setSelected(true);
                    holder.itemView.setBackgroundResource(R.drawable.border_selected);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(allproductslist==null){
            return 0;
        }
        return allproductslist.size();
    }

}
