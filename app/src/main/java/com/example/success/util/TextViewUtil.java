package com.example.success.util;

/**
 * Created by dell on 2015/10/28.
 */

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

/**
 * Created by dell on 2015/10/28.
 */
public class TextViewUtil {

    public static interface OnTextViewDragListener {
        void onDrag(TextView textView, float offset);

        void onRelease(TextView textView, float maxOffset);
    }

    public static void setDragListener(TextView textView, OnTextViewDragListener listener) {
        if (textView == null || listener == null) {
            return;
        }

        if (!textView.isClickable()) {
            textView.setClickable(true);
        }
        textView.setOnTouchListener(new DefaultTouchListener(listener));
    }



    public static void setLetterDragListener(TextView textView) {
        setDragListener(textView, new OnTextViewDragListener() {

            boolean flag = true;


            @Override
            public void onDrag(TextView textView, float offset) {
                //textView.setLetterSpacing(getSpace(offset));
            }

            float getSpace(float offset) {
                return (float) (0.005 * offset);
            }

            @Override
            public void onRelease(TextView textView, float maxOffset) {
                if (maxOffset != 0) {
                    postTextViewLetter(textView, getSpace(maxOffset), 0);
                }
            }
        });
    }

    @SuppressLint("NewApi")
	public static void postTextViewLetter(TextView textView, long time, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "letterSpacing", from, to);
        animator.setDuration(time);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    public static void postTextViewLetter(TextView textView, float from, float to) {
        postTextViewLetter(textView, 500, from, to);
    }

    private static class DefaultTouchListener implements View.OnTouchListener {

        float x;
        OnTextViewDragListener mListener;

        public DefaultTouchListener(OnTextViewDragListener listener) {
            mListener = listener;
        }

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = e.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float offset = e.getX() - x;
                    if (offset > 0) {
                        mListener.onDrag((TextView) v, offset);
                    } else if (offset == 0) {
                        mListener.onRelease((TextView) v, 0);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    float max = (int) (e.getX() - x);
                    if (max > 0) {
                        mListener.onRelease((TextView) v, e.getX() - x);
                    }
                    break;
            }
            return false;
        }
    }

}
