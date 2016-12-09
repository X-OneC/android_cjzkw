package com.android.cjzk.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.cjzk.R;
import com.android.cjzk.Utility.Utility;

public class MyTextView extends TextView {
    private static final String TAG = CollapsedTextView.class.getName();
    private int textSize;

    public MyTextView(Context context) {
        super(context);
        init(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public TextPaint getPaint() {
        return super.getPaint();
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            float ratioWidth = (float) Utility.screenWidth / 720;
            float ratioHeight = (float) Utility.screenHeight / 1280;
            float RATIO = Math.min(ratioWidth, ratioHeight);
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CollapsedTextView);
            textSize = ta.getInt(R.styleable.CollapsedTextView_textSize, 0);
            setTextSize(Math.round(textSize * RATIO));
            ta.recycle();
        }

    }
}
