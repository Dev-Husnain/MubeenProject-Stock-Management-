package hmtechagency.mubeenproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import hmtechagency.mubeenproject.Adapters.CustomersAdapter;
import hmtechagency.mubeenproject.Adapters.ExpensesAdapter;
import hmtechagency.mubeenproject.Models.CustomersModelClass;
import hmtechagency.mubeenproject.Models.ExpensesModel;
import hmtechagency.mubeenproject.databinding.ActivityCustomersBinding;
import hmtechagency.mubeenproject.databinding.ActivityExpensesBinding;

public class ExpensesActivity extends AppCompatActivity {
    ActivityExpensesBinding binding;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    ExpensesAdapter adminOrdersAdapter;

    ArrayList<ExpensesModel> list=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView=findViewById(R.id.recyclerViewExpenses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        FetchData();
        progressDialog =new ProgressDialog(ExpensesActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        binding.fabAddExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpensesActivity.this,AddExpenses.class));
            }
        });


    }

    private void FetchData() {
        firebaseDatabase.getReference("ExpensesList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        ExpensesModel model=snapshot1.getValue(ExpensesModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ExpensesAdapter(ExpensesActivity.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(ExpensesActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ExpensesActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
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