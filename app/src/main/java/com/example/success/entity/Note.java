package com.example.success.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * @author wzj
 * @version 2015年12月20日 下午9:54:46
 * @Mark
 */
@Table
public class Note {

	@Id
	public Integer _id;
	@Column
	public String title;
	@Column
	public String content;
	@Column
	public long time;

	public Note(Integer _id, String title, String content, long time) {
		super();
		this._id = _id;
		this.title = title;
		this.content = content;
		this.time = time;
	}

	public Note(Integer _id) {
		super();
		this._id = _id;
	}

	public Note() {
		super();
	}

}
