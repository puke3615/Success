package com.example.success.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.success.adapter.NoteAdapter;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:47:51
 * @Mark 
 */
public class NoteListView extends ListView {
	
	private NoteAdapter mAdapter;

	public NoteListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * @param context
	 */
	private void init(Context context) {
		mAdapter = new NoteAdapter(context, null);
		setAdapter(mAdapter);
		setOnItemClickListener(mAdapter);
	}
	
	public NoteAdapter adapter() {
		return mAdapter;
	}

}
