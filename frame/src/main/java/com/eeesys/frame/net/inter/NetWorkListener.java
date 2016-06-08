package com.eeesys.frame.net.inter;

import java.util.Map;

public interface NetWorkListener {
	
	void LoadUrl(String baseURL);
	
	void LoadUrl(String baseURL, Map<String, Object> urlParam);

	void LoadUrl(String baseURL, Map<String, Object> urlParam, String cookie);

	void LoadUrlWithNoThread(String baseURL);

	void LoadUrlWithNoThread(String baseURL, Map<String, Object> urlParam);

	void LoadUrlWithNoThread(String baseURL, Map<String, Object> urlParam,
			String cookie);
}
