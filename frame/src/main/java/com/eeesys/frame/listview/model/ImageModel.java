package com.eeesys.frame.listview.model;

import java.io.Serializable;

public class ImageModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public int imageId_1, imageId_2, imageId_3;
	public ImageModel(){
		super();
	}

	public ImageModel(int imageId_1) {
		super();
		this.imageId_1 = imageId_1;
	}

	public ImageModel(int imageId_1, int imageId_2) {
		super();
		this.imageId_1 = imageId_1;
		this.imageId_2 = imageId_2;
	}

	public ImageModel(int imageId_1, int imageId_2, int imageId_3) {
		super();
		this.imageId_1 = imageId_1;
		this.imageId_2 = imageId_2;
		this.imageId_3 = imageId_3;
	}

}
