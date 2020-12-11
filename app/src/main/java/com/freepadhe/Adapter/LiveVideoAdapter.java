package com.freepadhe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.freepadhe.GsonModel.LiveVideo.LiveVideoDatum;
import com.freepadhe.R;
import com.freepadhe.databinding.RawLiveItemBinding;
import com.freepadhe.interfaces.OnclickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LiveVideoAdapter extends RecyclerView.Adapter<LiveVideoAdapter.MainViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private List<LiveVideoDatum> listItemArrayList;
    public List<LiveVideoDatum> exampleListFull=new ArrayList<>();
    OnclickListener mOnclickListener;

    public LiveVideoAdapter(Context context, List<LiveVideoDatum> listItemArrayList, OnclickListener mOnclickListener){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        if(listItemArrayList!=null)
            this.exampleListFull.addAll(listItemArrayList);
        this.mOnclickListener=mOnclickListener;
    }

    @Override
    public int getItemCount() {
        return exampleListFull.size();
    }

    @Override
    public LiveVideoAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RawLiveItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.raw_live_item, parent, false);
        return new LiveVideoAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LiveVideoAdapter.MainViewHolder holder, final int position) {

        RawLiveItemBinding binding = holder.mbinding;

        binding.tvTitleLive.setText(exampleListFull.get(position).getTitle());

        if(exampleListFull.get(position).getDateAndTime()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(exampleListFull.get(position).getDateAndTime());
                SimpleDateFormat sdfmonth = new SimpleDateFormat("dd-MM-yyyy");
                String date_text = sdfmonth.format(convertedDate);
                binding.tvDateLive.setText("" + date_text);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                binding.tvDateLive.setText("" + exampleListFull.get(position).getDateAndTime());
            }
        }

        binding.llMainLive.setTag(position);
        binding.llMainLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(mOnclickListener!=null){
                    mOnclickListener.OnItemclick(pos);
                }
            }
        });
    }

    public void setData(List<LiveVideoDatum> mlist) {
        this.listItemArrayList = mlist;
        exampleListFull.clear();

        this.exampleListFull.addAll(mlist);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private RawLiveItemBinding mbinding;

        public MainViewHolder(RawLiveItemBinding mbinding) {
            super(mbinding.llMainLive);
            this.mbinding = mbinding;
        }
    }

}
