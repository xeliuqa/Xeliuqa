package xeliuqa.com;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class PIN_Verify extends AppCompatActivity {

    Button create;
    EditText code;
    private SessionHandler session;
    Handler h = new Handler();
    int delay = 1000;
    Runnable runnable;

    private String address;
    private String keys;
    private String addressb;
    private String keysb;
    private String word_seed;
    private String pin;
    TextView word;
    TextView name;
    String femail;
    TextView email;
    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;

    private String url= "http://"+ conf.url() + ":8080/v1/email";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verify);
        session = new SessionHandler(getApplicationContext());


        User user = session.getUserDetails();
        femail = user.getFullName();
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        address = enc.decrypt(key, initVector, user.getKeys());
        keys = enc.decrypt(key, initVector, user.getSecrets());
        addressb = enc.decrypt(key, initVector, user.getBKeys());
        keysb = enc.decrypt(key, initVector, user.getBSecrets());
        word_seed = user.word();
        pin = user.getOTP();

        email.setText(femail);

        get_data();


        code = (EditText) findViewById(R.id.code);
        word = (TextView) findViewById(R.id.word);

        code.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String codes = code.getText().toString();
                if (codes.length() == 6) {
                    if (codes.equals(pin)) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        //word.setText("Code not match.");
                        Spannable WordtoSpan = new SpannableString("You entered wrong PIN");
                        WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        word.setText(WordtoSpan);
                    }
                } else {
                    word.setText("Please enter your 6 digit PIN code");
                }
            }
        });
        TextView v = (TextView) findViewById(R.id.email);
        v.setVisibility(TextView.GONE);
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



