package com.eeesys.frame.model;

import java.util.HashMap;

import com.eeesys.frame.utils.Tools;

public class BaseModel {
	
	public HashMap<String, Object> toMap(){
		return Tools.toMap(this);
	}

}
