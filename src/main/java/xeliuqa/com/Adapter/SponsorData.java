package xeliuqa.com.Adapter;

import android.graphics.Color;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import xeliuqa.com.Sponsor;

public class SponsorData extends AsyncTask<Void, RecyclerView.ViewHolder,Void> {
    String data = "";
    String dataname1 ="";
    String dataamount1 ="";
    String name1 ="";
    String amount1 ="";
    String dataname2 ="";
    String dataamount2 ="";
    String name2 ="";
    String amount2 ="";
    String dataname3 ="";
    String dataamount3 ="";
    String name3 ="";
    String amount3 ="";
    String dataname4 ="";
    String dataamount4 ="";
    String name4 ="";
    String amount4 ="";
    String dataname5 ="";
    String dataamount5 ="";
    String name5 ="";
    String amount5 ="";
    String dataname6 ="";
    String dataamount6 ="";
    String name6 ="";
    String amount6 ="";
    String dataname7 ="";
    String dataamount7 ="";
    String name7 ="";
    String amount7 ="";
    String dataname8 ="";
    String dataamount8 ="";
    String name8 ="";
    String amount8 ="";
    String dataname9 ="";
    String dataamount9 ="";
    String name9 ="";
    String amount9 ="";
    String dataname10 ="";
    String dataamount10 ="";
    String name10 ="";
    String amount10 ="";

    public SponsorData() {

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL ("https://xeliuqa.app/js/sponser.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data = data+line;
            }

            JSONObject object = new JSONObject(data);
            JSONObject JA  = object.getJSONObject("result");
            for(int i=0 ;i< JA.length(); i++){
                name1 = JA.getString("name1");
                amount1 =  JA.getString ("amount1") + " ETH";
                name2 = JA.getString("name2");
                amount2 =  JA.getString ("amount2") + " ETH";
                name3 = JA.getString("name3");
                amount3 =  JA.getString ("amount3") + " ETH";
                name4 = JA.getString("name4");
                amount4 =  JA.getString ("amount4") + " ETH";
                name5 = JA.getString("name5");
                amount5 =  JA.getString ("amount5") + " ETH";
                name6 = JA.getString("name6");
                amount6 =  JA.getString ("amount6") + " ETH";
                name7 = JA.getString("name7");
                amount7 =  JA.getString ("amount7") + " ETH";
                name8 = JA.getString("name8");
                amount8 =  JA.getString ("amount8") + " ETH";
                name9 = JA.getString("name9");
                amount9 =  JA.getString ("amount9") + " ETH";
                name10 = JA.getString("name10");
                amount10 =  JA.getString ("amount10") + " ETH";

                dataname1 =  name1;
                dataamount1 = amount1;
                dataname2 =  name2;
                dataamount2 = amount2;
                dataname3 =  name3;
                dataamount3 = amount3;
                dataname4 =  name4;
                dataamount4 = amount4;
                dataname5 =  name5;
                dataamount5 = amount5;
                dataname6 =  name6;
                dataamount6 = amount6;
                dataname7 =  name7;
                dataamount7 = amount7;
                dataname8 =  name8;
                dataamount8 = amount8;
                dataname9 =  name9;
                dataamount9 = amount9;
                dataname10 =  name10;
                dataamount10 = amount10;


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Sponsor.dataname1.setTextColor((this.dataname1.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname1.setText(this.dataname1);
        Sponsor.dataamount1.setTextColor((this.dataamount1.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount1.setText(this.dataamount1);
        Sponsor.dataname2.setTextColor((this.dataname2.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname2.setText(this.dataname2);
        Sponsor.dataamount2.setTextColor((this.dataamount2.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount2.setText(this.dataamount2);
        Sponsor.dataname3.setTextColor((this.dataname3.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname3.setText(this.dataname3);
        Sponsor.dataamount3.setTextColor((this.dataamount3.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount3.setText(this.dataamount3);
        Sponsor.dataname4.setTextColor((this.dataname4.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname4.setText(this.dataname4);
        Sponsor.dataamount4.setTextColor((this.dataamount4.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount4.setText(this.dataamount4);
        Sponsor.dataname5.setTextColor((this.dataname5.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname5.setText(this.dataname5);
        Sponsor.dataamount5.setTextColor((this.dataamount5.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount5.setText(this.dataamount5);
        Sponsor.dataname6.setTextColor((this.dataname6.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname6.setText(this.dataname6);
        Sponsor.dataamount6.setTextColor((this.dataamount6.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount6.setText(this.dataamount6);
        Sponsor.dataname7.setTextColor((this.dataname7.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname7.setText(this.dataname7);
        Sponsor.dataamount7.setTextColor((this.dataamount7.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount7.setText(this.dataamount7);
        Sponsor.dataname8.setTextColor((this.dataname8.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname8.setText(this.dataname8);
        Sponsor.dataamount8.setTextColor((this.dataamount8.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount8.setText(this.dataamount8);
        Sponsor.dataname9.setTextColor((this.dataname9.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname9.setText(this.dataname9);
        Sponsor.dataamount9.setTextColor((this.dataamount9.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount9.setText(this.dataamount9);
        Sponsor.dataname10.setTextColor((this.dataname10.contains("Name")?
                Color.parseColor("#696969"):Color.parseColor("#12cedb")));
        Sponsor.dataname10.setText(this.dataname10);
        Sponsor.dataamount10.setTextColor((this.dataamount10.contains("0.0000")?
                Color.parseColor("#696969"):Color.parseColor("#00ff00")));
        Sponsor.dataamount10.setText(this.dataamount10);
    }
}