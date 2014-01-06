package com.appiari.parkaspharmacy.adapters;

//Added a little header file up here yallssss

public class Leaf {

	public Leaf(String name, String urlWiki, String image,int databasePosition) {
		super();
		this.name = name;
		this.urlWiki = urlWiki;
		this.image = image;
		this.databasePosition = databasePosition;
	}

	private String name;
	private String urlWiki;
	private String image;
	private int databasePosition;
	
	public int getDatabasePosition(){
		return databasePosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String nameText) {
		name = nameText;
	}

	public String getUrlWiki() {
		return urlWiki;
	}

	public void setUrlWiki(String urlWiki) {
		this.urlWiki = urlWiki;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	//Added a little footer here 

}
