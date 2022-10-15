package hmtechagency.mubeenproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hmtechagency.mubeenproject.Adapters.CustomersAdapter;
import hmtechagency.mubeenproject.Adapters.SelectCustomersAdapter;
import hmtechagency.mubeenproject.Models.CustomersModelClass;
import hmtechagency.mubeenproject.databinding.ActivityCustomersBinding;
import hmtechagency.mubeenproject.databinding.ActivitySelectCustomerBinding;

public class SelectCustomer extends AppCompatActivity {
    ActivitySelectCustomerBinding binding;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    SelectCustomersAdapter adminOrdersAdapter;

    ArrayList<CustomersModelClass> list=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySelectCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView=findViewById(R.id.selectCustomerRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        FetchData();
        progressDialog =new ProgressDialog(SelectCustomer.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    private void FetchData() {
        firebaseDatabase.getReference("Customers_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        CustomersModelClass model=snapshot1.getValue(CustomersModelClass.class);
                        //model.setMessageId(snapshot1.getKey());
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new SelectCustomersAdapter(SelectCustomer.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(SelectCustomer.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SelectCustomer.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adminOrdersAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}