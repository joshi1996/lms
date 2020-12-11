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
import com.freepadhe.GsonModel.CourseDatum;
import com.freepadhe.R;
import com.freepadhe.databinding.RowCourselistBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.SharePrefs;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class CourselistAdapter extends RecyclerView.Adapter<CourselistAdapter.MainViewHolder> implements Filterable {


    private LayoutInflater inflater;
    private Context context;
    private List<CourseDatum> listItemArrayList;
    Transformation<Bitmap> transformation2;
    OnclickListener mOnclickListener;
    public String language="ALL";
    public List<CourseDatum> exampleListFull=new ArrayList<>();

    public CourselistAdapter(Context context, List<CourseDatum> listItemArrayList, OnclickListener mOnclickListener){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
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
    public CourselistAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowCourselistBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_courselist, parent, false);
        return new CourselistAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CourselistAdapter.MainViewHolder holder, final int position) {

        RowCourselistBinding binding = holder.mbinding;

  if(exampleListFull.get(position).getImage()!=null) {
      Glide.with(context)
              .load(exampleListFull.get(position).getImage())
              .transform(transformation2).placeholder(R.drawable.placeholder_banner)
              .into(binding.ivCategoryimage); }

        binding.ratingBar.setRating(exampleListFull.get(position).getRating());
        binding.tvTitle.setText(exampleListFull.get(position).getName());
        binding.llMain.setTag(position);
        binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                if(mOnclickListener!=null){
                    mOnclickListener.OnItemclick(pos);
                    //Toast.makeText(context, ""+exampleListFull.get(pos).getId(), Toast.LENGTH_SHORT).show();
                    SharePrefs.setLocale(exampleListFull.get(pos).getId());
                }
            }
        });
    }

   public void setLanguage(String tag)
   {
       language=tag;
   }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CourseDatum> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                for (CourseDatum item : listItemArrayList) {
                     if(language.equalsIgnoreCase("all")){
                         filteredList.addAll(listItemArrayList);
                          break;
                     }
                    else if (item.getMedium().equalsIgnoreCase(language)) {
                        filteredList.add(item);
                    }
                }
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CourseDatum item : listItemArrayList) {
                    if(language.equalsIgnoreCase("all")) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                     else  if (item.getName().toLowerCase().contains(filterPattern) && item.getMedium().equalsIgnoreCase(language)) {
                        filteredList.add(item);
                    }
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

    public void setData(List<CourseDatum> mlist) {
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
