package example.manoj_pc.federalassignment.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import example.manoj_pc.federalassignment.R;
import example.manoj_pc.federalassignment.app.Config;
import example.manoj_pc.federalassignment.util.NetworkUtils;
import example.manoj_pc.federalassignment.util.NotificationUtils;
import example.manoj_pc.federalassignment.util.SharedPreference;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private EditText etMsg;
    private Button btSubmit,btView;
    private String getDeviceToken;
    private OutputStreamWriter wr;
    ArrayList<String> strMsg = new ArrayList<>();
    private TextView txtViewCount;
    private String message;
    private int getStatus;
    private Menu menu;
    private WebView wvContent;
    private String jsontree ="{\n" +
            "  \"message\":{\n"+
            "\"token\":\"fOlKyT7qrxE:APA91bHNEcwy20YkFb3UOegD8Te9sehECK1bAzwT0wuoPxKMPHxaP6Hrpp1QPWibUny9krgnNDoCwP0FwmoZKM7wal7twTOt0PopR6jKlSyEckcaU4OPFXz0xI3CIbvO54OD2RHGmhcH\",\n" +
            "    \"notification\":{\n" +
            "      \"title\":\"Portugal vs. Denmark\",\n" +
            "      \"body\":\"great match!\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreference = new SharedPreference();
        btSubmit = (Button) findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(this);
        if(getIntent()!=null){
            getStatus = getIntent().getIntExtra(Config.STATUS,0);
        }else {
            getStatus = 0;
        }
        etMsg = (EditText)findViewById(R.id.etMsg);
        wvContent = (WebView)findViewById(R.id.wvContent);
        setWebView();
        message = getIntent().getStringExtra("message");
        strMsg.add(message);
        if(message!=null)
        sharedPreference.addFavorite(MainActivity.this, message);
        displayFirebaseRegId();
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new FetchMsg(message).execute();
            }
        }, 3000);*/
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Opening website...", true);

        wvContent.getSettings().setJavaScriptEnabled(true); // enable javascript
        wvContent.getSettings().setLoadWithOverviewMode(true);
        wvContent.getSettings().setUseWideViewPort(true);
        wvContent.getSettings().setBuiltInZoomControls(true);
        wvContent.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

            }


            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        wvContent .loadUrl("http://www.google.com");
    }

    private void displayNotification() {
         MenuItem notificaitons = menu.findItem(R.id.action_notification);
        txtViewCount = (TextView)notificaitons.getActionView().findViewById(R.id.txtCount);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message == null) {
                    txtViewCount.setVisibility(View.GONE);

                } else {
                    txtViewCount.setVisibility(View.VISIBLE);
                    // supportInvalidateOptionsMenu();
                }
            }
        });
    }



    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationUtils.clearNotifications(getApplicationContext());
       /* // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());*/
    }

    @Override
    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSubmit:
                SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                if(pref.getString("regId",null)!=null){
                    getDeviceToken = pref.getString("regId", null);
                }else {
                    getDeviceToken = Config.EMPTY;
                }
                if(NetworkUtils.isNetworkAvailable(MainActivity.this)){
                    if(checkValidation()){
                        AsyncSendNotification asyncSendNotification = new AsyncSendNotification(etMsg.getText().toString().trim(),getDeviceToken,Config.API_URL_FCM);
                        asyncSendNotification.execute("");
                    }
                }else{
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                }

                break;

        }


       /* try {
            sendPushNotification(etMsg.getText().toString().trim(),getDeviceToken,Config.API_URL_FCM);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private boolean checkValidation() {
        if(etMsg.getText().toString().trim().equals("")){
            Toast.makeText(this, "Please enter message", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendPushNotification(String msg, String getDeviceToken, String apiUrlFcm) throws IOException, JSONException {
        String result = "";
        URL url = new URL(apiUrlFcm);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + Config.API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("to", getDeviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", "notification title"); // Notification title
        info.put("body", msg); // Notification
        // body
        json.put("notification", info);

        try {
             wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            result = Config.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            result = Config.FAILURE;
        }

        System.out.println("GCM Notification is sent successfully");
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncSendNotification extends AsyncTask<String, Void, String> {
        String msg,deviceToken,fcmUrl;
        private ProgressDialog pDialog;

        public AsyncSendNotification(String msg, String getDeviceToken, String apiUrlFcm) {
            this.msg = msg;
            deviceToken = getDeviceToken;
            fcmUrl = apiUrlFcm;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Sending Notification...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

            @Override
        protected String doInBackground(String... strings) {
                String result = "";
                URL url = null;
                try {
                    url = new URL(fcmUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                try {
                    conn.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                conn.setRequestProperty("Authorization", "key=" + Config.API_KEY);
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();

               /* try {
                    json.put("to", deviceToken.trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject info = new JSONObject();
                try {
                    info.put("title", "notification title"); // Notification title
                    info.put("body", msg); // Notification
                    // body
                    json.put("notification", info);

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Gson gson = new Gson();

                /*MyPojo myPojo = gson.fromJson(jsontree, MyPojo.class);
                myPojo.getMessage().setToken(deviceToken);
                myPojo.getMessage().getNotification().setTitle("Title");
                myPojo.getMessage().getNotification().setBody(msg);*/
//                String jsontree = gson.toJson(myPojo);

                try {
                    wr = new OutputStreamWriter(
                            conn.getOutputStream());
                    wr.write(jsontree);
                    wr.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }
                    result = Config.SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    result = Config.FAILURE;
                }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            etMsg.setText(Config.EMPTY);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        this.menu = menu;
        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();
        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        if(getStatus==1){
            txtViewCount.setVisibility(View.GONE);
        }else {
            txtViewCount.setVisibility(View.GONE);
        }
        if (message == null)
            txtViewCount.setVisibility(View.GONE);
        else {
            txtViewCount.setVisibility(View.VISIBLE);
        }

        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message == null)
                    txtViewCount.setVisibility(View.GONE);
                else {
                    txtViewCount.setVisibility(View.VISIBLE);
                }

            }
        });
        notificaitons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message == null)
                    txtViewCount.setVisibility(View.GONE);
                else {
                    txtViewCount.setVisibility(View.VISIBLE);
                }
                Intent intent = new Intent(MainActivity.this,ViewMessageActivity.class);
                intent.putExtra(Config.MSGLIST, strMsg);
                startActivity(intent);
            }
        });
        return true;
    }

   /* @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final View notificaitons = menu.findItem(R.id.action_notification).getActionView();
        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        if(getStatus==1){
            txtViewCount.setVisibility(View.GONE);
        }else {
            txtViewCount.setVisibility(View.GONE);
        }
        if (message == null)
            txtViewCount.setVisibility(View.GONE);
        else {
            txtViewCount.setVisibility(View.VISIBLE);
        }

        return super.onPrepareOptionsMenu(menu);
    }*/

    private void setNotifCount(String message){
        this.message = message;
        invalidateOptionsMenu();
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchMsg extends AsyncTask<Void, Void, String> {
        String message;

        public FetchMsg(String message) {
            this.message = message;
        }

        @Override
        protected String doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            return message;
        }

        @Override
        public void onPostExecute(String message) {
            setNotifCount(message);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
