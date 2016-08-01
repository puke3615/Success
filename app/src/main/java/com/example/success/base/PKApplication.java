package com.example.success.base;

import android.app.Application;
import android.content.Context;

import com.example.success.entity.Note;
import com.example.success.sqlite.NoteDao;

/**
 * Created by dell on 2015/11/10.
 */
public class PKApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        
    }

}
