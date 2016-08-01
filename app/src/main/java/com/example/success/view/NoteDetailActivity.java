package com.example.success.view;

import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;

import com.example.success.R;
import com.example.success.anno.PK;
import com.example.success.base.App;
import com.example.success.base.BaseActivity;
import com.example.success.entity.Note;
import com.example.success.sqlite.INoteDao;
import com.example.success.sqlite.NoteDao;
import com.example.success.widget.PKHeadView;
import com.example.success.widget.head.HeadView.Dir;
import com.example.success.widget.head.HeadView.OnHeadClickListener;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:43:48
 * @Mark
 */
@PK(R.layout.activity_notedetail)
public class NoteDetailActivity extends BaseActivity {

	private static final int Add = 0x01;
	private static final int Update = 0x02;

	@PK
	private EditText content;
	@PK
	private PKHeadView header;

	private int type;
	private int id;
	private INoteDao dao;
	private boolean isEditable;

	@Override
	protected void init() {
		dao = NoteDao.instance();
		id = getIntent().getIntExtra(App.KEY, -1);
		if (id == -1) {
			type = Add;
			setEditable(true);
		} else {
			type = Update;
			Note note = dao.findById(id);
			if (note == null) {
				T("出错了..");
				return;
			}
			content.setText(note.content);
			setEditable(false);
		}

	}

	@Override
	protected void initListener() {
		content.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				content.setFocusable(true);
				return false;
			}
		});
		header.setOnHeadClickListener(new OnHeadClickListener() {

			@Override
			public void onHeadClick(Dir dir, View view) {
				switch (dir) {
				case Left:
					finish();
					break;
				case Right:
					if (isEditable) {
						sumbit();
					} else {
						setEditable(true);
					}
					break;
				}
			}

		});
	}

	public void sumbit() {
		String ct = getStr(content);
		if (isEmpty(ct)) {
			T("内容不能为空..");
			return;
		}
		long cur = System.currentTimeMillis();
		switch (type) {
		case Add:
			dao.add(new Note(0, "", ct, cur));
			type = Update;
			break;
		case Update:
			dao.update(new Note(id, "", ct, cur));
			break;
		}
		setEditable(false);
		T("保存成功..");
	}

	private void setEditable(boolean editable) {
		content.setEnabled(editable);
		header.setRightText(editable ? "保存" : "编辑");
		isEditable = editable;
	}

}
