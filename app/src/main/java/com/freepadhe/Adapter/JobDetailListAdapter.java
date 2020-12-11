package com.freepadhe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.freepadhe.GsonModel.JobDetailModels.JobDetailSubjectDatum;
import com.freepadhe.R;
import com.freepadhe.databinding.RowSubcourseBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.ThemeClass;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class JobDetailListAdapter extends RecyclerView.Adapter<JobDetailListAdapter.MainViewHolder> {


    private LayoutInflater inflater;
    private Context context;
    private List<JobDetailSubjectDatum> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;

    public JobDetailListAdapter(Context context, List<JobDetailSubjectDatum> listItemArrayList){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        transformation2 = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(14, 0, RoundedCornersTransformation.CornerType.TOP));
        this.mOnclickListener=mOnclickListener;

    }

    @Override
    public int getItemCount() {
        return listItemArrayList!=null?listItemArrayList.size():0;
    }


    @Override
    public JobDetailListAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowSubcourseBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_subcourse, parent, false);
        return new JobDetailListAdapter.MainViewHolder(binding);


    }


    @Override
    public void onBindViewHolder(JobDetailListAdapter.MainViewHolder holder, final int position) {

        RowSubcourseBinding binding = holder.mbinding;

        if(listItemArrayList.get(position).getImage()!=null) {
            Glide.with(context)
                    .load(listItemArrayList.get(position).getImage()).dontAnimate()
                    .transform(transformation2).placeholder(R.drawable.placeholder_banner)
                    .into(binding.ivCategoryimage);
        }


        binding.tvTitle.setText(listItemArrayList.get(position).getName());
        binding.tvCount.setText(listItemArrayList.get(position).getLessoncount().toString()+" Videos");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tvCount.setCompoundDrawableTintList(ThemeClass.getcolorstate(context));
        }

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
        private RowSubcourseBinding mbinding;

        public MainViewHolder(RowSubcourseBinding mbinding) {
            super(mbinding.llMain);
            this.mbinding = mbinding;
        }
    }

}
