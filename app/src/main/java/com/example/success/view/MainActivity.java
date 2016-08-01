package com.example.success.view;

import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.success.R;
import com.example.success.anno.PK;
import com.example.success.base.BaseActivity;

@PK(R.layout.activity_main)
public class MainActivity extends BaseActivity {

	@PK
	private EditText text;
	@PK
	private Button button, enter;

	@Override
	protected void init() {

	}

	@Override
	protected void initListener() {
		enter.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				go();
				return false;
			}
		});
	}

	public void button() {
		if (getStr(text).equals("静水流深")) {
			go();
		}
	}

	private void go() {
		text.setText("");
		startActivity(new Intent(this, NoteListActivity.class));
	}

}
