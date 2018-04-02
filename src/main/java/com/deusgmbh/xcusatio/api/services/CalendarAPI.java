package com.deusgmbh.xcusatio.api.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class CalendarAPI extends APIService {
    private final static Logger LOGGER = Logger.getLogger(CalendarAPI.class.getName());

    @Override
    public CalendarContext get(UserSettings usersettings) {

        List<String> lecturesRead = new LinkedList<>();
        lecturesRead.add("Software Engineering ");
        lecturesRead.add("Programmieren in C");

        List<Tag> tagList = new LinkedList<>();
        tagList.add(Tag.MALE);
        tagList.add(Tag.AGE_UNDER_50);
        tagList.add(Tag.FUNNY);

        Lecturer kruse = new Lecturer(null, null, null);
        kruse.setLectures(lecturesRead);
        kruse.setName("Eckard Kruse");
        kruse.setTags(tagList);

        String dateFormat = "MM/dd/yyyy hh:mm:ss";
        Date startTime = null, endTime = null;
        try {
            startTime = new SimpleDateFormat(dateFormat).parse("03/27/2018 09:00:00");
            endTime = new SimpleDateFormat(dateFormat).parse("03/27/2018 12:15:00");
        } catch (Exception e) {
            LOGGER.info("parsed wrong date format, " + e.getMessage());
        }

        CalendarContext calendarContext = new CalendarContext("Software Engineering 2", kruse, startTime, endTime, 190,
                15);
        return calendarContext;
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