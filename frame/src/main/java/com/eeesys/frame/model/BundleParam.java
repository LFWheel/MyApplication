package com.eeesys.frame.model;

import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

public class BundleParam implements Parcelable {
	
	private HashMap<String, Object> map;

	public BundleParam convert(HashMap<String, Object> map) {
		this.map = map;
		return this;
	}

	public HashMap<String, Object> getMap() {
		return map;
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(map);
	}

	public static final Parcelable.Creator<BundleParam> CREATOR = new Parcelable.Creator<BundleParam>() {
		@SuppressWarnings("unchecked")
		public BundleParam createFromParcel(Parcel in) {
			BundleParam bp = new BundleParam();
			bp.map = in.readHashMap(HashMap.class.getClassLoader());
			return bp;
		}

		public BundleParam[] newArray(int size) {
			return new BundleParam[size];
		}
	};

}
