package com.casparx.housailei;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.casparx.housailei.model.DemoModel;

import java.io.InputStream;
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
        //pic.setImageResource(data.get(i).getResId());
        pic.setImageBitmap(readBitMap(inflater.getContext(),data.get(i).getResId()));
        //pic.setImageDrawable(inflater.getContext().getResources().getDrawable(R.drawable.me_touxiang));
        title.setText(data.get(i).getTitle());
        return view;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }
}
