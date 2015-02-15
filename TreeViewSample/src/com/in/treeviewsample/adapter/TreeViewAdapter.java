package com.in.treeviewsample.adapter;

import android.R.color;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.in.treeviewsample.R;
import com.in.treeviewsample.TreeViewActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class TreeViewAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	public int ItemHeight = 0;
	public static final int PaddingLeft = 40;

	public TreeViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return ((ArrayList<HashMap<String, Object>>) list.get(groupPosition)
				.get("sublist")).get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	public ExpandableListView getExpandableListView(int count) {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, count * ItemHeight);
		ExpandableListView superTreeView = new ExpandableListView(context);
		superTreeView.setLayoutParams(lp);
		superTreeView.setVerticalScrollBarEnabled(false);
		superTreeView.setGroupIndicator(null);
		superTreeView.setDivider(null);
		superTreeView.setSelector(new ColorDrawable(color.transparent));
		superTreeView.setCacheColorHint(0);
		return superTreeView;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final ArrayList<HashMap<String, Object>> childlist = ((ArrayList<HashMap<String, Object>>) list
				.get(groupPosition).get("sublist"));
		final int count = childlist.size();
		final ExpandableListView treeView = getExpandableListView(count);
		final ItemTreeViewAdapter treeViewAdapter = new ItemTreeViewAdapter(
				this.context, childlist);
		treeView.setAdapter(treeViewAdapter);
		treeView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				for (int i = 0; i < count; i++) {
					if (groupPosition != i) {
						treeView.collapseGroup(i);
					}
				}
				int subcount = ((ArrayList<HashMap<String, Object>>) childlist
						.get(groupPosition).get("sublist")).size();
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, (count + subcount)
								* ItemHeight);
				treeView.setLayoutParams(lp);
				TreeViewActivity.selected_child = groupPosition;
			}
		});

		treeView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.FILL_PARENT, count * ItemHeight);
				treeView.setLayoutParams(lp);
				TreeViewActivity.selected_child = -1;
			}
		});
		treeView.setPadding(TreeViewAdapter.PaddingLeft, 0, 0, 0);
		if (TreeViewActivity.selected_child != -1) {
			treeView.expandGroup(TreeViewActivity.selected_child);
		}
		return treeView;
	}

	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.layout_listview, null);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		ImageView iv_pointer = (ImageView) convertView
				.findViewById(R.id.iv_pointer);
		LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
		tv_name.setText(list.get(groupPosition).get("name").toString());
		if (((ArrayList<HashMap<String, Object>>) list.get(groupPosition).get(
				"sublist")).size() == 0) {
			iv_pointer.setVisibility(4);
		} else if (TreeViewActivity.selected_group == groupPosition) {
			iv_pointer.setBackgroundResource(R.drawable.point_expand);
			iv_pointer.setVisibility(0);
		} else {
			iv_pointer.setBackgroundResource(R.drawable.point);
			iv_pointer.setVisibility(0);
		}

		if (ItemHeight == 0) {
			ll.measure(0, 0);
			ItemHeight = ll.getMeasuredHeight();
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
}
