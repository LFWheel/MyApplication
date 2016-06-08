package com.eeesys.frame.net.inter;

import com.eeesys.frame.net.model.URLComplete;

public interface URLCallBack {
	
	public void onSucess(URLComplete urlComplete);
	
	public void onFail(URLComplete urlComplete);
  
}
