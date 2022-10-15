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
import android.net.Uri;
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
import java.util.HashMap;

import hmtechagency.mubeenproject.BillingDetail;
import hmtechagency.mubeenproject.IssueProducts;
import hmtechagency.mubeenproject.Models.BillingModel;
import hmtechagency.mubeenproject.Models.SuppliersModelClass;
import hmtechagency.mubeenproject.ProductsDetailsActivity;
import hmtechagency.mubeenproject.R;


public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<BillingModel> OrdersList;
  ArrayList<BillingModel> newArraylistFill;

    public BillingAdapter(Context context, ArrayList<BillingModel> ordersList) {
        this.context = context;
        newArraylistFill = ordersList;
        OrdersList = new ArrayList<>(newArraylistFill);
    }

    @NonNull
    @Override
    public AdminOrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_recycler_sample,parent,false);
        return new AdminOrdersAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.CustomerName.setText(OrdersList.get(position).getProductName());
        holder.Price.setText(OrdersList.get(position).getProductTotal_Price());
        holder.Date.setText(OrdersList.get(position).getDate());
        holder.Type.setText(OrdersList.get(position).getType());
        holder.Status.setText(OrdersList.get(position).getStatus());
        holder.Remaining.setText(OrdersList.get(position).getRemaining());
        if (holder.Status.getText().toString().equals("Approved")){
            holder.Status.setTextColor(Color.GREEN);
        }

        holder.ApproveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update quantity
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.payment_recived_layout_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(true);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                EditText paymentReceived=v.findViewById(R.id.EnterPaymentYourReceived);
                Button submitPaymentReceived=v.findViewById(R.id.submitPaymentReceivedButton);
                submitPaymentReceived.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vvv) {
                        //calculate to price of products
                        String sss = holder.Remaining.getText().toString();
                        Float fff = Float.valueOf(sss);
                        String ppp = paymentReceived.getText().toString();
                        Float pppp = Float.valueOf(ppp);

                        if (fff>=pppp){
                            Float TTotalPrice = fff-pppp;
                            String TTTotalPrice = String.valueOf(TTotalPrice);
                            holder.Remaining.setText(TTTotalPrice);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref.child("IssuedProducts_List").orderByChild("TimeOfIssuesReceived")
                                    .equalTo(OrdersList.get(position).getTimeOfIssuesReceived());
                            Query applesQuery1 = ref.child("Billing").orderByChild("TimeOfIssuesReceived")
                                    .equalTo(OrdersList.get(position).getTimeOfIssuesReceived());
                            Query applesQuery2 = ref.child("ReceivedProducts_List").orderByChild("TimeOfIssuesReceived")
                                    .equalTo(OrdersList.get(position).getTimeOfIssuesReceived());
                            Query applesQuery3 = ref.child("Reports").orderByChild("TimeOfIssuesReceived")
                                    .equalTo(OrdersList.get(position).getTimeOfIssuesReceived());
                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap<String, Object> map12 = new HashMap<>();
                                    map12.put("Remaining", TTTotalPrice);
                                    map12.put("Status", "Approved");
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().updateChildren(map12);
                                    }
                                    Toast.makeText(vvv.getContext() ," Updated Successfully", Toast.LENGTH_LONG).show();


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(vvv.getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                }
                            });
                            applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap<String, Object> map12 = new HashMap<>();
                                    map12.put("Remaining", TTTotalPrice);
                                    map12.put("Status", "Approved");
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().updateChildren(map12);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(vvv.getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                }
                            });
                            applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap<String, Object> map12 = new HashMap<>();
                                    map12.put("Remaining", TTTotalPrice);
                                    map12.put("Status", "Approved");
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().updateChildren(map12);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(vvv.getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                }
                            });
                            applesQuery3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    HashMap<String, Object> map12 = new HashMap<>();
                                    map12.put("Remaining", TTTotalPrice);
                                    map12.put("Status", "Approved");
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().updateChildren(map12);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(vvv.getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "onCancelled", databaseError.toException());
                                }
                            });
                            holder.Status.setText("Approved");
                            alertDialog.dismiss();
                        }
                        else {
                            Toast.makeText(vvv.getContext(), "Payment Received is greater than total payment", Toast.LENGTH_LONG).show();

                        }



                    }
                });

            }
        });



        holder.Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), BillingDetail.class);
                intent.putExtra("Name",OrdersList.get(position).getName());
                intent.putExtra("ProductName",OrdersList.get(position).getProductName());
                intent.putExtra("Price",OrdersList.get(position).getProductPrice());
                intent.putExtra("TotalPrice",OrdersList.get(position).getProductTotal_Price());
                intent.putExtra("Quantity",OrdersList.get(position).getProductQuantity());
                intent.putExtra("Email",OrdersList.get(position).getEmail());
                intent.putExtra("Comment",OrdersList.get(position).getComment());
                intent.putExtra("Date",OrdersList.get(position).getDate());
                intent.putExtra("Type",OrdersList.get(position).getType());
                intent.putExtra("TimeStamps",OrdersList.get(position).getTimeOfIssuesReceived());
                intent.putExtra("Status",OrdersList.get(position).getStatus());
                intent.putExtra("Remaining",OrdersList.get(position).getRemaining());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
        holder.PicOptionMenuProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.CustomerName.getContext());
                builder.setTitle("Select Option");
                builder.setMessage("Select any option to perform action");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Billing").orderByChild("TimeOfIssuesReceived")
                                .equalTo(OrdersList.get(position).getTimeOfIssuesReceived());

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
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uName=OrdersList.get(position).getName();
                        String uProductName=OrdersList.get(position).getProductName();
                        String uDate=OrdersList.get(position).getDate();
                        String uPrice=OrdersList.get(position).getProductPrice();
                        String uTotalPrice=OrdersList.get(position).getProductTotal_Price();
                        String uQuantity=OrdersList.get(position).getProductQuantity();
                        String uSubject="Bill Report";
                        String recepientEmail =""; // either set to destination email or leave empty

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        // intent.setType("text/email");
                        intent.setData(Uri.parse("mailto:" + recepientEmail));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{OrdersList.get(position).getEmail()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, uSubject);
                        intent.putExtra(Intent.EXTRA_TEXT, "Name:  " + uName + " \n\nProduct Name:  " + uProductName + " \n\nDate:  " + uDate +
                                " \n\nQuantity:  " + uQuantity+  " \n\nPrice/item:  " + uPrice  + " \n\nTotal Price:  " + uTotalPrice );
                        view.getContext().startActivity(intent);



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
            ArrayList<BillingModel> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (BillingModel news:newArraylistFill){
                    if (news.getProductName().toLowerCase().contains(filterPattern)
                            ||news.getName().toLowerCase().contains(filterPattern)
                            ||news.getType().toLowerCase().contains(filterPattern)
                            ||news.getDate().toLowerCase().contains(filterPattern))
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
        TextView CustomerName,Date,Price,Type,Status,Remaining,ApproveButton;
        ImageView PicOptionMenuProducts;


        public AdminOrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            CustomerName=itemView.findViewById(R.id.billingCustomerNameList);
            Date=itemView.findViewById(R.id.billingDateList);
            Price=itemView.findViewById(R.id.billPriceList);
            Type=itemView.findViewById(R.id.billingType);
            PicOptionMenuProducts=itemView.findViewById(R.id.picOptionMenuBillList);
            Status=itemView.findViewById(R.id.billingPaymentStatus);
            Remaining=itemView.findViewById(R.id.remainingPayment);
            ApproveButton=itemView.findViewById(R.id.billingToApprovePayment);



        }
    }
}
