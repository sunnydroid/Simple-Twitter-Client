package com.sunny.apps.simpleTwitter.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Tweets")
public class Tweet extends Model {
	@Column(name = "text")
	String text;

	@Column(name = "tweetId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	Long tweetId;

	@Column(name = "user", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	User user;

	@Column(name = "createdAt")
	String createdAt;

	public Tweet() {
		super();
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
		tweet.tweetId = json.getLong("id");
		tweet.createdAt = json.getString("created_at");
		tweet.user = User.userFromJson(json.getJSONObject("user"));

		return tweet;
	}

	public static List<Tweet> tweetsFromJsonArray(JSONArray array) {
		List<Tweet> tweets = new ArrayList<>();
		
		ActiveAndroid.beginTransaction();

		try {			
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
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
		

		return tweets;
	}
	
	public static void persistTweets(List<Tweet> tweets) {
		for(Tweet tweet : tweets) {
			/* you have to persist all objects that map to a model */
			tweet.getUser().save();
			tweet.save();
		}
	}
	
	public static List<Tweet> getPersistedTweets() {
		return new Select()
		.from(Tweet.class)
		.orderBy("tweetId DESC")
		.limit(25)
		.execute();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long id) {
		this.tweetId = id;
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
