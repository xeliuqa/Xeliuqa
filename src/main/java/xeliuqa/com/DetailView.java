package xeliuqa.com;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailView extends AppCompatActivity {
    private TextView nametxt;
    private ImageView fullimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();

        String name=i.getStringExtra("name");
        //nametxt.setText(name);

        if (name.contains("0x")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://etherscan.io/tx/"+name));
            startActivity(browserIntent);
        }else{
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.blockchain.com/btc/tx/"+name));
            startActivity(browserIntent);
        }

    }
}
