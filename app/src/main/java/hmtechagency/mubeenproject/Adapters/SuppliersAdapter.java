package hmtechagency.mubeenproject.Adapters;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import java.util.HashMap;

import hmtechagency.mubeenproject.Models.CustomersModelClass;
import hmtechagency.mubeenproject.Models.SuppliersModelClass;
import hmtechagency.mubeenproject.R;


public class SuppliersAdapter extends RecyclerView.Adapter<SuppliersAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<SuppliersModelClass> OrdersList;
  ArrayList<SuppliersModelClass> newArraylistFill;

    public SuppliersAdapter(Context context, ArrayList<SuppliersModelClass> ordersList) {
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
        holder.ProductName.setText(OrdersList.get(position).getSupplierName());


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
                CName.setText(OrdersList.get(position).getSupplierName());
                CEmail.setText(OrdersList.get(position).getSupplierEmail());
                CPhone.setText(OrdersList.get(position).getSupplierPhone());
                CAddress.setText(OrdersList.get(position).getSupplierAddress());
                CBank.setText(OrdersList.get(position).getSupplierBankDetail());
                CDiscount.setText(OrdersList.get(position).getSupplierDiscount());
                CNotes.setText(OrdersList.get(position).getSupplierNotes());
                actionUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hashMap=new HashMap<>();
                        hashMap.put("SupplierName",CName.getText().toString());
                        hashMap.put("SupplierEmail",CEmail.getText().toString());
                        hashMap.put("SupplierPhone",CPhone.getText().toString());
                        hashMap.put("SupplierAddress",CAddress.getText().toString());
                        hashMap.put("SupplierBankDetail",CBank.getText().toString());
                        hashMap.put("SupplierDiscount",CDiscount.getText().toString());
                        hashMap.put("SupplierNotes",CNotes.getText().toString());
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Suppliers_List").orderByChild("SupplierTimeStamps")
                                .equalTo(OrdersList.get(position).getSupplierTimeStamps());

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
                        Query applesQuery = ref.child("Suppliers_List").orderByChild("SupplierTimeStamps")
                                .equalTo(OrdersList.get(position).getSupplierTimeStamps());

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
                        CName.setText(OrdersList.get(position).getSupplierName());
                        CEmail.setText(OrdersList.get(position).getSupplierEmail());
                        CPhone.setText(OrdersList.get(position).getSupplierPhone());
                        CAddress.setText(OrdersList.get(position).getSupplierAddress());
                        CBank.setText(OrdersList.get(position).getSupplierBankDetail());
                        CDiscount.setText(OrdersList.get(position).getSupplierDiscount());
                        CNotes.setText(OrdersList.get(position).getSupplierNotes());
                        actionUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> hashMap=new HashMap<>();
                                hashMap.put("SupplierName",CName.getText().toString());
                                hashMap.put("SupplierEmail",CEmail.getText().toString());
                                hashMap.put("SupplierPhone",CPhone.getText().toString());
                                hashMap.put("SupplierAddress",CAddress.getText().toString());
                                hashMap.put("SupplierBankDetail",CBank.getText().toString());
                                hashMap.put("SupplierDiscount",CDiscount.getText().toString());
                                hashMap.put("SupplierNotes",CNotes.getText().toString());
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("Suppliers_List").orderByChild("SupplierTimeStamps")
                                        .equalTo(OrdersList.get(position).getSupplierTimeStamps());

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
            ArrayList<SuppliersModelClass> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (SuppliersModelClass news:newArraylistFill){
                    if (news.getSupplierName().toLowerCase().contains(filterPattern))
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
