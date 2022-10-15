package hmtechagency.mubeenproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hmtechagency.mubeenproject.IssueProducts;
import hmtechagency.mubeenproject.Models.CustomersModelClass;
import hmtechagency.mubeenproject.Models.SuppliersModelClass;
import hmtechagency.mubeenproject.R;
import hmtechagency.mubeenproject.ReceivedProduct;


public class SelectSuppliersAdapter extends RecyclerView.Adapter<SelectSuppliersAdapter.AdminOrdersAdapterViewHolder> implements Filterable {

  Context context;
  ArrayList<SuppliersModelClass> OrdersList;
  ArrayList<SuppliersModelClass> newArraylistFill;

    public SelectSuppliersAdapter(Context context, ArrayList<SuppliersModelClass> ordersList) {
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectCustomer=new Intent(v.getContext(), ReceivedProduct.class);
                selectCustomer.putExtra("SupplierName",OrdersList.get(position).getSupplierName());
                selectCustomer.putExtra("SupplierEmail",OrdersList.get(position).getSupplierEmail());
                selectCustomer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(selectCustomer);
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
