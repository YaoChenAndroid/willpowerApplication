package com.example.willpower.lai.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TreeGameObject implements Parcelable{
	
	private long mCostPerAcre; // Credits that needed to plant new trees
	private double mCurrentAcresIncreaseRate; // Increase rate, which is given by system
	private long mCurrentAcres; // Acres that users currently handle
	private long mCurrentCredits; // Credits that users currently handle
	private long mCurrentMaintainPerAcre; // Credits that needed to maintain the trees per peroid time
	private long mCurrentValuePerAcre; // Credits that users can gain when cutting down trees
	
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
	 * @param mCostPerAcre
	 * @param mCurrentAcresIncreaseRate
	 * @param mCurrentAcres
	 * @param mCurrentCredits
	 */
	public TreeGameObject(long mCostPerAcre, double mCurrentAcresIncreaseRate, long mCurrentAcres, long mCurrentCredits,
			long mCurrentMaintainPerAcre, long mCurrentValuePerAcre) {
		this.mCostPerAcre = mCostPerAcre;
		this.mCurrentAcresIncreaseRate = mCurrentAcresIncreaseRate;
		this.mCurrentAcres = mCurrentAcres;
		this.mCurrentCredits = mCurrentCredits;
		this.mCurrentMaintainPerAcre = mCurrentMaintainPerAcre;
		this.mCurrentValuePerAcre = mCurrentValuePerAcre;
	}
	
	/**
	 * Create a new TreeGameObject object from a Parcel
	 * @param p
	 */
	public TreeGameObject(Parcel p) {
		this.mCostPerAcre = p.readLong();
		this.mCurrentAcresIncreaseRate = p.readDouble();
		this.mCurrentAcres = p.readLong();
		this.mCurrentCredits = p.readLong();
		this.mCurrentMaintainPerAcre = p.readLong();
		this.mCurrentValuePerAcre = p.readLong();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.mCostPerAcre);
		dest.writeDouble(this.mCurrentAcresIncreaseRate);
		dest.writeLong(this.mCurrentAcres);
		dest.writeLong(this.mCurrentCredits);
		dest.writeLong(this.mCurrentMaintainPerAcre);
		dest.writeLong(this.mCurrentValuePerAcre);
	}
	
	// Some get and set functions
	public long getCostPerAcre() {
		return this.mCostPerAcre;
	}
	
	public void setCostPerAcres(int mCostPerAcre) {
		this.mCostPerAcre = mCostPerAcre;
	}
	
	public double getCurrentAcresIncreaseRate() {
		return this.mCurrentAcresIncreaseRate;
	}
	
	public void setCurrentAcresIncreaseRate(double mCurrentAcresIncreaseRate) {
		this.mCurrentAcresIncreaseRate = mCurrentAcresIncreaseRate;
	}
	
	public long getCurrentAcres() {
		return this.mCurrentAcres;
	}
	
	public void setCurrentAcres(int mCurrentAcres) {
		this.mCurrentAcres = mCurrentAcres;
	}
	
	public long getCurrentCredits() {
		return this.mCurrentCredits;
	}
	
	public void setCurrentCredits(int mCurrentCredits) {
		this.mCurrentCredits = mCurrentCredits;
	}
	
	public long getCurrentMaintainPerAcre() {
		return this.mCurrentMaintainPerAcre;
	}
	
	public void setCurrentMaintainPerAcre(int mCurrentMaintainPerAcre) {
		this.mCurrentMaintainPerAcre = mCurrentMaintainPerAcre;
	}
	
	public long getCurrentValuePerAcre() {
		return this.mCurrentValuePerAcre;
	}
	
	public void setCurrentValuePerAcre(int mCurrentValuePerAcre) {
		this.mCurrentValuePerAcre = mCurrentValuePerAcre;
	}
}
