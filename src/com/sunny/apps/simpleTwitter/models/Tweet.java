package com.sunny.apps.simpleTwitter.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet {
	@Column(name = "text")
	String text;

	@Column(name = "id")
	long id;

	@Column(name = "user")
	User user;

	@Column(name = "createdAt")
	String createdAt;

	public Tweet() {

	}

	/**
	 * Tweet factory
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public static Tweet tweetFromJson(JSONObject json) throws JSONException {
		Tweet tweet = new Tweet();
		tweet.text = json.getString("text");
		tweet.id = json.getLong("id");
		tweet.createdAt = json.getString("created_at");
		tweet.user = User.userFromJson(json.getJSONObject("user"));

		return tweet;
	}

	public static List<Tweet> tweetsFromJsonArray(JSONArray array) {
		List<Tweet> tweets = new ArrayList<>();

		for (int i = 0; i < array.length(); i++) {
			try {
				Tweet tweet = Tweet.tweetFromJson(array.getJSONObject(i));
				if(tweet != null) {					
					tweets.add(tweet);
				}
			} catch (JSONException je) {
				Log.d("Error", "Error parsing JSON array: " + je.getMessage());
				continue;
			}
		}

		return tweets;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User: " );
		sb.append(this.user.name);
		sb.append("=> ");
		sb.append(this.text);
		
		return sb.toString();
	}
}
