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

import hmtechagency.mubeenproject.databinding.ActivityIssueProductsBinding;

public class IssueProducts extends AppCompatActivity {
    ActivityIssueProductsBinding binding;
    //String availableQuantity;
    EditText SQ;
    TextView AQ;
    String Name12, Price12;
    int f,p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIssueProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        AQ = findViewById(R.id.availableQuantityIssue);
        SQ = findViewById(R.id.issueQuantity);

        String saveCurrentDate;
        Calendar callForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());
        binding.issueDate.setText(saveCurrentDate);
        //ger customer name and email from intent
        binding.issueSelectCustomer.setText(getIntent().getStringExtra("CustomerName"));
        binding.customerEmailIssue.setText(getIntent().getStringExtra("CustomerEmail"));


        //get data from shared prefernce
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String quantity5 = pref.getString("key_quantity", null);
        Name12 = pref.getString("PName1", null);
        Price12 = pref.getString("PPrice1", null);
        AQ.setText(quantity5);

        binding.iconBackAddIssueGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.issueSelectCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IssueProducts.this, SelectCustomer.class));

            }
        });
        //get data from shared preference and store data to database
        binding.iconSaveIssueGoodsRick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                /////////////////////////////////
                if (!binding.issueQuantity.getText().toString().isEmpty()){
                    String s = SQ.getText().toString();
                    f = Integer.parseInt(s);
                    String p1 = AQ.getText().toString();
                    p = Integer.parseInt(p1);
                }
                if (binding.issueQuantity.getText().toString().isEmpty()) {
                    binding.issueQuantity.setError("Enter quantity");
                    binding.issueQuantity.requestFocus();
                } else if (binding.issueSelectCustomer.getText().toString().isEmpty()) {
                    binding.issueSelectCustomer.setError("Select Customer");
                    binding.issueSelectCustomer.requestFocus();
                }
                else if (f > p) {
                    Toast.makeText(IssueProducts.this, "Insufficient stock quantity value", Toast.LENGTH_SHORT).show();
                    binding.issueQuantity.setError("Insufficient stock quantity");
                    binding.issueQuantity.requestFocus();
                } else {

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

                    //get data from shared preference
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefQuantity", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("key_nameQuantity", binding.issueQuantity.getText().toString());
                    // Save the changes in SharedPreferences
                    editor.apply(); // commit changes
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Month", month);
                    map.put("Year", year);
                    map.put("WeekOfYear", weekOfYear);
                    map.put("DayOfYear", dayOfYear);
                    map.put("ProductName", Name12);
                    map.put("ProductTotal_Price", TTTotalPrice);
                    map.put("ProductPrice", Price12);
                    map.put("ProductQuantity", SQ.getText().toString());
                    map.put("Name", binding.issueSelectCustomer.getText().toString());
                    map.put("Email", binding.customerEmailIssue.getText().toString());
                    map.put("Date", binding.issueDate.getText().toString());
                    map.put("Comment", binding.issueComment.getText().toString());
                    map.put("TimeOfIssuesReceived", timeOfIssuesReceived);
                    map.put("Type", "Outgoing");
                    map.put("Status", "Pending");
                    map.put("Remaining", TTTotalPrice);
                    FirebaseDatabase.getInstance().getReference().child("IssuedProducts_List").push().setValue(map);
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
                            int remainingQuantity = p5 - f4;
                            String TotalRemainingQuantity = String.valueOf(remainingQuantity);

                            HashMap<String, Object> map12 = new HashMap<>();
                            map12.put("ProductQuantity", TotalRemainingQuantity);
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().updateChildren(map12);
                            }
                            Toast.makeText(IssueProducts.this, ""+s4+" "+Name12+" Issued Successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(IssueProducts.this, ProductsDetailsActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(IssueProducts.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onCancelled", databaseError.toException());
                        }
                    });


                }


            }
        });
    }
}