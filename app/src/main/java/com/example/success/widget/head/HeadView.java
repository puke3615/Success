package com.example.success.widget.head;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.success.R;


/**
 * 
 * @Mark 自定义头部视图
 * @Author wzj
 * @Version 2015-3-4 上午9:23:11
 */
public class HeadView extends RelativeLayout {

	private TextView leftText, rightText, title;
	private ImageView leftImage, rightImage;
	private ProgressBar bar;
	private RelativeLayout layout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private OnHeadClickListener listener;
	private Context context;
	// 保存当前显示的视图
	private List<View> leftList = new ArrayList<View>();
	private List<View> rightList = new ArrayList<View>();

	/**
	 * @Mark 枚举类型：Left(左)，Right(右)
	 * @Author wzj
	 * @Version 2015-3-4 上午10:25:41
	 */
	public enum Dir {
		Left, Right;
	}

	/**
	 * @Mark 回调接口，用于回调头部左右控件的点击事件
	 * @Author wzj
	 * @Version 2015-3-4 上午10:23:14
	 */
	public interface OnHeadClickListener {
		void onHeadClick(Dir dir, View view);
	}

	public void setOnHeadClickListener(OnHeadClickListener listener) {
		this.listener = listener;
	}

	public HeadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public HeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public HeadView(Context context) {
		super(context);
		init(context, null);
	}

	protected void init(Context context, AttributeSet attrs) {
		this.context = context;
		removeAllViews();
		LayoutInflater.from(context).inflate(R.layout.item_header, this);
		OnClick listener = new OnClick();
		leftText = leftText();
		rightText = rightText();
		leftImage = leftImage();
		rightImage = rightImage();
		title = title();
		bar = bar();
		(leftLayout = getView(null, R.id.leftLayout)).setTag(leftList);
		(rightLayout = getView(null, R.id.rightLayout)).setTag(rightList);
		leftLayout.setOnTouchListener(new OnTouch());
		rightLayout.setOnTouchListener(new OnTouch());
		leftLayout.setOnClickListener(listener);
		rightLayout.setOnClickListener(listener);
		layout = getView(null, R.id.headViewLayout);
		if (attrs != null) {// 设置属性
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadView);
			// 设置stringId
			setValue(leftText, typedArray.getResourceId(R.styleable.HeadView_leftText, -1), 0);
			setValue(rightText, typedArray.getResourceId(R.styleable.HeadView_rightText, -1), 0);
			setValue(title, typedArray.getResourceId(R.styleable.HeadView_title, -1), 0);
			// 设置string
			setValue(leftText, typedArray.getString(R.styleable.HeadView_leftText), 1);
			setValue(rightText, typedArray.getString(R.styleable.HeadView_rightText), 1);
			setValue(title, typedArray.getString(R.styleable.HeadView_title), 1);
			// 设置图片
			setValue(leftImage, typedArray.getResourceId(R.styleable.HeadView_leftImage, -1));
			setValue(rightImage, typedArray.getResourceId(R.styleable.HeadView_rightImage, -1));
			// 设置背景
			setValue(layout, typedArray.getResourceId(R.styleable.HeadView_background, -1), 0);
			setValue(layout, typedArray.getColor(R.styleable.HeadView_background, -1), 1);
			setValue(layout, typedArray.getDimension(R.styleable.HeadView_height, -1), 2);
			typedArray.recycle();
		}
	}

	private class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (listener != null) {
				switch (v.getId()) {
				case R.id.leftLayout:
					listener.onHeadClick(Dir.Left, v);
					break;
				case R.id.rightLayout:
					listener.onHeadClick(Dir.Right, v);
					break;
				}
			}
		}

	}

	private class OnTouch implements OnTouchListener {// 添加点击效果

		private long startTime;

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			@SuppressWarnings("unchecked")
			List<View> list = (List<View>) v.getTag();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startTime = System.currentTimeMillis();
				setEffect(list);
				break;
			case MotionEvent.ACTION_UP:
				returnEffect(list);
				if (System.currentTimeMillis() - startTime <= 300) {
					performClick();
				}
				break;
			case MotionEvent.ACTION_CANCEL:
				returnEffect(list);
				break;
			}
			return false;
		}

		private void setEffect(List<View> list) {// 设置点击效果
			for (View view : list) {
				if (view instanceof TextView) {
					setTextViewClickEffect((TextView) view);
				} else if (view instanceof ImageView) {
					setImageViewClickEffect((ImageView) view);
				}
			}
		}

		private void returnEffect(List<View> list) {// 返回正常状态
			for (View view : list) {
				if (view instanceof TextView) {
					returnTextViewClickEffect((TextView) view);
				} else if (view instanceof ImageView) {
					returnImageViewClickEffect((ImageView) view);
				}
			}
		}
	}

	private void setValue(View view, Object value) {
		setValue(view, value, 0);
	}

	/**
	 * xml中给指定控件设置相关属性
	 * 
	 * @param view
	 * @param value
	 * @param flag
	 */
	private void setValue(View view, Object value, int flag) {
		if (view == null) {
			throw new NullPointerException("设置控件为 Null");
		}
		int integer = -1;
		float f = -1;
		if (value instanceof Integer && (integer = (Integer) value) == -1) {
			return;
		}
		if (value instanceof Float && (f = (Float) value) == -1) {
			return;
		}
		if (value == null) {
			return;
		}
		if (view instanceof TextView) {// TextView
			show(view);
			if (flag == 0) {
				((TextView) view).setText(integer);
			} else if (flag == 1) {
				((TextView) view).setText(value.toString());
			}
		} else if (view instanceof ImageView) {// ImageView
			show(view);
			((ImageView) view).setImageResource(integer);
		} else if (view instanceof RelativeLayout) {// 父控件
			if (flag == 0) {
				view.setBackgroundResource(integer);
			} else if (flag == 1) {
				view.setBackgroundColor(integer);
			} else if (flag == 2) {
				view.getLayoutParams().height = (int) f;
			}
		}
	}

	// 显示控件,每显示一个，即添加入List
	public void show(View view) {
		view.setVisibility(0);
		ViewParent parent = view.getParent();
		if (parent == rightLayout) {
			rightList.add(view);
		} else if (parent == leftLayout) {
			leftList.add(view);
		}
	}

	// 初始化各个控件成员变量
	@SuppressWarnings("unchecked")
	private <T> T getView(T view, int id) {
		if (view == null) {
			view = (T) findViewById(id);
			// 初始化处理，添加属性样式
			if (view instanceof TextView) {
				TextView tv = (TextView) view;
				int style;
				switch (id) {
				case R.id.leftText:
					if ((style = getLeftTextStyle()) != -1) {
						tv.setTextAppearance(context, style);
					}
					break;
				case R.id.rightText:
					if ((style = getRightTextStyle()) != -1) {
						tv.setTextAppearance(context, style);
					}
					break;
				case R.id.title:
					if ((style = getTitleStyle()) != -1) {
						tv.setTextAppearance(context, style);
					}
					break;
				}
			}
		}
		return view;
	}

	/**
	 * 设置图片亮度值
	 * 
	 * @param imageview
	 *            imageview
	 * @param brightness
	 *            0-255
	 */
	private void changeLight(ImageView imageview, int brightness) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0,
				brightness, 0, 0, 0, 1, 0 });
		imageview.setColorFilter(new ColorMatrixColorFilter(matrix));
	}

	// ========================设置方法开始===========================

	/** 设置头部背景颜色 **/
	public void setBackgroundColor(int color) {
		layout.setBackgroundColor(color);
	}

	/** 设置头部背景图片 **/
	public void setBackgroundResource(int resId) {
		layout.setBackgroundResource(resId);
	}

	/** 设置标题内容 **/
	public void setTitle(int resId) {
		title().setText(resId);
	}

	/** 设置标题内容 **/
	public void setTitle(String title) {
		title().setText(title);
	}

	/** 设置左部TextView内容 **/
	public void setLeftText(int resId) {
		leftText().setText(resId);
	}

	/** 设置左部ImageView内容 **/
	public void setLeftImage(int resId) {
		leftImage().setImageResource(resId);
	}

	/** 设置左部ImageView内容 **/
	public void setLeftImage(Drawable drawable) {
		leftImage().setImageDrawable(drawable);
	}

	/** 设置左部TextView内容 **/
	public void setLeftText(String title) {
		leftText().setText(title);
	}

	/** 设置左部TextView大小 **/
	public void setLeftTextSize(int size) {
		leftText().setTextSize(size);
	}

	/** 设置左部TextView颜色 **/
	public void setLeftTextColor(int color) {
		leftText().setTextColor(color);
	}

	/** 设置右部ImageView内容 **/
	public void setRightImage(int resId) {
		rightImage().setImageResource(resId);
	}

	/** 设置右部ImageView内容 **/
	public void setRightImage(Drawable drawable) {
		rightImage().setImageDrawable(drawable);
	}

	/** 设置右部TextView内容 **/
	public void setRightText(int resId) {
		rightText().setText(resId);
	}

	/** 设置右部TextView内容 **/
	public void setRightText(String title) {
		rightText().setText(title);
	}

	/** 设置右部TextView大小 **/
	public void setRightTextSize(int size) {
		rightText().setTextSize(size);
	}

	/** 设置右部TextView颜色 **/
	public void setRightTextColor(int color) {
		rightText().setTextColor(color);
	}

	/** 显示加载条，隐藏右部其他控件 **/
	public void showBar() {
		for (View view : rightList) {
			view.setVisibility(8);
		}
		bar.setVisibility(0);
	}

	/** 取消加载进度条，恢复初始状态 **/
	public void dismissBar() {
		bar.setVisibility(8);
		for (View view : rightList) {
			view.setVisibility(0);
		}
	}

	// ========================设置方法结束===========================

	/** 左部TextView对象 **/
	protected int getLeftTextStyle() {
		return -1;
	}

	/** 右部TextView对象 **/
	protected int getRightTextStyle() {
		return -1;
	}

	/** 标题样式 **/
	protected int getTitleStyle() {
		return -1;
	}

	/** 得到头部对象 **/
	protected RelativeLayout layout() {
		return getView(layout, R.id.headViewLayout);
	}

	/** 得到左部视图对象 **/
	protected ImageView leftImage() {
		return getView(leftImage, R.id.leftImage);
	}

	/** 得到左部TextView对象 **/
	protected TextView leftText() {
		return getView(leftText, R.id.leftText);
	}

	/** 得到标题对象 **/
	protected TextView title() {
		return getView(title, R.id.title);
	}

	/** 得到右部TextView对象 **/
	protected TextView rightText() {
		return getView(rightText, R.id.rightText);
	}

	/** 得到右部视图对象 **/
	protected ImageView rightImage() {
		return getView(rightImage, R.id.rightImage);
	}

	/** 得到进度条对象 **/
	protected ProgressBar bar() {
		return getView(bar, R.id.bar);
	}

	// ===================添加点击效果=======================
	/** 点击效果 **/
	protected void setTextViewClickEffect(TextView view) {
		view.setTextColor(Color.WHITE);
	}

	/** 恢复点击效果 **/
	protected void returnTextViewClickEffect(TextView view) {
		view.setTextColor(Color.BLACK);
	}

	/** 点击效果 **/
	protected void setImageViewClickEffect(ImageView view) {
		changeLight(view, -80);
	}

	/** 恢复点击效果 **/
	protected void returnImageViewClickEffect(ImageView view) {
		changeLight(view, 0);
	}
}
