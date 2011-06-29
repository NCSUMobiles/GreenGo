package com.application.sustain_v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.application.sustain.R;

public class sustainability_v2 extends Activity {
	/* Declaration of variables */
	double latitude;
	double longitude;

	static int count = 1;
	static int flag = 0;
	public static double sum_smallDistance = 0;
	public static int gps_flag = 0;
	public static double sum_largeDistance = 0;
	public static String last_recorded_date = null;
	public static int prev_points = 25;
	AnimationDrawable anim;
	/* user file category */
	String dataFileName = "data";
	String dist_walk_FileName = "smallDistance";
	String dist_mv_FileName = "largeDistance";

	/* android file location for file */
	String storageDir = "/mnt/sdcard/Android/data";
	BufferedWriter m_walk_Write;
	BufferedWriter mWrite;
	BufferedWriter m_mv_Write;
	BufferedReader mRead;
	String prevLatitude = "0";
	String prevLongitude = "0";
	LocationManager mlocManager;
	LocationListener mLocListener;

	File dataFile = new File(storageDir, dataFileName);
	File dist_walk_File = new File(storageDir, dist_walk_FileName);
	File dist_mv_File = new File(storageDir, dist_mv_FileName);

	FileWriter outwriter;
	FileWriter dist_walk_writer;
	FileWriter dist_mv_writer;
	Button buttonExit;
	ImageButton ViewData;
	ImageButton viewChart;
	ImageButton Garden;
	ImageButton info;
	Button total_distance;

	public static List<PieDetailsItem> PieData = new ArrayList<PieDetailsItem>(
			0);

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		

		Log.d("Create", "within create");

		this.buttonExit = (Button) this.findViewById(R.id.Exit);
		this.buttonExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		this.ViewData = (ImageButton) this.findViewById(R.id.ViewData);
		this.ViewData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (gps_flag == 0) {
					GPS_Start();
				} else {
					GPS_Stop();
				}
			}
		});

		this.info = (ImageButton) this.findViewById(R.id.info);
		this.info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				show_info();

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

		this.total_distance = (Button) this
				.findViewById(R.id.create_total_chart);

		this.total_distance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("create", "button clicked");
				total_chart_create();
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

	}

	public void show_info() {
		Intent i = new Intent(this, Info.class);
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

	public void total_chart_create() {
		Intent i = new Intent(this, activity_chart.class);
		Log.d("create", "create");
		startActivity(i);
		Log.d("start", "start");
	}

	public void calculate_Distance() {

		try {
			String[] values;
			double[] distValues = new double[5];
			int i = 0;
			dist_walk_writer = new FileWriter(dist_walk_File, false);
			dist_mv_writer = new FileWriter(dist_mv_File, false);
			BufferedReader br = new BufferedReader(new FileReader(dataFile));
			String line;

			while ((line = br.readLine()) != null) {
				values = line.split(",");

				double lat_prev = Double.parseDouble(prevLatitude);
				double long_prev = Double.parseDouble(prevLongitude);
				double lat_cur = Double.parseDouble(values[1]);
				double long_cur = Double.parseDouble(values[2]);

				double dist = distanceBetweenPoints(lat_prev, long_prev,
						lat_cur, long_cur);

				m_walk_Write = new BufferedWriter(dist_walk_writer);
				m_mv_Write = new BufferedWriter(dist_mv_writer);
				if (!dataFile.exists()) {
					m_walk_Write.write("");
					m_mv_Write.write("");
				}
				if (dist > 1.0 && dist < 800) {
					if (i == 5)
						i = 0;
					distValues[i] = dist;
					i++;
					if (dist <= 75) {
						double sum = 0;
						double average = 0;
						for (int j = 0; j < i; j++) {
							sum = sum + distValues[j];
						}
						average = sum / 5;
						if (average < 75) {
							sum_smallDistance = sum_smallDistance + dist;
							m_walk_Write.append(Double.toString(dist) + "\n");
							m_walk_Write.flush();
						} else {
							sum_largeDistance = sum_largeDistance + dist;
							m_mv_Write.append(Double.toString(dist) + "\n");
							m_mv_Write.flush();
						}
					} else {
						double sum = 0;
						double average = 0;
						for (int j = 0; j < i; j++) {
							sum = sum + distValues[j];
						}
						average = sum / 5;
						if (average < 75) {
							sum_smallDistance = sum_smallDistance + dist;
							m_walk_Write.append(Double.toString(dist) + "\n");
							m_walk_Write.flush();
						} else {
							sum_largeDistance = sum_largeDistance + dist;
							m_mv_Write.append(Double.toString(dist) + "\n");
							m_mv_Write.flush();
						}
					}
				}
				prevLatitude = values[1];
				prevLongitude = values[2];
			}
		} catch (IOException e) {

		}

	}


	public void GPS_Start() {

		mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000,
				0, mLocListener);
		gps_flag = 1;

	}

	

	public void GPS_Stop() {
		try {
			mlocManager.removeUpdates(this.mLocListener);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"unable to stop GPS as a process is running!",
					Toast.LENGTH_SHORT).show();
			gps_flag=1;
			return;
		} finally {
		}
		gps_flag = 0;
	}

	/*
	 * Compute the distance between two location when give two
	 * (latitude,longitude)
	 */
	static double distanceBetweenPoints(double lat1, double lon1, double lat2,
			double lon2) {
		if (lat1 != 0 && lon1 != 0) {
			double earth_radius = 6371.0 * 1000; // metres
			lat1 = degToRad(lat1);
			lat2 = degToRad(lat2);
			lon1 = degToRad(lon1);
			lon2 = degToRad(lon2);
			double d = Math.acos(Math.sin(lat1) * Math.sin(lat2)
					+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
					* earth_radius;
			Log.d("Tests-dist", "computed distance =" + d + "\n");

			return d;
		} else
			return 0;

	}

	public int calculate_points() {
		calculate_Distance();
		double total_distance = sum_smallDistance + sum_largeDistance;
		double percentage = sum_smallDistance / total_distance;
		Log.d("small", Double.toString(sum_smallDistance));
		Log.d("large", Double.toString(sum_largeDistance));
		Log.d("percentage", Double.toString(percentage));
		int points = 0;
		double km = (sum_smallDistance / 1000);
		Log.d("km", Double.toString(km));
		points = (int) km * 10;
		if (percentage >= 0.10 && percentage <= 0.25) {
			points += 10;
		} else if (percentage > 0.25 && percentage <= 0.50) {
			points += 20;
		} else if (percentage > 0.50 && percentage <= 0.75) {
			points += 30;
		} else if (percentage > 0.75 && percentage <= 1.00) {
			points += 40;
		}
		prev_points = prev_points + points;
		Log.d("points", Integer.toString(points));
		return prev_points;
	}

	private static double degToRad(double a) {
		// TODO Auto-generated method stub
		return a * Math.PI / 180;
	}

	private boolean isNetworkAvailable() {
		boolean connected = false;
		InputStream is = null;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState() == NetworkInfo.State.CONNECTED
				|| connectivityManager.getNetworkInfo(
						ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			// we are connected to a network
			connected = true;
			Log.d("yes", "yes");
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://csc463.blogspot.com");
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				connected = true;
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection " + e.toString());
				connected = false;
			}
		}
		return connected;
	}

	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			latitude = loc.getLatitude();
			longitude = loc.getLongitude();
			Log.d("Loc", "Changed");
			String text = "GPS Values-" + "Latitude:" + loc.getLatitude()
					+ "Longitude" + loc.getLongitude();
			Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
					.show();

			// writes data to SD card on phone
			try {
				Calendar cal = new GregorianCalendar();
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				String Current_date = day + "/" + (month + 1) + "/" + year;
				server_connection obj = new server_connection();
				int points = calculate_points();
				String deviceID;
				String date;
				deviceID = Secure.getString(getBaseContext()
						.getContentResolver(), Secure.ANDROID_ID);
				if (isNetworkAvailable() == true) {
					Log.d("Network", "network");
					int check = obj.checkUser(deviceID);
					Log.d("check", Integer.toString(check));
					if (check == 0) {
						obj.insert_data(deviceID);
						if (obj.getDate(deviceID) == null)
							date = null;
						else
							date = obj.getDate(deviceID);
					} else {
						if (obj.getDate(deviceID) == null)
							date = null;
						else
							date = obj.getDate(deviceID);
					}
				} else {
					Log.d("nonet", "nonet");
					date = "false";
				}
				Log.d("date", date);
				if (!date.equals("false")) {
					if (date != "null") {
						String[] split_values = date.split("/");
						if (Integer.parseInt(split_values[0]) < day
								|| Integer.parseInt(split_values[1]) < (month + 1)
								|| Integer.parseInt(split_values[2]) < year) {
							Log.d("enter", "enter");
							obj.insert_data(deviceID);
							dataFile.delete();
							date = obj.getDate(deviceID);
							Log.d("new_date", date);
							File dataFile = new File(storageDir, dataFileName);
							FileWriter outwriter = new FileWriter(dataFile,
									true);

							deviceID = Secure.getString(getBaseContext()
									.getContentResolver(), Secure.ANDROID_ID);

							String finalLine;
							finalLine = deviceID + "," + latitude + ","
									+ longitude + "," + Current_date;

							mWrite = new BufferedWriter(outwriter);
							if (!dataFile.exists()) {
								Log.d("file", "not exist");
								mWrite.write("");
							}
							mWrite.append(finalLine + "\n");
							mWrite.flush();

						} else {
							File dataFile = new File(storageDir, dataFileName);
							outwriter = new FileWriter(dataFile, true);
							deviceID = Secure.getString(getBaseContext()
									.getContentResolver(), Secure.ANDROID_ID);

							String finalLine;
							finalLine = deviceID + "," + latitude + ","
									+ longitude + "," + Current_date;

							mWrite = new BufferedWriter(outwriter);
							if (!dataFile.exists()) {
								Log.d("file", "not exist");
								mWrite.write("");
							} else
								Log.d("file", "exist");

							mWrite.append(finalLine + "\n");
							mWrite.flush();
						}
					} else {
						Log.d("file", "enter");
						File dataFile = new File(storageDir, dataFileName);
						outwriter = new FileWriter(dataFile, true);
						deviceID = Secure.getString(getBaseContext()
								.getContentResolver(), Secure.ANDROID_ID);

						String finalLine;
						finalLine = deviceID + "," + latitude + "," + longitude
								+ "," + Current_date;

						Log.d("file", finalLine);
						mWrite = new BufferedWriter(outwriter);
						if (!dataFile.exists()) {
							Log.d("file", "not exist");
							mWrite.write("");
						}
						mWrite.append(finalLine + "\n");
						mWrite.flush();
					}
				} else {
					Log.d("enter", "enter-false");
					// File dataFile = new File(storageDir, dataFileName);
					outwriter = new FileWriter(dataFile, true);
					deviceID = Secure.getString(getBaseContext()
							.getContentResolver(), Secure.ANDROID_ID);

					String finalLine;
					finalLine = deviceID + "," + latitude + "," + longitude
							+ "," + Current_date;

					mWrite = new BufferedWriter(outwriter);
					if (!dataFile.exists()) {
						Log.d("file", "not exist");
						mWrite.write("");
					}
					mWrite.append(finalLine + "\n");
					mWrite.flush();
				}

			} catch (IOException e) {
				e.printStackTrace();
				Log.d("Testbgfg", "Write Fail");
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS disabled",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(getApplicationContext(), "GPS enabled",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
	}
}
