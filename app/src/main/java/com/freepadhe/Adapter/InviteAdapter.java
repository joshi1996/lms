package com.freepadhe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.freepadhe.GsonModel.InviteModel.Datum;
import com.freepadhe.R;
import com.freepadhe.databinding.RowCourselistBinding;
import com.freepadhe.databinding.RowInviteBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.ThemeClass;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MainViewHolder>{


    private LayoutInflater inflater;
    private Context context;
    private List<Datum> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;
    public List<Datum> exampleListFull=new ArrayList<>();

    public InviteAdapter(Context context, List<Datum> listItemArrayList, OnclickListener mOnclickListener){

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
    public InviteAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowInviteBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_invite, parent, false);
        return new InviteAdapter.MainViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(InviteAdapter.MainViewHolder holder, final int position) {

        RowInviteBinding binding = holder.mbinding;

     if(exampleListFull.get(position).getProfilePhoto()!=null && exampleListFull.get(position).getProfilePhoto()!=null) {
               Glide.with(context)
              .load(exampleListFull.get(position).getProfilePhoto()).dontAnimate()
               .transform(transformation2).placeholder(R.drawable.appstore)
              .into(binding.ivCategoryimage);
      }

        if(exampleListFull.get(position).getFullName()!=null && exampleListFull.get(position).getFullName()!=null) {
            binding.tvTitle.setText(exampleListFull.get(position).getFullName());
            binding.tvTitle.setTextColor(ThemeClass.getThemecolor(context));
        }



        if(exampleListFull.get(position).getRoleName()!=null && exampleListFull.get(position).getRoleName()!=null) {
            binding.tvCoursedetail.setText(exampleListFull.get(position).getRoleName());

        }



    }




    public void setData(List<Datum> mlist) {
        this.listItemArrayList = mlist;
        exampleListFull.clear();

        this.exampleListFull.addAll(mlist);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private RowInviteBinding mbinding;

        public MainViewHolder(RowInviteBinding mbinding) {
            super(mbinding.llMain);
            this.mbinding = mbinding;
        }
    }

}
