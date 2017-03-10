package com.feliperrm.wikiolap.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felip on 10/03/2017.
 */

public class XYHolder implements Parcelable {
    double x;
    double y;

    public XYHolder(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
    }

    protected XYHolder(Parcel in) {
        this.x = in.readDouble();
        this.y = in.readDouble();
    }

    public static final Parcelable.Creator<XYHolder> CREATOR = new Parcelable.Creator<XYHolder>() {
        @Override
        public XYHolder createFromParcel(Parcel source) {
            return new XYHolder(source);
        }

        @Override
        public XYHolder[] newArray(int size) {
            return new XYHolder[size];
        }
    };
}

