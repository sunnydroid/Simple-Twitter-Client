package com.sunny.apps.simpleTwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {

	private TwitterClient client;
	private ImageView ivUserProfilePicture;
	private TextView tvUserName;
	private TextView tvUserScreenName;
	private TextView tvCharRemaining;
	private EditText etTweetText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		client = TwitterApplication.getRestClient();
		prepUserViews();
		getUserDetails();
	}
	
	private void prepUserViews() {
		ivUserProfilePicture = (ImageView) findViewById(R.id.ivUserProfilePicture);
		ivUserProfilePicture.setImageResource(android.R.color.transparent);
		tvUserName = (TextView) findViewById(R.id.tvUsername);
		tvUserScreenName = (TextView) findViewById(R.id.tvUserScreenName);
		tvCharRemaining = (TextView) findViewById(R.id.tvCharsRemaining);
		etTweetText = (EditText) findViewById(R.id.etTweetText);
		/* Add text change listener to edit text so that number of characters is updated live */
		etTweetText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				/* do nothing */
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				/* do nothing */
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				/* update char remaining */
				Integer charsRemaining = 140 - s.length();
				tvCharRemaining.setText(charsRemaining.toString());
			}
		});
	}
	
	public void postTweet(View v) {
		if(!etTweetText.getText().toString().isEmpty()) {
			client.postTweet(new JsonHttpResponseHandler() {

				@Override
				public void onFailure(Throwable t, String s) {
					Log.d("Error", "Error posting tweet=> " + t.getMessage());
				}

				@Override
				public void onSuccess(JSONObject json) {
					Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
					startActivity(i);
				}
				
			}, etTweetText.getText().toString());
		}
	}
	

	public void onProfileClick(View v) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
	private void getUserDetails() {
		client.getUserDetails(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONObject json) {
				try {
					String imageUrl = json.getString("profile_image_url");
					String username = json.getString("name");
					String screenName = json.getString("screen_name");
					Toast.makeText(getApplicationContext(), "get user details successful", Toast.LENGTH_SHORT).show();
					updateUserViews(imageUrl, username, screenName);
				} catch (JSONException e) {
					Log.d("Error", "Unable to parse user details=> " + e.getMessage());
				}
			}

			@Override
			public void onFailure(Throwable t, String s) {
				Log.d("Error", "Error fetching user details=> " + t.getMessage());
			}
			
		});
	}
	
	private void updateUserViews(String imageUrl, String username, String screenName) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imageUrl, ivUserProfilePicture);
		tvUserName.setText(username);
		tvUserScreenName.setText("@" + screenName);
	}
}
