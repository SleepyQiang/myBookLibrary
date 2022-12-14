package com.jnu.booklibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

@SuppressLint("AppCompatCustomView")
public class ReSpinner extends Spinner {
    public boolean isDropDownMenuShown = false;

    /**
     * @param context 用来解决原生spinner点击同一选项无反应的问题
     */
    public ReSpinner(Context context) {
        super(context);
    }

    public ReSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = (position == getSelectedItemPosition());
        super.setSelection(position, animate);
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public boolean performClick() {
        this.isDropDownMenuShown = true;
        return super.performClick();
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = (position == getSelectedItemPosition());
        super.setSelection(position);
        if (sameSelected) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }
}
