package xeliuqa.com.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import xeliuqa.com.Model;
import xeliuqa.com.R;

public class CoinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*Adapter adapter;
    boolean isLoading;
    Activity activity;
    List<Model> items;

    int visibleThreshold = 5,lastVisibleItem, totalItemcount;

    public CoinAdapter(RecyclerView recyclerView,Activity activity, List<Model> items) {
        this.activity = activity;
        this.items = items;


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemcount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemcount <= (lastVisibleItem+visibleThreshold))
                {
                    if(adapter !=null)
                        adapter.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.activity_main,parent,false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Model item = items.get(position);
        CoinViewHolder holderItem = (CoinViewHolder)holder;

        holderItem.btc_percent_change_1h.setText(item.getPercentage_change_1h()+"%");
        holderItem.btc_percent_change_24h.setText(item.getPercentage_change_24h()+"%");
        holderItem.btc_percent_change_7d.setText(item.getPercentage_change_7d()+"%");

        holderItem.btc_percent_change_1h.setTextColor(item.getPercentage_change_1h().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
        holderItem.btc_percent_change_24h.setTextColor(item.getPercentage_change_24h().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));
        holderItem.btc_percent_change_7d.setTextColor(item.getPercentage_change_7d().contains("-")?
                Color.parseColor("#FF0000"):Color.parseColor("#32CD32"));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setLoader() {isLoading = true;}
    public void updateData (List<Model> models)
    {
        this.items = models;
        notifyDataSetChanged();
    }*/
}
