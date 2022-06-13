package android.example.vendor.Adapters;

import android.example.vendor.Classes.Product;
import android.example.vendor.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapterforOrderRequest extends RecyclerView.Adapter<recyclerAdapterforOrderRequest.MyViewHolder>{

    private ArrayList<Product> customercartlist;

    public recyclerAdapterforOrderRequest(ArrayList<Product> customercartlist){
        this.customercartlist=customercartlist;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView productimage;
        private TextView productnameeng;
        private TextView productnamehindi;
        private TextView productquantity;

        public MyViewHolder(@NonNull View view) {
            super(view);
            productimage=view.findViewById(R.id.productimage_orderrequest);
            productnameeng =view.findViewById(R.id.productnameeng_orderrequest);
            productnamehindi=view.findViewById(R.id.productnamehindi_orderrequest);
            productquantity=view.findViewById(R.id.productquantity_orderrequest);
        }
    }


    @NonNull
    @Override
    public recyclerAdapterforOrderRequest.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_request_list_items,parent,false);
        return new recyclerAdapterforOrderRequest.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterforOrderRequest.MyViewHolder holder, int position) {
        holder.productimage.setImageResource(customercartlist.get(holder.getAdapterPosition()).getImageRid());
        holder.productnameeng.setText(customercartlist.get(holder.getAdapterPosition()).getProductnameeng());
        holder.productnamehindi.setText(customercartlist.get(holder.getAdapterPosition()).getProductnamehindi());
        holder.productquantity.setText(String.valueOf(customercartlist.get(holder.getAdapterPosition()).getQuantity())+"kg");
    }

    @Override
    public int getItemCount() {
        return customercartlist.size();
    }
}
