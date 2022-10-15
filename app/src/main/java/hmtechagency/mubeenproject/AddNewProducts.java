package hmtechagency.mubeenproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import hmtechagency.mubeenproject.databinding.ActivityAddNewProductsBinding;

public class AddNewProducts extends AppCompatActivity {
    ActivityAddNewProductsBinding binding;
    Uri sFile;
    ImageView ProductPic;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_products);

        Button saveAlertButton = findViewById(R.id.saveProductAdd);
        EditText ProductName = findViewById(R.id.productNameAdd);
        EditText ProductPrice = findViewById(R.id.productPriceAdd);
        EditText ProductQuantity = findViewById(R.id.productQuantityAdd);
        EditText ProductTotalPrice = findViewById(R.id.productTotalAdd);
        ProductPic = findViewById(R.id.productPic);
        ImageView UploadProductPic = findViewById(R.id.uploadProductPic);
        progressDialog=new ProgressDialog(AddNewProducts.this);
        progressDialog.setMessage("Uploading data...");
        progressDialog.setCancelable(false);

        //upload new products to database with image
        saveAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductName.getText().toString().isEmpty()) {
                    ProductName.setError("Please Enter Product Name");
                    ProductName.requestFocus();
                } else if (ProductPrice.getText().toString().isEmpty()) {
                    ProductPrice.setError("Please Enter Product Price");
                    ProductPrice.requestFocus();
                }
                else if (ProductQuantity.getText().toString().isEmpty()) {
                    ProductQuantity.setError("Please Enter Product Quantity");
                    ProductQuantity.requestFocus();
                }
                else if (sFile == null) {
                    Toast.makeText(AddNewProducts.this, "Please Select Image", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.show();
                    final StorageReference reference = FirebaseStorage.getInstance().getReference("ProductsImages/" +UUID.randomUUID().toString());
                    reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(@NonNull Uri uri) {
                                    String saveCurrentDate, saveCurrentTime;
                                    Calendar callForDate = Calendar.getInstance();
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                                    saveCurrentDate = currentDate.format(callForDate.getTime());
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                                    saveCurrentTime = currentTime.format(callForDate.getTime());
                                    String timeStamps = String.valueOf((new Date().getTime()));
                                    final HashMap<String, Object> addToCartMap = new HashMap<>();
                                    addToCartMap.put("currentDate", saveCurrentDate);
                                    addToCartMap.put("currentTime", saveCurrentTime);
                                    addToCartMap.put("TimeStamps", timeStamps);
                                    addToCartMap.put("ProductName", ProductName.getText().toString());
                                    addToCartMap.put("ProductPrice", ProductPrice.getText().toString());
                                    addToCartMap.put("ProductQuantity", ProductQuantity.getText().toString());
                                    addToCartMap.put("ProductTotalPrice", ProductTotalPrice.getText().toString());
                                    addToCartMap.put("ProductImage", uri.toString());
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    database.getReference().child("Products_List").push().setValue(addToCartMap);
                                    Toast.makeText(AddNewProducts.this, "Added Successfully", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    });

                }
            }
        });


        //get image from gallery
        UploadProductPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //  */* if you want to browse other types of files from device then use */*
                startActivityForResult(intent, 33);

            }
        });

        ProductTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductPrice.getText().toString().isEmpty() && ProductQuantity.getText().toString().isEmpty()) {
                    Toast.makeText(AddNewProducts.this, "Please enter price and quantity first", Toast.LENGTH_SHORT).show();
                } else {
                    String s = ProductQuantity.getText().toString();
                    Float f = Float.valueOf(s);
                    String p1 = ProductPrice.getText().toString();
                    Float p = Float.valueOf(p1);
                    Float TotalPrice = f * p;
                    ProductTotalPrice.setText(String.valueOf(TotalPrice));

                }

            }
        });


    }

    //set image to activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null ? data.getData() : null) != null) {
            sFile = data.getData();
            ProductPic.setImageURI(sFile);
        }
        else {
            Toast.makeText(AddNewProducts.this, "Choose image to update profile", Toast.LENGTH_LONG).show();
        }
    }
}