package com.sukohi.lib;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class FitTextView extends TextView {
	
    private float minTextSize = 10f;
    private String viewText;
	private Paint paint;
 
    public FitTextView(Context context) {
        super(context);
    }
 
    public FitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setFitText(CharSequence text) {
    	
    	setText(text);
    	
    	paint = new Paint();
		paint.setTextSize(getTextSize());
		FontMetrics fontMetrics = paint.getFontMetrics();
		int paddingHeight = getPaddingTop() + getPaddingBottom();
		int viewHeight = (int) (Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent) + paddingHeight);
		setHeight(viewHeight);
    	
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        
        fitText();
        
    }
    
    private void fitText() {
 
    	viewText = this.getText().toString();

    	if(viewText.equals("")) {

    		return;
    		
    	}
    	
    	paint = new Paint();
        final int viewWidth = this.getWidth() - (getPaddingRight() + getPaddingLeft());
        float textSize = getTextSize();
        
        if(minTextSize >= textSize) {

        	textSize = minTextSize;
        	
        } else {
        	
        	float textWidth;
        	
            for (float fitTextSize = textSize; fitTextSize > minTextSize; fitTextSize--) {
            	
                textWidth = getTextWidth(fitTextSize);
                
                if(textWidth < viewWidth) {
                	
                	textSize = fitTextSize;
                	break;
                	
                }
            	
    		}
            
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        invalidate();
        
    }
    
    private float getTextWidth(float textSize) {
    	
    	paint.setTextSize(textSize);
    	return paint.measureText(viewText);
    	
    }
 
}

/*** Example

	// xml

	<com.sukohi.lib.FitTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="test text"
        android:textColor="#000000"
        android:textSize="25sp"
        android:paddingRight="5dp"
        android:paddingLeft="5dp"
        android:gravity="center" />

	// Java

	FitTextView fitTextView = new FitTextView();
	fitTextView.setFitText(text);

***/
