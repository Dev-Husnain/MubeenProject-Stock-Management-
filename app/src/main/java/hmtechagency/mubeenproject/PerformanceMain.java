package hmtechagency.mubeenproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

import hmtechagency.mubeenproject.databinding.ActivityPerformanceMainBinding;

public class PerformanceMain extends AppCompatActivity {
    ActivityPerformanceMainBinding binding;
    DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;

    int year,month,day;
    String[] MonthsLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPerformanceMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        MonthsLists=getResources().getStringArray(R.array.monthsList);

        Calendar calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
         month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        binding.iconBackAddPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.supplierReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.sales_custome_dialog_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(true);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                RadioButton CurrentMonth=v.findViewById(R.id.currentMonthRadio);
                RadioButton CurrentWeek=v.findViewById(R.id.CurrentWeekRadio);
                RadioButton CurrentYear=v.findViewById(R.id.CurrentYearRadio);
                RadioButton Last60=v.findViewById(R.id.lastSixtyRadio);
                RadioButton Last30=v.findViewById(R.id.lastThirtyRadio);
                RadioButton Last90=v.findViewById(R.id.lastNinetyRadio);
                Button OK=v.findViewById(R.id.OKRadioButton);
                Button Cancel=v.findViewById(R.id.CancelRadioButton);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                OK.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        if (CurrentMonth.isChecked()){
                            Calendar calendar=Calendar.getInstance();
                            int year=calendar.get(Calendar.YEAR);
                            int month=calendar.get(Calendar.MONTH);
                            int day=calendar.get(Calendar.DAY_OF_MONTH);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM");
                            String CustomerMonth1=simpleDateFormat.format(Calendar.getInstance().getTime());
                            Intent intentCM=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentCM.putExtra("CustomerMonth",CustomerMonth1);
                            intentCM.putExtra("Type","SupplierMonth");
                            startActivity(intentCM);
                            alertDialog.dismiss();
                        }
                        else if (Last30.isChecked()){
                            Intent intentLast30=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentLast30.putExtra("Type","Last30DaysSupplier");
                            startActivity(intentLast30);
                            alertDialog.dismiss();
                        }
                        else if (Last60.isChecked()){
                            Intent intentLast60=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentLast60.putExtra("Type","Last60DaysSupplier");
                            startActivity(intentLast60);
                            alertDialog.dismiss();
                        }
                        else if (Last90.isChecked()){
                            Intent intentLast90=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentLast90.putExtra("Type","Last90DaysSupplier");
                            startActivity(intentLast90);
                            alertDialog.dismiss();
                        }
                        else if (CurrentYear.isChecked()){
                            Calendar calendar=Calendar.getInstance();
                            int year=calendar.get(Calendar.YEAR);
                            int month=calendar.get(Calendar.MONTH);
                            int day=calendar.get(Calendar.DAY_OF_MONTH);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
                            String CustomerYear1=simpleDateFormat.format(Calendar.getInstance().getTime());
                            Intent intentCurrentYear=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentCurrentYear.putExtra("CustomerYear",CustomerYear1);
                            intentCurrentYear.putExtra("Type","CurrentYearSupplier");
                            startActivity(intentCurrentYear);
                            alertDialog.dismiss();
                        }
                        else if (CurrentWeek.isChecked()){
                            LocalDate date32 = LocalDate.now();
                            WeekFields weekFields = WeekFields.of(Locale.getDefault());
                            String weekOfYear= String.valueOf(date32.get(weekFields.weekOfWeekBasedYear())-1);
                            //String WeekOfYear= String.valueOf(date32.get(weekFields.weekOfMonth())); //week of month
                            DateTime dt=new DateTime();
                            String dayOfYear = String.valueOf(dt.getDayOfYear());
                            Intent intentLast90=new Intent(PerformanceMain.this,ReportsDetails.class);
                            intentLast90.putExtra("CurrentWeek",weekOfYear);
                            intentLast90.putExtra("Type","CurrentWeekSupplier");
                            startActivity(intentLast90);
                            alertDialog.dismiss();
                        }
                        else {
                            Toast.makeText(PerformanceMain.this, "Select at least one ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        binding.customersReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.sales_custome_dialog_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(true);
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                RadioButton CurrentMonth=v.findViewById(R.id.currentMonthRadio);
                RadioButton CurrentWeek=v.findViewById(R.id.CurrentWeekRadio);
                RadioButton CurrentYear=v.findViewById(R.id.CurrentYearRadio);
                RadioButton Last60=v.findViewById(R.id.lastSixtyRadio);
                RadioButton Last30=v.findViewById(R.id.lastThirtyRadio);
                RadioButton Last90=v.findViewById(R.id.lastNinetyRadio);
                Button OK=v.findViewById(R.id.OKRadioButton);
                Button Cancel=v.findViewById(R.id.CancelRadioButton);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
               OK.setOnClickListener(new View.OnClickListener() {
                   @RequiresApi(api = Build.VERSION_CODES.O)
                   @Override
                   public void onClick(View v) {
                       if (CurrentMonth.isChecked()){
                           Calendar calendar=Calendar.getInstance();
                           int year=calendar.get(Calendar.YEAR);
                           int month=calendar.get(Calendar.MONTH);
                           int day=calendar.get(Calendar.DAY_OF_MONTH);
                           SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM");
                           String CustomerMonth1=simpleDateFormat.format(Calendar.getInstance().getTime());

                           Intent intentCM=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentCM.putExtra("CustomerMonth",CustomerMonth1);
                           intentCM.putExtra("Type","CustomerMonth");
                           startActivity(intentCM);
                           alertDialog.dismiss();
                       }
                       else if (Last30.isChecked()){
                           Intent intentLast30=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentLast30.putExtra("Type","Last30Days");
                           startActivity(intentLast30);
                           alertDialog.dismiss();
                       }
                       else if (Last60.isChecked()){
                           Intent intentLast60=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentLast60.putExtra("Type","Last60Days");
                           startActivity(intentLast60);
                           alertDialog.dismiss();
                       }
                       else if (Last90.isChecked()){
                           Intent intentLast90=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentLast90.putExtra("Type","Last90Days");
                           startActivity(intentLast90);
                           alertDialog.dismiss();
                       }
                       else if (CurrentYear.isChecked()){
                           Calendar calendar=Calendar.getInstance();
                           int year=calendar.get(Calendar.YEAR);
                           int month=calendar.get(Calendar.MONTH);
                           int day=calendar.get(Calendar.DAY_OF_MONTH);
                           SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
                           String CustomerYear1=simpleDateFormat.format(Calendar.getInstance().getTime());
                           Intent intentCurrentYear=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentCurrentYear.putExtra("CustomerYear",CustomerYear1);
                           intentCurrentYear.putExtra("Type","CurrentYearCustomer");
                           startActivity(intentCurrentYear);
                           alertDialog.dismiss();
                       }
                       else if (CurrentWeek.isChecked()){
                           LocalDate date32 = LocalDate.now();
                           WeekFields weekFields = WeekFields.of(Locale.getDefault());
                           String weekOfYear= String.valueOf(date32.get(weekFields.weekOfWeekBasedYear())-1);
                           //String WeekOfYear= String.valueOf(date32.get(weekFields.weekOfMonth())); //week of month
                           DateTime dt=new DateTime();
                           String dayOfYear = String.valueOf(dt.getDayOfYear());
                           Intent intentLast90=new Intent(PerformanceMain.this,ReportsDetails.class);
                           intentLast90.putExtra("CurrentWeek",weekOfYear);
                           intentLast90.putExtra("Type","CurrentWeek");
                           startActivity(intentLast90);
                           alertDialog.dismiss();
                       }
                       else {
                           Toast.makeText(PerformanceMain.this, "Select at least one ", Toast.LENGTH_SHORT).show();
                       }

                   }
               });

            }
        });
        binding.weekWiseReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.week_wise_number_picker_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(false);
                //alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView SelectedWeek=v.findViewById(R.id.weekNumberTV);
                NumberPicker WeekNumberPicker=v.findViewById(R.id.weekNumberPicker);
                Button SetWeek=v.findViewById(R.id.weekPickerSetButton);
                Button CancelWeek=v.findViewById(R.id.weekPickerCancelButton);
                WeekNumberPicker.setMinValue(0);
                WeekNumberPicker.setMaxValue(54);
                WeekNumberPicker.setValue(0);
                WeekNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        SelectedWeek.setText(String.valueOf(newVal));
                    }
                });

                SetWeek.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentYear=new Intent(PerformanceMain.this,ReportsDetails.class);
                        intentYear.putExtra("Week",SelectedWeek.getText().toString());
                        intentYear.putExtra("Type","WeekOfYear");
                        startActivity(intentYear);
                        alertDialog.dismiss();
                    }
                });
                CancelWeek.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });


            }
        });
        binding.monthWiseSaleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.month_picker_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(false);
                //alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView SelectedMonth=v.findViewById(R.id.monthNumberTV);
                NumberPicker monthNumberPicker=v.findViewById(R.id.monthNumberPicker);
                Button SetMonth=v.findViewById(R.id.monthPickerSetButton);
                Button CancelMonth=v.findViewById(R.id.monthPickerCancelButton);
                monthNumberPicker.setMinValue(0);
                monthNumberPicker.setMaxValue(12);
                monthNumberPicker.setValue(00);
                monthNumberPicker.setDisplayedValues(MonthsLists);
                monthNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        SelectedMonth.setText(String.valueOf(MonthsLists[newVal]));
                    }
                });
                SetMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentYear=new Intent(PerformanceMain.this,ReportsDetails.class);
                        intentYear.putExtra("Month",SelectedMonth.getText().toString());
                        intentYear.putExtra("Type","Month");
                        startActivity(intentYear);
                        alertDialog.dismiss();
                    }
                });
                CancelMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
            }
        });
        binding.yearWiseSaleReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v= LayoutInflater.from(view.getContext()).inflate(R.layout.year_picker_sample, null);
                AlertDialog alertDialog=new AlertDialog.Builder(view.getContext())
                        .setView(v).show();
                alertDialog.setCancelable(false);
                //alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView SelectedYear=v.findViewById(R.id.yearNumberTV);
                NumberPicker YearNumberPicker=v.findViewById(R.id.yearNumberPicker);
                Button SetYear=v.findViewById(R.id.yearPickerSetButton);
                Button CancelYear=v.findViewById(R.id.yearPickerCancelButton);
                YearNumberPicker.setMinValue(2000);
                YearNumberPicker.setMaxValue(2099);
                YearNumberPicker.setValue(2021);


                YearNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        SelectedYear.setText(String.valueOf(newVal));
                    }
                });

                SetYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentYear=new Intent(PerformanceMain.this,ReportsDetails.class);
                        intentYear.putExtra("Year",SelectedYear.getText().toString());
                        intentYear.putExtra("Type","Year");
                        startActivity(intentYear);
                        alertDialog.dismiss();
                    }
                });
                CancelYear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       alertDialog.dismiss();
                    }
                });


            }
        });

    }
}