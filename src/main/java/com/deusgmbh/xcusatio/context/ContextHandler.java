package com.deusgmbh.xcusatio.context;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;

import com.deusgmbh.xcusatio.api.APIManager;
import com.deusgmbh.xcusatio.context.data.CalendarContext;
import com.deusgmbh.xcusatio.data.lecturer.Lecturer;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;

/**
 * 
 * @author tobias.schmidt@de.ibm.com
 *
 */
public class ContextHandler {

    /**
     * This method creates a new context object out of data from userSettings
     * the lecturers and the data from the apis specified in the scenario object
     * 
     * @param userSettings
     * @param lecturers
     * @param scenario
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws JSONException
     */
    public Context buildContext(UserSettings userSettings, List<Lecturer> lecturers, Scenario scenario) {
        Context context = new Context().setAge(userSettings.getBirthdate())
                .setSex(userSettings.getSex())
                .setExcuseVibeMode(userSettings.getExcuseVibeMode());

        context.setApiContext(new APIManager(scenario).getAPIData(userSettings));

        if (context.getApiContext() != null) {

            CalendarContext calendar = context.getApiContext()
                    .getCalendar();

            if (calendar != null && calendar.getLectureEvent() != null) {
                String lectureTitle = calendar.getLectureEvent()
                        .getLectureTitle();
                lecturers.stream()
                        .filter(Lecturer.hasLecture(lectureTitle))
                        .findAny()
                        .ifPresent(lecturer -> context.setLecturer(lecturer));
            }

            if (context.getLecturer() == null) {
                context.getApiContext()
                        .setCalendar(null);
            }
        }

        if (context.getLecturer() == null || context.getExcuseVibeMode()
                .equals(ExcuseVibeMode.MANUALLY)) {
            context.setManuellExcusesVibes(userSettings.getExcuseVibes());
        }

        return context;
    }

}
