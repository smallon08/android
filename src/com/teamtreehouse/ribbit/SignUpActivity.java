package com.teamtreehouse.ribbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
	
	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mSignUpBtn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);		
		setContentView(R.layout.activity_sign_up);
		
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mEmail = (EditText) findViewById(R.id.emailField);
		mSignUpBtn = (Button) findViewById(R.id.signupButton);
		
		mSignUpBtn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				String username = mUsername.getText().toString();
				String email = mEmail.getText().toString();
				String password = mPassword.getText().toString();
				username = username.trim();
				email = email.trim();
				password = password.trim();
				
				if(username.isEmpty()||email.isEmpty()||password.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
					builder.setTitle(R.string.signup_error_title)
							.setMessage(R.string.signup_error_msg)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}else{

					setProgressBarIndeterminateVisibility(true);
					ParseUser user = new ParseUser();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					
					user.signUpInBackground(new SignUpCallback() {						
						@Override
						public void done(ParseException e) {
							if(e==null){
								setProgressBarIndeterminateVisibility(false);
								
								Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);	
								startActivity(intent);
							}else{
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
								builder.setTitle(R.string.signup_error_title)
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
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}
