package com.deusgmbh.xcusatio.data;

import java.util.ArrayList;
import java.util.List;

import com.deusgmbh.xcusatio.api.data.traffic.TrafficIncidentType;
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
        tagList.stream()
                .forEach(tag -> {
                    translatedTagList.add(toGerman(tag));
                });
        return translatedTagList;
    }

    public static String toGerman(TrafficIncidentType enumToTranslate) {
        switch (enumToTranslate) {
        case ACCIDENT:
            return "ein Unfall";
        case CONGESTION:
            return "eine \u00DCberlastung";
        case DISABLED_VEHICLE:
            return "ein fahrunf\u00E4higes Fahrzeug";
        case ROAD_HAZARD:
            return "eine Gefahrenstelle";
        case CONSTRUCTION:
            return "eine Baustelle";
        case PLANNED_EVENT:
            return "eine geplante Sperrung";
        case MASS_TRANSIT:
            return "stockender Verkehr";
        case OTHER_NEWS:
            return "eine Baustelle";
        case WEATHER:
            return "schlechte Wetterbedingungen";
        case MISC:
            return "kein durchkommen";
        case ROAD_CLOSURE:
            return "eine Stra\u00DFensperrung";
        case LANE_RESTRICTION:
            return "eine gesperrte Spur";
        default:
            return null;
        }
    }
}
