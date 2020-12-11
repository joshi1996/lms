package com.freepadhe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.freepadhe.GsonModel.EarningModels.DatumEarning;
import com.freepadhe.R;
import com.freepadhe.databinding.RowEarnBinding;
import com.freepadhe.databinding.RowTransactionBinding;
import com.freepadhe.interfaces.OnclickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class EarnAdapter extends RecyclerView.Adapter<EarnAdapter.MainViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<DatumEarning> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;
    public List<DatumEarning> exampleListFull=new ArrayList<>();

    public EarnAdapter(Context context, List<DatumEarning> listItemArrayList, OnclickListener mOnclickListener){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        if(listItemArrayList!=null)
        this.exampleListFull.addAll(listItemArrayList);

        transformation2 = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(14, 0, RoundedCornersTransformation.CornerType.TOP));
        this.mOnclickListener=mOnclickListener;

    }

    @Override
    public int getItemCount() {
        return exampleListFull.size();
    }

    @Override
    public EarnAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowEarnBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_earn, parent, false);
        return new EarnAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EarnAdapter.MainViewHolder holder, final int position) {

        RowEarnBinding binding = holder.mbinding;


        if(exampleListFull.get(position).getAmount()!=null) {
            binding.tvamount.setText("Rs. "+exampleListFull.get(position).getAmount()+"/-");

        }

        if (exampleListFull.get(position).getIsPaid().equals(1))
        {
            binding.tvstats.setText("Paid");
            binding.layoutPaid.setVisibility(View.VISIBLE);
            binding.layoutUnpaid.setVisibility(View.GONE);
            //ThemeClass.changeHeaderColor(binding.tvstats,context);
        }

        else
        {
            binding.layoutUnpaid.setVisibility(View.VISIBLE);
            binding.layoutPaid.setVisibility(View.GONE);
            binding.tvstatsUnpaid.setText("UnPaid");
        }


        if(exampleListFull.get(position).getCreatedAt()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(exampleListFull.get(position).getCreatedAt());
                SimpleDateFormat sdfmonth = new SimpleDateFormat("dd-MM-yyyy");
                String date_text = sdfmonth.format(convertedDate);

                binding.tvdate.setText("" + date_text);

            } catch (ParseException e) {
                e.printStackTrace();
                binding.tvdate.setText("" + exampleListFull.get(position).getCreatedAt());

            }
        }

        binding.llfilter.setTag(position);

        if (exampleListFull.get(position).getIsPaid() == 1)
        {
            binding.llfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=(int)v.getTag();
                    if(mOnclickListener!=null){
                        mOnclickListener.OnItemclick(pos);
                    }
                }
            });
        }

        else
        {
        }

    }

    public void setData(List<DatumEarning> mlist) {
        this.listItemArrayList = mlist;
        exampleListFull.clear();

        this.exampleListFull.addAll(mlist);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private RowEarnBinding mbinding;

        public MainViewHolder(RowEarnBinding mbinding) {
            super(mbinding.llfilter);
            this.mbinding = mbinding;
        }
    }

}
