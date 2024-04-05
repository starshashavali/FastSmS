package com.cms.binding;

public class PersonCounts {

	private Integer maleCount;
	private Integer femaleCount;
	private Integer childrenCount;
	private Integer seniorCitizenCount;
	
	public PersonCounts() {
		
	}
	
	public PersonCounts(Integer maleCount, Integer femaleCount, Integer childrenCount, Integer seniorCitizenCount) {
		super();
		this.maleCount = maleCount;
		this.femaleCount = femaleCount;
		this.childrenCount = childrenCount;
		this.seniorCitizenCount = seniorCitizenCount;
	}
	public Integer getMaleCount() {
		return maleCount;
	}
	public void setMaleCount(Integer maleCount) {
		this.maleCount = maleCount;
	}
	public Integer getFemaleCount() {
		return femaleCount;
	}
	public void setFemaleCount(Integer femaleCount) {
		this.femaleCount = femaleCount;
	}
	public Integer getChildrenCount() {
		return childrenCount;
	}
	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}
	public Integer getSeniorCitizenCount() {
		return seniorCitizenCount;
	}
	public void setSeniorCitizenCount(Integer seniorCitizenCount) {
		this.seniorCitizenCount = seniorCitizenCount;
	}
	

}
