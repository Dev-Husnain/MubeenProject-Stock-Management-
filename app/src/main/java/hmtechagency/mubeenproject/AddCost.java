package hmtechagency.mubeenproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
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

import hmtechagency.mubeenproject.databinding.ActivityAddCostBinding;


public class AddCost extends AppCompatActivity {
    ActivityAddCostBinding binding;
    String viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        viewType = getIntent().getStringExtra("ExName");
        binding.costType.setText(viewType);

        if (binding.costType.getText().toString().equals("Edit")) {
            binding.DateAdd.setText(getIntent().getStringExtra("Date"));
            binding.paperLengthAdd.setText(getIntent().getStringExtra("paperLength"));
            binding.PaperWidthNameAdd.setText(getIntent().getStringExtra("PaperWidth"));
            binding.gramAdd.setText(getIntent().getStringExtra("gram"));
            binding.RateAdd.setText(getIntent().getStringExtra("Rate"));
            binding.priceOfPaperAdd.setText(getIntent().getStringExtra("priceOfPaper"));
            binding.onePieceAdd.setText(getIntent().getStringExtra("onePiece"));
            binding.RatePricePerPieceAdd.setText(getIntent().getStringExtra("RatePricePerPiece"));
            binding.pricePerPieceAdd.setText(getIntent().getStringExtra("pricePerPiece"));
            binding.silicateValueEnterAdd.setText(getIntent().getStringExtra("silicateValue"));
            binding.silicateAdd.setText(getIntent().getStringExtra("silicate"));
            binding.PrintingAdd.setText(getIntent().getStringExtra("Printing"));
            binding.LaminationLengthAdd.setText(getIntent().getStringExtra("LaminationLength"));
            binding.LiminationWidthAdd.setText(getIntent().getStringExtra("LiminationWidth"));
            binding.LaminationRateAdd.setText(getIntent().getStringExtra("LaminationRate"));
            binding.LaminationTotalAdd.setText(getIntent().getStringExtra("LaminationTotal"));
            binding.PinValueAdd.setText(getIntent().getStringExtra("PinValue"));
            binding.LabourValueTotalAdd.setText(getIntent().getStringExtra("LabourValueTotal"));
            binding.NetValueAdd.setText(getIntent().getStringExtra("NetValue"));
        } else {
            String saveCurrentDate;
            Calendar callForDate = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());
            binding.DateAdd.setText(saveCurrentDate);
        }

        binding.numberPickerCost.setMinValue(1);
        binding.numberPickerCost.setMaxValue(3000);
        binding.numberPickerCost.setValue(1);

        binding.priceOfPaperAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.PaperWidthNameAdd.getText().toString().isEmpty()
                ||binding.paperLengthAdd.getText().toString().isEmpty()
                ||binding.gramAdd.getText().toString().isEmpty()
                ||binding.RateAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    String wValue=binding.PaperWidthNameAdd.getText().toString();
                    Float wValue1=Float.valueOf(wValue);
                    String lValue=binding.paperLengthAdd.getText().toString();
                    Float lValue1=Float.valueOf(lValue);
                    String gValue=binding.gramAdd.getText().toString();
                    Float gValue1=Float.valueOf(gValue);
                    String RateValue=binding.RateAdd.getText().toString();
                    Float RateValue1=Float.valueOf(RateValue);
                    Float TTotalPaper=wValue1*lValue1*gValue1*RateValue1/1550000;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.priceOfPaperAdd.setText(TTTotalPaper);
                }


            }
        });

        binding.NetValueAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.priceOfPaperAdd.getText().toString().isEmpty()
                        ||binding.onePieceAdd.getText().toString().isEmpty()
                        ||binding.silicateAdd.getText().toString().isEmpty()
                        ||binding.PrintingAdd.getText().toString().isEmpty()
                        ||binding.LaminationTotalAdd.getText().toString().isEmpty()
                        ||binding.PinValueAdd.getText().toString().isEmpty()
                        ||binding.LabourValueTotalAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    String PaperTotalValue=binding.priceOfPaperAdd.getText().toString();
                    Float PaperTotalValue1=Float.valueOf(PaperTotalValue);
                    String PieceTotalValue=binding.pricePerPieceAdd.getText().toString();
                    Float PieceTotalValue1=Float.valueOf(PieceTotalValue);
                    String silicatTotalValue=binding.silicateAdd.getText().toString();
                    Float silicatTotalValue1=Float.valueOf(silicatTotalValue);
                    String PrintTotalValue=binding.PrintingAdd.getText().toString();
                    Float PrintTotalValue1=Float.valueOf(PrintTotalValue);
                    String LaminationTotalValue=binding.LaminationTotalAdd.getText().toString();
                    Float LaminationTotalValue1=Float.valueOf(LaminationTotalValue);
                    String PinTotalValue=binding.PinValueAdd.getText().toString();
                    Float PinTotalValue1=Float.valueOf(PinTotalValue);
                    String LabourTotalValue=binding.LabourValueTotalAdd.getText().toString();
                    Float LabourTotalValue1=Float.valueOf(LabourTotalValue);
                    Float TTotalPaper=PaperTotalValue1+PieceTotalValue1+silicatTotalValue1+PrintTotalValue1+LaminationTotalValue1+PinTotalValue1+LabourTotalValue1;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.NetValueAdd.setText(TTTotalPaper);
                }



            }
        });

        binding.LaminationTotalAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.LiminationWidthAdd.getText().toString().isEmpty()
                        ||binding.LaminationLengthAdd.getText().toString().isEmpty()
                        ||binding.LaminationRateAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    String wValue=binding.LiminationWidthAdd.getText().toString();
                    Float wValue1=Float.valueOf(wValue);
                    String lValue=binding.LaminationLengthAdd.getText().toString();
                    Float lValue1=Float.valueOf(lValue);
                    String RateValue=binding.LaminationRateAdd.getText().toString();
                    Float RateValue1=Float.valueOf(RateValue);
                    Float TTotalPaper=wValue1*lValue1*RateValue1/144;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.LaminationTotalAdd.setText(TTTotalPaper);
                }

            }
        });


        binding.silicateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.PaperWidthNameAdd.getText().toString().isEmpty()
                        ||binding.paperLengthAdd.getText().toString().isEmpty()
                        ||binding.silicateValueEnterAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please Calculate Previous Values First", Toast.LENGTH_SHORT).show();
                }
                else {
                    String wValue=binding.PaperWidthNameAdd.getText().toString();
                    Float wValue1=Float.valueOf(wValue);
                    String lValue=binding.paperLengthAdd.getText().toString();
                    Float lValue1=Float.valueOf(lValue);
                    String SilicateValue=binding.silicateValueEnterAdd.getText().toString();
                    Float SilicateValue1=Float.valueOf(lValue);
                    Float TTotalPaper=wValue1*lValue1/SilicateValue1;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.silicateAdd.setText(TTTotalPaper);
                }


            }
        });

        binding.onePieceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.paperLengthAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please enter length of paper", Toast.LENGTH_SHORT).show();
                }
                else {
                    String lValue=binding.paperLengthAdd.getText().toString();
                    Float lValue1=Float.valueOf(lValue);
                    Float TTotalPaper=2400/lValue1;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.onePieceAdd.setText(TTTotalPaper);
                }

            }
        });


        binding.pricePerPieceAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.PaperWidthNameAdd.getText().toString().isEmpty()
                ||binding.RatePricePerPieceAdd.getText().toString().isEmpty()
                ||binding.onePieceAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please Calculate Previous Values First", Toast.LENGTH_LONG).show();
                }
                else {
                    String wValue=binding.PaperWidthNameAdd.getText().toString();
                    Float wValue1=Float.valueOf(wValue);
                    String RateValue=binding.RatePricePerPieceAdd.getText().toString();
                    Float RateValue1=Float.valueOf(RateValue);
                    String PieceValue=binding.onePieceAdd.getText().toString();
                    Float PieceValue1=Float.valueOf(PieceValue);
                    Float TTotalPaper=wValue1*RateValue1/PieceValue1;
                    String TTTotalPaper=String.valueOf(TTotalPaper);
                    binding.pricePerPieceAdd.setText(TTTotalPaper);
                }




            }
        });

        binding.numberPickerCost.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (binding.priceOfPaperAdd.getText().toString().isEmpty()
                        ||binding.onePieceAdd.getText().toString().isEmpty()
                        ||binding.silicateAdd.getText().toString().isEmpty()
                        ||binding.PrintingAdd.getText().toString().isEmpty()
                        ||binding.LaminationTotalAdd.getText().toString().isEmpty()
                        ||binding.PinValueAdd.getText().toString().isEmpty()
                        ||binding.LabourValueTotalAdd.getText().toString().isEmpty()){
                    Toast.makeText(AddCost.this, "Please enter values in all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    String PaperTotalValue=binding.priceOfPaperAdd.getText().toString();
                    Float PaperTotalValue1=Float.valueOf(PaperTotalValue);
                    String PieceTotalValue=binding.onePieceAdd.getText().toString();
                    Float PieceTotalValue1=Float.valueOf(PieceTotalValue);
                    String silicatTotalValue=binding.silicateAdd.getText().toString();
                    Float silicatTotalValue1=Float.valueOf(silicatTotalValue);
                    String PrintTotalValue=binding.PrintingAdd.getText().toString();
                    Float PrintTotalValue1=Float.valueOf(PrintTotalValue);
                    String LaminationTotalValue=binding.LaminationTotalAdd.getText().toString();
                    Float LaminationTotalValue1=Float.valueOf(LaminationTotalValue);
                    String PinTotalValue=binding.PinValueAdd.getText().toString();
                    Float PinTotalValue1=Float.valueOf(PinTotalValue);
                    String LabourTotalValue=binding.LabourValueTotalAdd.getText().toString();
                    Float LabourTotalValue1=Float.valueOf(LabourTotalValue);
                    Float TTotalPaper=PaperTotalValue1+PieceTotalValue1+silicatTotalValue1+PrintTotalValue1+LaminationTotalValue1+PinTotalValue1+LabourTotalValue1;
                    Float FinalNetValue= TTotalPaper*newVal;
                    //SelectedWeek.setText(String.valueOf(newVal));
                    String FinalNetValueString=String.valueOf(FinalNetValue);
                    binding.NetValueAdd.setText(FinalNetValueString);
                }
            }
        });


        binding.iconBackAddCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.iconSaveCostRick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.costType.getText().toString().equals("Edit")) {
                    UpdateExpensesData();
                } else {
                    AddNewExpensesData();
                }

            }
        });
    }

    //update details of expenses
    private void UpdateExpensesData() {
        if (binding.NetValueAdd.getText().toString().isEmpty()) {
            binding.NetValueAdd.setError("Please calculate net Value");
            binding.NetValueAdd.requestFocus();
        } else {
            String ProductTime = getIntent().getStringExtra("timeStamp");
            HashMap<String, Object> hashMapUpdate = new HashMap<>();
            hashMapUpdate.put("PaperWidth", binding.PaperWidthNameAdd.getText().toString());
            hashMapUpdate.put("paperLength", binding.paperLengthAdd.getText().toString());
            hashMapUpdate.put("gram", binding.gramAdd.getText().toString());
            hashMapUpdate.put("Rate", binding.RateAdd.getText().toString());
            hashMapUpdate.put("priceOfPaper", binding.priceOfPaperAdd.getText().toString());
            hashMapUpdate.put("onePiece", binding.onePieceAdd.getText().toString());
            hashMapUpdate.put("RatePricePerPiece", binding.RatePricePerPieceAdd.getText().toString());
            hashMapUpdate.put("pricePerPiece", binding.pricePerPieceAdd.getText().toString());
            hashMapUpdate.put("silicateValue", binding.silicateValueEnterAdd.getText().toString());
            hashMapUpdate.put("silicate", binding.silicateAdd.getText().toString());
            hashMapUpdate.put("Printing", binding.PrintingAdd.getText().toString());
            hashMapUpdate.put("LaminationWidth", binding.LiminationWidthAdd.getText().toString());
            hashMapUpdate.put("LaminationLength", binding.LaminationLengthAdd.getText().toString());
            hashMapUpdate.put("LaminationRate", binding.LaminationRateAdd.getText().toString());
            hashMapUpdate.put("LaminationTotal", binding.LaminationTotalAdd.getText().toString());
            hashMapUpdate.put("PinValue", binding.PinValueAdd.getText().toString());
            hashMapUpdate.put("LabourValueTotal", binding.LabourValueTotalAdd.getText().toString());
            hashMapUpdate.put("NetValue", binding.NetValueAdd.getText().toString());
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("Cost_Record").orderByChild("CostTimeStamps").equalTo(ProductTime);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().updateChildren(hashMapUpdate);
                    }
                    Toast.makeText(AddCost.this, " Updated Successfully", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(AddCost.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });
        }

    }

    //add new data of expenses in database
    private void AddNewExpensesData() {
        if (binding.NetValueAdd.getText().toString().isEmpty()) {
            binding.NetValueAdd.setError("Please calculate net Value");
            binding.NetValueAdd.requestFocus();
        } else {
            String TimeStamps = String.valueOf((new Date().getTime()));
            HashMap<String, Object> hashMapUpdate = new HashMap<>();

            hashMapUpdate.put("PaperWidth", binding.PaperWidthNameAdd.getText().toString());
            hashMapUpdate.put("paperLength", binding.paperLengthAdd.getText().toString());
            hashMapUpdate.put("gram", binding.gramAdd.getText().toString());
            hashMapUpdate.put("Rate", binding.RateAdd.getText().toString());
            hashMapUpdate.put("priceOfPaper", binding.priceOfPaperAdd.getText().toString());
            hashMapUpdate.put("onePiece", binding.onePieceAdd.getText().toString());
            hashMapUpdate.put("RatePricePerPiece", binding.RatePricePerPieceAdd.getText().toString());
            hashMapUpdate.put("pricePerPiece", binding.pricePerPieceAdd.getText().toString());
            hashMapUpdate.put("silicateValue", binding.silicateValueEnterAdd.getText().toString());
            hashMapUpdate.put("silicate", binding.silicateAdd.getText().toString());
            hashMapUpdate.put("Printing", binding.PrintingAdd.getText().toString());
            hashMapUpdate.put("LaminationWidth", binding.LiminationWidthAdd.getText().toString());
            hashMapUpdate.put("LaminationLength", binding.LaminationLengthAdd.getText().toString());
            hashMapUpdate.put("LaminationRate", binding.LaminationRateAdd.getText().toString());
            hashMapUpdate.put("LaminationTotal", binding.LaminationTotalAdd.getText().toString());
            hashMapUpdate.put("PinValue", binding.PinValueAdd.getText().toString());
            hashMapUpdate.put("LabourValueTotal", binding.LabourValueTotalAdd.getText().toString());
            hashMapUpdate.put("NetValue", binding.NetValueAdd.getText().toString());
            hashMapUpdate.put("Date", binding.DateAdd.getText().toString());
            hashMapUpdate.put("CostTimeStamps", TimeStamps);

            FirebaseDatabase.getInstance().getReference().child("Cost_Record").push().setValue(hashMapUpdate);
            Toast.makeText(AddCost.this, "Added Successfully", Toast.LENGTH_LONG).show();

        }

    }


}