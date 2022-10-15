package hmtechagency.mubeenproject.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import hmtechagency.mubeenproject.AddCustomersActivity;
import hmtechagency.mubeenproject.Models.CustomersModelClass;
import hmtechagency.mubeenproject.Models.ProductsModel;
import hmtechagency.mubeenproject.ProductsActivity;
import hmtechagency.mubeenproject.ProductsDetailsActivity;
import hmtechagency.mubeenproject.R;


public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<CustomersModelClass> OrdersList;
  ArrayList<CustomersModelClass> newArraylistFill;

    public CustomersAdapter(Context context, ArrayList<CustomersModelClass> ordersList) {
        this.context = context;
        newArraylistFill = ordersList;
        OrdersList = new ArrayList<>(newArraylistFill);
    }

    @NonNull
    @Override
    public AdminOrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customers_recyclerview_sample,parent,false);
        return new AdminOrdersAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ProductName.setText(OrdersList.get(position).getCustomerName());


        holder.ProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.update_customer_sample_dialog);
                dialog.show();
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                EditText CName = dialog.findViewById(R.id.customerNameUpdate);
                EditText CEmail = dialog.findViewById(R.id.customerEmailUpdate);
                EditText CPhone = dialog.findViewById(R.id.customerPhoneUpdate);
                EditText CAddress = dialog.findViewById(R.id.customerAddressUpdate);
                EditText CBank = dialog.findViewById(R.id.customerBankDetailsUpdate);
                EditText CDiscount = dialog.findViewById(R.id.customerDiscountUpdate);
                EditText CNotes = dialog.findViewById(R.id.customerNotestUpdate);
                ImageView backArrow=dialog.findViewById(R.id.iconBackUpdateCustomer);
                ImageView actionUpdate=dialog.findViewById(R.id.iconSaveCustomersRickUpdate);
                backArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ///////////////////////////////////////////////////////////
                CName.setText(OrdersList.get(position).getCustomerName());
                CEmail.setText(OrdersList.get(position).getCustomerEmail());
                CPhone.setText(OrdersList.get(position).getCustomerPhone());
                CAddress.setText(OrdersList.get(position).getCustomerAddress());
                CBank.setText(OrdersList.get(position).getCustomerBankDetail());
                CDiscount.setText(OrdersList.get(position).getCustomerDiscount());
                CNotes.setText(OrdersList.get(position).getCustomerNotes());
                actionUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("CustomerName",CName.getText().toString());
                        hashMap.put("CustomerEmail",CEmail.getText().toString());
                        hashMap.put("CustomerPhone",CPhone.getText().toString());
                        hashMap.put("CustomerAddress",CAddress.getText().toString());
                        hashMap.put("CustomerBankDetail",CBank.getText().toString());
                        hashMap.put("CustomerDiscount",CDiscount.getText().toString());
                        hashMap.put("CustomerNotes",CNotes.getText().toString());
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Customers_List").orderByChild("CustomerTimeStamps")
                                .equalTo(OrdersList.get(position).getCustomerTimeStamps());

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().updateChildren(hashMap);
                                    Toast.makeText(v.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(context, "Error: "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onCancelled", databaseError.toException());
                                dialog.dismiss();
                            }
                        });

                    }
                });

            }
        });

        holder.PicOptionMenuProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.ProductName.getContext());
                builder.setTitle("Select Option");
                builder.setMessage("Select any option to perform action");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Customers_List").orderByChild("CustomerTimeStamps")
                                .equalTo(OrdersList.get(position).getCustomerTimeStamps());

                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
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
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.update_customer_sample_dialog);
                        dialog.show();
                        dialog.setCancelable(true);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.BOTTOM);

                        EditText CName = dialog.findViewById(R.id.customerNameUpdate);
                        EditText CEmail = dialog.findViewById(R.id.customerEmailUpdate);
                        EditText CPhone = dialog.findViewById(R.id.customerPhoneUpdate);
                        EditText CAddress = dialog.findViewById(R.id.customerAddressUpdate);
                        EditText CBank = dialog.findViewById(R.id.customerBankDetailsUpdate);
                        EditText CDiscount = dialog.findViewById(R.id.customerDiscountUpdate);
                        EditText CNotes = dialog.findViewById(R.id.customerNotestUpdate);
                        ImageView backArrow=dialog.findViewById(R.id.iconBackUpdateCustomer);
                        ImageView actionUpdate=dialog.findViewById(R.id.iconSaveCustomersRickUpdate);
                        backArrow.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        ///////////////////////////////////////////////////////////
                        CName.setText(OrdersList.get(position).getCustomerName());
                        CEmail.setText(OrdersList.get(position).getCustomerEmail());
                        CPhone.setText(OrdersList.get(position).getCustomerPhone());
                        CAddress.setText(OrdersList.get(position).getCustomerAddress());
                        CBank.setText(OrdersList.get(position).getCustomerBankDetail());
                        CDiscount.setText(OrdersList.get(position).getCustomerDiscount());
                        CNotes.setText(OrdersList.get(position).getCustomerNotes());
                        actionUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> hashMap=new HashMap<>();
                                hashMap.put("CustomerName",CName.getText().toString());
                                hashMap.put("CustomerEmail",CEmail.getText().toString());
                                hashMap.put("CustomerPhone",CPhone.getText().toString());
                                hashMap.put("CustomerAddress",CAddress.getText().toString());
                                hashMap.put("CustomerBankDetail",CBank.getText().toString());
                                hashMap.put("CustomerDiscount",CDiscount.getText().toString());
                                hashMap.put("CustomerNotes",CNotes.getText().toString());
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("Customers_List").orderByChild("CustomerTimeStamps")
                                        .equalTo(OrdersList.get(position).getCustomerTimeStamps());

                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().updateChildren(hashMap);
                                            Toast.makeText(v.getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(context, "Error: "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                        Log.e(TAG, "onCancelled", databaseError.toException());
                                        dialog.dismiss();
                                    }
                                });

                            }
                        });




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
            ArrayList<CustomersModelClass> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (CustomersModelClass news:newArraylistFill){
                    if (news.getCustomerName().toLowerCase().contains(filterPattern))
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


        public AdminOrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName=itemView.findViewById(R.id.customersNameList);
            PicOptionMenuProducts=itemView.findViewById(R.id.picOptionMenuCustomers);


        }
    }
}
