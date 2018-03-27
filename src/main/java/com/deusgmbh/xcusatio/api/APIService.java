package com.deusgmbh.xcusatio.api;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public abstract class APIService {
    public abstract <T extends Object> T get(UserSettings usersettings);
}