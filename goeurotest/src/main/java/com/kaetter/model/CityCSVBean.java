package com.kaetter.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
/**
 *  This defines the CSV structure for the City bean 
 * @author user
 *
 */
@JsonPropertyOrder({ "id", "name", "type", "latitude", "longitude" })
public class CityCSVBean {

	private Integer id;
	private String name;
	private String type;
	private Double latitude;
	private Double longitude;

	public CityCSVBean(City city) {
		this.id = city.getId();
		this.name = city.getName();
		this.type = city.getType();
		this.latitude = city.getGeoPosition().getLatitude();
		this.longitude = city.getGeoPosition().getLongitude();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "CityCSVBean [id=" + id + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
