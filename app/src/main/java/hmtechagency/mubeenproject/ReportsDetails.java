package hmtechagency.mubeenproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import java.util.List;

import hmtechagency.mubeenproject.Adapters.BillingAdapter;
import hmtechagency.mubeenproject.Adapters.ExpensesAdapter;
import hmtechagency.mubeenproject.Adapters.ReportsAdapter;
import hmtechagency.mubeenproject.Models.BillingModel;
import hmtechagency.mubeenproject.Models.ExpensesModel;
import hmtechagency.mubeenproject.Models.ProductsModel;
import hmtechagency.mubeenproject.databinding.ActivityReportsDetailsBinding;

public class ReportsDetails extends AppCompatActivity {
    ActivityReportsDetailsBinding binding;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    ReportsAdapter adminOrdersAdapter;

    ArrayList<BillingModel> list=new ArrayList<>();
    ProgressDialog progressDialog;
    String year,month,WeekOfYear,CustomerMonth3,CustomerYear3,CurrentWeekCustomer;
    String SupplierWeekOfYear,SupplierMonth3,SupplierYear3,CurrentWeekSupplier;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReportsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.iconBackReportsDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        year=getIntent().getStringExtra("Year");
        month=getIntent().getStringExtra("Month");
        WeekOfYear=getIntent().getStringExtra("Week");
        CustomerMonth3=getIntent().getStringExtra("CustomerMonth");
        CustomerYear3=getIntent().getStringExtra("CustomerYear");
        CurrentWeekCustomer=getIntent().getStringExtra("CurrentWeek");
        //////////////////////////////
        SupplierWeekOfYear=getIntent().getStringExtra("Week");
        SupplierMonth3=CustomerMonth3=getIntent().getStringExtra("CustomerMonth");
        SupplierYear3= CustomerYear3=getIntent().getStringExtra("CustomerYear");
        CurrentWeekSupplier=CurrentWeekCustomer=getIntent().getStringExtra("CurrentWeek");
        type=getIntent().getStringExtra("Type");





        recyclerView=findViewById(R.id.recyclerviewReportsDetail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(null);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog =new ProgressDialog(ReportsDetails.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        if (type.equals("Year")){
            FetchYearData();
            Toast.makeText(ReportsDetails.this, "Year Wise", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Month")){
            FetchMonthData();
            Toast.makeText(ReportsDetails.this, "Month Wise", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("WeekOfYear")){
            FetchWeekData();
            Toast.makeText(ReportsDetails.this, "Week Wise", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("CustomerMonth")){
            FetchCurrentMonthData();
            Toast.makeText(ReportsDetails.this, "Current Month Wise", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last30Days")){
            FetchLast30DaysData();
            Toast.makeText(ReportsDetails.this, "  Last 30 Days", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last60Days")){
            FetchLast60DaysData();
            Toast.makeText(ReportsDetails.this, "  Last 60 Days", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last90Days")){
            FetchLast90DaysData();
            Toast.makeText(ReportsDetails.this, "  Last 90 Days", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("CurrentYearCustomer")){
            FetchCurrentYearCustomerData();
            Toast.makeText(ReportsDetails.this, "  Current Year ", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("CurrentWeek")){
            FetchCurrentWeekCustomerData();
            Toast.makeText(ReportsDetails.this, "  Current Week ", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("SupplierMonth")){
            FetchSupplierMonthData();
            Toast.makeText(ReportsDetails.this, "Current Month Wise Supplier", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last30DaysSupplier")){
            Last30DaysSupplier();
            Toast.makeText(ReportsDetails.this, "  Last 30 Days Supplier", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last90DaysSupplier")){
            Last90DaysSupplier();
            Toast.makeText(ReportsDetails.this, "  Last 90 Days Supplier", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("Last60DaysSupplier")){
            Last60DaysSupplier();
            Toast.makeText(ReportsDetails.this, "  Last 60 Days Supplier", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("CurrentYearSupplier")){
            CurrentYearSupplier();
            Toast.makeText(ReportsDetails.this, "  Current Year Supplier ", Toast.LENGTH_LONG).show();
        }
        else if (type.equals("CurrentWeekSupplier")){
            CurrentWeekSupplier();
            Toast.makeText(ReportsDetails.this, "  Current Week Supplier ", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(ReportsDetails.this, "  Data of selected filter does not exist ", Toast.LENGTH_LONG).show();
        }
    }

    private void CurrentWeekSupplier() {
        firebaseDatabase.getReference("ReceivedProducts_List").orderByChild("WeekOfYear").equalTo(CurrentWeekSupplier).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void CurrentYearSupplier() {
        firebaseDatabase.getReference("ReceivedProducts_List").orderByChild("Year").equalTo(SupplierYear3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void Last60DaysSupplier() {
        firebaseDatabase.getReference("ReceivedProducts_List").limitToFirst(60).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void Last90DaysSupplier() {
        firebaseDatabase.getReference("ReceivedProducts_List").limitToFirst(90).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void Last30DaysSupplier() {
        firebaseDatabase.getReference("ReceivedProducts_List").limitToFirst(30).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchSupplierMonthData() {
        firebaseDatabase.getReference("ReceivedProducts_List").orderByChild("Month").equalTo(SupplierMonth3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchCurrentWeekCustomerData() {
        firebaseDatabase.getReference("IssuedProducts_List").orderByChild("WeekOfYear").equalTo(CurrentWeekCustomer).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchCurrentYearCustomerData() {
        firebaseDatabase.getReference("IssuedProducts_List").orderByChild("Year").equalTo(CustomerYear3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchLast90DaysData() {
        firebaseDatabase.getReference("IssuedProducts_List").limitToFirst(90).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchLast60DaysData() {
        firebaseDatabase.getReference("IssuedProducts_List").limitToFirst(60).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchLast30DaysData() {
        firebaseDatabase.getReference("IssuedProducts_List").limitToFirst(30).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchCurrentMonthData() {
        firebaseDatabase.getReference("IssuedProducts_List").orderByChild("Month").equalTo(CustomerMonth3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchWeekData() {
        firebaseDatabase.getReference("Reports").orderByChild("WeekOfYear").equalTo(WeekOfYear).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchYearData() {
        firebaseDatabase.getReference("Reports").orderByChild("Year").equalTo(year).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
    private void FetchMonthData() {
        firebaseDatabase.getReference("Reports").orderByChild("Month").equalTo(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        BillingModel model=snapshot1.getValue(BillingModel.class);
                        list.add(model);
                        progressDialog.dismiss();
                    }
                    adminOrdersAdapter=new ReportsAdapter(ReportsDetails.this,list);
                    recyclerView.setAdapter(adminOrdersAdapter);
                    adminOrdersAdapter.notifyDataSetChanged();
                    calculateTotalAmount(list);
                    calculateTotalQuantity(list);

                }
                else {
                    Toast.makeText(ReportsDetails.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportsDetails.this, error.getMessage(), Toast.LENGTH_LONG).show();

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

    //calculate total amount
    private void calculateTotalAmount(List<BillingModel> cartModelList) {
        double totalAmount = 0.0;
        for(BillingModel myCartModel: cartModelList){
            totalAmount +=Double.valueOf(myCartModel.getProductTotal_Price()) ;

        }
        binding.totalPaymentReportDetail.setText("Rs: " + totalAmount);
    }

    //calculate total amount
    private void calculateTotalQuantity(List<BillingModel> cartModelList) {
        double totalAmount = 0.0;
        for(BillingModel myCartModel: cartModelList){
            totalAmount +=Double.valueOf(myCartModel.getProductQuantity()) ;

        }
        binding.totalProductsReportDetail.setText("Qty: " + totalAmount);
    }
}