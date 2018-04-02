package com.deusgmbh.xcusatio.api.data;

import com.deusgmbh.xcusatio.data.usersettings.Address;

public class GeocodeData {

	private Address address;
	private double[] spotCoordinates;
	private int[] mapTilesLowZoom;
	private int[] mapTilesMediumZoom;
	private int[] mapTilesHighZoom;
	private double[][] boundingBoxCoordinates;
	private String quadkey;

	public GeocodeData(Address address, double[] spotCoordinates, int[] mapTilesLowZoom, int[] mapTilesMediumZoom,
			int[] mapTilesHighZoom) {
		super();
		this.address = address;
		this.spotCoordinates = spotCoordinates;
		this.mapTilesLowZoom = mapTilesLowZoom;
		this.mapTilesMediumZoom = mapTilesMediumZoom;
		this.mapTilesHighZoom = mapTilesHighZoom;
	}

	public GeocodeData(Address address) {
		super();
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double[] getAddressSpotCoordinates() {
		return spotCoordinates;
	}

	public void setAddressSpotCoordinates(double[] addressSpotCoordinates) {
		this.spotCoordinates = addressSpotCoordinates;
	}

	public double[][] getBoundingBoxCoordinates() {
		return boundingBoxCoordinates;
	}

	public void setBoundingBoxCoordinates(double[][] boundingBoxCoordinates) {
		this.boundingBoxCoordinates = boundingBoxCoordinates;
	}

	public String getQuadkey() {
		return quadkey;
	}

	public void setQuadkey(String quadkey) {
		this.quadkey = quadkey;
	}

	public double[] getSpotCoordinates() {
		return spotCoordinates;
	}

	public void setSpotCoordinates(double[] spotCoordinates) {
		this.spotCoordinates = spotCoordinates;
	}

	public int[] getMapTilesLowZoom() {
		return mapTilesLowZoom;
	}

	public void setMapTilesLowZoom(int[] mapTilesLowZoom) {
		this.mapTilesLowZoom = mapTilesLowZoom;
	}

	public int[] getMapTilesMediumZoom() {
		return mapTilesMediumZoom;
	}

	public void setMapTilesMediumZoom(int[] mapTilesMediumZoom) {
		this.mapTilesMediumZoom = mapTilesMediumZoom;
	}

	public int[] getMapTilesHighZoom() {
		return mapTilesHighZoom;
	}

	public void setMapTilesHighZoom(int[] mapTilesHighZoom) {
		this.mapTilesHighZoom = mapTilesHighZoom;
	}

}
