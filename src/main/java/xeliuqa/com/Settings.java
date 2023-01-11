package xeliuqa.com;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Settings extends AppCompatActivity {
    private SessionHandler session;
    LinearLayout logout;
    RelativeLayout contact;
    RelativeLayout help;
    RelativeLayout info;

    TextView cont;
    TextView hel;
    TextView inf;

    ImageView back;
    ImageView edit;
    TextView name;
    TextView email;
    TextView vers;
    TextView password;
    TextView code;
    String fname;
    String femail;
    Switch Switch1;
    RelativeLayout referral;
    RelativeLayout asset;
    RelativeLayout sponser;
    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;

    private String url="http://"+ conf.url()+":8080/v1/otpcode";
    private String url2="http://"+ conf.url()+":8080/v1/update_otp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        //fname =enc.decrypt(key,initVector,user.getUsername());
        femail = user.getFullName();
        //Toast.makeText(Account.this,femail,Toast.LENGTH_LONG).show();
        // Get clipboard manager object.
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0 ,0);
                        return true;
                    case R.id.exchange:
                        startActivity(new Intent(getApplicationContext(),Exchange.class));
                        overridePendingTransition(0 ,0);
                        return true;
                    case R.id.settings:
                        return true;

                }

                return false;
            }
        });

        Switch1 = (Switch) findViewById(R.id.fa);
        asset = (RelativeLayout) findViewById(R.id.asset_button);
        contact = (RelativeLayout) findViewById(R.id.contact_button);
        help = (RelativeLayout) findViewById(R.id.help_button);
        sponser = (RelativeLayout) findViewById(R.id.sponser_button);

        hel= (TextView) findViewById(R.id.hel);
        vers= (TextView) findViewById(R.id.vers);

        try {


            String versionName = BuildConfig.VERSION_NAME;
            vers.setText("Version: " + versionName);
        }catch (Exception e){
            e.printStackTrace();
        }

        get_data();
        update_data("0");
        Switch1.setText("Off");


        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, Assets.class);
                startActivity(i);
            }
        });

        sponser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this, Sponsor.class);
                startActivity(i);
            }
        });


        hel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText = hel.getText().toString();
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Settings.this, "Copied successfully.", Toast.LENGTH_LONG).show();
            }
        });

        Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (Switch1.isChecked()) {
                    update_data("1");
                    Switch1.setText("On");
                    //Toast.makeText(Settings.this, "",Toast.LENGTH_LONG).show();
                }else{
                    update_data("0");
                    Switch1.setText("Off");
                }

            }
        });

    }

    private void get_data(){
        JSONObject request = new JSONObject();
        try {
            request.put("email", femail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(Settings.this,response.toString(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            // Extract data from json and store into ArrayList as class objects
                            for(int i=0;i<dataArray.length();i++) {
                                JSONObject json_data = dataArray.getJSONObject(i);

                              String otps = json_data.getString("otpcode");
                              if (otps.equals("1")){
                                  Switch1.setChecked(true);
                                  Switch1.setText("On");
                              }else{
                                  Switch1.setChecked(false);
                                  Switch1.setText("Off");
                              }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void update_data(String val){
        JSONObject request = new JSONObject();
        try {
            request.put("email", femail);
            request.put("otpcode", val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.PUT, url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}
