package com.application.sustain_v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.app.Activity;

public class server_connection{
	
	public static int count = 1;
    public int walking_distance=0;
    public int mv_distance=0;
    public int points=0;
    
	public static void main(String[] args) {

	}
	
	public String getDate(String deviceID) {
		String result = "";
		String date = null;
		InputStream is = null;
		// the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("deviceid", deviceID));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://people.engr.ncsu.edu/sshanmu/getDate.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());
			return null;
		}
		// convert response to string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			Log.e("Result:", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				if (i == 0) {
					if (json_data.getString("last_date") != null) {
						date = json_data.getString("last_date");
						continue;
					}
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		} finally {
		}
		return date;

	}
	

	public void getDistance(String deviceID) {
		String result = "";
		InputStream is = null;
		// the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("deviceid", deviceID));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://people.engr.ncsu.edu/sshanmu/getDistance.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			Log.e("Result:", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				
					if (json_data.getString("walk") != null) {
						walking_distance = json_data.getInt("walk");
						Log.d("Walking",Integer.toString(walking_distance));
				
					
				
					if (json_data.getString("mv") != null) {
						mv_distance = json_data.getInt("mv");
						Log.d("Travelling",Integer.toString(mv_distance));
						break;
					}
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		} finally {
		}
	}
	
	public void getPoints(String deviceID) {
		String result = "";
		InputStream is = null;
		// the year data to send
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("deviceid", deviceID));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://people.engr.ncsu.edu/sshanmu/getPoints.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			Log.e("Result:", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				
					if (json_data.getString("points") != null) {
						points = json_data.getInt("points");
						Log.d("Tpoints",Integer.toString(points));
				
					}
				
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		} finally {
		}
	}
	
    public int getCount()
    {
    	String result = "";
		int count = 0;
		InputStream is = null;
	

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://people.engr.ncsu.edu/sshanmu/getDate.php");
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			Log.e("Result:", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				if (i == 0) {
					if (json_data.getString("count") != null) {
						count = json_data.getInt("count");
						continue;
					}
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		} finally {
		}
		return count;
    }
    
    
    public int checkUser(String deviceID)
    {
    	String result = "";
		int check = 0;
		InputStream is = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("deviceid", deviceID));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://people.engr.ncsu.edu/sshanmu/checkUser.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			Log.e("Result:", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try {
			JSONArray jArray = new JSONArray(result);

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				if (i == 0) {
					if (json_data.getString("chk") != null) {
						check = json_data.getInt("chk");
						Log.d("check",Integer.toString(check));
						break;
					}
				}
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		} finally {
		}
		return check;
    }

	public void insert_data(String deviceID) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://people.engr.ncsu.edu/sshanmu/query.php");
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		sustainability_v2 obj = new sustainability_v2();
		obj.sum_smallDistance = 0;
		obj.sum_largeDistance = 0;
		obj.prev_points = 25;
        obj.calculate_Distance();
		int points = (int) obj.calculate_points();
		String Current_date = day + "/" + (month + 1) + "/" + year;
		try {
           int maxCount=getCount(); 
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", Integer
					.toString(maxCount)));
			nameValuePairs.add(new BasicNameValuePair("deviceid", deviceID));
			nameValuePairs.add(new BasicNameValuePair("sd", Double
					.toString(obj.sum_smallDistance)));
			nameValuePairs.add(new BasicNameValuePair("ld", Double
					.toString(obj.sum_largeDistance)));
			nameValuePairs.add(new BasicNameValuePair("pts", Integer
					.toString(points)));
			nameValuePairs.add(new BasicNameValuePair("Date", Current_date));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			count = count + 1;
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection" + e.toString());
		}

	}
}
