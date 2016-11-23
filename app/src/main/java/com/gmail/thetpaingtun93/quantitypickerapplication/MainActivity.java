package com.gmail.thetpaingtun93.quantitypickerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmail.thetpaingtun93.quantitypicker.QuantityPickerView;

public class MainActivity extends AppCompatActivity implements QuantityPickerView.OnPickerQuantityChangedListener {



    QuantityPickerView mQuantityPickerView;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQuantityPickerView = (QuantityPickerView) findViewById(R.id.quantity_picker_up);
        mTextView = (TextView) findViewById(R.id.total_text);


        mQuantityPickerView.setOnPickerQunatityChangedListener(this);


    }

    @Override
    public void onPickerQuantityChanged(int currentQuantity) {
        int totalPrice = 5*currentQuantity;
        String totalpriceString = " RM "+totalPrice;
        mTextView.append(totalpriceString);
    }
}
