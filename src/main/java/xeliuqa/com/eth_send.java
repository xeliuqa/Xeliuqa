package xeliuqa.com;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xeliuqa.com.Adapter.GasFee;

public class eth_send extends AppCompatActivity {
    public static TextView eth_dataSafeGas;
    public static TextView eth_dataProposeGas;
    public static TextView eth_dataFastGas;

    private SessionHandler session;
    private OkHttpClient client;
    private String address;
    private EditText toaddress;
    private EditText amount;
    private String keys;
    private TextView result;
    private TextView usdA;
    private ProgressDialog pDialog;
    private String contract;
    private ImageView logo;
    private String to;
    private String amt;
    private String gweifee;
    private String fee;
    private TextView gas;
    private TextView usd;
    private boolean mStopHandler = false;
    Button send;

    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();

    private String api_url = "http://"+ conf.url() +":8080/v1/send/ethraw";
    private String api_url4 = "http://"+ conf.url() +":8080/v1/ethbalance";
    private String api_url2 = "http://"+ conf.url() +":8080/v1/tokenbalance";
    private String api_url3 = "http://"+ conf.url() +":8082/v1/coin";

    private String otp_url = "http://"+ conf.url() +":8081/v1/otp";
    private String emails = "http://"+ conf.url() +"/email.php";
    private String url="http://"+ conf.url() +":8080/v1/otpcode";

    private static final String KEY_STATUS = "error";
    private static final String KEY_MESSAGE = "data";
    private String seth_rate;
    private TextView max;
    ImageButton scan;
    private IntentIntegrator qrScan;
    ImageView back;

    private DataHandler enc;

    private String otp;
    private String femail;
    private String otp_status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth_send);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        client = new OkHttpClient();

        femail = user.getFullName();
        address = enc.decrypt(key,initVector,user.getKeys());
        keys = enc.decrypt(key,initVector,user.getSecrets());
        //addressb = user.getBKeys();
        //keysb = user.getBSecrets();

        send = (Button) findViewById(R.id.btn_send);
        result = (TextView) findViewById(R.id.ETH_Balance);
        toaddress = (EditText) findViewById(R.id.etAddress);
        amount = (EditText) findViewById(R.id.etAmount);
        gas = (EditText) findViewById(R.id.gweifee);
        usd = (TextView)findViewById(R.id.ETH_usd);
        usdA = (TextView)findViewById(R.id.usd);
        scan=(ImageButton) findViewById(R.id.btn_scan);
        max = (TextView)findViewById(R.id.max);
        back = (ImageView) findViewById(R.id.back);
        logo = (ImageView) findViewById(R.id.logo);
        logo.setBackgroundResource(R.drawable.eth);

        eth_dataSafeGas = (TextView) findViewById(R.id.eth_dataSafeGas);
        eth_dataProposeGas = (TextView) findViewById(R.id.eth_dataProposeGas);
        eth_dataFastGas = (TextView) findViewById(R.id.eth_dataFastGas);

        Locale locale = new Locale("eng");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        qrScan = new IntentIntegrator(this);


        getOTP();
        get_status_otp();

        get_balance_ETH();
        getETH_fees();
        getUSD_ETH();

        //Change the logo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    double rat = Double.parseDouble(seth_rate);
                    double bals = Double.parseDouble(result.getText().toString());
                    bals= rat * bals;
                    String eths = String.format("%.2f", bals);
                    usd.setText("$"+String.valueOf(eths));
                } catch (Exception e){

                }
            }
        }, 3000);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Double get = Double.parseDouble(fee);
                    Double total= get / 1000000000;
                    Double totals = total * 21000;
                    double mybals = Double.parseDouble(result.getText().toString());

                    Double d = mybals - totals;
                    if (d.toString().contains("-")){
                        amount.setText("0.00");
                    }else{
                        String ret = String.format("%.5f", d);
                        amount.setText(ret.toString());
                    }

                }catch (Exception e){

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(eth_send.this, Transaction_ETH.class);
                startActivity(i);
            }
        });

        //Amount keypress watcher
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (!mStopHandler) {
                    new Handler().postDelayed(this, 1000);

                    try {
                        double toamount = Double.parseDouble(amount.getText().toString());
                        double mybals = Double.parseDouble(result.getText().toString());

                        double rat = Double.parseDouble(seth_rate);
                        double bals = Double.parseDouble(amount.getText().toString());
                        bals= rat * bals;
                        String eths = String.format("%.2f", bals);
                        usdA.setText("$"+String.valueOf(eths));

                        //limit the balance here
                        if (toamount > mybals){
                            amount.setError("\uD83E\uDD26\u200D♂️ Not enough funds.");
                        }
                    } catch (Exception e){
                        usdA.setText("0.00");
                    }

                }
            }
        };
        new Handler().post(runnable);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                to = toaddress.getText().toString();
                amt = amount.getText().toString();
                gweifee = gas.getText().toString();

                if (to.isEmpty() || to.length() == 0 || to.equals("") || to == null) {
                    toaddress.setError("Address is require!");
                }else if (amt.isEmpty() || amt.length() == 0 || amt.equals("") || amt == null) {
                    amount.setError("Please choose amount");
                }else if (gas.length() == 0 || gas.equals("") || gas == null){
                    gas.setError("Must insert fee!");
                }
                else {
                    double toamount = Double.parseDouble(amount.getText().toString());
                    double mybals = Double.parseDouble(result.getText().toString());

                    Double get = Double.parseDouble(fee);
                    Double total= get / 1000000000;
                    Double totals = total * 21000;
                    Double d = mybals - totals;
                    //limit the balance here
                    if (toamount > d){
                        amount.setError("Mining fee is "+ gweifee+ " GWEI");
                    }else{
                        if (otp_status.equals("1")){
                            //Start Here
                            //Add the OTP here
                            email(femail);
                            // get prompts.xml view
                            LayoutInflater li = LayoutInflater.from(eth_send.this);
                            View promptsView = li.inflate(R.layout.activity_otp, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(eth_send.this);
                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);
                            final EditText userInput = (EditText) promptsView.findViewById(R.id.email);

                            // set dialog message
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    String OTPs = userInput.getText().toString();
                                                    if (otp.equals(OTPs)){
                                                        send_transaction();
                                                    }else{
                                                        dialog.cancel();
                                                        Message alert = new Message();
                                                        alert.showDialog(eth_send.this, "OTP is wrong.");
                                                    }
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            //End here
                        }else{
                            send_transaction();
                        }
                    }
                }
            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(eth_send.this);
        pDialog.setMessage("Sending.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void get_balance_ETH(){

        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url4, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            result.setText(format);
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

    private void send_transaction()
    {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("contract", contract);
            request.put("address", address);
            request.put("algo", keys);
            request.put("toaddress", to);
            request.put("amount", amt);
            request.put("fees", gweifee);

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Toast.makeText(eth_send.this, response.toString(), Toast.LENGTH_LONG).show();
                            String res = response.toString();
                            if (res.contains("false")) {
                                String IDs = response.getString(KEY_MESSAGE);
                                //Go back to dashboard
                                Intent i = new Intent(getApplicationContext(), blockchain.class);
                                i.putExtra("hash", IDs);
                                i.putExtra("coin","ETH");
                                startActivity(i);

                            }else if(res.contains("true")){
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "Error : Transaction failed.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                "Connection was interrupted: please try to resend", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


    private void getETH_fees()
    {
        final Request request = new Request.Builder().url("https://api.etherscan.io/api?module=gastracker&action=gasoracle&apikey=IZWJ6D87UJWX6QSZ85WKFN1H5V41DUFTZM").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("0");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = response.body().string();
                            String[] parts = res.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];

                            fee = part3;
                            gas.setText(fee);
                            GasFee process = new GasFee();
                            process.execute();
                            //} catch (JSONException e) {
                            //Toast.makeText(MainActivity.this, part1, Toast.LENGTH_LONG).show();
                        } catch (IOException e){
                            Toast.makeText(eth_send.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void getUSD_ETH() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "ethereum");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            //Float bal = BigDecimal.valueOf(jsonObject.getDouble("price")).floatValue();
                            Double rate =jsonObject.getDouble("USD");
                            //usd.setText("$"+rate.toString());
                            seth_rate = rate.toString();
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
    //This is the qr scanner
    //QR code scanner class
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    String datas = result.getContents();
                    if (datas.contains(":")){
                        String[] parts = datas.split(":");
                        String part1 = parts[1];
                        toaddress.setText(part1);
                    }else{
                        toaddress.setText(result.getContents());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getOTP()
    {
        JSONObject request = new JSONObject();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, otp_url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.get("data").toString();
                            otp = res;
                            //Toast.makeText(btc_send.this, otp, Toast.LENGTH_LONG).show();
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

    private void email(String emailss){
        JSONObject request = new JSONObject();
        try {
            request.put("totp", otp);
            request.put("email", emailss);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, emails, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //String res = response.get("data").toString();
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

    private void get_status_otp(){
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

                                otp_status=otps;
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

