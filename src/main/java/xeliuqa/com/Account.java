package xeliuqa.com;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Account extends AppCompatActivity {
    private SessionHandler session;
    private Config conf;
    LinearLayout logout;
    ImageView back;
    ImageView edit;
    TextView name;
    TextView email;
    TextView password;
    TextView code;
    String fname;
    String femail;
    RelativeLayout referral;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;

    private String url= "http://"+ conf.url() + ":8080/v1/email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        femail = user.getFullName();

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
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        overridePendingTransition(0 ,0);
                        return true;

                }

                return false;
            }
        });

        logout = (LinearLayout) findViewById(R.id.logout);
        edit = (ImageView) findViewById(R.id.edit);
        name = (TextView) findViewById(R.id.fullname);
        email = (TextView) findViewById(R.id.email);
        code = (TextView) findViewById(R.id.code);
        password = (TextView) findViewById(R.id.password);
        referral = (RelativeLayout) findViewById(R.id.referral_button);

        email.setText(femail);

        get_data();


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Account.this, Account_Edit.class);
                i.putExtra("password", password.getText().toString());
                startActivity(i);
            }
        });

        referral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText = code.getText().toString();
                // Create a new ClipData.
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                // Set it as primary clip data to copy text to system clipboard.
                clipboardManager.setPrimaryClip(clipData);
                // Popup a snackbar.
                Toast.makeText(Account.this, "Copied successfully.", Toast.LENGTH_LONG).show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(Account.this, Signin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });


    }

    private void get_data(){
        JSONObject request = new JSONObject();
        try {
            request.put("email", email.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Toast.makeText(Account.this,response.toString(),Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray dataArray = jsonObject.getJSONArray("data");
                            // Extract data from json and store into ArrayList as class objects
                            for(int i=0;i<dataArray.length();i++) {
                                JSONObject json_data = dataArray.getJSONObject(i);

                                name.setText(json_data.getString("username"));
                                code.setText(json_data.getString("code"));
                                password.setText(json_data.getString("password"));
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
}
