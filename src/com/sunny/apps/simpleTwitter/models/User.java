package com.sunny.apps.simpleTwitter.models;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Users")
public class User extends Model {
	@Column(name = "userId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	long userId;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "screenName")
	String screenName;
	
	@Column(name = "imageUrl")
	String imageUrl;
	
	public User() {
		super();
	}

	/**
	 * User factory from JSON
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static User userFromJson(JSONObject json) throws JSONException {
		User user = new User();
		user.userId = json.getLong("id");
		user.name = json.getString("name");
		user.imageUrl = json.getString("profile_image_url_https");
		user.screenName = json.getString("screen_name");
		
		return user;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	

}
