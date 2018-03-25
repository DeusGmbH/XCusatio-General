package com.deusgmbh.xcusatio.api.services;

import java.util.List;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.context.wildcard.TramDetails;
import com.deusgmbh.xcusatio.context.wildcard.TramNews;
import com.deusgmbh.xcusatio.context.wildcard.TramStatus;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class RNVAPI extends APIService {

    @Override
    public RNVContext get(UserSettings usersettings) {
        // TODO Auto-generated method stub

        List<String> stops = null;
        stops.add("Mannheim Hbf");
        stops.add("Duale Hochschule");
        stops.add("Edingen");
        stops.add("Heidelberg Hbf");
        TramDetails tram = new TramDetails("5", "Mannheim Hbf", "Heidelberg Hbf", stops);

        List<String> affectedLines = null;
        affectedLines.add("3");
        affectedLines.add("5");
        TramNews newsEntry = new TramNews("Stoerung", "Oberleitung beschaedigt", affectedLines);

        RNVContext rnvContext = new RNVContext(tram, newsEntry, TramStatus.CANCELLED, 30);

        return rnvContext;
    }

}
