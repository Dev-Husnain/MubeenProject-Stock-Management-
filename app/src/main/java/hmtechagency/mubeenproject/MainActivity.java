package hmtechagency.mubeenproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hmtechagency.mubeenproject.Adapters.ProductsAdapter;
import hmtechagency.mubeenproject.Models.ProductsModel;
import hmtechagency.mubeenproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase firebaseDatabase;
    ProductsAdapter adminOrdersAdapter;

    ArrayList<ProductsModel> list=new ArrayList<>();
    ProgressDialog progressDialog;
    double FindTotalStock = 0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        FetchTotalAmount();

        //move to next activity
       binding.goodsCard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,ProductsActivity.class));
           }
       });
        binding.performanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PerformanceMain.class));
            }
        });
        binding.customersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomersActivity.class));
            }
        });
        binding.suppliersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SuppliersActivity.class));
            }
        });
        binding.costCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CostActivity.class));
            }
        });
        binding.expensesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ExpensesActivity.class));
            }
        });
        binding.billingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Billing.class));
            }
        });


    }

    //Fetch data from firebase database
    private void FetchTotalAmount() {
        firebaseDatabase.getReference("Products_List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        ProductsModel model=snapshot1.getValue(ProductsModel.class);
                        //model.setMessageId(snapshot1.getKey());
                        list.add(model);
                        FindTotalStock= snapshot.getChildrenCount();
                        binding.totalStock.setText(Double.toString(FindTotalStock));
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ProductsAdapter(MainActivity.this,list);
                    //recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                }
                else {
                    Toast.makeText(MainActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //calculate total amount
    private void calculateTotalAmount(List<ProductsModel> cartModelList) {
        double totalAmount = 0.0;
        for(ProductsModel myCartModel: cartModelList){
            totalAmount +=Double.valueOf(myCartModel.getProductTotalPrice()) ;

        }
        binding.totalBalance.setText("Rs: " + totalAmount);
    }
}