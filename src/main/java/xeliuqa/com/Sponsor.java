package xeliuqa.com;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import xeliuqa.com.Adapter.SponsorData;


public class Sponsor extends AppCompatActivity {
    public static TextView dataname1;
    public static TextView dataamount1;
    public static TextView dataname2;
    public static TextView dataamount2;
    public static TextView dataname3;
    public static TextView dataamount3;
    public static TextView dataname4;
    public static TextView dataamount4;
    public static TextView dataname5;
    public static TextView dataamount5;
    public static TextView dataname6;
    public static TextView dataamount6;
    public static TextView dataname7;
    public static TextView dataamount7;
    public static TextView dataname8;
    public static TextView dataamount8;
    public static TextView dataname9;
    public static TextView dataamount9;
    public static TextView dataname10;
    public static TextView dataamount10;

    ImageView help;

    private RelativeLayout btc_button;
    private RelativeLayout eth_button;
    private TextView btc;
    private TextView eth;

    ImageView back;

    private SwipeRefreshLayout pullToRefresh;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sponser);
        back = (ImageView) findViewById(R.id.back);
        pullToRefresh = findViewById(R.id.pullToRefresh);

        dataname1 = (TextView) findViewById(R.id.name1);
        dataamount1 = (TextView) findViewById(R.id.amount1);
        dataname2 = (TextView) findViewById(R.id.name2);
        dataamount2 = (TextView) findViewById(R.id.amount2);
        dataname3 = (TextView) findViewById(R.id.name3);
        dataamount3 = (TextView) findViewById(R.id.amount3);
        dataname4 = (TextView) findViewById(R.id.name4);
        dataamount4 = (TextView) findViewById(R.id.amount4);
        dataname5 = (TextView) findViewById(R.id.name5);
        dataamount5 = (TextView) findViewById(R.id.amount5);
        dataname6 = (TextView) findViewById(R.id.name6);
        dataamount6 = (TextView) findViewById(R.id.amount6);
        dataname7 = (TextView) findViewById(R.id.name7);
        dataamount7 = (TextView) findViewById(R.id.amount7);
        dataname8 = (TextView) findViewById(R.id.name8);
        dataamount8 = (TextView) findViewById(R.id.amount8);
        dataname9 = (TextView) findViewById(R.id.name9);
        dataamount9 = (TextView) findViewById(R.id.amount9);
        dataname10 = (TextView) findViewById(R.id.name10);
        dataamount10 = (TextView) findViewById(R.id.amount10);
        SponsorData process = new SponsorData();
        process.execute();

        help = (ImageView) findViewById(R.id.help_button);

        btc = (TextView) findViewById(R.id.btc);
        eth = (TextView) findViewById(R.id.eth);

        eth_button =findViewById(R.id.eth_button);
        btc_button =findViewById(R.id.btc_button);

        // Get clipboard manager object.
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        btc_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText = btc.getText().toString();
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Sponsor.this, "Copied successfully.", Toast.LENGTH_LONG).show();
            }
        });

        eth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText = eth.getText().toString();
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Sponsor.this, "Copied successfully.", Toast.LENGTH_LONG).show();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message dialog = new Message();
                dialog.showDialog(Sponsor.this, "Xeliuqa is free to use, but costs money, time and dedication to maintain. Your support is appreciated!");
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sponsor.this, Settings.class);
                startActivity(i);
            }
        });
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SponsorData process = new SponsorData();
                process.execute();
                pullToRefresh.setRefreshing(false);
            }
        });

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SponsorData process = new SponsorData();
                process.execute();
                pullToRefresh.setRefreshing(false);
            }
        });
    }
}