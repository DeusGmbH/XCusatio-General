package com.deusgmbh.xcusatio.api.services;

import java.util.List;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.context.wildcard.CalendarContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.tags.Tag;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

public class CalendarAPI extends APIService {

    @Override
    public CalendarContext get(UserSettings usersettings) {

        List<String> lecturesRead = null;
        lecturesRead.add("Software Engineering ");
        lecturesRead.add("Programmieren in C");

        List<Tag> tagList = null;
        tagList.add(Tag.MALE);
        tagList.add(Tag.AGE_UNDER_50);
        tagList.add(Tag.FUNNY);

        Lecturer kruse = new Lecturer(null, null, null);
        kruse.setLectures(lecturesRead);
        kruse.setName("Eckard Kruse");
        kruse.setTags(tagList);

        CalendarContext calendarContext = new CalendarContext("Software Engineering 2", kruse, "9:00 Uhr", "12:15 Uhr",
                190, 15);
        return calendarContext;
    }

    // TODO: convert int to string for each time detail, e.g. minutesLeft = 11 -->
    // "11 Minuten" / 1 --> "1 Minute"

}
