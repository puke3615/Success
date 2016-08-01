package com.example.success.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.success.R;
import com.example.success.anno.Click;
import com.example.success.anno.PK;
import com.example.success.util.SystemBarTintManager;
import com.example.success.util.TextViewUtil;

/**
 * Created by dell on 2015/11/13.
 */
public class InstanceManager {

    private static final Map<Class<?>, Object> mInstances = new HashMap<Class<?>, Object>();
    private static final byte[] lock = new byte[0];

    public static <T> T getSingleInstance(Class<T> cls, Object... params) {
        if (cls == null) {
            return null;
        }
        if (!mInstances.containsKey(cls)) {
            synchronized (lock) {
                if (!mInstances.containsKey(cls)) {
                    T instance = getInstance(cls, params);
                    if (instance != null) {
                        mInstances.put(cls, instance);
                    }
                }
            }
        }
        return (T) mInstances.get(cls);
    }

    public static <T> T getInstance(Class<T> cls, Object... params) {
        T instance = null;
        try {
            int size = params == null ? 0 : params.length;
            if (size == 0) {
                instance = cls.newInstance();
            } else {
                Class[] paramsType = new Class[size];
                for (int i = 0; i < size; i++) {
                    paramsType[i] = params[i].getClass();
                }
                instance = (T) cls.getConstructor(paramsType).newInstance(params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }


    public static View handleFragment(final Fragment fragment, LayoutInflater inflater) {
        if (fragment == null || inflater == null) {
            return null;
        }
        View view = null;
        try {
            Context context = inflater.getContext();
            final Class<?> cls = fragment.getClass();

            int layout = 0;
            if (cls.isAnnotationPresent(PK.class)) {
                layout = cls.getAnnotation(PK.class).value();
            }
            if (layout != 0) {
                view = inflater.inflate(layout, null);
            }

            for (final Field field : cls.getDeclaredFields()) {
                Class<?> fieldType = field.getType();
                if (View.class.isAssignableFrom(fieldType)) {
                    if (field.isAnnotationPresent(PK.class)) {
                        int id = field.getAnnotation(PK.class).value();
                        if (id == 0) {
                            id = context.getResources().getIdentifier(field.getName(), "id", context.getPackageName());
                        }
                        if (id != 0) {
                            final View v = view.findViewById(id);
                            if (v != null) {
                                field.setAccessible(true);
                                field.set(fragment, v);

                                if (v instanceof Button || field.isAnnotationPresent(Click.class)) {
                                    v.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (v instanceof TextView) {
                                                TextViewUtil.postTextViewLetter((TextView) v, 0.5f, 0);
                                            }
                                            try {
                                                final Method method = cls.getDeclaredMethod(field.getName());
                                                method.setAccessible(true);
                                                method.invoke(fragment);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public static void initSystemBar(Activity activity) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			setTranslucentStatus(activity, true);

		}

		SystemBarTintManager tintManager = new SystemBarTintManager(activity);

		tintManager.setStatusBarTintEnabled(true);

		// 使用颜色资源

		tintManager.setStatusBarTintResource(R.drawable.bg_status);

	}

	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {

		Window win = activity.getWindow();

		WindowManager.LayoutParams winParams = win.getAttributes();

		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

		if (on) {

			winParams.flags |= bits;

		} else {

			winParams.flags &= ~bits;

		}

		win.setAttributes(winParams);

	}

    public static void handleActivity(final Activity activity) {
        if (activity == null) {
            return;
        }
        try {
        	//initSystemBar(activity);
            final Class<?> cls = activity.getClass();

            int layout = 0;
            if (cls.isAnnotationPresent(PK.class)) {
                layout = cls.getAnnotation(PK.class).value();
            }
            if (layout != 0) {
                activity.setContentView(layout);
            }

            for (final Field field : cls.getDeclaredFields()) {
                Class<?> fieldType = field.getType();
                if (View.class.isAssignableFrom(fieldType)) {
                    if (field.isAnnotationPresent(PK.class)) {
                        int id = field.getAnnotation(PK.class).value();
                        if (id == 0) {
                            id = activity.getResources().getIdentifier(field.getName(), "id", activity.getPackageName());
                        }
                        if (id != 0) {
                            final View view = activity.findViewById(id);
                            if (view != null) {
                                field.setAccessible(true);
                                field.set(activity, view);

                                if (view instanceof Button || field.isAnnotationPresent(Click.class)) {
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (view instanceof TextView) {
                                                TextViewUtil.postTextViewLetter((TextView) view, 0.5f, 0);
                                            }
                                            try {
                                                final Method method = cls.getDeclaredMethod(field.getName());
                                                method.setAccessible(true);
                                                method.invoke(activity);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    }

                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
