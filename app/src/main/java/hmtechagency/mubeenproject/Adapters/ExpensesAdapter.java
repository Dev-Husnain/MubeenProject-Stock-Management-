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

import hmtechagency.mubeenproject.AddExpenses;
import hmtechagency.mubeenproject.Models.ExpensesModel;
import hmtechagency.mubeenproject.Models.SuppliersModelClass;
import hmtechagency.mubeenproject.R;


public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<ExpensesModel> OrdersList;
  ArrayList<ExpensesModel> newArraylistFill;

    public ExpensesAdapter(Context context, ArrayList<ExpensesModel> ordersList) {
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
        holder.ExpensesName.setText(OrdersList.get(position).getExpensesName());
        holder.ExpensesPrice.setText(OrdersList.get(position).getExpenses());
        holder.ExpensesDate.setText(OrdersList.get(position).getExpensesDate());

        holder.ExpensesName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), AddExpenses.class);
                intent.putExtra("ExName",OrdersList.get(position).getExpensesName());
                intent.putExtra("ExPrice",OrdersList.get(position).getExpenses());
                intent.putExtra("ExDate",OrdersList.get(position).getExpensesDate());
                intent.putExtra("ExDescription",OrdersList.get(position).getExpensesDescription());
                intent.putExtra("timeStamp",OrdersList.get(position).getExpensesTimeStamps());
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
                        Query applesQuery = ref.child("ExpensesList").orderByChild("ExpensesTimeStamps")
                                .equalTo(OrdersList.get(position).getExpensesTimeStamps());

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
            ArrayList<ExpensesModel> filteredNewsList=new ArrayList<>();
            if (constraint==null||constraint.length()==0){
                 filteredNewsList.addAll(newArraylistFill);

            }
            else {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (ExpensesModel news:newArraylistFill){
                    if (news.getExpensesName().toLowerCase().contains(filterPattern)
                            ||news.getExpensesDate().toLowerCase().contains(filterPattern))
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
