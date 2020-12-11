package com.freepadhe.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.freepadhe.GsonModel.UserCourse.Datum;
import com.freepadhe.R;
import com.freepadhe.databinding.RowCourselistBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.SharePrefs;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UserCourseAdapter extends RecyclerView.Adapter<UserCourseAdapter.MainViewHolder> implements Filterable{


    private LayoutInflater inflater;
    private Context context;
    private List<Datum> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;
    public List<Datum> exampleListFull=new ArrayList<>();

    public UserCourseAdapter(Context context, List<Datum> listItemArrayList, OnclickListener mOnclickListener){

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
    public UserCourseAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowCourselistBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_courselist, parent, false);
        return new UserCourseAdapter.MainViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(UserCourseAdapter.MainViewHolder holder, final int position) {

        RowCourselistBinding binding = holder.mbinding;

     if(exampleListFull.get(position).getCourseDetail()!=null && exampleListFull.get(position).getCourseDetail().getImage()!=null) {
               Glide.with(context)
              .load(exampleListFull.get(position).getCourseDetail().getImage()).dontAnimate()
               .transform(transformation2).placeholder(R.drawable.placeholder_banner)
              .into(binding.ivCategoryimage);
      }


        if(exampleListFull.get(position).getCourseDetail()!=null && exampleListFull.get(position).getCourseDetail().getName()!=null) {
            binding.tvTitle.setText(exampleListFull.get(position).getCourseDetail().getName());

        }
        if(exampleListFull.get(position).getCourseDetail()!=null && exampleListFull.get(position).getCourseDetail().getRating()!=null) {

            binding.ratingBar.setRating(exampleListFull.get(position).getCourseDetail().getRating());
        }else{
            binding.ratingBar.setRating(0);

        }

        binding.llMain.setTag(position);
        binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(mOnclickListener!=null){
                    mOnclickListener.OnItemclick(pos);
                   // Toast.makeText(context, ""+exampleListFull.get(pos).getCourseId(), Toast.LENGTH_SHORT).show();
                    SharePrefs.setLocale(exampleListFull.get(pos).getCourseId());
                }
            }
        });


    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Datum> filteredList = new ArrayList<>();

                String filterPattern = constraint.toString().toLowerCase().trim();
            if(filterPattern.equalsIgnoreCase(context.getString(R.string.all))) {
                filteredList.addAll(listItemArrayList);
            }
            else  if (filterPattern.equalsIgnoreCase(context.getString(R.string.running))) {
                for (Datum item : listItemArrayList) {
                     if(item.getStatus()==1)
                     filteredList.add(item);
                }
            }
            else  if (filterPattern.equalsIgnoreCase(context.getString(R.string.completed))) {
                for (Datum item : listItemArrayList) {
                    if(item.getStatus()==3)
                        filteredList.add(item);
                }
            }


            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleListFull.clear();
            exampleListFull.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setData(List<Datum> mlist) {
        this.listItemArrayList = mlist;
        exampleListFull.clear();

        this.exampleListFull.addAll(mlist);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private RowCourselistBinding mbinding;

        public MainViewHolder(RowCourselistBinding mbinding) {
            super(mbinding.llMain);
            this.mbinding = mbinding;
        }
    }

}
