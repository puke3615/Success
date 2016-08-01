/**
 * 
 */
package com.example.success.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.success.R;
import com.example.success.widget.head.HeadView;


/**
 * @version 2015-4-16
 * @author wzj
 * @Mark
 */
public class PKHeadView extends HeadView {

	public PKHeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PKHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PKHeadView(Context context) {
		super(context);
	}
	
	@Override
	protected void init(Context context, AttributeSet attrs) {
		super.init(context, attrs);
		setBackgroundResource(R.drawable.bg_header);
		RelativeLayout layout = layout();
		RelativeLayout.LayoutParams lp = (LayoutParams) layout.getLayoutParams();
		lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
		layout.setLayoutParams(lp);
		
		title().setTextColor(Color.WHITE);

		leftText().setTextColor(Color.WHITE);
		leftText().setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		leftText().setOnTouchListener(null);
		
		title().setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		
		rightText().setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		rightText().setTextColor(Color.WHITE);
		
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
	@Override
	public void returnTextViewClickEffect(TextView view) {
		view.setTextColor(Color.WHITE);
	}
	
	@Override
	public void setTextViewClickEffect(TextView view) {
		view.setTextColor(Color.GRAY);
	}

	@Override
	public void setImageViewClickEffect(ImageView view) {
		super.setImageViewClickEffect(view);
	}

	@Override
	public void returnImageViewClickEffect(ImageView view) {
		super.returnImageViewClickEffect(view);
	}
	

}
