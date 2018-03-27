package com.deusgmbh.xcusatio.api.services;

import java.util.LinkedList;
import java.util.List;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class RNVAPI extends APIService {

    @Override
    public RNVContext get(UserSettings usersettings) {
        // TODO Auto-generated method stub

        List<String> stops = new LinkedList<>();
        stops.add("Mannheim Hbf");
        stops.add("Duale Hochschule");
        stops.add("Edingen");
        stops.add("Heidelberg Hbf");
        TramDetails tram = new TramDetails("5", "Mannheim Hbf", "Heidelberg Hbf", stops);

        List<String> affectedLines = new LinkedList<>();
        affectedLines.add("3");
        affectedLines.add("5");
        TramNews newsEntry = new TramNews("Stoerung", "Oberleitung beschaedigt", affectedLines);

        RNVContext rnvContext = new RNVContext(tram, newsEntry, TramStatus.CANCELLED, 30);

        return rnvContext;
    }

    @Override
    public void transmitDataToWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void extractDesiredInfoFromResponse() {
        // TODO Auto-generated method stub

    }

    // public static void main(String[] args) {
    // UserSettings userSettings = new UserSettings(null, 0, null, null, null,
    // null, null);
    // RNVAPI rnvapi = new RNVAPI();
    // RNVContext rnvContext = rnvapi.get(userSettings);
    // rnvContext.printContextContent();
    // }

}
