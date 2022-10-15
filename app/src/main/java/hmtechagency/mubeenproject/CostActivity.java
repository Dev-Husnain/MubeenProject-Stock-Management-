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

import hmtechagency.mubeenproject.Models.CostModel;
import hmtechagency.mubeenproject.databinding.ActivityCostBinding;


public class CostActivity extends AppCompatActivity {
    ActivityCostBinding binding;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    CostAdapter adminOrdersAdapter;
    ArrayList<CostModel> list=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fabAddCostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CostActivity.this,AddCost.class));
            }
        });


        recyclerView=findViewById(R.id.recyclerViewCost);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CostActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog =new ProgressDialog(CostActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        FetchData();
    }




    private void FetchData() {
        firebaseDatabase.getReference("Cost_Record").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        CostModel model=snapshot1.getValue(CostModel.class);
                        //model.setMessageId(snapshot1.getKey());
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new CostAdapter(CostActivity.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();


                }
                else {
                    Toast.makeText(CostActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CostActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

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