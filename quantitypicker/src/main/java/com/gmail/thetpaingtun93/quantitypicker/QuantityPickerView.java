package com.gmail.thetpaingtun93.quantitypicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LEO on 11/22/16.
 */

public class QuantityPickerView extends LinearLayout implements View.OnClickListener {


    private ImageView mPlusButton;
    private ImageView mMinusButton;
    private TextView mCountText;



    //store the current count
    private int mCurrentCount = 0;

    private static final int MINUM_POSSIBLE_COUNT = 0;
    private static final int MAXINUM_POSSIBLE_COUNT = 15;


    public QuantityPickerView(Context context) {
        super(context);

        initailzeView(context);
    }

    public QuantityPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initailzeView(context);
    }

    public QuantityPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initailzeView(context);
    }

    private void initailzeView(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_quantity_picker, this, true);


        //similar to findViewById()
        mPlusButton = (ImageView) this.findViewById(R.id.quantity_picker_plus_btn);
        mCountText = (TextView) this.findViewById(R.id.quantity_picker_count_text);
        mMinusButton = (ImageView) this.findViewById(R.id.quantity_picker_minus_btn);



        mPlusButton.setOnClickListener(this);
        mMinusButton.setOnClickListener(this);

        mMinusButton.setEnabled(false); // initially disable the minus button


    }


    //handle the button click
    @Override
    public void onClick(View v) {
        String countString = "";
        if (v.getId() == mPlusButton.getId()) {
            mCurrentCount++;
        } else {
            mCurrentCount--;
        }

        if (mCurrentCount == MINUM_POSSIBLE_COUNT) { //disable plus the button if the upper limit is reached
            mMinusButton.setEnabled(false);
        } else {
            mMinusButton.setEnabled(true);
        }

        if (mCurrentCount == MAXINUM_POSSIBLE_COUNT) { //disable minus the button if the lower limit is reached
            mPlusButton.setEnabled(false);
        } else {
            mPlusButton.setEnabled(true);
        }

        if (mCurrentCount < 10) {           //to add extra 0 to single digit number
            countString = "0" + mCurrentCount;
        } else {
            countString = String.valueOf(mCurrentCount);
        }

        mCountText.setText(countString);  //set textview with current count

    }

}
