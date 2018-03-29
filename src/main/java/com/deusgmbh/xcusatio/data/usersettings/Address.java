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

    public Address(String streetname, String streetnum, String zip, String city) {
        super();
        this.streetname = streetname;
        this.streetnum = streetnum;
        this.zip = zip;
        this.city = city;
        this.country = "germany";
    }

    public Address() {
        super();
        this.country = "germany";
    }

    public String getStreetName() {
        return streetname;
    }

    public Address setStreetname(String streetname) {
        this.streetname = streetname;
        return this;
    }

    public String getStreetnum() {
        return streetnum;
    }

    public Address setStreetnum(String streetnum) {
        this.streetnum = streetnum;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public Address setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }
}
