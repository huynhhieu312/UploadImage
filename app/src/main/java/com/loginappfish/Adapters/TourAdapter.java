package com.loginappfish.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loginappfish.Models.Tour;
import com.loginappfish.R;

import java.util.List;

/**
 * Created by hieuhuynh on 9/2/17.
 */

public class TourAdapter extends BaseAdapter {
    public static final String TAG = "TourModel";
    List<Tour> list;

    Context context;
    public TourAdapter(List<Tour> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public Tour getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Tour item = getItem(position);
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item1, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //viewHolder.loadData(item, position, getPrevItem(position));
        viewHolder.imghinh= (ImageView) view.findViewById(R.id.imghinh);
        viewHolder.txttentour=(TextView) view.findViewById(R.id.txttentour);
        viewHolder.txtphuongtien=(TextView) view.findViewById(R.id.txtphuongtien);
        viewHolder.txtngaykhoihanh=(TextView) view.findViewById(R.id.txtkhoihanh);
        viewHolder.txtgiatour=(TextView) view.findViewById(R.id.txtgiatour);

        Glide.with(this.context).load(item.getHinh()).into(viewHolder.imghinh);
        viewHolder.txttentour.setText(item.getTentour());

        viewHolder.txtphuongtien.setText(item.getPhuongtien()+"");
        viewHolder.txtngaykhoihanh.setText(item.getNgaykhoihanh()+"");
        viewHolder.txtgiatour.setText(item.getGiatour()+"");
        return view;
    }

    public static class ViewHolder {
        ImageView imghinh;
        TextView txttentour,txtphuongtien,txtngaykhoihanh,txtgiatour;
    }

}
