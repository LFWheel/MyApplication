package com.eeesys.frame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.eeesys.frame.utils.PinYin;

public class Search<T> {
	private Map<String, T> fullPy;
	private Map<String, T> firstPy;
	private Map<String, T> hanzi;

	public Search() {
		super();
		fullPy = new HashMap<String, T>();
		firstPy = new HashMap<String, T>();
		hanzi = new HashMap<String, T>(); 
	}

	public void getFullPy(String hz, T obj) {
		fullPy.put(PinYin.getFullPinYin(hz), obj);
	}

	public void getFirstPy(String hz, T obj) {
		firstPy.put(PinYin.getFirstPinYin(hz), obj);
	}
	
	public void getHanzi(String hz, T obj) {
		firstPy.put(hz, obj);
	}

	public Map<String, T> getFullPy() {
		return fullPy;
	}

	public Map<String, T> getFirstPy() {
		return firstPy;
	}
	
	public Map<String, T> getHanzi() {
		return hanzi;
	}
	
	public List<T> search(String s) {
		Set<Entry<String, T>> set = firstPy.entrySet();
		List<T> list = new ArrayList<T>();
		for (Entry<String, T> entry : set) {
			if(entry.getKey().contains(s)){
				if(!list.contains(entry.getValue()))
					list.add(entry.getValue());
			}
		}
		
		set = fullPy.entrySet();
		for (Entry<String, T> entry : set) {
			if(entry.getKey().contains(s)){
				if(!list.contains(entry.getValue()))
					list.add(entry.getValue());
			}
		}
		
		set = hanzi.entrySet();
		for (Entry<String, T> entry : set) {
			if(entry.getKey().contains(s)){
				if(!list.contains(entry.getValue()))
					list.add(entry.getValue());
			}
		}
		return list;
		
	}

}
