package xeliuqa.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;

public class Transaction_ETC extends AppCompatActivity {
    private static final String apikey = Config.ethplorerAPI();
    private SessionHandler session;
    private OkHttpClient client;
    private String address;
    private String keys;
    private TextView result;
    private String Jresult;
    private String setc_rate;

    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    //private RecyclerView mRVFishPrice;
    //private Adaptor mAdapter;
    private String Typess;
    private Button received;
    private Button btn_send;
    private TextView rates;
    private String api_url = "http://"+conf.url()+":8080/v1/ethbalance";
    private String api_url3 = "http://"+ conf.url()+":8082/v1/coin";
    ImageView back;
    private DataHandler enc;
    private SwipeRefreshLayout pullToRefresh;

    private RecyclerView rv;
    private List<List_Data>list_data;
    private MyAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etc_transaction);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        client = new OkHttpClient();
        address = enc.decrypt(key,initVector,user.getKeys());
        keys = enc.decrypt(key,initVector,user.getSecrets());

        result = (TextView) findViewById(R.id.ETC);
        received = (Button) findViewById(R.id.btn_receive);
        btn_send=(Button) findViewById(R.id.btn_send);
        rates = (TextView) findViewById(R.id.AFCASH_rate);
        back = (ImageView) findViewById(R.id.back);
        pullToRefresh = findViewById(R.id.pullToRefresh);

        //Set language to usd
        Locale locale = new Locale("eng");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        get_balance_ETC();
        getUSD_ETC();

        rv=(RecyclerView)findViewById(R.id.fishPriceList);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list_data=new ArrayList<>();
        adapter=new MyAdapter(list_data,this);

        //new AsyncFetch().execute();
        getData();
        //rates.setText("$"+ getIntent().getStringExtra("rate"));

        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transaction_ETC.this, qrcode.class);
                i.putExtra("coin","ETC");
                startActivity(i);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transaction_ETC.this, etc_send.class);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transaction_ETC.this, MainActivity.class);
                startActivity(i);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_balance_ETC();
                getUSD_ETC();
                new AsyncFetch().execute();
                pullToRefresh.setRefreshing(false);
            }
        });
        //Change the logo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    double rat = Double.parseDouble(setc_rate);
                    double bals = Double.parseDouble(result.getText().toString());
                    bals= rat * bals;
                    String etcs = String.format("%.2f", bals);
                    rates.setText("$"+String.valueOf(etcs));
                } catch (Exception e){

                }
            }
        }, 3000);
    }
    private void get_balance_ETC(){

        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, api_url, request, new Response.Listener<JSONObject>() {
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
                }, new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    //This the trasaction area
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Transaction_ETC.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://api.ethplorer.io/getAddressTransactions/" +address+ "?apiKey=" + apikey);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we received data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<cash_> data=new ArrayList<>();

            pdLoading.dismiss();
            try {
                JSONArray dataArray = new JSONArray(result);
                for(int i=0;i<dataArray.length();i++){
                    JSONObject json_data = dataArray.getJSONObject(i);
                    cash_ fishData = new cash_();

                    String s= json_data.getString("timestamp");
                    long timestamp = Long.parseLong(s) * 1000L;
                    //fishData.timeStamp=getDate(timestamp );

                    String types= json_data.getString("from");
                    String add1= address.substring(0,5);
                    String add2 =types.substring(0,5);

                    String up1 = add1.toUpperCase();
                    String up2 = add2.toUpperCase();

                    if (up1.equals(up2))
                    {
                        String EtcAm= json_data.getString("value");
                        fishData.amount = EtcAm+ " ETC";
                        fishData.timeStamp=(R.drawable.arrow_b);
                        String hashs= json_data.getString("hash");
                        fishData.from="Hash: "+hashs;
                    }
                    else
                    {
                        String EtcAm= json_data.getString("value");
                        fishData.amount = EtcAm+ " ETC";
                        fishData.timeStamp=(R.drawable.arrow_r);
                        String hashs= json_data.getString("hash");
                        fishData.from="Hash: "+hashs;
                    }
                    data.add(fishData);
                }
                //mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                //mAdapter = new Adaptor(Transaction_ETC.this, data);
                //mRVFishPrice.setAdapter(mAdapter);
                //mRVFishPrice.setLayoutManager(new LinearLayoutManager(Transaction_ETC.this));

            } catch (JSONException e) {
                //Toast.makeText(Transaction_ETC.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
    private String getDate(long timeStamp){

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    private void getUSD_ETC() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "ethereum classic");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=ETC&tsyms=USD", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            //Float bal = BigDecimal.valueOf(jsonObject.getDouble("price")).floatValue();
                            Double rate =jsonObject.getDouble("USD");
                            //usd.setText("$"+rate.toString());
                            setc_rate = rate.toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void getData() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "https://api.etcplorer.io/getAddressTransactions/" +address+ "?apiKey=" + apikey, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray dataArray = new JSONArray(response);
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject json_data = dataArray.getJSONObject(i);
                        List_Data ld = new List_Data();

                        String types= json_data.getString("from");
                        String add1= address.substring(0,5);
                        String add2 =types.substring(0,5);

                        String up1 = add1.toUpperCase();
                        String up2 = add2.toUpperCase();

                        if (up1.equals(up2))
                        {
                            String EtcAm= json_data.getString("value");
                            Double vals = Double.parseDouble(EtcAm);
                            String btcs = String.format("%.8f", vals);
                            ld.amount = btcs+ " ETC";
                            ld.image=(R.drawable.arrow_b);
                            String hashs= json_data.getString("hash");
                            ld.name=hashs;
                        }
                        else
                        {
                            String EtcAm= json_data.getString("value");
                            Double vals = Double.parseDouble(EtcAm);
                            String btcs = String.format("%.8f", vals);
                            ld.amount = btcs+ " ETC";
                            ld.image=(R.drawable.arrow_r);
                            String hashs= json_data.getString("hash");
                            ld.name=hashs;
                        }
                        list_data.add(ld);
                    }
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
