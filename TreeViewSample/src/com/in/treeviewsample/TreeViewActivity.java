package com.in.treeviewsample;

import java.util.ArrayList;
import java.util.HashMap;

import com.in.treeviewsample.R;
import com.in.treeviewsample.adapter.TreeViewAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class TreeViewActivity extends Activity {

	private ExpandableListView elv;
	private TreeViewAdapter adapter;
	private ArrayList<HashMap<String, Object>> datalist = new ArrayList<HashMap<String, Object>>();

	public static int selected_group = -1;
	public static int selected_child = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_treeview);

		getData();
		elv = (ExpandableListView) findViewById(R.id.elv);
		adapter = new TreeViewAdapter(this, datalist);
		elv.setAdapter(adapter);
		elv.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				int count = datalist.size();
				for (int i = 0; i < count; i++) {
					if (groupPosition != i) {
						elv.collapseGroup(i);
					}
				}
				selected_group = groupPosition;
			}
		});
		elv.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				selected_group = -1;
				selected_child = -1;
			}
		});
	}

	//填充数据
	private void getData() {
		for (int i = 0; i < 7; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			map.put("name", i + "");
			for (int j = 0; j < 6; j++) {
				HashMap<String, Object> submap = new HashMap<String, Object>();
				ArrayList<HashMap<String, Object>> sublist = new ArrayList<HashMap<String, Object>>();
				submap.put("name", j + "");
				for (int k = 0; k < 5; k++) {
					HashMap<String, Object> childmap = new HashMap<String, Object>();
					childmap.put("name", k + "");
					sublist.add(childmap);
				}
				submap.put("sublist", sublist);
				list.add(submap);
			}
			map.put("sublist", list);
			datalist.add(map);
		}
	}
}
