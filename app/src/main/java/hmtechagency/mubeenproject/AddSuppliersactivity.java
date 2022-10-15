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
import hmtechagency.mubeenproject.databinding.ActivityAddSuppliersactivityBinding;

public class AddSuppliersactivity extends AppCompatActivity {

    ActivityAddSuppliersactivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddSuppliersactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.iconBackAddSuppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSuppliersactivity.this,SuppliersActivity.class));
            }
        });

        //store supplier data to database
        binding.iconSaveSuppliersRick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.supplierNameAdd.getText().toString().isEmpty() ||binding.supplierPhoneAdd.getText().toString().isEmpty()
                        ||binding.supplierEmailAdd.getText().toString().isEmpty())
                {
                    binding.supplierNameAdd.setError("Please enter name");
                    binding.supplierNameAdd.requestFocus();
                    binding.supplierPhoneAdd.setError("Please enter phone");
                    binding.supplierPhoneAdd.requestFocus();
                    binding.supplierEmailAdd.setError("Please enter email");
                    binding.supplierEmailAdd.requestFocus();
                }
                else {
                    HashMap<String, Object> hashMap=new HashMap<>();
                    String timeStampss= String.valueOf((new Date().getTime()));
                    hashMap.put("SupplierName",binding.supplierNameAdd.getText().toString());
                    hashMap.put("SupplierEmail",binding.supplierEmailAdd.getText().toString());
                    hashMap.put("SupplierPhone",binding.supplierPhoneAdd.getText().toString());
                    hashMap.put("SupplierAddress",binding.supplierAddressAdd.getText().toString());
                    hashMap.put("SupplierBankDetail",binding.supplierBankDetailsAdd.getText().toString());
                    hashMap.put("SupplierDiscount",binding.supplierDiscountAdd.getText().toString());
                    hashMap.put("SupplierNotes",binding.supplierNotestAdd.getText().toString());
                    hashMap.put("SupplierTimeStamps",timeStampss);
                    FirebaseDatabase.getInstance().getReference().child("Suppliers_List").push().setValue(hashMap);
                    Toast.makeText(AddSuppliersactivity.this, "Added Successfully", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}