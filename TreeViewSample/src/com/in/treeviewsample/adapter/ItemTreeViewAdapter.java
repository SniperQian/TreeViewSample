package com.in.treeviewsample.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.in.treeviewsample.R;
import com.in.treeviewsample.TreeViewActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemTreeViewAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	public ItemTreeViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return ((ArrayList<HashMap<String, Object>>) list.get(groupPosition)
				.get("sublist")).get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<HashMap<String, Object>>) list.get(groupPosition)
				.get("sublist")).size();
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_listview, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.iv_pointer = (ImageView) convertView
					.findViewById(R.id.iv_pointer);
			viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.iv_pointer.setVisibility(View.INVISIBLE);
		String name = ((ArrayList<HashMap<String, Object>>) list
				.get(groupPosition).get("sublist")).get(childPosition)
				.get("name").toString();
		viewHolder.tv_name.setText(name);
		return convertView;
	}

	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_listview, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.iv_pointer = (ImageView) convertView
					.findViewById(R.id.iv_pointer);
			viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_name.setText(list.get(groupPosition).get("name")
				.toString());
		if (getChildrenCount(groupPosition) == 0) {
			viewHolder.iv_pointer.setVisibility(4);
		} else if (TreeViewActivity.selected_child == groupPosition) {
			viewHolder.iv_pointer
					.setBackgroundResource(R.drawable.point_expand);
			viewHolder.iv_pointer.setVisibility(0);
		} else {
			viewHolder.iv_pointer.setBackgroundResource(R.drawable.point);
			viewHolder.iv_pointer.setVisibility(0);
		}
		return convertView;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	public int getGroupCount() {
		return list.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	private class ViewHolder {
		private LinearLayout ll;
		private ImageView iv_pointer;
		private TextView tv_name;
	}
}
