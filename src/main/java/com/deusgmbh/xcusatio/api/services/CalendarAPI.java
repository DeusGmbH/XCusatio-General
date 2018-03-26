package com.deusgmbh.xcusatio.api.services;

import java.util.LinkedList;
import java.util.List;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class CalendarAPI extends APIService {

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

        CalendarContext calendarContext = new CalendarContext("Software Engineering 2", kruse, "09.00", "12.15", 190,
                15);
        return calendarContext;
    }

    // public static void main(String[] args) {
    // UserSettings userSettings = new UserSettings(null, 0, null, null, null,
    // null, null);
    // CalendarAPI calendarAPI = new CalendarAPI();
    //
    // CalendarContext calendarContext = calendarAPI.get(userSettings);
    // calendarContext.printContextContent();
    // }

}
