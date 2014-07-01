package com.sunny.apps.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sunny.apps.simpleTwitter.R;
import com.sunny.apps.simpleTwitter.adapter.TweetArrayAdapter;
import com.sunny.apps.simpleTwitter.listener.EndlessScrollListener;
import com.sunny.apps.simpleTwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	
	/* Fragments should not directly access activity, use sparingly */
	protected Activity activity;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter adapterTweets;
	private ListView lvTweets;

	/* 1st event to be fired off in the fragment lifecycle 
	 * Does now view initialization 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		/* we can create and setup the adapters + list since 
		 * they do not depend on the View being ready
		 */
		setupAdapter();
	}

	/* 2nd event to be fired in the fragment lifecycle */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the view */
		View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		/* Assign references to the view and set it up */
		setupView(view);
		
		/* return the view */
		return view;
	}

	
	private void setupAdapter() {
		tweets = new ArrayList<>();
		/* Fragments do not have a context like activities... 
		 * In order to construct our adapter, we'll have to reach into the activity. 
		 * We should not be doing this... unless we absolutely have to. 
		 */
		adapterTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	private void setupView(View view) {
		lvTweets = (ListView) view.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(adapterTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				populateTimeline(page);
				
			}
		});
	}
	
	protected void populatedAdapter(List<Tweet> tweets) {
		adapterTweets.addAll(tweets);
	}

	protected void populateTimeline(int page) {
		/* Over-ridden */
	}
}
