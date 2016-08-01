package com.example.success.adapter.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author wzj
 * @version 2015年12月6日 下午5:29:57
 * @Mark
 */
@SuppressWarnings("unchecked")
public abstract class PKListView<E, H extends PKHolder> extends ListView {

	private DefaultAdapter adapter;

	public PKListView(Context context) {
		super(context);
		init(context);
	}

	public PKListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PKListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		adapter = new DefaultAdapter(context, null);
		setAdapter(adapter);
	}

	/**
	 * 设置布局文件的具体内容 <br>
	 * 该方法中不需要再做view的setTag、getTag等重复操作<br>
	 * 该部分实现已在{@link #getView(int, View, ViewGroup)}中完成<br>
	 * 只需要在此适配数据与视图的关系即可
	 **/
	protected abstract void setView(int position, E entity, H holder, View view, ViewGroup parent);

	protected class DefaultAdapter extends BaseAdapter {

		protected Context context;

		protected List<E> list;

		private Class<H> hodlerClass;

		public DefaultAdapter(Context context, List<E> list) {
			this.context = context;
			this.list = list == null ? new ArrayList<E>() : list;
			try {
				Class<?> cls = PKListView.this.getClass();

				// 设置holderClass
				ParameterizedType pt = (ParameterizedType) cls.getGenericSuperclass();
				hodlerClass = (Class<H>) pt.getActualTypeArguments()[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public E getItem(int position) {
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
			H holder = null;
			if (view == null) {
				try {
					Constructor<H> c = hodlerClass.getConstructor();
					c.setAccessible(true);
					holder = c.newInstance();
					view = holder.createView(context);
					view.setTag(holder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				holder = (H) view.getTag();
			}
			setView(position, getItem(position), holder, view, parent);
			return view;
		}

		public void add(int index, E bean) {
			list.add(index, bean);
			notifyDataSetChanged();
		}

		public void add(E bean) {
			list.add(bean);
			notifyDataSetChanged();
		}

		public void add(List<E> list) {
			this.list.addAll(list);
			notifyDataSetChanged();
		}

		public List<E> getList() {
			return list;
		}

		public void update(int index, E bean) {
			list.remove(index);
			list.add(index, bean);
			notifyDataSetChanged();
		}

		public void set(List<E> list) {
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

	}

	public void add(int index, E bean) {
		adapter.add(index, bean);
	}

	public void add(E bean) {
		adapter.add(bean);
	}

	public void add(List<E> list) {
		adapter.add(list);
	}

	public List<E> getList() {
		return adapter.getList();
	}

	public void update(int index, E bean) {
		adapter.update(index, bean);
	}

	public void set(List<E> list) {
		adapter.set(list);
	}

	public void removeAll() {
		adapter.removeAll();
	}

	public void remove(int index) {
		adapter.remove(index);
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
