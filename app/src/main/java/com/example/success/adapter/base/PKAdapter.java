package com.example.success.adapter.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.success.anno.PK;

/**
 * @version 2015-7-1
 * @author wzj
 * @Mark Adapter基类，ListView适配器的常用抽象类
 */
@SuppressWarnings("unchecked")
public abstract class PKAdapter<Bean, Holder extends PKHolder> extends BaseAdapter {

	protected Context context;

	protected List<Bean> list;

	private int itemLayout;// 每项View的布局文件

	private Class<Holder> hodlerClass;

	public PKAdapter(Context context, List<Bean> list) {
		this.context = context;
		this.list = list == null ? new ArrayList<Bean>() : list;
		try {
			Class<?> cls = this.getClass();

			// 设置holderClass
			ParameterizedType pt = (ParameterizedType) cls.getGenericSuperclass();
			hodlerClass = (Class<Holder>) pt.getActualTypeArguments()[1];

			// 设置itemLayout
			if (hodlerClass.isAnnotationPresent(PK.class)) {
				PK pk = hodlerClass.getAnnotation(PK.class);
				itemLayout = pk.value();
			}
			// 未注解时，默认取getItemLayout()返回值
			if (itemLayout == 0) {
				itemLayout = getItemLayout();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Bean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/** 返回每项的布局文件，若未使用注解，则要重写该方法 **/
	protected int getItemLayout() {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Holder holder = null;
		if (view == null) {
			try {
				Constructor<Holder> c = hodlerClass.getConstructor();
				c.setAccessible(true);
				view = (holder = c.newInstance()).createView(context);
				view.setTag(holder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			holder = (Holder) view.getTag();
		}
		setView(position, getItem(position), holder, view, parent);
		return view;
	}

	/** 创建初始View的过程，若需添加，可重写 (视图相关放在Holder中处理，这里只做数据适配) **/
	@Deprecated
	protected View createView(LayoutInflater inflater, int layoutId) {
		return inflater.inflate(layoutId, null);
	}

	/**
	 * 设置布局文件的具体内容 <br>
	 * 该方法中不需要再做view的setTag、getTag等重复操作<br>
	 * 该部分实现已在{@link #getView(int, View, ViewGroup)}中完成<br>
	 * 只需要在此适配数据与视图的关系即可
	 **/
	protected abstract void setView(int position, Bean bean, Holder holder, View view, ViewGroup parent);

	public void add(int index, Bean bean) {
		list.add(index, bean);
		notifyDataSetChanged();
	}

	public void add(Bean bean) {
		list.add(bean);
		notifyDataSetChanged();
	}

	public void add(List<Bean> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public List<Bean> getList() {
		return list;
	}

	public void update(int index, Bean bean) {
		list.remove(index);
		list.add(index, bean);
		notifyDataSetChanged();
	}

	public void set(List<Bean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void removeAll() {
		list.clear();
		notifyDataSetChanged();
	}

	public void remove(int index) {
		list.remove(index);
		notifyDataSetChanged();
	}

	public static void GONE(View view) {
		view.setVisibility(8);
	}

	public static void VISIBILE(View view) {
		view.setVisibility(0);
	}

	public static void INVISIBILE(View view) {
		view.setVisibility(4);
	}

}
