package hmtechagency.mubeenproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

import hmtechagency.mubeenproject.databinding.ActivityAddCustomersBinding;

public class AddCustomersActivity extends AppCompatActivity {
    ActivityAddCustomersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCustomersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.iconBackAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCustomersActivity.this,CustomersActivity.class));
            }
        });

        //upload customer data to firebase database
        binding.iconSaveCustomersRick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.customerNameAdd.getText().toString().isEmpty() ||binding.customerPhoneAdd.getText().toString().isEmpty()
                        ||binding.customerEmailAdd.getText().toString().isEmpty())
                {
                    binding.customerNameAdd.setError("Please enter name");
                    binding.customerNameAdd.requestFocus();
                    binding.customerPhoneAdd.setError("Please enter phone");
                    binding.customerPhoneAdd.requestFocus();
                    binding.customerEmailAdd.setError("Please enter email");
                    binding.customerEmailAdd.requestFocus();
                }
                else {
                    HashMap<String, Object>hashMap=new HashMap<>();
                    String timeStampss= String.valueOf((new Date().getTime()));
                    hashMap.put("CustomerName",binding.customerNameAdd.getText().toString());
                    hashMap.put("CustomerEmail",binding.customerEmailAdd.getText().toString());
                    hashMap.put("CustomerPhone",binding.customerPhoneAdd.getText().toString());
                    hashMap.put("CustomerAddress",binding.customerAddressAdd.getText().toString());
                    hashMap.put("CustomerBankDetail",binding.customerBankDetailsAdd.getText().toString());
                    hashMap.put("CustomerDiscount",binding.customerDiscountAdd.getText().toString());
                    hashMap.put("CustomerNotes",binding.customerNotestAdd.getText().toString());
                    hashMap.put("CustomerTimeStamps",timeStampss);
                    FirebaseDatabase.getInstance().getReference().child("Customers_List").push().setValue(hashMap);
                    Toast.makeText(AddCustomersActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}