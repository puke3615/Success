package com.example.success.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.example.success.base.PKApplication;
import com.example.success.entity.Note;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:03:03
 * @Mark 
 */
public class NoteDao implements INoteDao {
	
	private DbUtils mDbUtils;
	
	private static class NoteDaoHolder {
		static NoteDao instance = new NoteDao();
	}
	
	public static NoteDao instance() {
		return NoteDaoHolder.instance;
	}
	
	private NoteDao() {
		mDbUtils = DbUtils.create(PKApplication.mContext);
	}
	
	@Override
	public boolean add(Note note) {
		if (note == null) {
			return false;
		}
		
		try {
			mDbUtils.saveBindingId(note);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		try {
			mDbUtils.delete(new Note(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Note note) {
		try {
			mDbUtils.update(note, "title", "content", "time");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Note findById(Integer id) {
		try {
			return mDbUtils.findById(Note.class, id);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Note> findAll() {
		try {
			return mDbUtils.findAll(Selector.from(Note.class).orderBy("time", true));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public boolean delete(List<Note> list) {
		try {
			mDbUtils.deleteAll(list);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
