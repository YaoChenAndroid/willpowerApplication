package com.example.willpower.lai.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TreeGameObject implements Parcelable{
	
	private int mCostPerAcres;
	private int mCurrentAcresIncreaseRate;
	private int mCurrentAcres;
	private int mCurrentCredits;
	
	public static final Parcelable.Creator<TreeGameObject> CREATOR = new Parcelable.Creator<TreeGameObject>() {

		@Override
		public TreeGameObject createFromParcel(Parcel p) {
			// TODO Auto-generated method stub
			return new TreeGameObject(p);
		}

		@Override
		public TreeGameObject[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TreeGameObject[size];
		}
		
	};
	
	/**
	 * Create a new TreeGameObject object from arguments directly
	 * @param mCostPerAcres
	 * @param mCurrentAcresIncreaseRate
	 * @param mCurrentAcres
	 * @param mCurrentCredits
	 */
	public TreeGameObject(int mCostPerAcres, int mCurrentAcresIncreaseRate, int mCurrentAcres, int mCurrentCredits) {
		this.mCostPerAcres = mCostPerAcres;
		this.mCurrentAcresIncreaseRate = mCurrentAcresIncreaseRate;
		this.mCurrentAcres = mCurrentAcres;
		this.mCurrentCredits = mCurrentCredits;
	}
	
	/**
	 * Create a new TreeGameObject object from a Parcel
	 * @param p
	 */
	public TreeGameObject(Parcel p) {
		this.mCostPerAcres = p.readInt();
		this.mCurrentAcresIncreaseRate = p.readInt();
		this.mCurrentAcres = p.readInt();
		this.mCurrentCredits = p.readInt();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mCostPerAcres);
		dest.writeInt(this.mCurrentAcresIncreaseRate);
		dest.writeInt(this.mCurrentAcres);
		dest.writeInt(this.mCurrentCredits);
	}
	
	// Some get and set functions
	public int getCostPerAcres() {
		return this.mCostPerAcres;
	}
	
	public void setCostPerAcres(int mCostPerAcres) {
		this.mCostPerAcres = mCostPerAcres;
	}
	
	public int getCurrentAcresIncreaseRate() {
		return this.mCurrentAcresIncreaseRate;
	}
	
	public void setCurrentAcresIncreaseRate(int mCurrentAcresIncreaseRate) {
		this.mCurrentAcresIncreaseRate = mCurrentAcresIncreaseRate;
	}
	
	public int getCurrentAcres() {
		return this.mCurrentAcres;
	}
	
	public void setCurrentAcres(int mCurrentAcres) {
		this.mCurrentAcres = mCurrentAcres;
	}
	
	public int getCurrentCredits() {
		return this.mCurrentCredits;
	}
	
	public void setCurrentCredits(int mCurrentCredits) {
		this.mCurrentCredits = mCurrentCredits;
	}
}
