package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import hmtechagency.mubeenproject.Models.CostModel;
import hmtechagency.mubeenproject.Models.ExpensesModel;


public class CostAdapter extends RecyclerView.Adapter<CostAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<CostModel> OrdersList;
  ArrayList<CostModel> newArraylistFill;


    public CostAdapter(Context context, ArrayList<CostModel> ordersList) {
        this.context = context;
        newArraylistFill = ordersList;
        OrdersList = new ArrayList<>(newArraylistFill);
    }

    @NonNull
    @Override
    public AdminOrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_recycler_sample,parent,false);
        return new AdminOrdersAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ExpensesName.setText(OrdersList.get(position).getNetValue());
        holder.ExpensesPrice.setText(OrdersList.get(position).getDate());
        holder.ExpensesDate.setVisibility(View.INVISIBLE);

        holder.ExpensesName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), AddCost.class);
                intent.putExtra("Date",OrdersList.get(position).getDate());
                intent.putExtra("PaperWidth",OrdersList.get(position).getPaperWidth());
                intent.putExtra("paperLength",OrdersList.get(position).getPaperLength());
                intent.putExtra("gram",OrdersList.get(position).getGram());
                intent.putExtra("Rate",OrdersList.get(position).getRate());
                intent.putExtra("priceOfPaper",OrdersList.get(position).getPriceOfPaper());
                intent.putExtra("onePiece",OrdersList.get(position).getOnePiece());
                intent.putExtra("RatePricePerPiece",OrdersList.get(position).getRatePricePerPiece());
                intent.putExtra("pricePerPiece",OrdersList.get(position).getPricePerPiece());
                intent.putExtra("silicateValue",OrdersList.get(position).getSilicateValue());
                intent.putExtra("silicate",OrdersList.get(position).getSilicate());
                intent.putExtra("Printing",OrdersList.get(position).getPrinting());
                intent.putExtra("LiminationWidth",OrdersList.get(position).getLaminationWidth());
                intent.putExtra("LaminationLength",OrdersList.get(position).getLaminationLength());
                intent.putExtra("LaminationRate",OrdersList.get(position).getLaminationRate());
                intent.putExtra("LaminationTotal",OrdersList.get(position).getLaminationTotal());
                intent.putExtra("PinValue",OrdersList.get(position).getPinValue());
                intent.putExtra("LabourValueTotal",OrdersList.get(position).getLabourValueTotal());
                intent.putExtra("NetValue",OrdersList.get(position).getNetValue());
                intent.putExtra("ExName","Edit");
                intent.putExtra("timeStamp",OrdersList.get(position).getCostTimeStamps());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });


        holder.PicOptionMenuProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.ExpensesName.getContext());
                builder.setTitle("Select Option");
                builder.setMessage("Select any option to perform action");
                builder.setCancelable(true);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query applesQuery = ref.child("Cost_Record").orderByChild("CostTimeStamps")
                                .equalTo(OrdersList.get(position).getCostTimeStamps());
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
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

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
            ArrayList<CostModel> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (CostModel news:newArraylistFill){
                    if (news.getNetValue().toLowerCase().contains(filterPattern)
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
        TextView ExpensesName,ExpensesPrice,ExpensesDate;
        ImageView PicOptionMenuProducts;

        public AdminOrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ExpensesName=itemView.findViewById(R.id.expensesNameList);
            ExpensesPrice=itemView.findViewById(R.id.expensesPriceList);
            ExpensesDate=itemView.findViewById(R.id.expenseDateList);
            PicOptionMenuProducts=itemView.findViewById(R.id.picOptionMenuExpensesList);


        }
    }
}
