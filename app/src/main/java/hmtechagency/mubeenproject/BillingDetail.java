package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import hmtechagency.mubeenproject.databinding.ActivityBillingBinding;
import hmtechagency.mubeenproject.databinding.ActivityBillingDetailBinding;

public class BillingDetail extends AppCompatActivity {
    ActivityBillingDetailBinding binding;
    String TimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBillingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        TimeStamp=getIntent().getStringExtra("TimeStamps");
        binding.billDetailNameAdd.setText(getIntent().getStringExtra("Name"));
        binding.billDetailPriceAdd.setText(getIntent().getStringExtra("Price"));
        binding.billDetailQuantityAdd.setText(getIntent().getStringExtra("Quantity"));
        binding.billDetailTotalPriceAdd.setText(getIntent().getStringExtra("TotalPrice"));
        binding.billDetailDateAdd.setText(getIntent().getStringExtra("Date"));
        binding.billDetailEmailAdd.setText(getIntent().getStringExtra("Email"));
        binding.billDetailProductNameAdd.setText(getIntent().getStringExtra("ProductName"));
        binding.billDetailCommentAdd.setText(getIntent().getStringExtra("Comment"));
        binding.billDetailTypeAdd.setText(getIntent().getStringExtra("Type"));

        binding.iconBackAddBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.billDetailTotalPriceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.billDetailPriceAdd.getText().toString().isEmpty()||binding.billDetailQuantityAdd.getText().toString().isEmpty()){
                    binding.billDetailPriceAdd.setError("Enter Price");
                    binding.billDetailQuantityAdd.setError("Enter Quantity");
                }
                else {
                    String sss = binding.billDetailQuantityAdd.getText().toString();
                    Float fff = Float.valueOf(sss);
                    String ppp = binding.billDetailPriceAdd.getText().toString();
                    Float pppp = Float.valueOf(ppp);
                    Float TTotalPrice = fff * pppp;
                    String TTTotalPrice = String.valueOf(TTotalPrice);
                    binding.billDetailTotalPriceAdd.setText(TTTotalPrice);
                }

            }
        });
        //Update data of billing
        binding.iconSaveBillDetailRick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.billDetailPriceAdd.getText().toString().isEmpty()
                        ||binding.billDetailQuantityAdd.getText().toString().isEmpty()
                        ||binding.billDetailNameAdd.getText().toString().isEmpty()
                        ||binding.billDetailProductNameAdd.getText().toString().isEmpty()
                        ||binding.billDetailEmailAdd.getText().toString().isEmpty()
                        ||binding.billDetailTypeAdd.getText().toString().isEmpty()
                        ||binding.billDetailDateAdd.getText().toString().isEmpty()){
                    binding.billDetailPriceAdd.setError("Enter Price");
                    binding.billDetailQuantityAdd.setError("Enter Quantity");
                }
                else {
                    String sss = binding.billDetailQuantityAdd.getText().toString();
                    Float fff = Float.valueOf(sss);
                    String ppp = binding.billDetailPriceAdd.getText().toString();
                    Float pppp = Float.valueOf(ppp);
                    Float TTotalPrice = fff * pppp;
                    String TTTotalPrice = String.valueOf(TTotalPrice);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("ProductName", binding.billDetailProductNameAdd.getText().toString());
                    map.put("ProductPrice", binding.billDetailPriceAdd.getText().toString());
                    map.put("ProductTotal_Price", TTTotalPrice);
                    map.put("ProductQuantity", binding.billDetailQuantityAdd.getText().toString());
                    map.put("Name", binding.billDetailNameAdd.getText().toString());
                    map.put("Email", binding.billDetailEmailAdd.getText().toString());
                    map.put("Date", binding.billDetailDateAdd.getText().toString());
                    map.put("Comment", binding.billDetailCommentAdd.getText().toString());
                    map.put("Type", binding.billDetailTypeAdd.getText().toString());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Billing").orderByChild("TimeOfIssuesReceived")
                            .equalTo(TimeStamp);
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().updateChildren(map);
                            }
                            Toast.makeText(BillingDetail.this, " Updated Successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(BillingDetail.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                }

            }
        });

    }
}