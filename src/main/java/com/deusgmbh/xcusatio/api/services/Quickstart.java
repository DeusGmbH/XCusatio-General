
package com.deusgmbh.xcusatio.api.services;

import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Quickstart {
	
	
		
	 private static class Target {
	    	private double[][] boundingBoxCoordinates;
	    	
	    	private Target(MapView mapView) {
	    		this.boundingBoxCoordinates[0] = mapView.topLeft;
	    		this.boundingBoxCoordinates[1] = mapView.bottomRight;
	    		
	    	}
	    }
	 

 	private static class MapView {
     	private double[] topLeft;
     	private double[] bottomRight;
     	
     	private MapView(double[] topLeft, double[] bottomRight) {
     		this.topLeft = topLeft;
     		this.bottomRight = bottomRight;
     	}
     }
     
     private static class Location {
     	private MapView mapView;
     	private Location(MapView mapView) {
     		this.mapView = mapView;
     	}
     }
     
     private static class Result {
     	private Location location;
     	private Result(Location location) {
     		this.location = location;
     	}
     }
	 
	
	static class Event {
		private String name;
		private String source;
		private Event(String name, String source) {
			this.name = name;
			this.source = source;
		}

		@Override
		public String toString() {
			return String.format("(name=%s, source=%s)", name, source);
		}
	}
	
	
	public static void main(String[] europium) {
		Gson gson = new Gson();
		Collection collection = new ArrayList<>();
		collection.add("Hello");
		collection.add(5);
		collection.add(new Event("Web engineering 2", "lecturer"));
		String json = "{\"Response\":{\"MetaInfo\":{\"Timestamp\":\"2018-04-05T11:24:30.384+0000\"},\"View\":[{\"_type\":\"SearchResultsViewType\",\"ViewId\":0,\"Result\":[{\"Relevance\":0.99,\"MatchLevel\":\"houseNumber\",\"MatchQuality\":{\"City\":1.0,\"Street\":[1.0],\"HouseNumber\":0.98,\"PostalCode\":1.0},\"MatchType\":\"pointAddress\",\"Location\":{\"LocationId\":\"NT_s9U6u43X.b7i45BtwF4JAB_4A\",\"LocationType\":\"point\",\"DisplayPosition\":{\"Latitude\":50.11327,\"Longitude\":8.69587},\"NavigationPosition\":[{\"Latitude\":50.11336,\"Longitude\":8.69588}],\"MapView\":{\"TopLeft\":{\"Latitude\":50.1143942,\"Longitude\":8.694117},\"BottomRight\":{\"Latitude\":50.1121458,\"Longitude\":8.697623}},\"Address\":{\"Label\":\"Hanauer Landstraße 8, 60314 Frankfurt am Main, Deutschland\",\"Country\":\"DEU\",\"State\":\"Hessen\",\"County\":\"Frankfurt am Main\",\"City\":\"Frankfurt am Main\",\"District\":\"Ostend\",\"Street\":\"Hanauer Landstraße\",\"HouseNumber\":\"8\",\"PostalCode\":\"60314\",\"AdditionalData\":[{\"value\":\"Deutschland\",\"key\":\"CountryName\"},{\"value\":\"Hessen\",\"key\":\"StateName\"},{\"value\":\"Frankfurt am Main\",\"key\":\"CountyName\"}]}},\"AdditionalData\":[{\"value\":\"true\",\"key\":\"houseNumberFallback\"},{\"value\":\"2\",\"key\":\"houseNumberFallbackDifference\"}]}]}]}}";
		System.out.println("Using Gson.toJson on raw collection: " + json);
		
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonObject total = gson.fromJson(element.getAsJsonObject(), JsonObject.class);
		JsonObject response = gson.fromJson(total.get("Response"), JsonObject.class);
		System.out.println("Total: " + total);
		
		JsonArray view = gson.fromJson(response.get("View"), JsonArray.class);
		JsonObject viewObject = gson.fromJson(view.get(0), JsonObject.class);
		JsonArray result = gson.fromJson(viewObject.get("Result"), JsonArray.class);
		JsonObject resultObject = gson.fromJson(result.get(0), JsonObject.class);
		JsonObject location = gson.fromJson(resultObject.get("Location"), JsonObject.class);
		JsonObject mapView = gson.fromJson(location.get("MapView"), JsonObject.class);
		JsonObject[] boundingBox = new JsonObject[] { gson.fromJson(mapView.get("TopLeft"), JsonObject.class), gson.fromJson(mapView.get("BottomRight"), JsonObject.class) };
		double[] topLeft = new double[] {gson.fromJson(boundingBox[1].get("Latitude"), double.class), gson.fromJson(boundingBox[0].get("Longitude"), double.class)};
		double[] bottomRight = new double[] {gson.fromJson(boundingBox[0].get("Latitude"), double.class), gson.fromJson(boundingBox[1].get("Longitude"), double.class)};
		
		
		
		System.out.printf("Top left: %f %f\nBottom Right: %f %s", topLeft[0], topLeft[1], bottomRight[0], bottomRight[1]);
		
		
		
		
		
//		JsonObject location = gson.fromJson(location.getAsJsonObject(), JsonObject.class);
		
		
		
		
//		System.out.println(element);
		
		
	
//		String message = gson.fromJson(array.get(0), String.class);
//		int number = gson.fromJson(array.get(1), int.class);
//		Event event = gson.fromJson(array.get(2), Event.class);
		
		
//		System.out.printf("Using Gson.fromJson() to get: %s", target.toString());
		
	}
	
	private static int indexOf(JsonArray jsonArray, String key) {
		for (int i = 0; i < jsonArray.size(); ++i) {
			System.out.println(jsonArray.get(i).toString());
			if (jsonArray.get(i).equals("Result")) {
				return i;
			}
		}
		return -1;
	}
	
}