package hmtechagency.mubeenproject.Adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import hmtechagency.mubeenproject.IssueProducts;
import hmtechagency.mubeenproject.Models.ProductsModel;
import hmtechagency.mubeenproject.ProductsDetailsActivity;
import hmtechagency.mubeenproject.R;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<ProductsModel> OrdersList;
  ArrayList<ProductsModel> newArraylistFill;

    public ProductsAdapter(Context context, ArrayList<ProductsModel> ordersList) {
        this.context = context;
        newArraylistFill = ordersList;
        OrdersList = new ArrayList<>(newArraylistFill);
    }

    @NonNull
    @Override
    public AdminOrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler_sample,parent,false);
        return new AdminOrdersAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ProductName.setText(OrdersList.get(position).getProductName());
        Glide.with(holder.ProductCirclePic.getContext()).load(OrdersList.get(position).getProductImage()).into(holder.ProductCirclePic);




        holder.ProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = v.getContext().getSharedPreferences("MyPrefAdapter", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ProductName",OrdersList.get(position).getProductName());
                editor.putString("ProductPrice",OrdersList.get(position).getProductPrice());
                editor.putString("ProductQuantity",OrdersList.get(position).getProductQuantity());
                editor.putString("ProductTotalPrice",OrdersList.get(position).getProductTotalPrice());
                editor.putString("ProductTimeStamp",OrdersList.get(position).getTimeStamps());
                editor.putString("img",OrdersList.get(position).getProductImage());
                // Save the changes in SharedPreferences
                editor.apply(); // commit changes
                Intent intent=new Intent(v.getContext(), ProductsDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });
        holder.PicOptionMenuProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder=new AlertDialog.Builder(holder.ProductName.getContext());
                builder.setTitle("Delete Record");
                builder.setMessage("Are you sure you want to delete this record permanently?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                       // databaseRef.child(id).removeValue();
//                        FirebaseDatabase.getInstance().getReference().child("Products_List")
//                                .child(holder.ProductName.getKey()).removeValue();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Products_List").orderByChild("TimeStamps")
                                .equalTo(OrdersList.get(position).getTimeStamps());

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                HashMap<String, Object> hashMap=new HashMap<>();
//                                hashMap.put("ProductName","Loptop");
//                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
//                                    appleSnapshot.getRef().updateChildren(hashMap);
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(context, "Error: "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onCancelled", databaseError.toException());
                            }
                        });

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return OrdersList.size();
    }




    @Override
    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ProductsModel> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (ProductsModel news:newArraylistFill){
                    if (news.getProductName().toLowerCase().contains(filterPattern)
                            ||news.getProductPrice().toLowerCase().contains(filterPattern))
                        filteredNewsList.add(news);

                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredNewsList;
            results.count=filteredNewsList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            OrdersList.clear();
            OrdersList.addAll((ArrayList)results.values);
            notifyDataSetChanged();

        }
    };




    public static class AdminOrdersAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView ProductName;
        ImageView PicOptionMenuProducts;
        CircleImageView ProductCirclePic;


        public AdminOrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName=itemView.findViewById(R.id.productsNameList);
            PicOptionMenuProducts=itemView.findViewById(R.id.picOptionMenuProducts);
            ProductCirclePic=itemView.findViewById(R.id.productCirclePic);


        }
    }
}
