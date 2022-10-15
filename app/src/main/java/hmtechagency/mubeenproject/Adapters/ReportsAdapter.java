package hmtechagency.mubeenproject.Adapters;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import hmtechagency.mubeenproject.Models.BillingModel;
import hmtechagency.mubeenproject.Models.ProductsModel;
import hmtechagency.mubeenproject.ProductsDetailsActivity;
import hmtechagency.mubeenproject.R;


public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<BillingModel> OrdersList;
  ArrayList<BillingModel> newArraylistFill;

    public ReportsAdapter(Context context, ArrayList<BillingModel> ordersList) {
        this.context = context;
        newArraylistFill = ordersList;
        OrdersList = new ArrayList<>(newArraylistFill);
    }

    @NonNull
    @Override
    public AdminOrdersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_detail_recycler_sample,parent,false);
        return new AdminOrdersAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ProductName.setText(OrdersList.get(position).getProductName());
        holder.Name.setText(OrdersList.get(position).getName());
        holder.Price.setText(OrdersList.get(position).getProductTotal_Price());
        holder.Quantity.setText(OrdersList.get(position).getProductQuantity());
        holder.Status.setText(OrdersList.get(position).getStatus());
        holder.Remainig.setText(OrdersList.get(position).getRemaining());



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
        TextView Name,ProductName,Price,Quantity,Date,Remainig,Status;



        public AdminOrdersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName=itemView.findViewById(R.id.reportDetailProductName);
            Name=itemView.findViewById(R.id.reportDetailName);
            Price=itemView.findViewById(R.id.reportDetailPrice);
            Quantity=itemView.findViewById(R.id.reportDetailQuantity);
            Remainig=itemView.findViewById(R.id.reportDetailRemainingPayment);
            Status=itemView.findViewById(R.id.reportDetailPaymentStatus);




        }
    }
}
