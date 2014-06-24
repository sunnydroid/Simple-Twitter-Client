package com.sunny.apps.simpleTwitter.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sunny.apps.simpleTwitter.R;
import com.sunny.apps.simpleTwitter.R.id;
import com.sunny.apps.simpleTwitter.R.layout;
import com.sunny.apps.simpleTwitter.models.Tweet;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = (Tweet) getItem(position);
		
		View v;
		/* If there is no recyled view, get a inflator and inflate the view */
		if(convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			v = layoutInflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvScreenName = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvTweetText = (TextView) v.findViewById(R.id.tvTweetText);
		TextView tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
		
		/* if this is a recycled view, clear out the old image */
		ivProfileImage.setImageResource(android.R.color.transparent);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getImageUrl(), ivProfileImage);
		
		tvName.setText(tweet.getUser().getName());
		tvScreenName.setText("@" + tweet.getUser().getScreenName());
		tvTweetText.setText(tweet.getText());
		tvTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
		return v;
	}
	
	
	private String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_RELATIVE).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
}
