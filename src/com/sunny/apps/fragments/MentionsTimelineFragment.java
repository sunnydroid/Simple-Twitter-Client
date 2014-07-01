package com.sunny.apps.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sunny.apps.simpleTwitter.TwitterApplication;
import com.sunny.apps.simpleTwitter.TwitterClient;
import com.sunny.apps.simpleTwitter.models.Tweet;

public class MentionsTimelineFragment extends TweetsListFragment {

	private TwitterClient client; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		client = TwitterApplication.getRestClient();
		populateTimeline(0);
	}
	
	@Override
	public void populateTimeline(int page) {
		client.getMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				Toast.makeText(activity, "fetched timeline", Toast.LENGTH_SHORT).show();
				List<Tweet> newTweets = Tweet.tweetsFromJsonArray(jsonArray);
				populatedAdapter(newTweets);
			}
			
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("error", t.getMessage());
				Log.d("error", s.toString());
			}
		}, page);
	}
}
