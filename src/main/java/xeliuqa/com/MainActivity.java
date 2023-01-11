package xeliuqa.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import xeliuqa.com.Adapter.BATData;
import xeliuqa.com.Adapter.BCHData;
import xeliuqa.com.Adapter.BNBData;
import xeliuqa.com.Adapter.BTCData;
import xeliuqa.com.Adapter.ETCData;
import xeliuqa.com.Adapter.ETHData;
import xeliuqa.com.Adapter.MKRData;
import xeliuqa.com.Adapter.OMGData;
import xeliuqa.com.Adapter.PAXData;
import xeliuqa.com.Adapter.TUSDData;
import xeliuqa.com.Adapter.USDCData;

public class MainActivity extends AppCompatActivity {
    //Get percentage
    public static TextView btc_percent_change_1h;
    public static TextView btc_percent_change_24h;
    public static TextView btc_percent_change_7d;
    public static TextView eth_percent_change_1h;
    public static TextView eth_percent_change_24h;
    public static TextView eth_percent_change_7d;
    public static TextView etc_percent_change_1h;
    public static TextView etc_percent_change_24h;
    public static TextView etc_percent_change_7d;
    public static TextView pax_percent_change_1h;
    public static TextView pax_percent_change_24h;
    public static TextView pax_percent_change_7d;
    public static TextView bch_percent_change_1h;
    public static TextView bch_percent_change_24h;
    public static TextView bch_percent_change_7d;
    public static TextView bnb_percent_change_1h;
    public static TextView bnb_percent_change_24h;
    public static TextView bnb_percent_change_7d;
    public static TextView bat_percent_change_1h;
    public static TextView bat_percent_change_24h;
    public static TextView bat_percent_change_7d;
    public static TextView omg_percent_change_1h;
    public static TextView omg_percent_change_24h;
    public static TextView omg_percent_change_7d;
    public static TextView usdc_percent_change_1h;
    public static TextView usdc_percent_change_24h;
    public static TextView usdc_percent_change_7d;
    public static TextView mkr_percent_change_1h;
    public static TextView mkr_percent_change_24h;
    public static TextView mkr_percent_change_7d;
    public static TextView tusd_percent_change_1h;
    public static TextView tusd_percent_change_24h;
    public static TextView tusd_percent_change_7d;

    private OkHttpClient client;
    private SessionHandler session;
    private String address;
    private String keys;
    private String addressb;
    private String keysb;
    private String bch_address;
    private String bch_key;
    private TextView result;
    private TextView exchange_site;
    private TextView USD;
    private String version="1.0"; //Update your version
    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;
    ImageView setting;
    private Spinner spin;
    //private TextView lyte_balance;
    private TextView btc_balance;
    private TextView eth_balance;
    private TextView etc_balance;
    private TextView bch_balance;
    private TextView pax_balance;
    private TextView bnb_balance;
    private TextView bat_balance;
    private TextView omg_balance;
    private TextView usdc_balance;
    private TextView mkr_balance;
    private TextView tusd_balance;

    private TextView btc_usd;
    private TextView eth_usd;
    private TextView etc_usd;
    private TextView bch_usd;
    private TextView pax_usd;
    private TextView bnb_usd;
    private TextView bat_usd;
    private TextView omg_usd;
    private TextView usdc_usd;
    private TextView mkr_usd;
    private TextView tusd_usd;

    private String api_url = "http://"+ conf.url() +":8080/v1/ethbalance"; //Change your IP
    private String api_url2 = "http://"+ conf.url()+":8080/v1/tokenbalance";//Change your IP
    private String api_url3 = "http://"+ conf.url()+":8082/v1/coin";//Change your IP

    //RelativeLayout btn_lyte;
    RelativeLayout btn_btc;
    RelativeLayout btn_eth;
    RelativeLayout btn_etc;
    RelativeLayout btn_bch;
    RelativeLayout btn_pax;
    RelativeLayout btn_bnb;
    RelativeLayout btn_bat;
    RelativeLayout btn_omg;
    RelativeLayout btn_usdc;
    RelativeLayout btn_mkr;
    RelativeLayout btn_tusd;
    private Handler handler = new Handler();
    Handler h = new Handler();
    int delay = 5000;
    Runnable runnable;

    private String eth_rate;
    private String etc_rate;
    private String btc_rate;
    private String bch_rate;
    private String pax_rate;
    private String bnb_rate;
    private String bat_rate;
    private String omg_rate;
    private String usdc_rate;
    private String mkr_rate;
    private String tusd_rate;

    private String seth_rates;
    private String setc_rates;
    private String sbtc_rates;
    private String sbch_rates;
    private String spax_rates;
    private String sbnb_rates;
    private String sbat_rates;
    private String somg_rates;
    private String susdc_rates;
    private String smkr_rates;
    private String stusd_rates;

    private String seth_rate;
    private String setc_rate;
    private String sbtc_rate;
    private String sbch_rate;
    private String spax_rate;
    private String sbnb_rate;
    private String sbat_rate;
    private String somg_rate;
    private String susdc_rate;
    private String smkr_rate;
    private String stusd_rate;

    private String update_content;

    private TextView vol;
    private TextView high;
    private TextView low;

    ImageView notify;
    private String coins1;

    private long mBackPressed;

    private SwipeRefreshLayout pullToRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
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

        address = enc.decrypt(key,initVector,user.getKeys());
        keys = enc.decrypt(key,initVector,user.getSecrets());
        addressb = enc.decrypt(key,initVector,user.getBKeys());
        keysb = enc.decrypt(key,initVector,user.getBSecrets());
        bch_address = enc.decrypt(key,initVector,user.getCKeys());
        bch_key = enc.decrypt(key,initVector,user.getCSecrets());

        //Set language to usd
        Locale locale = new Locale("eng");
        Locale.setDefault(locale);
        final Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        spin = (Spinner) findViewById(R.id.spinner1);

        btc_balance = (TextView) findViewById(R.id.btc_balance);
        eth_balance = (TextView) findViewById(R.id.eth_balance);
        etc_balance = (TextView) findViewById(R.id.etc_balance);
        bch_balance = (TextView) findViewById(R.id.bch_balance);
        pax_balance = (TextView) findViewById(R.id.pax_balance);
        bnb_balance = (TextView) findViewById(R.id.bnb_balance);
        bat_balance = (TextView) findViewById(R.id.bat_balance);
        omg_balance = (TextView) findViewById(R.id.omg_balance);
        usdc_balance = (TextView) findViewById(R.id.usdc_balance);
        mkr_balance = (TextView) findViewById(R.id.mkr_balance);
        tusd_balance = (TextView) findViewById(R.id.tusd_balance);

        btn_btc = (RelativeLayout) findViewById(R.id.btc_button);
        btn_eth = (RelativeLayout) findViewById(R.id.eth_button);
        btn_etc = (RelativeLayout) findViewById(R.id.etc_button);
        btn_bch = (RelativeLayout) findViewById(R.id.bch_button);
        btn_pax = (RelativeLayout) findViewById(R.id.pax_button);
        btn_bnb = (RelativeLayout) findViewById(R.id.bnb_button);
        btn_bat = (RelativeLayout) findViewById(R.id.bat_button);
        btn_omg = (RelativeLayout) findViewById(R.id.omg_button);
        btn_usdc = (RelativeLayout) findViewById(R.id.usdc_button);
        btn_mkr = (RelativeLayout) findViewById(R.id.mkr_button);
        btn_tusd = (RelativeLayout) findViewById(R.id.tusd_button);

        eth_usd = (TextView) findViewById(R.id.eth_usd);
        etc_usd = (TextView) findViewById(R.id.etc_usd);
        btc_usd = (TextView) findViewById(R.id.btc_usd);
        bch_usd = (TextView) findViewById(R.id.bch_usd);
        pax_usd = (TextView) findViewById(R.id.pax_usd);
        bnb_usd = (TextView) findViewById(R.id.bnb_usd);
        bat_usd = (TextView) findViewById(R.id.bat_usd);
        omg_usd = (TextView) findViewById(R.id.omg_usd);
        usdc_usd = (TextView) findViewById(R.id.usdc_usd);
        mkr_usd = (TextView) findViewById(R.id.mkr_usd);
        tusd_usd = (TextView) findViewById(R.id.tusd_usd);

        USD = (TextView) findViewById(R.id.total_balance);
        pullToRefresh = findViewById(R.id.pullToRefresh);

        //Percentage
        btc_percent_change_1h = (TextView) findViewById(R.id.btc_percent_change_1h);
        btc_percent_change_24h = (TextView) findViewById(R.id.btc_percent_change_24h);
        btc_percent_change_7d = (TextView) findViewById(R.id.btc_percent_change_7d);
        eth_percent_change_1h = (TextView) findViewById(R.id.eth_percent_change_1h);
        eth_percent_change_24h = (TextView) findViewById(R.id.eth_percent_change_24h);
        eth_percent_change_7d = (TextView) findViewById(R.id.eth_percent_change_7d);
        etc_percent_change_1h = (TextView) findViewById(R.id.etc_percent_change_1h);
        etc_percent_change_24h = (TextView) findViewById(R.id.etc_percent_change_24h);
        etc_percent_change_7d = (TextView) findViewById(R.id.etc_percent_change_7d);
        pax_percent_change_1h = (TextView) findViewById(R.id.pax_percent_change_1h);
        pax_percent_change_24h = (TextView) findViewById(R.id.pax_percent_change_24h);
        pax_percent_change_7d = (TextView) findViewById(R.id.pax_percent_change_7d);
        bch_percent_change_1h = (TextView) findViewById(R.id.bch_percent_change_1h);
        bch_percent_change_24h = (TextView) findViewById(R.id.bch_percent_change_24h);
        bch_percent_change_7d = (TextView) findViewById(R.id.bch_percent_change_7d);
        bnb_percent_change_1h = (TextView) findViewById(R.id.bnb_percent_change_1h);
        bnb_percent_change_24h = (TextView) findViewById(R.id.bnb_percent_change_24h);
        bnb_percent_change_7d = (TextView) findViewById(R.id.bnb_percent_change_7d);
        bat_percent_change_1h = (TextView) findViewById(R.id.bat_percent_change_1h);
        bat_percent_change_24h = (TextView) findViewById(R.id.bat_percent_change_24h);
        bat_percent_change_7d = (TextView) findViewById(R.id.bat_percent_change_7d);
        omg_percent_change_1h = (TextView) findViewById(R.id.omg_percent_change_1h);
        omg_percent_change_24h = (TextView) findViewById(R.id.omg_percent_change_24h);
        omg_percent_change_7d = (TextView) findViewById(R.id.omg_percent_change_7d);
        usdc_percent_change_1h = (TextView) findViewById(R.id.usdc_percent_change_1h);
        usdc_percent_change_24h = (TextView) findViewById(R.id.usdc_percent_change_24h);
        usdc_percent_change_7d = (TextView) findViewById(R.id.usdc_percent_change_7d);
        mkr_percent_change_1h = (TextView) findViewById(R.id.mkr_percent_change_1h);
        mkr_percent_change_24h = (TextView) findViewById(R.id.mkr_percent_change_24h);
        mkr_percent_change_7d = (TextView) findViewById(R.id.mkr_percent_change_7d);
        tusd_percent_change_1h = (TextView) findViewById(R.id.tusd_percent_change_1h);
        tusd_percent_change_24h = (TextView) findViewById(R.id.tusd_percent_change_24h);
        tusd_percent_change_7d = (TextView) findViewById(R.id.tusd_percent_change_7d);

        main_task get_info = new main_task();
        get_info.execute();


        coins1="No";


        btn_btc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_BTC.class);
                startActivity(i);
            }
        });

        btn_bch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message dialog = new Message();
                dialog.showDialog(MainActivity.this, "BCH is not available.");
            }
        });

        btn_eth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ETH.class);
                startActivity(i);
            }
        });

        btn_bnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                i.putExtra("coin",  "BNB");
                i.putExtra("contract",  "0xB8c77482e45F1F44dE1745F52C74426C631bDD52");
                startActivity(i);
            }
        });

        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message dialog = new Message();
                dialog.showDialog(MainActivity.this, "ETC is not yet available.");
            }
        });

        btn_pax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                i.putExtra("coin",  "PAX");
                i.putExtra("contract",  "0x8e870d67f660d95d5be530380d0ec0bd388289e1");
                startActivity(i);
            }
        });

        btn_bat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                //i.putExtra("rate",  sbnt_rates);
                i.putExtra("coin",  "BAT");
                i.putExtra("contract",  "0x0d8775f648430679a709e98d2b0cb6250d2887ef");
                startActivity(i);
            }
        });

        btn_omg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                //i.putExtra("rate",  sbnt_rates);
                i.putExtra("coin",  "OMG");
                i.putExtra("contract",  "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07");
                startActivity(i);
            }
        });

        btn_usdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                //i.putExtra("rate",  sbnt_rates);
                i.putExtra("coin",  "USDC");
                i.putExtra("contract",  "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48");
                startActivity(i);
            }
        });

        btn_mkr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                //i.putExtra("rate",  sbnt_rates);
                i.putExtra("coin",  "MKR");
                i.putExtra("contract",  "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2");
                startActivity(i);
            }
        });
        btn_tusd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Transaction_ERC.class);
                i.putExtra("coin",  "TUSD");
                i.putExtra("contract",  "0x0000000000085d4780B73119b644AE5ecd22b376");
                startActivity(i);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //balance
                get_version_content();

                get_ETH_balance();
                get_ETC_balance();
                get_BTC_balance();
                get_PAX_balance();
                get_BNB_balance();
                get_BAT_balance();
                get_OMG_balance();
                get_USDC_balance();
                get_MKR_balance();
                get_TUSD_balance();

                getUSD_BTC();
                getUSD_ETH();
                getUSD_ETC();
                getUSD_PAX();
                getUSD_BCH();
                getUSD_BNB();
                getUSD_BAT();
                getUSD_OMG();
                getUSD_USDC();
                getUSD_MKR();
                getUSD_TUSD();

                //Calculate to usd
                onResume();
                get_version();
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void get_ETH_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            eth_balance.setText(format);

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

    private void get_BTC_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, "https://blockchain.info/balance?active="+addressb, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String res = response.toString();
                            String[] parts = res.split(",");
                            String part1 = parts[0]; // "price_usd": "0.7042710636"
                            String[] part2 = part1.split(":");
                            String part3 = part2[2];
                            String remove=part3.replace(" ", "");
                            Double result = Double.parseDouble(remove);
                            Double total= result/100000000;
                            String btcs = String.format("%.8f", total);
                            btc_balance.setText(btcs);
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


    private void get_BCH_balance() {
        final okhttp3.Request request = new okhttp3.Request.Builder().url("https://bcc.zupago.pe/api/addr/"+bch_address+"/Balance").build();
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
                            Double result = Double.parseDouble(res);
                            Double total= result/100000000;
                            String btcs = String.format("%.8f", total);
                            bch_balance.setText(btcs);
                        } catch (IOException e) {
                        }
                    }
                });
            }
        });
    }

    private void get_PAX_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0x8E870D67F660D95d5be530380D0eC0bd388289E1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            pax_balance.setText(format);
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

    private void get_ETC_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0x9FD5C62A90ED0508c11a724302A809CC16F42aEc");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            etc_balance.setText(format);
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

    private void get_BNB_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0xB8c77482e45F1F44dE1745F52C74426C631bDD52");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            bnb_balance.setText(format);
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

    private void get_BAT_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0x0d8775f648430679a709e98d2b0cb6250d2887ef");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            bat_balance.setText(format);
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

    private void get_OMG_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0xd26114cd6EE289AccF82350c8d8487fedB8A0C07");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            omg_balance.setText(format);
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

    private void get_USDC_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48");
            request.put("coin", "USDC");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            //String res = response.get("balance").toString();
                            //Toast.makeText(MainActivity.this, res.toString(), Toast.LENGTH_LONG).show();
                            Double rates = res / 1000000;
                            String format = String.format("%.8f",rates);
                            usdc_balance.setText(format);
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

    private void get_MKR_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            mkr_balance.setText(format);
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

    private void get_TUSD_balance(){
        JSONObject request = new JSONObject();
        try {
            request.put("address", address);
            request.put("contract", "0x0000000000085d4780B73119b644AE5ecd22b376");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.POST, api_url2, request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Double res = Double.parseDouble(response.get("balance").toString());
                            String format = String.format("%.8f",res);
                            tusd_balance.setText(format);
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
    ///////////////////////////////////////////////////////////////////////////////////////////////USD RATE
    private void getUSD_ETH() {

        JSONObject request = new JSONObject();
        try {
            request.put("coin", "ethereum");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            //Float bal = BigDecimal.valueOf(jsonObject.getDouble("price")).floatValue();
                            Double rate =jsonObject.getDouble("USD");
                            String btcs = String.format("%.2f", rate);
                            eth_usd.setText("$"+btcs);
                            seth_rates = rate.toString();
                            ETHData process = new ETHData();
                            process.execute();
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

    private void getUSD_BTC() {

        JSONObject request = new JSONObject();
        try {
            request.put("coin", "bitcoin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            btc_usd.setText("$"+eths);
                            sbtc_rates = eths;
                            BTCData process = new BTCData();
                            process.execute();
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

    private void getUSD_BCH() {

        JSONObject request = new JSONObject();
        try {
            request.put("coin", "bitcoin-cash");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=BCH&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            bch_usd.setText("$"+eths);
                            sbch_rates = eths;
                            BCHData process = new BCHData();
                            process.execute();
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

    private void getUSD_PAX() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "paxos-standard-token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=PAX&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            pax_usd.setText("$"+eths);
                            spax_rates = rate.toString();
                            PAXData process = new PAXData();
                            process.execute();
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

    private void getUSD_ETC() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "paxos-standard-token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=ETC&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            etc_usd.setText("$"+eths);
                            setc_rates = rate.toString();
                            ETCData process = new ETCData();
                            process.execute();
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

    private void getUSD_BNB() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "binance-coin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=BNB&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            bnb_usd.setText("$"+eths);
                            sbnb_rates = rate.toString();
                            BNBData process = new BNBData();
                            process.execute();
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

    private void getUSD_BAT() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "basic-attention-token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=BAT&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            bat_usd.setText("$"+eths);
                            sbat_rates = rate.toString();
                            BATData process = new BATData();
                            process.execute();
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

    private void getUSD_OMG() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "omisego");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=OMG&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            omg_usd.setText("$"+eths);
                            somg_rates = rate.toString();
                            OMGData process = new OMGData();
                            process.execute();
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

    private void getUSD_USDC() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "usd-coin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=USDC&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            usdc_usd.setText("$"+eths);
                            susdc_rates = rate.toString();
                            USDCData process = new USDCData();
                            process.execute();
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

    private void getUSD_MKR() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "maker");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=MKR&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            mkr_usd.setText("$"+eths);
                            smkr_rates = rate.toString();
                            MKRData process = new MKRData();
                            process.execute();
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

    private void getUSD_TUSD() {
        JSONObject request = new JSONObject();
        try {
            request.put("coin", "trueusd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.GET, "https://min-api.cryptocompare.com/data/price?fsym=TUSD&tsyms=USD", request, new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.toString();
                            JSONObject jsonObject = new JSONObject(res);
                            Double rate =jsonObject.getDouble("USD");
                            String eths = String.format("%.2f", rate);
                            tusd_usd.setText("$"+eths);
                            stusd_rates = rate.toString();
                            TUSDData process = new TUSDData();
                            process.execute();
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

    protected void onResume() {

        h.postDelayed( runnable = new Runnable() {
            public void run() {
                if (btc_usd.length()!=0) {
                    try {
                        double f = Double.parseDouble(seth_rates);
                        double bals = Double.parseDouble(eth_balance.getText().toString());
                        bals = bals * f;
                        String eths = String.format("%.2f", bals);
                        seth_rate = eths;
                        //
                        double f1 = Double.parseDouble(sbtc_rates);
                        double bals1 = Double.parseDouble(btc_balance.getText().toString());
                        bals1 = bals1 * f1;
                        String eths1 = String.format("%.2f", bals1);
                        sbtc_rate = eths1;

                        double f3 = Double.parseDouble(spax_rates);
                        double bals3 = Double.parseDouble(pax_balance.getText().toString());
                        bals3 = bals3 * f3;
                        String eths3 = String.format("%.2f", bals3);
                        spax_rate = eths3;
                        //
                        double f4 = Double.parseDouble(sbnb_rates);
                        double bals4 = Double.parseDouble(bnb_balance.getText().toString());
                        bals4 = bals4 * f4;
                        String eths4 = String.format("%.2f", bals4);
                        sbnb_rate = eths4;
                        //
                        double f5 = Double.parseDouble(sbat_rates);
                        double bals5 = Double.parseDouble(bat_balance.getText().toString());
                        bals5 = bals5 * f5;
                        String eths5 = String.format("%.2f", bals5);
                        sbat_rate = eths5;
                        //
                        double f6 = Double.parseDouble(somg_rates);
                        double bals6 = Double.parseDouble(omg_balance.getText().toString());
                        bals6 = bals6 * f6;
                        String eths6 = String.format("%.2f", bals6);
                        somg_rate = eths6;
                        //
                        double f7 = Double.parseDouble(susdc_rates);
                        double bals7 = Double.parseDouble(usdc_balance.getText().toString());
                        bals7 = bals7 * f7;
                        String eths7 = String.format("%.2f", bals7);
                        susdc_rate = eths7;
                        //
                        double f8 = Double.parseDouble(smkr_rates);
                        double bals8 = Double.parseDouble(mkr_balance.getText().toString());
                        bals8 = bals8 * f8;
                        String eths8 = String.format("%.2f", bals8);
                        smkr_rate = eths8;
                        //
                        double f9 = Double.parseDouble(stusd_rates);
                        double bals9 = Double.parseDouble(tusd_balance.getText().toString());
                        bals9 = bals9 * f9;
                        String eths9 = String.format("%.2f", bals9);
                        stusd_rate = eths9;
                        //
                        double f10 = Double.parseDouble(setc_rates);
                        double bals10 = Double.parseDouble(etc_balance.getText().toString());
                        bals10 = bals10 * f;
                        String eths10 = String.format("%.2f", bals10);
                        setc_rate = eths10;
                        //
                        //Total
//                        double ly = Double.parseDouble(slyte_rate);
                        double bt = Double.parseDouble(sbtc_rate);
                        double et = Double.parseDouble(seth_rate);
                        double ec = Double.parseDouble(setc_rate);
                        double px = Double.parseDouble(spax_rate);
                        double bn = Double.parseDouble(sbnb_rate);
                        double ba = Double.parseDouble(sbat_rate);
                        double om = Double.parseDouble(somg_rate);
                        double usc = Double.parseDouble(susdc_rate);
                        double mk = Double.parseDouble(smkr_rate);
                        double tu = Double.parseDouble(stusd_rate);

                        double total =  et + bt + px + bn + ba + om + usc + mk + tu + ec;
                        String tot = String.format("%.2f", total);
                        USD.setText("$ " + tot);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                h.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    //Get version available
    private void get_version()
    {
        final okhttp3.Request request = new okhttp3.Request.Builder().url("http://127.0.0.0:8080/v1/version").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btc_balance.setText("0");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = response.body().string().trim();
                            ///version =res;
                            if (res.equals(version)){

                            }else{
//                               notify.setImageResource(R.drawable.notify2);
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

                                builder1.setTitle("New update available!");
                                builder1.setMessage(update_content);

                                builder1.setCancelable(false);
                                builder1.setPositiveButton("Update now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("Your app store link"));
                                        startActivity(browserIntent);
                                    }
                                });

                                builder1.setNegativeButton("Remind me later", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                AlertDialog Alert1 = builder1.create();
                                Alert1 .show();
                                ((TextView)Alert1.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());

                            }
                        } catch (IOException e){

                        }
                    }
                });
            }
        });
    }

    private void get_version_content()
    {
        final okhttp3.Request request = new okhttp3.Request.Builder().url("http://127.0.0.0:8080/v1/update_content").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btc_balance.setText("0");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String res = response.body().string().trim();
                            update_content = res;
                        } catch (IOException e){

                        }
                    }
                });
            }
        });
    }


    private class main_task extends AsyncTask<Integer,Integer,String>{

        @Override
        protected String doInBackground(Integer... integers) {

            try{
                get_version_content();

                get_ETH_balance();
                get_ETC_balance();
                get_BTC_balance();
                //get_Lyte_balance();

                get_PAX_balance();
                get_BNB_balance();
                get_BAT_balance();
                get_OMG_balance();
                get_USDC_balance();
                get_MKR_balance();
                get_TUSD_balance();

                getUSD_ETH();
                getUSD_ETC();
                getUSD_BTC();
                getUSD_BCH();
                getUSD_PAX();
                getUSD_BNB();
                getUSD_BAT();
                getUSD_OMG();
                getUSD_USDC();
                getUSD_MKR();
                getUSD_TUSD();

                get_version();
                //Calculate to usd
                onResume();

            }catch (Exception e)
            {
                //Log.wtf("Main Activity", e.getMessage());
            }
            return null;
        }
    }
    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            this.finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();

    }

}
