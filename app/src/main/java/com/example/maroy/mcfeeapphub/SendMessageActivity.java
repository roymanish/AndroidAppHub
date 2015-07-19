/**
 * 
 */
package com.example.maroy.mcfeeapphub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * @author MaRoy
 *
 */
public class SendMessageActivity extends Activity {

	private Integer position;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_sms);

	}
	
	public void onSend(View v){

		EditText amountView = (EditText)v.getRootView().findViewById(R.id.numberValue);
		String number = amountView.getText().toString();
		
		Intent intent=new Intent();
		intent.putExtra("phone_number", number);
		setResult(1, intent);
		finish();
	}
	
	public void onCancelEdit(View v){

        SendMessageActivity.this.finish();
	}
}
