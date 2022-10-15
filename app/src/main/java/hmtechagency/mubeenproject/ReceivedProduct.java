package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import hmtechagency.mubeenproject.databinding.ActivityReceivedProductBinding;

public class ReceivedProduct extends AppCompatActivity {
    ActivityReceivedProductBinding binding;
    EditText SQ;
    TextView AQ;
    String Name12, Price12;
    int f,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReceivedProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        AQ = findViewById(R.id.availableQuantityReceive);
        SQ = findViewById(R.id.receiveQuantity);
        String saveCurrentDate;
        Calendar callForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());
        binding.receiveDate.setText(saveCurrentDate);
        binding.receiveSelectCustomer.setText(getIntent().getStringExtra("SupplierName"));
        binding.supplierEmailReceive.setText(getIntent().getStringExtra("SupplierEmail"));

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
        String quantity5 = pref.getString("key_quantity", null);
        Name12 = pref.getString("PName1", null);
        Price12 = pref.getString("PPrice1", null);
        AQ.setText(quantity5);

        binding.receiveSelectCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceivedProduct.this, SelectSuppliers.class));

            }
        });
        binding.iconBackAddReceiveGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.iconSaveReceiveGoodsRick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                /////////////////////////////////
                if (!binding.receiveQuantity.getText().toString().isEmpty()){
                    String s = SQ.getText().toString();
                    f = Integer.parseInt(s);
                    String p1 = AQ.getText().toString();
                    p = Integer.parseInt(p1);
                }

                if (binding.receiveQuantity.getText().toString().isEmpty()) {
                    binding.receiveQuantity.setError("Enter quantity");
                    binding.receiveQuantity.requestFocus();
                } else if (binding.receiveSelectCustomer.getText().toString().isEmpty()) {
                    binding.receiveSelectCustomer.setError("Select Customer");
                    binding.receiveSelectCustomer.requestFocus();
                }

                 else {

                    SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPrefAdapter", MODE_PRIVATE);
                    String ProductTime = pref1.getString("ProductTimeStamp", null);
                    //get current months, year,day
                    Date date12=new Date();
                    SimpleDateFormat sdfMonth=new SimpleDateFormat("MM");
                    SimpleDateFormat sdfYear=new SimpleDateFormat("yyyy");
                    String month=sdfMonth.format(date12);
                    String year=sdfYear.format(date12);
                    ///////////////////////////////////////////
                    LocalDate date32 = LocalDate.now();
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    String weekOfYear= String.valueOf(date32.get(weekFields.weekOfWeekBasedYear())-1);
                    //String WeekOfYear= String.valueOf(date32.get(weekFields.weekOfMonth())); //week of month
                    DateTime dt=new DateTime();
                    String dayOfYear = String.valueOf(dt.getDayOfYear());
                    //Month.setText(dayOfYear);
                    //Month.setText(dayOfYear);
                    /////////////////////////////////////////////////////////////////
                    //calculate to price of products
                    String sss = SQ.getText().toString();
                    Float fff = Float.valueOf(sss);
                    String ppp = Price12;
                    Float pppp = Float.valueOf(ppp);
                    Float TTotalPrice = fff * pppp;
                    String TTTotalPrice = String.valueOf(TTotalPrice);
                    String timeOfIssuesReceived=String.valueOf((new Date().getTime()));

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefQuantity", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("key_nameQuantity", binding.receiveQuantity.getText().toString());
                    // Save the changes in SharedPreferences
                    editor.apply(); // commit changes
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Month", month);
                    map.put("Year", year);
                    map.put("WeekOfYear", weekOfYear);
                    map.put("DayOfYear", dayOfYear);
                    map.put("ProductName", Name12);
                    map.put("ProductPrice", Price12);
                    map.put("ProductTotal_Price", TTTotalPrice);
                    map.put("ProductQuantity", SQ.getText().toString());
                    map.put("Name", binding.receiveSelectCustomer.getText().toString());
                    map.put("Email", binding.supplierEmailReceive.getText().toString());
                    map.put("Date", binding.receiveDate.getText().toString());
                    map.put("Comment", binding.receiveComment.getText().toString());
                    map.put("TimeOfIssuesReceived", timeOfIssuesReceived);
                    map.put("Type", "Incoming");
                    map.put("Status", "Pending");
                    map.put("Remaining", TTTotalPrice);
                    FirebaseDatabase.getInstance().getReference().child("ReceivedProducts_List").push().setValue(map);
                    FirebaseDatabase.getInstance().getReference().child("Billing").push().setValue(map);
                    FirebaseDatabase.getInstance().getReference().child("Reports").push().setValue(map);
                    //update quantity
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Products_List").orderByChild("TimeStamps")
                            .equalTo(ProductTime);

                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String s4 = SQ.getText().toString();
                            int f4 = Integer.parseInt(s4);
                            String p4 = AQ.getText().toString();
                            int p5 = Integer.parseInt(p4);
                            int remainingQuantity = p5+f4;
                            String TotalRemainingQuantity = String.valueOf(remainingQuantity);

                            HashMap<String, Object> map12 = new HashMap<>();
                            map12.put("ProductQuantity", TotalRemainingQuantity);
                            map12.put("ProductTotalPrice", TTTotalPrice);
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().updateChildren(map12);
                            }
                            Toast.makeText(ReceivedProduct.this, ""+s4+" "+Name12+" Received Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ReceivedProduct.this, ProductsDetailsActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ReceivedProduct.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });
                    ////////////////////

                }


            }
        });
    }
}