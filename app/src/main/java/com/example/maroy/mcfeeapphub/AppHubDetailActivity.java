package com.example.maroy.mcfeeapphub;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maroy.data.AppData;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by MaRoy on 4/19/2015.
 */
public class AppHubDetailActivity extends Activity{

    private AppData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_hub_detail);

        final Activity activity = this;
        data = (AppData)this.getIntent().getParcelableExtra("data");

        ImageView image = (ImageView)findViewById(R.id.imageView);

        try {
            URL myUrl = new URL(data.getUrl());
            InputStream inputStream = (InputStream) myUrl.getContent();
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            image.setImageDrawable(drawable);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        TextView appNameView = (TextView)findViewById(R.id.textView);
        appNameView.setText(data.getName());

        TextView ratingView = (TextView)findViewById(R.id.textView2);
        ratingView.setText(""+data.getRating());

        TextView typePriceView = (TextView)findViewById(R.id.typePriceValView);
        typePriceView.setText(data.getType()+"/$"+data.getPrice());

        TextView usersView = (TextView)findViewById(R.id.usersValView);
        usersView.setText(""+data.getUsers());

        TextView lastUpdatedView = (TextView)findViewById(R.id.lastUpdatedValView);
        lastUpdatedView.setText(data.getLast_update());

        final Button appStoreButton = (Button)findViewById(R.id.button);

        appStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appStoreButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.dk_blue_reounded_rect));
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse(data.getUrl()));
                activity.startActivity(i);
                appStoreButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lt_blue_rounded_rect));
            }
        });

        final Button shareButton = (Button)findViewById(R.id.button2);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lt_blue_rounded_rect));
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                shareButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.dk_blue_reounded_rect));
            }
        });

        final Button backButton = (Button)findViewById(R.id.button3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.dk_green_rounded_rect));
                Intent i = new Intent(getApplicationContext(), AppHubMainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                AppHubDetailActivity.this.finish();
            }
        });

        final Button smsButton = (Button)findViewById(R.id.button4);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                smsButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.lt_green_rounded_rect));
                Intent i = new Intent(getApplicationContext(), SendMessageActivity.class);
                startActivityForResult(i,1);
                smsButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.dk_green_rounded_rect));
            }
        });
    }

    private void sendSMSMessage(String phoneNo, String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode==1 && intent != null)
        {
            String number = intent.getStringExtra("phone_number");
            sendSMSMessage(number, data.getUrl());
        }
    }
}
