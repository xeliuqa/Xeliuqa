package xeliuqa.com.Adapter;

import android.graphics.Color;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import xeliuqa.com.erc_send;

public class ErcGasFee extends AsyncTask<Void,RecyclerView.ViewHolder,Void> {
    String data = "";
    String dataSafeGas ="";
    String dataProposeGas ="";
    String dataFastGas ="";
    String SafeGasPrice = "";
    String ProposeGasPrice = "";
    String FastGasPrice = "";

    public ErcGasFee() {

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL ("https://api.etherscan.io/api?module=gastracker&action=gasoracle&apikey=IZWJ6D87UJWX6QSZ85WKFN1H5V41DUFTZM");
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
                SafeGasPrice = JA.getString("SafeGasPrice");
                ProposeGasPrice = JA.getString("ProposeGasPrice");
                FastGasPrice =  JA.getString("FastGasPrice") ;

                dataSafeGas = SafeGasPrice;
                dataProposeGas = ProposeGasPrice;
                dataFastGas = FastGasPrice;


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
        erc_send.erc_dataSafeGas.setTextColor(Color.parseColor("#32CD32"));
        erc_send.erc_dataSafeGas.setText(this.dataSafeGas);
        erc_send.erc_dataProposeGas.setTextColor(Color.parseColor("#12cedb"));
        erc_send.erc_dataProposeGas.setText(this.dataProposeGas);
        erc_send.erc_dataFastGas.setTextColor(Color.parseColor("#fc2a00"));
        erc_send.erc_dataFastGas.setText(this.dataFastGas);
    }
}
