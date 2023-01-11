package xeliuqa.com;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import xeliuqa.com.Adapter.ErcGasFee;

public class erc_send extends AppCompatActivity {

    public static TextView erc_dataSafeGas;
    public static TextView erc_dataProposeGas;
    public static TextView erc_dataFastGas;
    private SessionHandler session;
    private OkHttpClient client;
    private String address;
    private EditText toaddress;
    private EditText amount;
    private String keys;
    private TextView result;
    private TextView gas;
    private ProgressDialog pDialog;
    private String contract;
    private ImageView logo;
    private String name;
    private String to;
    private String amt;
    private String gweifee;
    private String transID;
    private String balance;
    private String fee;
    private ImageButton scan;
    Button send;

    private static final String KEY_STATUS = "error";
    private static final String KEY_MESSAGE = "data";
    private TextView usd;
    private TextView usdA;
    private TextView coin_name;
    private boolean mStopHandler = false;
    TextView tvData;
    Button btnQRScan;
    private TextView max;
    private String mybalance;

    private String safcash_rate;
    private String seth_rate;
    private String sbnb_rate;
    private String sbnt_rate;
    private String sven_rate;
    private String somg_rate;
    private String szrx_rate;
    private String smkr_rate;
    private String szil_rate;
    private String saion_rate;
    private String sae_rate;
    private String sbat_rate;
    private String sppt_rate;
    private String srep_rate;
    private String sntc_rate;
    private String scs_rate;
    private String sbtc_rate;
    private String susdc_rate;
    private String stusd_rate;
    ImageView back;
    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;
    Double ethbals;
    private String api_url2 = "http://"+ conf.url() +":8080/v1/tokenbalance";
    private String api_url = "http://"+ conf.url() +":8080/v1/send/eth";
    private String api_urleth = "http://"+ conf.url() +":8080/v1/ethbalance";
    private String api_url3 = "http://"+ conf.url() +":8084/api/v1/transfer";
    private String otp_url = "http://"+ conf.url() +":8081/v1/otp";
    private String emails = "http://"+ conf.url() +"/email.php";
    private String url="http://"+ conf.url() +":8080/v1/otpcode";

    private IntentIntegrator qrScan;
    private String otp;
    private String femail;
    private String otp_status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erc_send);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        femail = user.getFullName();
        client = new OkHttpClient();
        address = enc.decrypt(key,initVector,user.getKeys());
        keys = enc.decrypt(key,initVector,user.getSecrets());
        //addressb = user.getBKeys();
        //keysb = user.getBSecrets();

        send = (Button) findViewById(R.id.btn_send);
        result = (TextView) findViewById(R.id.AFCASH_Balance);
        toaddress = (EditText) findViewById(R.id.etAddress);
        amount = (EditText) findViewById(R.id.etAmount);
        gas = (TextView) findViewById(R.id.gweifee);
        logo = (ImageView) findViewById(R.id.logo);
        scan=(ImageButton) findViewById(R.id.btn_scan);
        //usd = (TextView)findViewById(R.id.AFCASH_usd);
        //usdA = (TextView)findViewById(R.id.usd);
        max = (TextView)findViewById(R.id.max);
        back = (ImageView) findViewById(R.id.back);

        erc_dataSafeGas = (TextView) findViewById(R.id.erc_dataSafeGas);
        erc_dataProposeGas = (TextView) findViewById(R.id.erc_dataProposeGas);
        erc_dataFastGas = (TextView) findViewById(R.id.erc_dataFastGas);

        Locale locale = new Locale("eng");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        contract = getIntent().getStringExtra("contract");
        name =getIntent().getStringExtra("coin");

        //Toast.makeText(erc_send.this, contract, Toast.LENGTH_LONG).show();

        if (name.equals("YourCoin")){
            logo.setBackgroundResource(R.drawable.logo);
        }else if (name.equals("PAX")){
            logo.setBackgroundResource(R.drawable.pax);
        }else if (name.equals("OMG")){
            logo.setBackgroundResource(R.drawable.omg);
        }else if (name.equals("BAT")){
            logo.setBackgroundResource(R.drawable.bat);
        }else if (name.equals("BNB")){
            logo.setBackgroundResource(R.drawable.bnb);
        }else if (name.equals("USDC")){
            logo.setBackgroundResource(R.drawable.usdc);
        }else if (name.equals("MKR")){
            logo.setBackgroundResource(R.drawable.mkr);
        }else if (name.equals("TUSD")) {
            logo.setBackgroundResource(R.drawable.tusd);
        }

        getOTP();
        get_status_otp();

        get_ERC_balance();
        get_balance_ETH();
        getETH_fees();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
                }else if (gas.length() == 0 || gas.equals("") || gas == null) {
                    gas.setError("Must insert fee!");
                }
                else {
                    try{

                        double toamount = Double.parseDouble(amount.getText().toString());
                        double mybals = Double.parseDouble(result.getText().toString());
                        //limit the balance here
                        Double get = Double.parseDouble(fee);
                        Double total = get / 1000000000;
                        Double totals = total * 21000;
                        Double totalss = ethbals - totals;

                        if (totalss > 0.000 ){
                            if (toamount > mybals){
                                amount.setError("Insufficient funds.");
                            }else{

                                if (otp_status.equals("1")){
                                    //Start Here
                                    //Add the OTP here
                                    email(femail);
                                    // get prompts.xml view
                                    LayoutInflater li = LayoutInflater.from(erc_send.this);
                                    View promptsView = li.inflate(R.layout.activity_otp, null);

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(erc_send.this);
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
                                                                if (name.equals("USDC")){
                                                                    send_transaction2();
                                                                }else{
                                                                    send_transaction();
                                                                }
                                                            }else{
                                                                dialog.cancel();
                                                                Message alert = new Message();
                                                                alert.showDialog(erc_send.this, "OTP is wrong.");
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
                                    if (name.equals("USDC")){
                                        send_transaction2();
                                    }else{
                                        send_transaction();
                                    }
                                }
                            }
                        }else{
                            Message dialog = new Message();
                            dialog.showDialog(erc_send.this, "You need more moneys to complete this transaction.");
                        }
                    }catch (Exception e){
                        Message dialog = new Message();
                        dialog.showDialog(erc_send.this, e.getMessage());
                    }
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });

        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mybals = Double.parseDouble(result.getText().toString());
                amount.setText(String.valueOf(mybals));
            }
        });


        //listener click amount
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void displayLoader() {
        pDialog = new ProgressDialog(erc_send.this);
        pDialog.setMessage("Sending.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void get_ERC_balance(){
        JSONObject request = new JSONObject();
        try {
            //Toast.makeText(erc_send.this, "Address: "+address + " Contract: "+contract, Toast.LENGTH_LONG).show();
            if (name.equals("USDC")){
                request.put("address", address);
                request.put("contract", contract);
                request.put("coin", "USDC");
            }else{
                request.put("address", address);
                request.put("contract", contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (name.equals("USDC")){
                                //Toast.makeText(erc_send.this, response.get("balance").toString(), Toast.LENGTH_LONG).show();
                                Double res = Double.parseDouble(response.get("balance").toString());
                                Double rates = res / 1000000;
                                String format = String.format("%.6f",rates);
                                result.setText(format);
                            }else {
                                //Toast.makeText(erc_send.this, response.get("balance").toString(), Toast.LENGTH_LONG).show();
                                Double res = Double.parseDouble(response.get("balance").toString());
                                String format = String.format("%.8f",res);
                                result.setText(format);
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
            request.put("coin", name);

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            String res = response.toString();

                            if (res.contains("true")) {
                                Message dialog = new Message();
                                dialog.showDialog(erc_send.this, "Transaction Failed");
                            }else {
                                String IDs = response.getString(KEY_MESSAGE);
                                //Go back to dashboard
                                Intent i = new Intent(getApplicationContext(), blockchain.class);
                                i.putExtra("hash", IDs);
                                i.putExtra("coin","AFCASH");
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        Toast.makeText(getApplicationContext(),
                                "Connection was interrupted: please try to resend", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
    private void send_transaction2()
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
            request.put("coin", name);

        } catch (JSONException e) {
            //e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url3, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            String res = response.toString();

                            if (res.contains("true")) {
                                Message dialog = new Message();
                                dialog.showDialog(erc_send.this, "Transaction Failed");
                            }else {
                                String IDs = response.getString("hash");
                                //Go back to dashboard
                                Intent i = new Intent(getApplicationContext(), blockchain.class);
                                i.putExtra("hash", IDs);
                                i.putExtra("coin","AFCASH");
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        Toast.makeText(getApplicationContext(),
                                "Connection was interrupted: please try to resend", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), blockchain.class);
        startActivity(i);
        i.putExtra("hash",transID);

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
                            ErcGasFee process = new ErcGasFee();
                            process.execute();
                            //} catch (JSONException e) {
                            //Toast.makeText(MainActivity.this, part1, Toast.LENGTH_LONG).show();
                        } catch (IOException e){
                            Toast.makeText(erc_send.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    //This is the qr scanner


    //This is the USD rate per coin//////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getUSD_BNB() {
        final Request request = new Request.Builder().url("https://api.binance.com/api/v3/ticker/price?symbol=BNBUSDT").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sbnb_rate="0.00";
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
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("price");
                            String eths = String.format("%.2f", rate);
                            sbnb_rate = rate.toString();

                        } catch (JSONException e) {
                            sbnb_rate="0.00";
                        } catch (IOException e){
                            sbnb_rate="0.00";
                        }
                    }
                });
            }
        });
    }

    private void getUSD_VEN() {
        final Request request = new Request.Builder().url("https://api.binance.com/api/v3/ticker/price?symbol=VENUSDT").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sven_rate = "0.00";
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
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("price");
                            String eths = String.format("%.2f", rate);
                            sven_rate = rate.toString();

                        } catch (JSONException e) {
                            sven_rate = "0.00";
                        } catch (IOException e){
                            sven_rate = "0.00";
                        }
                    }
                });
            }
        });
    }

    private void getUSD_BNT() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Bancor/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sbnt_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            sbnt_rate = eths;

                            //} catch (JSONException e) {
                            //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        } catch (IOException e){
                            sbnt_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_ZRX() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/0x/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        szrx_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            szrx_rate = eths;

                        } catch (IOException e){
                            szrx_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_OMG() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Omisego/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        somg_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            somg_rate = eths;

                        } catch (IOException e){
                            somg_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_MKR() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Maker/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        smkr_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            smkr_rate = eths;

                        } catch (IOException e){
                            smkr_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_ZIL() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Zilliqa/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        szil_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            szil_rate = eths;

                        } catch (IOException e){
                            szil_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_AE() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Aeternity/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sae_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);

                            sae_rate = eths;

                        } catch (IOException e){
                            sae_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_AION() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Aion/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saion_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);

                            saion_rate = eths;

                        } catch (IOException e){
                            saion_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_BAT() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/basic-attention-token/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sbat_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            sbat_rate = eths;

                        } catch (IOException e){
                            sbat_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_PPT() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Populous/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sppt_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            sppt_rate = eths;

                        } catch (IOException e){
                            sppt_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_REP() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/Augur/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        srep_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.2f", value);
                            srep_rate = eths;

                        } catch (IOException e){
                            srep_rate = "0.00";
                        }
                        //catch (ParseException e){
                        //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        //}
                    }
                });
            }
        });
    }

    private void getUSD_CS() {
        final Request request = new Request.Builder().url("https://api.coinmarketcap.com/v1/ticker/credits/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scs_rate = "0.00";
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
                            String string = res;
                            String[] parts = string.split(",");
                            String part1 = parts[4]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split("\"");
                            String part3 = part2[3];
                            double value = Double.parseDouble(part3);
                            String eths = String.format("%.6f", value);
                            scs_rate = eths;

                        } catch (IOException e){
                            scs_rate = "0.00";
                        }
                    }
                });
            }
        });
    }

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

    private void get_balance_ETH(){

        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_urleth, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ethbals =Double.parseDouble(response.get("balance").toString());
                            //Toast.makeText(erc_send.this, ethbals.toString(), Toast.LENGTH_LONG).show();
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

