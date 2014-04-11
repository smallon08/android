package com.teamtreehouse.ribbit;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	protected TextView mSignUpTextView;
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);
		
		mSignUpTextView = (TextView)findViewById(R.id.signUpText);
		mSignUpTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
			}
		});
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mLoginBtn = (Button) findViewById(R.id.loginButton);
		
		mLoginBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				String username = mUsername.getText().toString();
				String password = mPassword.getText().toString();
				username = username.trim();
				password = password.trim();
				
				if(username.isEmpty()||password.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setTitle(R.string.login_error_title)
							.setMessage(R.string.login_error_msg)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					 
				}else{
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username, password, new LogInCallback() {						
						@Override
						public void done(ParseUser user, ParseException e) {
							// TODO Auto-generated method stub
							setProgressBarIndeterminateVisibility(false);							
							if(e==null){
								Intent intent = new Intent(LoginActivity.this,MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);	
								startActivity(intent);								
								
							}else{
								AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
								builder.setTitle(R.string.login_error_title)
										.setMessage(e.getMessage())
										.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();								
							}
							
						}
					});
				}
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
