package com.lms.Adapter;

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
import com.lms.GsonModel.coursedetail.SubjectDatum;
import com.lms.R;
import com.lms.databinding.RowVideolistBinding;
import com.lms.interfaces.OnclickListener;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MainViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private List<SubjectDatum> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;

    public VideoListAdapter(Context context, List<SubjectDatum> listItemArrayList, OnclickListener mOnclickListener){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        transformation2 = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(14, 0, RoundedCornersTransformation.CornerType.ALL));
        this.mOnclickListener=mOnclickListener;

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    @Override
    public VideoListAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowVideolistBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_videolist, parent, false);
        return new VideoListAdapter.MainViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(VideoListAdapter.MainViewHolder holder, final int position) {

        RowVideolistBinding binding = holder.mbinding;
        binding.llMain.setTag(position);
        binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(mOnclickListener!=null){
                    mOnclickListener.OnItemclick(pos);
                }
            }
        });

    }


    public class MainViewHolder extends RecyclerView.ViewHolder {
        private RowVideolistBinding mbinding;

        public MainViewHolder(RowVideolistBinding mbinding) {
            super(mbinding.llMain);
            this.mbinding = mbinding;
        }
    }

}
