package com.sunny.apps.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sunny.apps.simpleTwitter.R;
import com.sunny.apps.simpleTwitter.TwitterApplication;
import com.sunny.apps.simpleTwitter.TwitterClient;
import com.sunny.apps.simpleTwitter.models.Tweet;

public class UserProfileFragment extends Fragment {
	
	Activity activity;
	TwitterClient client;
	ImageView ivProfilePicUser;
	TextView tvUsernameUser;
	TextView tvDescription;
	TextView tvFollowers;
	TextView tvFollowing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		/* Get user id from the activity */
		String userId = getArguments().getString("userId");
		client = TwitterApplication.getRestClient();
		
		fetchAndPopulateUserDetails(userId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the view */
		View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
		/* Assign references to the view and set it up */
		ivProfilePicUser = (ImageView) view.findViewById(R.id.ivProfilePicTweetTimeline);
		tvUsernameUser = (TextView) view.findViewById(R.id.tvUsernameTweetTimeline);
		tvDescription = (TextView) view.findViewById(R.id.tvUserDescriptionTweetTimeline);
		tvFollowers = (TextView) view.findViewById(R.id.tvFollowersTweetTimeline);
		tvFollowing = (TextView) view.findViewById(R.id.tvFollowingTweetTimeline);
		/* return the view */
		return view;
	}
	

	public void fetchAndPopulateUserDetails(String userId) {
		
		if(null == userId) {
			client.getUserDetails(new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONObject json) {
					try {
						String imageUrl = json.getString("profile_image_url");
						String username = json.getString("name");
						String description = json.getString("description");
						String followers = json.getString("followers_count");
						String following = json.getString("following");
						Toast.makeText(activity, "get user details successful", Toast.LENGTH_SHORT).show();
						updateUserViews(imageUrl, username, description, followers, following);
					} catch (JSONException e) {
						Log.d("Error", "Unable to parse user details=> " + e.getMessage());
					}
				}

				@Override
				public void onFailure(Throwable t, String s) {
					Log.d("Error", "Error fetching user details=> " + t.getMessage());
				}
				
			});
		} else {
			
			client.getUserDetails(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					try {
						String imageUrl = json.getString("profile_image_url");
						String username = json.getString("name");
						String description = json.getString("description");
						String followers = json.getString("followers_count");
						String following = json.getString("following");
						Toast.makeText(activity, "get user details successful", Toast.LENGTH_SHORT).show();
						updateUserViews(imageUrl, username, description, followers, following);
					} catch (JSONException e) {
						Log.d("Error", "Unable to parse user details=> " + e.getMessage());
					}
				}

				@Override
				public void onFailure(Throwable t, String s) {
					Log.d("Error", "Error fetching user details=> " + t.getMessage());
				}
			}, userId);
		}
		
	}
	
	private void updateUserViews(String imageUrl, String username, String description, String followers, String following) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imageUrl, ivProfilePicUser);
		tvUsernameUser.setText(username);
		tvDescription.setText(description);
		tvFollowing.setText(following);
		tvFollowers.setText(followers);
	}
	
	public static UserProfileFragment newInstance(String userId) {
		UserProfileFragment fragment = new UserProfileFragment();
		Bundle args = new Bundle();
		args.putString("userId", userId);
		fragment.setArguments(args);
		
		return fragment;
	}
}
