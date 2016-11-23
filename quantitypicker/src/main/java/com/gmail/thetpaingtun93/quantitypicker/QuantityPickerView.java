package com.gmail.thetpaingtun93.quantitypicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
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

    private static final String SUPER_CLASS_KEY = "SuperClassKey";
    private static final String CURRENT_COUNT_KEY = "CurrentCountKey";

    private OnPickerQuantityChangedListener mOnPickerQuantityChangedListener = null;


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


    //handle the button click
    @Override
    public void onClick(View v) {
        if (v.getId() == mPlusButton.getId()) {
            mCurrentCount++;
        } else {
            mCurrentCount--;
        }

        if(mOnPickerQuantityChangedListener!=null){
            mOnPickerQuantityChangedListener.onPickerQuantityChanged(mCurrentCount); //notify the quantity change
        }
        else
            throw new RuntimeException("Need to set setOnPickerQunatityChangedListener(...).");


        setButtonState();
        updateCountText();
    }

    //====================================== getter and setter =====================================================//

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public void setCurrentCount(int count) throws IllegalArgumentException {
        if (count < MINUM_POSSIBLE_COUNT || count > MAXINUM_POSSIBLE_COUNT)
            throw new IllegalArgumentException("count is too small or too big");

        mCurrentCount = count;
        updateCountText();   //update textview with new count value
        setButtonState();
    }

    //====================================== private helper methods =====================================================//

    private void initailzeView(final Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_quantity_picker, this, true);


        //similar to findViewById()
        mPlusButton = (ImageView) this.findViewById(R.id.quantity_picker_plus_btn);
        mCountText = (TextView) this.findViewById(R.id.quantity_picker_count_text);
        mMinusButton = (ImageView) this.findViewById(R.id.quantity_picker_minus_btn);


        mPlusButton.setOnClickListener(this);
        mMinusButton.setOnClickListener(this);

        setButtonState();

    }

    private void updateCountText() {
        String countString = "";
        if (mCurrentCount < 10) {           //to add extra 0 to single digit number
            countString = "0" + mCurrentCount;
        } else {
            countString = String.valueOf(mCurrentCount);
        }

        mCountText.setText(countString);  //set textview with current count
    }

    private void setButtonState() {
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
    }


    //====================================== Restoring the state of the view =====================================================//


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(SUPER_CLASS_KEY, super.onSaveInstanceState());
        bundle.putInt(CURRENT_COUNT_KEY, mCurrentCount);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            super.onRestoreInstanceState(bundle.getParcelable(SUPER_CLASS_KEY));

            mCurrentCount = bundle.getInt(CURRENT_COUNT_KEY);
            setCurrentCount(mCurrentCount);
        } else
            super.onRestoreInstanceState(state);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    //notify the quantity change to any class that implement this interface
    //in order to use this class this is interface is a must to implement
    public interface OnPickerQuantityChangedListener{
        void onPickerQuantityChanged(int currentQuantity);
    }


    public void setOnPickerQunatityChangedListener(OnPickerQuantityChangedListener onPickerQunatityChangedListener){
        this.mOnPickerQuantityChangedListener = onPickerQunatityChangedListener;
    }


}
