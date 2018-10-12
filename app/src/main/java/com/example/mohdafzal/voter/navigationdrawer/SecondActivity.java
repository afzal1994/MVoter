package com.example.mohdafzal.voter.navigationdrawer;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.example.mohdafzal.voter.R;


public class SecondActivity extends BaseActivity {
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
		// titles
		// from
		// strings.xml

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);// load icons from
		// strings.xml

		set(navMenuTitles, navMenuIcons);
	}
}
