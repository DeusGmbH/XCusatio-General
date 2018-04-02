package com.deusgmbh.xcusatio.api.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.TramDetails;
import com.deusgmbh.xcusatio.api.data.TramNews;
import com.deusgmbh.xcusatio.api.data.TramStatus;
import com.deusgmbh.xcusatio.context.wildcard.RNVContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class RNVAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(RNVAPI.class.getName());

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

        String dateFormat = "MM/dd/yyyy hh:mm:ss";
        Date timeStamp = null;
        try {
            timeStamp = new SimpleDateFormat(dateFormat).parse("03/27/2018 09:00:00");
        } catch (Exception e) {
            LOGGER.info("parsed wrong date format, " + e.getMessage());
        }

        TramNews newsEntry = new TramNews(timeStamp, "Stoerung", "Oberleitung beschaedigt", affectedLines);

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

    @Override
    public void printResponse() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getResponseFromWebsite() {
        // TODO Auto-generated method stub

    }

    @Override
    public void buildRequestUrl(UserSettings usersettings) {
        // TODO Auto-generated method stub

    }

}
