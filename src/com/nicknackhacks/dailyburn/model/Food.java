package com.nicknackhacks.dailyburn.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * <?xml version="1.0" encoding="UTF-8"?>
 <foods type="array">
 <food>
 <brand>CLIF BAR</brand>
 <calories type="integer">240</calories>
 <id type="integer">16458</id>
 <name>CLIF BAR, Chocolate Chip</name>
 <protein type="float">10.0</protein>
 <serving-size>1 Bar (68 g)</serving-size>
 <total-carbs type="float">44.0</total-carbs>
 <total-fat type="float">5.0</total-fat>
 <user-id type="integer">8198</user-id>
 <thumb-url>/images/fu/0005/7523/0470103_1__thumb.jpg</thumb-url>
 <usda type="boolean">false</usda>
 </food>
 </foods>
 */
public class Food {
	private String brand;
	private int calories;
	private int id;
	private String name;
	private float protein;
	private String servingSize;
	private float totalCarbs;
	private float totalFat;
	private int userId;
	private String thumbUrl;
	private boolean usda;
	public Map<String, Double> unitNameToAmtInServing = new HashMap<String, Double>();

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getProtein() {
		return protein;
	}

	public void setProtein(float protein) {
		this.protein = protein;
	}

	public String getServingSize() {
		return servingSize;
	}

	public void setServingSize(String servingSize) {
		this.servingSize = servingSize;
		this.addMappings(servingSize);
	}

	public float getTotalCarbs() {
		return totalCarbs;
	}

	public void setTotalCarbs(float totalCarbs) {
		this.totalCarbs = totalCarbs;
	}

	public float getTotalFat() {
		return totalFat;
	}

	public void setTotalFat(float totalFat) {
		this.totalFat = totalFat;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getNormalUrl() {
		if (thumbUrl == null)
			return null;

		int startOfThumb = thumbUrl.lastIndexOf("_thumb");
		String first = thumbUrl.substring(0, startOfThumb);
		String second = thumbUrl.substring(startOfThumb + 6);
		String normalUrl = null;
		if (thumbUrl.contains("default_food")) {
			normalUrl = first + second;
		} else {
			normalUrl = first + "_normal" + second;
		}
		return normalUrl;
	}

	public boolean isUsda() {
		return usda;
	}

	public void setUsda(boolean usda) {
		this.usda = usda;
	}
	
	private void addMappings(String s) {
		unitNameToAmtInServing.put("servings", 1.0);
		
		Pattern p;
		Matcher m;
		
		// look for a number, possibly with a decimal, then one or more words.
		// space in-between is optional; often not used for grams
		p = Pattern.compile("(\\d+\\.?\\d*)\\s?([\\s\\w]+)");
		m = p.matcher(s);
		while(m.find()) {
			String v_string = m.group(1);
			String unit_name = m.group(2);
			
			unitNameToAmtInServing.put(unit_name, Double.valueOf(v_string));
		}

		// as above, but look for fractional numbers
		p = Pattern.compile("(\\d+)/(\\d+)\\s?([\\s\\w]+)");
		m = p.matcher(s);
		while(m.find()) {
			String num_string = m.group(1);
			String denom_string = m.group(2);
			String unit_name = m.group(3);
			
			unitNameToAmtInServing.put(unit_name, Double.valueOf(num_string) / Double.valueOf(denom_string));
		}
		
		// lots of entries have grams. in these cases, also provide ounces
		// TODO other such automatic conversions?
		if (unitNameToAmtInServing.containsKey("g")) {
			final double grams_per_oz = 28.3495231;
			// including "(mass)" do disambiguate from fluid ounces
			unitNameToAmtInServing.put("oz (mass)", 
					unitNameToAmtInServing.get("g")/grams_per_oz);
		}
	}
}
