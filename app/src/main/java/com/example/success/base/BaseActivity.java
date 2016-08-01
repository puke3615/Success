package com.example.success.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.success.util.ToastUtil;

/**
 * Created by dell on 2015/11/16.
 */
public abstract class BaseActivity extends Activity {

	public static final List<Activity> mTasks = new ArrayList<>();

	protected abstract void init();

	protected abstract void initListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTasks.add(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (useAnno()) {
			InstanceManager.handleActivity(this);
		}
		init();
		initListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTasks.remove(this);
	}

	public static void removeAll() {
		for (Activity activity : mTasks) {
			if (activity != null) {
				activity.finish();
			}
		}
		mTasks.clear();
	}

	protected boolean useAnno() {
		return true;
	}

	public static boolean isEmpty(Object... ss) {
		for (Object s : ss) {
			if (s == null) {
				return true;
			} else if (s instanceof String && ((String) s).length() == 0) {
				return true;
			}
		}
		return false;
	}

	public void T(Object s) {
		ToastUtil.show(s);
	}

	public static String getStr(TextView text) {
		return text.getText().toString().trim();
	}

}
