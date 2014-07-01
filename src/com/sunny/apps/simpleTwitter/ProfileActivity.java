package com.sunny.apps.simpleTwitter;

import com.sunny.apps.fragments.UserProfileFragment;
import com.sunny.apps.fragments.UserProfileTimelineFragment;
import com.sunny.apps.simpleTwitter.models.User;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

public class ProfileActivity extends FragmentActivity {

	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		user = (User) getIntent().getSerializableExtra("user");
		loadFragments();
	}
	
	private void loadFragments() {
		String userId = null;
		if(null != user) {
			Long userIdLong = Long.valueOf(user.getUserId());
			userId = userIdLong.toString();
		}
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(userId);
		UserProfileTimelineFragment userProfileTimelineFragment = UserProfileTimelineFragment.newInstance(userId);
		
		transaction.replace(R.id.rlUserProfile, userProfileFragment);
		transaction.replace(R.id.frgUserTimeline, userProfileTimelineFragment);
		transaction.commit();
		
	}
	
	
}
