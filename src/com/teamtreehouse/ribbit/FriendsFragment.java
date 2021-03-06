package com.teamtreehouse.ribbit;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FriendsFragment extends ListFragment{

	public static final String TAG = FriendsFragment.class.getSimpleName();

	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendRelation;
	protected ParseUser mCurrentUser;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friends,
				container, false);
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mCurrentUser = ParseUser.getCurrentUser();
		mFriendRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseUser> query =mFriendRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> friends, ParseException e) {

				getActivity().setProgressBarIndeterminateVisibility(false);
				if(e==null){
					mFriends = friends;
					
					String[] usernames = new String[mFriends.size()];
					int i = 0;
					for(ParseUser user : mFriends){
						usernames[i] = user.getUsername();
						i++;
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), 
							android.R.layout.simple_list_item_1, usernames);
					setListAdapter(adapter);
					
				}else{
					//Log.e(TAG,e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
					builder.setTitle(R.string.error_title)
							.setMessage(e.getMessage())
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}

}
