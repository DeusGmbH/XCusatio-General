package com.deusgmbh.xcusatio.api.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIService;
import com.deusgmbh.xcusatio.api.data.calendar.CalendarAPIConfig;
import com.deusgmbh.xcusatio.api.data.calendar.LectureEvent;
import com.deusgmbh.xcusatio.context.data.CalendarContext;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * 
 * @author Pascal.Schroeder@de.ibm.com, jan.leiblein@gmail.com
 *
 */

public class CalendarAPI extends APIService {
    private static final Logger LOGGER = Logger.getLogger(CalendarAPI.class.getName());

    private static final String JSONSTR_DATE_TIME = "dateTime";

    private static final Integer MAX_EVENTS = 10;

    public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
        CalendarAPIConfig.authorize();
        return new com.google.api.services.calendar.Calendar.Builder(CalendarAPIConfig.HTTP_TRANSPORT,
                CalendarAPIConfig.JSON_FACTORY, CalendarAPIConfig.credentials)
                        .setApplicationName(CalendarAPIConfig.APPLICATION_NAME)
                        .build();
    }

    private Event getCurrentLectureEvent() throws IOException {
        List<Event> events = getEvents();
        if (!events.isEmpty()) {
            Event firstEvent = events.get(0);
            return firstEvent;
        }
        return null;
    }

    private Date[] extractLectureTimes(Event firstEvent) throws ParseException {
        String startTime = firstEvent.getStart()
                .get(JSONSTR_DATE_TIME)
                .toString();
        startTime = startTime.substring(0, startTime.indexOf('+'));
        String endTime = firstEvent.getEnd()
                .get(JSONSTR_DATE_TIME)
                .toString();
        endTime = endTime.substring(0, endTime.indexOf('+'));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
        Date[] lectureTimes = new Date[2];

        lectureTimes[0] = sdf.parse(startTime);
        lectureTimes[1] = sdf.parse(endTime);

        return lectureTimes;
    }

    public List<Event> getEvents() throws IOException {
        com.google.api.services.calendar.Calendar service = getCalendarService();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events()
                .list("primary")
                .setMaxResults(MAX_EVENTS)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            LOGGER.info("No upcoming events found.");
        } else {
            LOGGER.info("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart()
                        .getDateTime();
                if (start == null) {
                    start = event.getStart()
                            .getDate();
                }
            }
        }
        return items;

    }

    private long extractMinutesPassed(Date startTime) {
        long now = System.currentTimeMillis();
        long startTimeMillis = startTime.getTime();
        return (((now - startTimeMillis) / 1000) / 60);
    }

    private long extractMinutesLeft(Date endTime) {
        long now = System.currentTimeMillis();
        long endTimeMillis = endTime.getTime();
        return (((endTimeMillis - now) / 1000) / 60);
    }

    @Override
    public CalendarContext get(UserSettings usersettings) throws IOException, JSONException, ParseException {
        if (CalendarAPIConfig.hasCredentials()) {
            Event currentEvent = this.getCurrentLectureEvent();
            if (currentEvent != null) {
                String lectureTitle = currentEvent.getSummary();
                Date startTime = extractLectureTimes(currentEvent)[0];
                Date endTime = extractLectureTimes(currentEvent)[1];

                LectureEvent currentLecture = new LectureEvent(lectureTitle, startTime, endTime);

                long minutesLeft = extractMinutesLeft(endTime);
                long minutesPassed = extractMinutesPassed(startTime);

                return new CalendarContext(currentLecture, minutesLeft, minutesPassed);
            }
        }
        return null;
    }

}