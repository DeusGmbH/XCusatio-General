package com.deusgmbh.xcusatio.data.usersettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class Address {
	private String streetname;
	private String streetnum;
	private String zip;
	private String city;
	private String country;

	public Address(String streetname, String streetnum, String zip, String city, String country) {
		super();
		this.streetname = streetname;
		this.streetnum = streetnum;
		this.zip = zip;
		this.city = city;
		this.country = country;
	}

	public Address(String streetname, String streetnum, String zip, String city) {
		this(streetname, streetnum, zip, city, null);
	}

	public String getStreetName() {
		return streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public String getStreetnum() {
		return streetnum;
	}

	public void setStreetnum(String streetnum) {
		this.streetnum = streetnum;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
