package com.freepadhe.Adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.freepadhe.GsonModel.ReviewsModel.ReviewDatum;
import com.freepadhe.R;
import com.freepadhe.databinding.ReviewLayoutBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowReviewAdapter extends RecyclerView.Adapter<ShowReviewAdapter.MainViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<ReviewDatum> listItemArrayList;
    public List<ReviewDatum> exampleListFull=new ArrayList<>();

    public ShowReviewAdapter(Context context, List<ReviewDatum> listItemArrayList){

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        if(listItemArrayList!=null)
            this.exampleListFull.addAll(listItemArrayList);
    }

    @Override
    public int getItemCount() {
        return exampleListFull.size();
    }


    @Override
    public ShowReviewAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ReviewLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.review_layout, parent, false);
        return new ShowReviewAdapter.MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ShowReviewAdapter.MainViewHolder holder, final int position) {

        ReviewLayoutBinding binding = holder.mbinding;

        binding.reviewNo.setText(exampleListFull.get(position).getRating()+"");
        binding.reviewName.setText(exampleListFull.get(position).getUserName());

        if(exampleListFull.get(position).getCreatedAt()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(exampleListFull.get(position).getCreatedAt());
                SimpleDateFormat sdfmonth = new SimpleDateFormat("dd-MM-yyyy");
                String date_text = sdfmonth.format(convertedDate);

                binding.reviewDate.setText("" + date_text);

            } catch (ParseException e) {
                e.printStackTrace();
                binding.reviewDate.setText("" + exampleListFull.get(position).getCreatedAt());

            }
        }

        binding.reviewText.setText(exampleListFull.get(position).getReview());
        binding.reviewText.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setData(List<ReviewDatum> mlist) {
        this.listItemArrayList = mlist;
        exampleListFull.clear();

        this.exampleListFull.addAll(mlist);
        notifyDataSetChanged();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        private ReviewLayoutBinding mbinding;

        public MainViewHolder(ReviewLayoutBinding mbinding) {
            super(mbinding.reviewLayout);
            this.mbinding = mbinding;
        }
    }
}