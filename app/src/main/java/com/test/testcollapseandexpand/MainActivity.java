package com.test.testcollapseandexpand;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends BaseClass {


    TextView tv_clicktext,tv_paytotal;
    LinearLayout hidden_view,base_cardview2,base_cardview3,base_cardview4,base_cardview5,base_cardview6;
    ConstraintLayout submit,tv_pay,cardview_progres,cl_main;
    ViewPager mViewPager;
    LinearLayout base_cardview;
    int[] images = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4};
    String[] locationName = {"Santorini","Marrakech","Santo","Kech"};
    String[] locationDesc = {"NEW OSOGBO","NEPTUNE","NEW TUNE","TUNEBO"};
    String[] locationPrice = {"0.6","1.2","1.5","1.8"};
    ViewPagerAdapter mViewPagerAdapter;
    CheckBox cb_child;
    NumberPicker numberPickerchild;
    Button mPickDateButton;
    TextView mShowSelectedDateText,tv_total,tv_done;
    double totalprice=0.0;
    float adultprice = 0.6f,childprice = 0.3f,totaladultprice=0.0f;
    ImageView iv_facescan,iv_payarrow,iv_seatsarrow;
    int rotationAngle = 0;
    RecyclerView rv_location;
    ArrayList<LocationObj> arrayList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    ImageView iv_back1,ic_back2;
    Boolean isclicked=false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        base_cardview = findViewById(R.id.base_cardview);
        base_cardview2 = findViewById(R.id.base_cardview2);
        mViewPager = findViewById(R.id.viewPagerMain);
        tv_clicktext = findViewById(R.id.tv_clicktext);
        submit = findViewById(R.id.submit);
        base_cardview3 = findViewById(R.id.base_cardview3);
        base_cardview4 = findViewById(R.id.base_cardview4);
        base_cardview5 = findViewById(R.id.base_cardview5);
        base_cardview6 = findViewById(R.id.base_cardview6);
        cb_child = findViewById(R.id.cb_child);
        tv_total = findViewById(R.id.tv_total);
        iv_facescan = findViewById(R.id.iv_facescan);
        tv_pay = findViewById(R.id.tv_pay);
        tv_paytotal = findViewById(R.id.tv_paytotal);
        cardview_progres = findViewById(R.id.cardview_progres);
        iv_payarrow = findViewById(R.id.iv_payarrow);
        iv_seatsarrow = findViewById(R.id.iv_seatsarrow);
        tv_done = findViewById(R.id.tv_done);
        rv_location = findViewById(R.id.rv_location);
        bottomNavigationView =findViewById(R.id.bottom_navigation);
        numberPickerchild = findViewById(R.id.number_picker_child);
        iv_back1 = findViewById(R.id.iv_back1);
        ic_back2 = findViewById(R.id.ic_back2);
        cl_main = findViewById(R.id.cl_main);
        mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, images,base_cardview,bottomNavigationView,cl_main,
                locationName,locationDesc,locationPrice,isclicked);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setPageTransformer( true, new ZoomInTransformer() );

        tv_clicktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(base_cardview2, getApplicationContext(), 2, "2");
                collapse(base_cardview,getApplicationContext());
                runthread();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationAngle = rotationAngle == 0 ? 180 : 0;  //toggle
                iv_seatsarrow.animate().rotation(rotationAngle).setDuration(500).start();
                setMargins(base_cardview4,0,0,50,0);
                expand(base_cardview4, getApplicationContext(), 3, "3");
            }
        });
        setnumberpicker();
        setchildnumberpicker();
        totalprice = adultprice;
        String totalprice1= String.format("%.2f", totalprice);
        tv_total.setText("Total "+totalprice1+" BTC");


        cb_child.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    numberPickerchild.setVisibility(View.VISIBLE);
                    totalprice = totaladultprice + childprice;
                    String totalprice1= String.format("%.2f", totalprice);
                    tv_total.setText("Total "+totalprice1+" BTC");
                }else{
                    numberPickerchild.setVisibility(View.GONE);
                }
            }
        });

        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
                        materialDateBuilder.setTitleText("SELECT A DATE");
                        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                        materialDatePicker.addOnPositiveButtonClickListener(
                                new MaterialPickerOnPositiveButtonClickListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onPositiveButtonClick(Object selection) {
                                        mShowSelectedDateText.setText("Trip Date : " + materialDatePicker.getHeaderText());
                                        mShowSelectedDateText.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                });

        Glide.with(this).load(R.drawable.facescan).into(iv_facescan);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotationAngle = rotationAngle == 0 ? 180 : 0;  //toggle
                iv_payarrow.animate().rotation(rotationAngle).setDuration(500).start();
                tv_paytotal.setText(String.format("%.2f", totalprice)+" BTC");
                expand(base_cardview5, getApplicationContext(), 4, "4");
            }
        });
        base_cardview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(base_cardview6,getApplicationContext(), 5, "5");
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse(base_cardview6,getApplicationContext());
                collapse(base_cardview5,getApplicationContext());
                collapse(base_cardview4,getApplicationContext());
                collapse(base_cardview3,getApplicationContext());
                collapse(base_cardview2,getApplicationContext());
                expand(cl_main,getApplicationContext(),7,"7");
                isclicked =false;
                mViewPagerAdapter.notifyDataSetChanged();
                // MainActivity.this.recreate();
            }
        });

        LoadRecyclerview();
        setUpBottomNavigation();


        iv_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collapse(base_cardview,getApplicationContext());
                expand(cl_main,getApplicationContext(),7,"7");
            }
        });

    }

    private void setUpBottomNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_favorites:
                        Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_nearby:
                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void LoadRecyclerview() {

        arrayList.add(new LocationObj("Nature",R.drawable.pic1));
        arrayList.add(new LocationObj("Mountains",R.drawable.pic2));
        arrayList.add(new LocationObj("Futuristic",R.drawable.pic3));
        arrayList.add(new LocationObj("City",R.drawable.pic4));
        arrayList.add(new LocationObj("Beaches",R.drawable.pic2));
        arrayList.add(new LocationObj("Nightlife",R.drawable.pic3));
        LocationAdapter adapter = new LocationAdapter(arrayList, getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false);

        rv_location.setLayoutManager(layoutManager);
        rv_location.setNestedScrollingEnabled(true);
        rv_location.setAdapter(adapter);

    }

    private void setchildnumberpicker() {

        numberPickerchild.setDividerColor(ContextCompat.getColor(this, R.color.transparent));
        numberPickerchild.setDividerColorResource(R.color.transparent );
        numberPickerchild.setSelectedTextColor(ContextCompat.getColor(this, R.color.white));
        numberPickerchild.setSelectedTextColorResource(R.color.white);

        numberPickerchild.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPickerchild.setSelectedTextSize(R.dimen.selected_text_size);
        numberPickerchild.setTextColor(ContextCompat.getColor(this, R.color.black));
        numberPickerchild.setTextColorResource(R.color.black);

        numberPickerchild.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPickerchild.setTextSize(R.dimen.text_size);

        numberPickerchild.setMaxValue(10);
        numberPickerchild.setMinValue(1);
        numberPickerchild.setValue(1);

        numberPickerchild.setFadingEdgeEnabled(true);
        numberPickerchild.setScrollerEnabled(true);
        numberPickerchild.setWrapSelectorWheel(true);
        numberPickerchild.setAccessibilityDescriptionEnabled(true);
        numberPickerchild.setOrientation(LinearLayout.HORIZONTAL);

        numberPickerchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on current value");
            }
        });

        numberPickerchild.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                totalprice = totaladultprice +childprice * newVal;
                String totalprice1= String.format("%.2f", totalprice);
                tv_total.setText("Total "+totalprice1+" BTC");
            }
        });

        numberPickerchild.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.d(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));

                }
            }
        });
    }

    private void setnumberpicker() {
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);

        numberPicker.setDividerColor(ContextCompat.getColor(this, R.color.transparent));
        numberPicker.setDividerColorResource(R.color.transparent );

        numberPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.white));
        numberPicker.setSelectedTextColorResource(R.color.white);

        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);

        numberPicker.setTextColor(ContextCompat.getColor(this, R.color.black));
        numberPicker.setTextColorResource(R.color.black);

        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);

        numberPicker.setFadingEdgeEnabled(true);
        numberPicker.setScrollerEnabled(true);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setAccessibilityDescriptionEnabled(true);
        numberPicker.setOrientation(LinearLayout.HORIZONTAL);

        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on current value");
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                totaladultprice = adultprice * newVal;
                String totalprice1= String.format("%.2f", totaladultprice);
                tv_total.setText("Total "+ totalprice1+" BTC");
            }
        });

        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.d(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));

                }
            }
        });
    }

    private void runthread() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                expand(base_cardview3, getApplicationContext(),6, "6");
                collapse(cardview_progres,getApplicationContext());
            }
        }, 4000);
    }

}