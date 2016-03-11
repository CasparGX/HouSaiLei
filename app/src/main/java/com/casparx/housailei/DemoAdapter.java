package com.casparx.housailei;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.casparx.housailei.model.DemoModel;

import java.util.ArrayList;

/**
 * Created by root on 16-3-9.
 */
public class DemoAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<DemoModel> data;

    private ImageView pic;
    private TextView title;

    public DemoAdapter(LayoutInflater inflater, ArrayList<DemoModel> data) {
        this.inflater = inflater;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_demo_gridview, viewGroup, false);
            pic = (ImageView) view.findViewById(R.id.demo_pic);
            title = (TextView) view.findViewById(R.id.demo_title);
        }
        pic.setImageDrawable(data.get(i).getPic());
        switch (i) {
            case 0:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img1));
                break;
            case 1:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img2));
                break;
            case 2:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img3));
                break;
            case 3:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img4));
                break;
            case 4:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img5));
                break;
            case 5:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img6));
                break;
            case 6:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img7));
                break;
            case 7:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img8));
                break;
            case 8:
                pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.demo_img9));
                break;
        }
        //pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.me_touxiang));
        title.setText(data.get(i).getTitle());
        return view;
    }
}
