package com.cocoon.jay;

import java.io.Serializable;


public class TagBean implements Serializable{


    private String tag_name;//标签名
    private String has;
    private float rank;//排行(当has=1时，有此参数)

	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}
	public String getHas() {
		return has;
	}
	public void setHas(String has) {
		this.has = has;
	}
	public float getRank() {
		return rank;
	}
	public void setRank(float rank) {
		this.rank = rank;
	}
	@Override
	public String toString() {
		return "TagBean [ tag_name=" + tag_name
				+ ", has=" + has + ", rank=" + rank + "]";
	}

}
