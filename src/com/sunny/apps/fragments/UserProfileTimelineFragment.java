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

public class UserProfileTimelineFragment extends TweetsListFragment {

private TwitterClient client; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Get user id from the activity */
		String userId = getArguments().getString("userId");
		client = TwitterApplication.getRestClient();
		
		populateTimeline(userId);
	}

	public void populateTimeline(String userId) {
		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				Toast.makeText(activity, "fetched user timeline", Toast.LENGTH_SHORT).show();
				List<Tweet> newTweets = Tweet.tweetsFromJsonArray(jsonArray);
				populatedAdapter(newTweets);
			}
			
			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("error", t.getMessage());
				Log.d("error", s.toString());
			}
		}, userId);
	}
	
	public static UserProfileTimelineFragment newInstance(String userId) {
		UserProfileTimelineFragment fragment = new UserProfileTimelineFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		fragment.setArguments(args);
		
		return fragment;
	}
}
