package com.application.sustain_v2;

import com.application.sustain.R;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class Info extends Activity {
	/** Called when the activity is first created. */
	ImageButton viewChart;
	ImageButton Garden;
	ImageButton info;
	ImageButton home;

	public void page_home() {
		Intent i = new Intent(this, sustainability_v2.class);
		startActivity(i);
	}

	public void show_garden() {
		Intent i = new Intent(this, Garden.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	public void chart_create() {
		Intent i = new Intent(this, ChartPage.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		TextView link = (TextView) findViewById(R.id.text_link);
		link.setText(Html
				.fromHtml("Not convinced??? <br/> <a href=\"http://www.youtube.com/watch?v=UcWpkWBX04E\"> Watch this video </a> "));
		link.setMovementMethod(LinkMovementMethod.getInstance());
		this.info = (ImageButton) this.findViewById(R.id.info);
		info.setImageResource(R.drawable.iconinfopressed);
		this.info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		this.viewChart = (ImageButton) this.findViewById(R.id.create_chart);

		this.viewChart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				chart_create();
			}
		});

		this.Garden = (ImageButton) this.findViewById(R.id.Garden);

		this.Garden.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				show_garden();
			}
		});

		this.home = (ImageButton) this.findViewById(R.id.home);
        
		this.home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				page_home();
			}
		});

	}
}