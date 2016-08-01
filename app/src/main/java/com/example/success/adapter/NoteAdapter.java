package com.example.success.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.success.R;
import com.example.success.adapter.base.PKAdapter;
import com.example.success.adapter.base.PKHolder;
import com.example.success.anno.PK;
import com.example.success.base.App;
import com.example.success.entity.Note;
import com.example.success.sqlite.INoteDao;
import com.example.success.sqlite.NoteDao;
import com.example.success.view.NoteDetailActivity;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:21:16
 * @Mark
 */
public class NoteAdapter extends PKAdapter<Note, NoteHodler> implements OnItemClickListener {

	private INoteDao dao;

	public NoteAdapter(Context context, List<Note> list) {
		super(context, list);
		dao = NoteDao.instance();
	}

	@Override
	protected void setView(final int position, Note bean, NoteHodler holder, View view, ViewGroup parent) {
		Note note = getItem(position);
		if (note == null) {
			return;
		}

		holder.title.setText(note.content);
		holder.time.setText(formatTime(note.time));

		holder.delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDeleteDialog(position);
			}
		});
	}

	protected void showDeleteDialog(final int position) {
		AlertDialog dialog = new AlertDialog.Builder(context)//
				.setMessage("确定要删除吗？")//
				.setNegativeButton("取消", null)//
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Note note = getItem(position);
						if (note != null) {
							dao.delete(note._id);
							list.remove(note);
							notifyDataSetChanged();
						}
					}
				}).create();
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
	}

	private static String formatTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd  HH:mm");
		return format.format(new Date(time));
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Note note = getItem(position);
		if (note == null) {
			return;
		}

		Intent intent = new Intent(context, NoteDetailActivity.class);
		intent.putExtra(App.KEY, note._id);
		context.startActivity(intent);
	}

}

@PK(R.layout.listview_item_note)
class NoteHodler extends PKHolder {

	public NoteHodler() {

	}

	@PK
	TextView title, time;
	@PK
	ImageView delete;

}