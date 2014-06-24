package com.sunny.apps.simpleTwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sunny.apps.simpleTwitter.adapter.TweetArrayAdapter;
import com.sunny.apps.simpleTwitter.listener.EndlessScrollListener;
import com.sunny.apps.simpleTwitter.models.Tweet;

public class TimelineActivity extends Activity {
	
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter adapterTweets;
	private ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		setupView();
		populateTimeline(0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Inflate the custome menu created */
		getMenuInflater().inflate(R.menu.compose_icon, menu);
		return true;
	}
	
	private void setupView() {
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<>();
		adapterTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(adapterTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(page);
				
			}
		});
	}
	
	public void onComposeClick(MenuItem mi) {
		Intent i = new Intent(this, ComposeTweetActivity.class);
		startActivity(i);
	}
	
	public void populateTimeline(int page) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				Toast.makeText(getApplicationContext(), "fetched timeline", Toast.LENGTH_SHORT).show();
				adapterTweets.addAll(Tweet.tweetsFromJsonArray(jsonArray));
			}
			
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("error", t.getMessage());
				Log.d("error", s.toString());
			}
		}, page);
	}
}
