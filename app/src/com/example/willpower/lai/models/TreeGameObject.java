package com.example.willpower.lai.models;

import android.os.Parcel;
import android.os.Parcelable;

public class TreeGameObject implements Parcelable{
	
	private int mCostPerAcre; // Credits that needed to plant new trees
	private double mCurrentAcresIncreaseRate; // Increase rate, which is given by system
	private int mCurrentAcres; // Acres that users currently handle
	private int mCurrentCredits; // Credits that users currently handle
	private int mCurrentMaintainPerAcre; // Credits that needed to maintain the trees per peroid time
	private int mCurrentValuePerAcre; // Credits that users can gain when cutting down trees
	
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
	public TreeGameObject(int mCostPerAcre, double mCurrentAcresIncreaseRate, int mCurrentAcres, int mCurrentCredits,
			int mCurrentMaintainPerAcre, int mCurrentValuePerAcre) {
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
		this.mCostPerAcre = p.readInt();
		this.mCurrentAcresIncreaseRate = p.readDouble();
		this.mCurrentAcres = p.readInt();
		this.mCurrentCredits = p.readInt();
		this.mCurrentMaintainPerAcre = p.readInt();
		this.mCurrentValuePerAcre = p.readInt();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.mCostPerAcre);
		dest.writeDouble(this.mCurrentAcresIncreaseRate);
		dest.writeInt(this.mCurrentAcres);
		dest.writeInt(this.mCurrentCredits);
		dest.writeInt(this.mCurrentMaintainPerAcre);
		dest.writeInt(this.mCurrentValuePerAcre);
	}
	
	// Some get and set functions
	public int getCostPerAcre() {
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
	
	public int getCurrentMaintainPerAcre() {
		return this.mCurrentMaintainPerAcre;
	}
	
	public void setCurrentMaintainPerAcre(int mCurrentMaintainPerAcre) {
		this.mCurrentMaintainPerAcre = mCurrentMaintainPerAcre;
	}
	
	public int getCurrentValuePerAcre() {
		return this.mCurrentValuePerAcre;
	}
	
	public void setCurrentValuePerAcre(int mCurrentValuePerAcre) {
		this.mCurrentValuePerAcre = mCurrentValuePerAcre;
	}
}
