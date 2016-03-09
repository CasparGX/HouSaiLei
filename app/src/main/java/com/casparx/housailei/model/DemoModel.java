package com.casparx.housailei.model;

import android.graphics.drawable.Drawable;

/**
 * Created by root on 16-3-9.
 */
public class DemoModel {
    private String title;
    private String dec;
    private Drawable pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getPic() {
        return pic;
    }

    public void setPic(Drawable pic) {
        this.pic = pic;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
