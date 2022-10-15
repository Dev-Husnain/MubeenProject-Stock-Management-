package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.HashMap;

import hmtechagency.mubeenproject.databinding.ActivityProductsDetailsBinding;

public class ProductsDetailsActivity extends AppCompatActivity {
    ActivityProductsDetailsBinding binding;


    String TimeStamp;
    ImageView ProductImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        ProductImage=findViewById(R.id.productImageDetail);
        TimeStamp = getIntent().getStringExtra("ProductTimeStamp");


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefAdapter", MODE_PRIVATE);
        String ProductName = pref.getString("ProductName", null);
        String ProductQuantity = pref.getString("ProductQuantity", null);
        String ProductPrice = pref.getString("ProductPrice", null);
        String ProductPic = pref.getString("img", null);
        //calculate to price of products
        Float fff = Float.valueOf(ProductQuantity);
        Float pppp = Float.valueOf(ProductPrice);
        Float TTotalPrice = fff * pppp;
        String TTTotalPrice = String.valueOf(TTotalPrice);
        binding.productNameDetail.setText(ProductName);
        binding.productPriceDetail.setText(ProductPrice);
        binding.productQuantityDetail.setText(ProductQuantity);
        binding.productTotalPriceDetail.setText(TTTotalPrice);
        Glide.with(getApplicationContext()).load(ProductPic).into(ProductImage);


        binding.productTotalPriceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sss = binding.productQuantityDetail.getText().toString();
                Float fff = Float.valueOf(sss);
                String ppp = binding.productPriceDetail.getText().toString();
                Float pppp = Float.valueOf(ppp);
                Float TTotalPrice = fff * pppp;
                String TTTotalPrice = String.valueOf(TTotalPrice);
                binding.productTotalPriceDetail.setText(TTTotalPrice);

            }
        });

        binding.iconBackProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.iconSaveProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                String sss = binding.productQuantityDetail.getText().toString();
                Float fff = Float.valueOf(sss);
                String ppp = binding.productPriceDetail.getText().toString();
                Float pppp = Float.valueOf(ppp);
                Float TTotalPrice = fff * pppp;
                String TTTotalPrice = String.valueOf(TTotalPrice);
                map.put("ProductName", binding.productNameDetail.getText().toString());
                map.put("ProductPrice", binding.productPriceDetail.getText().toString());
                map.put("ProductQuantity", binding.productQuantityDetail.getText().toString());
                map.put("ProductTotalPrice", TTTotalPrice);
                SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPrefAdapter", MODE_PRIVATE);
                String ProductTime = pref1.getString("ProductTimeStamp", null);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Products_List").orderByChild("TimeStamps")
                        .equalTo(ProductTime);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().updateChildren(map);
                        }
                        Toast.makeText(ProductsDetailsActivity.this, "Updated Successfully", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProductsDetailsActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });


            }
        });

        binding.receiveProductsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("key_quantity", binding.productQuantityDetail.getText().toString());
                editor.putString("key_name5", binding.productQuantityDetail.getText().toString());
                editor.putString("PName1", binding.productNameDetail.getText().toString());
                editor.putString("PPrice1", binding.productPriceDetail.getText().toString());
                editor.apply();
                Intent intent = new Intent(ProductsDetailsActivity.this, ReceivedProduct.class);
                startActivity(intent);
            }
        });

        binding.issueProductsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("key_quantity", binding.productQuantityDetail.getText().toString());
                editor.putString("key_name5", binding.productQuantityDetail.getText().toString());
                editor.putString("PName1", binding.productNameDetail.getText().toString());
                editor.putString("PPrice1", binding.productPriceDetail.getText().toString());
                editor.apply();
                Intent intent = new Intent(ProductsDetailsActivity.this, IssueProducts.class);
                startActivity(intent);


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}