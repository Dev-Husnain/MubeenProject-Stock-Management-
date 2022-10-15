package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import hmtechagency.mubeenproject.databinding.ActivityAddExpensesBinding;

public class AddExpenses extends AppCompatActivity {
    ActivityAddExpensesBinding binding;
    String viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpensesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        viewType = getIntent().getStringExtra("ExName");

        binding.expensesNameAdd.setText(getIntent().getStringExtra("ExName"));
        binding.expensesDateAdd.setText(getIntent().getStringExtra("ExDate"));
        binding.expensesDescriptionAdd.setText(getIntent().getStringExtra("ExDescription"));
        binding.ExpensesPriceAdd.setText(getIntent().getStringExtra("ExPrice"));
        binding.expensesType.setText(viewType);



        //check if we have to add new data or update existing data

        if (binding.expensesType.getText().toString().equals(viewType)) {
            binding.expensesNameAdd.setText(getIntent().getStringExtra("ExName"));
            binding.expensesDateAdd.setText(getIntent().getStringExtra("ExDate"));
            binding.expensesDescriptionAdd.setText(getIntent().getStringExtra("ExDescription"));
            binding.ExpensesPriceAdd.setText(getIntent().getStringExtra("ExPrice"));
            binding.expensesType.setText(viewType);
        } else {
            String saveCurrentDate;
            Calendar callForDate = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());
            binding.expensesDateAdd.setText(saveCurrentDate);
        }



        binding.iconSaveExpensesRick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.expensesType.getText().toString().equals(viewType)) {
                    UpdateExpensesData();
                } else {
                    AddNewExpensesData();
                }

            }
        });
        binding.iconBackAddExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //update details of expenses
    private void UpdateExpensesData() {
        if (binding.ExpensesPriceAdd.getText().toString().isEmpty() || binding.expensesNameAdd.getText().toString().isEmpty()
                || binding.expensesDateAdd.getText().toString().isEmpty()) {
            binding.ExpensesPriceAdd.setError("Please enter expenses");
            binding.ExpensesPriceAdd.requestFocus();
            binding.expensesNameAdd.setError("Please enter expenses name");
            binding.expensesNameAdd.requestFocus();
            binding.expensesDateAdd.setError("Please enter date");
            binding.expensesDateAdd.requestFocus();
        } else {
            String ProductTime = getIntent().getStringExtra("timeStamp");
            HashMap<String, Object> hashMapUpdate = new HashMap<>();
            hashMapUpdate.put("Expenses", binding.ExpensesPriceAdd.getText().toString());
            hashMapUpdate.put("ExpensesName", binding.expensesNameAdd.getText().toString());
            hashMapUpdate.put("ExpensesDescription", binding.expensesDescriptionAdd.getText().toString());
            hashMapUpdate.put("ExpensesDate", binding.expensesDateAdd.getText().toString());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("ExpensesList").orderByChild("ExpensesTimeStamps").equalTo(ProductTime);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().updateChildren(hashMapUpdate);
                    }
                    Toast.makeText(AddExpenses.this, " Updated Successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(AddExpenses.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });
        }

    }

    //add new data of expenses in database
    private void AddNewExpensesData() {
        if (binding.ExpensesPriceAdd.getText().toString().isEmpty() || binding.expensesNameAdd.getText().toString().isEmpty()
                || binding.expensesDateAdd.getText().toString().isEmpty()) {
            binding.ExpensesPriceAdd.setError("Please enter expenses");
            binding.ExpensesPriceAdd.requestFocus();
            binding.expensesNameAdd.setError("Please enter expenses name");
            binding.expensesNameAdd.requestFocus();
            binding.expensesDateAdd.setError("Please enter date");
            binding.expensesDateAdd.requestFocus();
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            String TimeStamps = String.valueOf((new Date().getTime()));
            hashMap.put("Expenses", binding.ExpensesPriceAdd.getText().toString());
            hashMap.put("ExpensesName", binding.expensesNameAdd.getText().toString());
            hashMap.put("ExpensesDescription", binding.expensesDescriptionAdd.getText().toString());
            hashMap.put("ExpensesDate", binding.expensesDateAdd.getText().toString());
            hashMap.put("ExpensesTimeStamps", TimeStamps);
            FirebaseDatabase.getInstance().getReference().child("ExpensesList").push().setValue(hashMap);
            Toast.makeText(AddExpenses.this, "Added Successfully", Toast.LENGTH_LONG).show();

        }

    }
}