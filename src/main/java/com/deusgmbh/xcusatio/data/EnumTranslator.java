package com.deusgmbh.xcusatio.data;

import java.util.ArrayList;
import java.util.List;

import com.deusgmbh.xcusatio.api.data.TrafficIncidentType;
import com.deusgmbh.xcusatio.data.tags.Tag;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class EnumTranslator {
	public static String toGerman(Tag tag) {
		switch (tag) {
		default:
			return tag.toString();
		}
	}

	public static List<String> toGerman(List<Tag> tagList) {
		List<String> translatedTagList = new ArrayList<String>();
		tagList.stream().forEach(tag -> {
			translatedTagList.add(toGerman(tag));
		});
		return translatedTagList;
	}

	public static String toGerman(TrafficIncidentType enumToTranslate) {
		switch (enumToTranslate) {
		case ACCIDENT:
			return "Unfall";
		case CONGESTION:
			return "\u00DCberlastung";
		case DISABLED_VEHICLE:
			return "fahrunf\u00E4higes Fahrzeug";
		case ROAD_HAZARD:
			return "Gefahrenstelle";
		case CONSTRUCTION:
			return "Baustelle";
		case PLANNED_EVENT:
			return "geplante Sperrung";
		case MASS_TRANSIT:
			return "stockender Verkehr";
		case OTHER_NEWS:
			return "anderer Zwischenfall";
		case WEATHER:
			return "Wetter";
		case MISC:
			return "sonstiges";
		case ROAD_CLOSURE:
			return "Stra\u00DFensperrung";
		case LANE_RESTRICTION:
			return "Engstelle";
		default:
			return null;
		}
	}
}
