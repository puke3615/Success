package com.example.success.sqlite;

import java.util.List;

import com.example.success.entity.Note;

/**
 * @author wzj
 * @version 2015年12月20日 下午10:00:06
 * @Mark 
 */
public interface INoteDao {
	
	boolean add(Note note);
	
	boolean delete(Integer id);
	
	boolean delete(List<Note> list);
	
	boolean update(Note note);
	
	Note findById(Integer id);
	
	List<Note> findAll();
	
}
