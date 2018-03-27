package com.deusgmbh.xcusatio.data.usersettings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.deusgmbh.xcusatio.data.StorageUnit;
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
        this.add(new UserSettings("", dt, Sex.MALE, new Address("Strasse", "1", "68165", "Olafhausen")));
        return this;
    }

}
