package xeliuqa.com;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class qrcode extends AppCompatActivity {
    private SessionHandler session;
    private ProgressDialog pDialog;
    private TextView qr_address;
    private String address;
    private String keys;
    private String addressb;
    private String keysb;
    private String bch_address;
    private String bch_keys;
    ImageView back;

    private String logs;
    private ImageView img;
    private Button copyButton;
    private TextView coin_name;
    private Config conf;
    private String key = conf.key();
    private String initVector = conf.pass();
    private DataHandler enc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        address = enc.decrypt(key,initVector,user.getKeys());
        keys = enc.decrypt(key,initVector,user.getSecrets());
        addressb = enc.decrypt(key,initVector,user.getBKeys());
        keysb = enc.decrypt(key,initVector,user.getBSecrets());
        bch_address = enc.decrypt(key,initVector,user.getCKeys());
        bch_keys = enc.decrypt(key,initVector,user.getCSecrets());
        qr_address = (TextView) findViewById(R.id.tvQrcode);
        back = (ImageView) findViewById(R.id.back);

        copyButton = (Button) findViewById(R.id.btn_copy);

        //Get the string
        String message=getIntent().getStringExtra("coin");
        coin_name = (TextView) findViewById(R.id.tvcoin);
        coin_name.setText("YOUR "+ message+ " ADDRESS");

        //Toast.makeText(qrcode.this, bch_address, Toast.LENGTH_LONG).show();

        if (message.equals("BTC")){
            qr_address.setText(addressb);
        }else if (message.equals("BCH")){
            qr_address.setText(bch_address);
        }else{
            qr_address.setText(address);
        }

        // Get clipboard manager object.
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager)clipboardService;

        if (message.equals("BTC")) {//https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=" + btcyouraddress + "&choe=UTF-8
            new DownloadImageFromInternet((ImageView) findViewById(R.id.imageQr))
                    .execute("https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=" + addressb + "&choe=UTF-8");
        }else if (message.equals("BCH")) {
            new DownloadImageFromInternet((ImageView) findViewById(R.id.imageQr))
                    .execute("https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl=" + bch_address + "&choe=UTF-8");
        }
        else {
            new DownloadImageFromInternet((ImageView) findViewById(R.id.imageQr))
                    .execute("https://chart.googleapis.com/chart?chs=250x250&cht=qr&chl=" + address + "&choe=UTF-8");
        }

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcText = qr_address.getText().toString();
                // Create a new ClipData.
                ClipData clipData = ClipData.newPlainText("Source Text", srcText);
                // Set it as primary clip data to copy text to system clipboard.
                clipboardManager.setPrimaryClip(clipData);
                // Popup a snackbar.
                Toast.makeText(qrcode.this, "Copied successfully.", Toast.LENGTH_LONG).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, generating your address...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
