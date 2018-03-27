package com.deusgmbh.xcusatio.data.usersettings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.ExcuseVibeMode;
import com.deusgmbh.xcusatio.data.usersettings.UserSettings.Sex;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */

public class UserSettingsManager extends StorageUnit<UserSettings> {

    public UserSettingsManager() {
        super(UserSettings.class);
    }

    @Override
    public StorageUnit<UserSettings> addDefaultValues() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dt = LocalDate.parse("1989-11-09", dtf);
        this.add(new UserSettings().setAge(dt)
                .setSex(Sex.MALE)
                .setExcuseVibeMode(ExcuseVibeMode.AUTOMATIC)
                .setExcusesVibes(new ExcuseVibes(true, false, true))
                .setHome(new Address().setCity("Mannheim")
                        .setZip("68159")
                        .setStreetname("Akademiestr.")
                        .setStreetnum("6")));
        return this;
    }

}
