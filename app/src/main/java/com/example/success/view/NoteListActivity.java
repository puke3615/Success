package com.example.success.view;

import java.util.List;

import android.content.Intent;
import android.view.View;

import com.example.success.R;
import com.example.success.anno.PK;
import com.example.success.base.BaseActivity;
import com.example.success.entity.Note;
import com.example.success.sqlite.INoteDao;
import com.example.success.sqlite.NoteDao;
import com.example.success.widget.NoteListView;
import com.example.success.widget.PKHeadView;
import com.example.success.widget.head.HeadView.Dir;
import com.example.success.widget.head.HeadView.OnHeadClickListener;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:20:34
 * @Mark 
 */
@PK(R.layout.activity_notelist)
public class NoteListActivity extends BaseActivity {
	
	@PK
	private NoteListView listView;
	@PK
	private PKHeadView header;
	
	private INoteDao dao;

	@Override
	protected void init() {
		dao = NoteDao.instance();
	}

	private void queryData() {
		List<Note> data = dao.findAll();
		if (data == null || data.size() == 0) {
			T("暂无数据，写点什么吧``");
		} else {
			listView.adapter().set(data);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		queryData();
	}

	@Override
	protected void initListener() {
		header.setOnHeadClickListener(new OnHeadClickListener() {

			@Override
			public void onHeadClick(Dir dir, View view) {
				switch(dir) {
				case Left:
					finish();
					break;
				case Right:
					startActivity(new Intent(NoteListActivity.this, NoteDetailActivity.class));
					break;
				}
			}
			
		});
	}

}
