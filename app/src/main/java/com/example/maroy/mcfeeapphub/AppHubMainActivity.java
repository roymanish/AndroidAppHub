package com.example.maroy.mcfeeapphub;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maroy.data.AppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AppHubMainActivity extends ListActivity {

    private ListView lv;
    private List<AppData> appDataList;
    private TextView cntView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_hub_main);

        cntView = (TextView)findViewById(R.id.productCountValue);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        lv = getListView();
        if (networkInfo != null && networkInfo.isConnected()) {

            AsyncServiceTask asyncTask = new AsyncServiceTask();
            asyncTask.execute("http://mcafee.0x10.info/api/app?type=json");

        } else {
            Toast.makeText(getApplicationContext(),"Check you internet!",Toast.LENGTH_SHORT);
        }

        final Button sortByPrice = (Button)findViewById(R.id.sortByPrice);

        sortByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortByPrice.setBackgroundDrawable(getResources().getDrawable(R.drawable.lt_red_rounded_rect));
                Collections.sort(appDataList,new Comparator<AppData>() {
                    @Override
                    public int compare(AppData appData, AppData appData2) {
                        if((appData.getPrice()-appData2.getPrice())<0)
                            return -1;
                        else if((appData.getPrice()-appData2.getPrice())>0)
                            return 1;
                        else
                            return 0;
                    }
                });
                AppHubMainAdapter adapter = new AppHubMainAdapter(getApplicationContext(),appDataList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                sortByPrice.setBackgroundDrawable(getResources().getDrawable(R.drawable.br_red_rounded_rect));
            }
        });

        final Button sortByRating = (Button)findViewById(R.id.sortByRating);

        sortByRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortByRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.br_red_rounded_rect));
                Collections.sort(appDataList,new Comparator<AppData>() {
                    @Override
                    public int compare(AppData appData, AppData appData2) {
                        if((appData.getRating()-appData2.getRating())<0)
                            return -1;
                        else if((appData.getRating()-appData2.getRating())>0)
                            return 1;
                        else
                            return 0;
                    }
                });
                AppHubMainAdapter adapter = new AppHubMainAdapter(getApplicationContext(),appDataList);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                sortByRating.setBackgroundDrawable(getResources().getDrawable(R.drawable.lt_red_rounded_rect));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_hub_main, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(getApplicationContext(),AppHubDetailActivity.class);
        i.putExtra("data",appDataList.get(position));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncServiceTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {

            progress = ProgressDialog.show(AppHubMainActivity.this, "Expense Meter",
                    "Loading...!!", true);
        }

        @Override
        protected String doInBackground(String... urls) {

            InputStream is = null;
            // params comes from the execute() call: params[0] is the url.
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();

                is = conn.getInputStream();

                // Convert the InputStream into a string
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = in.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                return sb.toString();
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppData>>(){}.getType();
            appDataList = gson.fromJson(result, type);
            cntView.setText(Integer.toString(appDataList.size()));
            AppHubMainAdapter adapter = new AppHubMainAdapter(getApplicationContext(),appDataList);
            lv.setAdapter(adapter);
            progress.dismiss();
        }
    }

}
