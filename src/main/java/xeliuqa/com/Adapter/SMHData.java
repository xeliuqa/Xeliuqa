package xeliuqa.com.Adapter;

import android.graphics.Color;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

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

import xeliuqa.com.MainActivity;

public class SMHData extends AsyncTask<Void, RecyclerView.ViewHolder,Void> {
    String data = "";
    String dataParsed1h ="";
    String dataParsed24h ="";
    String dataParsed7d ="";
    String percent_change_1h = "";
    String percent_change_24h = "";
    String percent_change_7d = "";

    public SMHData() {

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL ("https://api.coinpaprika.com/v1/ticker/smh-spacemesh");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data = data+line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i=0 ;i< JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);

                percent_change_1h = JO.getString("percent_change_1h") + "%";
                percent_change_24h = JO.getString("percent_change_24h") + "%";
                percent_change_7d = JO.getString("percent_change_7d") + "%" ;


                dataParsed1h = dataParsed1h + percent_change_1h;
                dataParsed24h = dataParsed24h + percent_change_24h;
                dataParsed7d = dataParsed7d + percent_change_7d;


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
        MainActivity.smh_percent_change_1h.setTextColor((this.dataParsed1h.contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32")));
        MainActivity.smh_percent_change_1h.setText(this.dataParsed1h);
        MainActivity.smh_percent_change_24h.setTextColor((this.dataParsed24h.contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32")));
        MainActivity.smh_percent_change_24h.setText(this.dataParsed24h);
        MainActivity.smh_percent_change_7d.setTextColor((this.dataParsed7d.contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32")));
        MainActivity.smh_percent_change_7d.setText(this.dataParsed7d);
    }
}
