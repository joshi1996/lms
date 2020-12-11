package com.freepadhe.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.freepadhe.R;
import com.freepadhe.interfaces.ListItem1;
import com.freepadhe.interfaces.OnLessionListener;
import com.freepadhe.utility.AlertClass;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;


import java.text.NumberFormat;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_HEADER = 0;
    private static final int LAYOUT_CHILD = 1;
    int spacingInPixels;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ListItem1> listItemArrayList;
    OnLessionListener mOnclickListener;
    Transformation<Bitmap> transformation, transformation2;
    NumberFormat format;
    int buystatus;

    public SearchAdapter(Context context, ArrayList<ListItem1> listItemArrayList, OnLessionListener mOnCategoryListener, int buystatus) {

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listItemArrayList = listItemArrayList;
        transformation = new MultiTransformation<>(
                new CenterCrop(), new BlurTransformation(3, 3),
                new RoundedCornersTransformation(14, 0, RoundedCornersTransformation.CornerType.ALL));

        transformation2 = new MultiTransformation<>(
                new CenterCrop(),
                new RoundedCornersTransformation(15, 0, RoundedCornersTransformation.CornerType.ALL));

        this.mOnclickListener = mOnCategoryListener;
        format = NumberFormat.getCurrencyInstance();
        spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.margin_10);
        this.buystatus = buystatus;
    }

    @Override
    public int getItemCount()
    {
        return listItemArrayList.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (listItemArrayList.get(position).isHeader())
        {
            return LAYOUT_HEADER;
        }
        else
            return LAYOUT_CHILD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        RecyclerView.ViewHolder holder;
        if (viewType == LAYOUT_HEADER)
        {
            View view = inflater.inflate(R.layout.rv_header_search, parent, false);
            holder = new MyViewHolderHeader(view);
        }
        else
        {
            View view = inflater.inflate(R.layout.rv_child, parent, false);
            holder = new MyViewHolderChild(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == LAYOUT_HEADER) {
            MyViewHolderHeader vaultItemHolder = (MyViewHolderHeader) holder;
            vaultItemHolder.tvTopicName.setText(listItemArrayList.get(position).getHeaderData().getName());


            vaultItemHolder.iv_arrow.setTag(position);
            vaultItemHolder.iv_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (int) v.getTag();
                    if (mOnclickListener != null)
                    {
                        mOnclickListener.OnHeaderclick(pos);
                    }

                }
            });

            if (listItemArrayList.get(position).getHeaderData().isShowchild())
            {
                vaultItemHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_up));
                vaultItemHolder.tvTopicName.setTextColor(SharePrefs.getSetting(context).getThemeColor());
            }
            else
            {
                vaultItemHolder.iv_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down));
                vaultItemHolder.tvTopicName.setTextColor(context.getResources().getColor(R.color.listgray));
            }
        }
        else
        {
            MyViewHolderChild vaultItemHolder = (MyViewHolderChild) holder;
            vaultItemHolder.tv_title.setText(listItemArrayList.get(position).getChildData().getTitle());


            if (listItemArrayList.get(position).getChildData().isShowchild())
            {
                vaultItemHolder.itemView.setVisibility(View.VISIBLE);
                RecyclerView.LayoutParams mparam = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mparam.leftMargin = (int) context.getResources().getDimension(R.dimen.margin_10);
                mparam.topMargin = (int) context.getResources().getDimension(R.dimen.margin_10);
                mparam.rightMargin = (int) context.getResources().getDimension(R.dimen.margin_10);
                vaultItemHolder.itemView.setLayoutParams(mparam);
                vaultItemHolder.itemView.requestLayout();

            }
            else
            {
                vaultItemHolder.itemView.setVisibility(View.GONE);
                RecyclerView.LayoutParams mparam = new RecyclerView.LayoutParams(0, 0);
                mparam.topMargin = 0;
                mparam.bottomMargin = 0;
                vaultItemHolder.itemView.setLayoutParams(mparam);
                vaultItemHolder.itemView.requestLayout();

            }

            if (buystatus == 2)
            {
                vaultItemHolder.ll_paidfree.setBackgroundColor(ThemeClass.getThemecolor(context));
                vaultItemHolder.tv_freeorpaid.setText("Play");
                vaultItemHolder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
            }

            else
            {
                // if content is buy
                if (listItemArrayList.get(position).getChildData().getContentPlanType().equalsIgnoreCase("paid"))
                {
                    //content not buy
                    vaultItemHolder.ll_paidfree.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    String cap = listItemArrayList.get(position).getChildData().getContentPlanType().substring(0, 1).toUpperCase() + listItemArrayList.get(position).getChildData().getContentPlanType().substring(1);
                    vaultItemHolder.tv_freeorpaid.setText(cap);
                    vaultItemHolder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
                }

                else
                {
                    vaultItemHolder.ll_paidfree.setBackgroundColor(ThemeClass.getThemecolor(context));
                    vaultItemHolder.tv_freeorpaid.setText("Play");
                    vaultItemHolder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
                }
            }

            if (listItemArrayList.get(position).getChildData().getPhoto() != null)
                Glide.with(context).load(listItemArrayList.get(position).getChildData().getPhoto())
                        .override((int) context.getResources().getDimension(R.dimen.size_50), (int) context.getResources().getDimension(R.dimen.size_50))
                        .placeholder(R.drawable.card_view).dontAnimate()
                        .apply(bitmapTransform(transformation2))
                        .into(vaultItemHolder.iv_videframe);

            vaultItemHolder.itemView.setTag(position);
            vaultItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (mOnclickListener != null)
                    {
                        if (buystatus == 2)
                        {
                            mOnclickListener.onItemClick(pos);
                        }

                        else {
                            // if content is buy
                            if (listItemArrayList.get(position).getChildData().getContentPlanType().equalsIgnoreCase("paid"))
                            {
                                //content not buy
                                AlertClass.BaseAlert_done(context, SharePrefs.getSetting(context).getOrganizationName(), "This is paid content, So please buy this Course", "Done","No", false, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();

                                    }
                                }, null);
                            }

                            else
                            {
                                mOnclickListener.onItemClick(pos);
                            }
                        }

                    }
                }
            });
        }
    }

    class MyViewHolderHeader extends RecyclerView.ViewHolder
    {

        TextView tvTopicName;
        ImageView iv_arrow;

        public MyViewHolderHeader(View itemView)
        {
            super(itemView);
            tvTopicName = (TextView) itemView.findViewById(R.id.tv_header);
            iv_arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
        }
    }

    class MyViewHolderChild extends RecyclerView.ViewHolder
    {
        ImageView iv_videframe;
        TextView tv_title, tv_freeorpaid;
        LinearLayout ll_paidfree;
        RelativeLayout ll_main;

        public MyViewHolderChild(View itemView)
        {
            super(itemView);
            iv_videframe = (ImageView) itemView.findViewById(R.id.iv_videframe);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_freeorpaid = (TextView) itemView.findViewById(R.id.tv_freeorpaid);
            ll_paidfree = (LinearLayout) itemView.findViewById(R.id.ll_paidfree);
            ll_main = (RelativeLayout) itemView.findViewById(R.id.ll_main);
        }
    }
}
