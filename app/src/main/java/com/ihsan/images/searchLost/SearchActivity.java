package com.ihsan.images.searchLost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ihsan.images.R;

import org.jetbrains.annotations.NotNull;

import in.aabhasjindal.otptextview.OtpTextView;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    LinearLayout existLayer, notExistLayer, registrationLayer, searchLayer;
    Button startSearch, call, submit, registerBtn;
    TextView name, plateNumber;
    EditText nameEditTxt, phoneEditTxt, myPhoneEditTxt;
    OtpTextView searchEditTxt, plateEditTxt;
    DatabaseReference lostPlatesRef = FirebaseDatabase.getInstance().getReference("lostPlates");
    String phoneNumber;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String myPhoneNumberKey = "myPhoneNumber";
    String myPhoneNumber;
    String regex = "^[\\u0621-\\u064A]{1,4}\\d{1,4}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        existLayer = findViewById(R.id.existLayer);
        notExistLayer = findViewById(R.id.notExistLayer);
        searchEditTxt = findViewById(R.id.searchEditTxt);
        startSearch = findViewById(R.id.startSearch);
        call = findViewById(R.id.call);
        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        plateNumber = findViewById(R.id.plateNumber);
        nameEditTxt = findViewById(R.id.nameEditTxt);
        plateEditTxt = findViewById(R.id.plateEditTxt);
        phoneEditTxt = findViewById(R.id.phoneEditTxt);
        registrationLayer = findViewById(R.id.registrationLayer);
        searchLayer = findViewById(R.id.searchLayer);
        registerBtn = findViewById(R.id.registerBtn);
        myPhoneEditTxt = findViewById(R.id.myPhoneEditTxt);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyData();
    }

    public void searchClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case R.id.startSearch:
                searchOfDataBase();
                break;
            case R.id.call:
                startPhoneCall();
                break;
            case R.id.submit:
                sendData();
                break;
            case R.id.registerBtn:
                myPhoneNumber = myPhoneEditTxt.getText().toString();
                if (myPhoneNumber.length() == 11) {
                    saveMyPhone();
                    getMyData();
                    Toast.makeText(this, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "رقم الهاتف غير صحيح", Toast.LENGTH_LONG).show();
                }
                break;


        }
    }


    private void searchOfDataBase() {
        String searchTxt = searchEditTxt.getOtp();
        if (isOnline(this)) {
            if (searchTxt.matches(regex)) {
                lostPlatesRef.orderByKey().equalTo(searchTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        existLayer.setVisibility(View.GONE);
                        notExistLayer.setVisibility(View.VISIBLE);
                        nameEditTxt.setText("");
                        phoneEditTxt.setText(myPhoneNumber);
                        plateEditTxt.setOTP(searchTxt);

                        if (dataSnapshot.hasChildren()) {
                            for (DataSnapshot getAll : dataSnapshot.getChildren()) {

                                SearchData plateData = getAll.getValue(SearchData.class);

                                if (!myPhoneNumber.equals(plateData.getPhoneNumber())) {
                                    existLayer.setVisibility(View.VISIBLE);
                                    notExistLayer.setVisibility(View.GONE);
                                    searchEditTxt.setOTP("");

                                    phoneNumber = plateData.getPhoneNumber();
                                    name.setText("الاسم : "+plateData.getName());
                                    plateNumber.setText("رقم اللوحة : "+plateData.getPlateNumber().replace(""," "));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError error) {
                        Toast.makeText(SearchActivity.this, "حدث خطأ ما حاول مرة أخرى", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(this, "تأكد من رقم اللوحة", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "تأكد من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    }

    private void startPhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void sendData() {
        if (isOnline(this)) {

            String plateNum = plateEditTxt.getOtp();
            String name = nameEditTxt.getText().toString();
            String phoneNum = phoneEditTxt.getText().toString();

            if (plateNum.matches(regex)) {
                if (name.length() > 2) {
                    if (phoneNum.length() == 11) {

                        SearchData plateData = new SearchData(name, plateNum, phoneNum);
                        lostPlatesRef.child(plateNum).setValue(plateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(SearchActivity.this, "تم تسجيل البيانات", Toast.LENGTH_LONG).show();

                                    existLayer.setVisibility(View.GONE);
                                    notExistLayer.setVisibility(View.GONE);

                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "تأكد من كتابة رقم الهاتف بشكل صحيح", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "تأكد من كتابة الاسم بشكل صحيح", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "تأكد من رقم اللوحة", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "تأكد من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    }

    private void getMyData() {
        myPhoneNumber = sharedPref.getString(myPhoneNumberKey, null);
        if (myPhoneNumber == null) {
            searchLayer.setVisibility(View.GONE);
            registrationLayer.setVisibility(View.VISIBLE);
        } else {
            searchLayer.setVisibility(View.VISIBLE);
            registrationLayer.setVisibility(View.GONE);
        }
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void saveMyPhone() {
        editor = sharedPref.edit();
        editor.putString(myPhoneNumberKey, myPhoneNumber);
        editor.apply();
    }
}