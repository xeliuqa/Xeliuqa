package xeliuqa.com.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import xeliuqa.com.R;

public class CoinViewHolder extends RecyclerView.ViewHolder {
    public TextView btc_percent_change_1h, btc_percent_change_24h,btc_percent_change_7d;
    public CoinViewHolder(View itemView) {
        super (itemView);

        /*btc_percent_change_1h = (TextView) itemView.findViewById(R.id.btc_percent_change_1h);
        btc_percent_change_24h = (TextView) itemView.findViewById(R.id.btc_percent_change_24h);
        btc_percent_change_7d = (TextView) itemView.findViewById(R.id.btc_percent_change_7d);*/
    }
}
