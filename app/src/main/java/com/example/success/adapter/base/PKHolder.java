package com.example.success.adapter.base;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.example.success.anno.PK;

/**
 * @author wzj
 * @version 2015年12月6日 下午5:02:31
 * @Mark
 */
public abstract class PKHolder {

	public PKHolder() {
	}

	public View createView(Context context) {
		View itemView = null;
		try {
			Class<?> cls = getClass();
			if (cls.isAnnotationPresent(PK.class)) {
				PK pkType = cls.getAnnotation(PK.class);
				int layout = pkType.value();
				if (layout != 0) {
					itemView = View.inflate(context, layout, null);

					Field[] fields = cls.getDeclaredFields();
					Resources resources = context.getResources();
					String packageName = context.getPackageName();
					for (Field field : fields) {
						if (field.isAnnotationPresent(PK.class)) {
							PK pkValue = field.getAnnotation(PK.class);
							int id = pkValue.value();
							if (id == 0) {
								id = resources.getIdentifier(field.getName(), "id", packageName);
							}
							if (id != 0) {
								View view = itemView.findViewById(id);
								if (view != null && field.getType().isInstance(view)) {
									field.setAccessible(true);
									field.set(this, view);
								}
							}
						}
					}

				}
			}
		} catch (Exception e) {
		}
		return itemView;
	}

}
