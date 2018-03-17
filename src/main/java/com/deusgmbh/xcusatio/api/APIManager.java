package com.deusgmbh.xcusatio.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deusgmbh.xcusatio.data.usersettings.UserSettings;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class APIManager {
    List<APIService> apis;

    public APIManager(List<APIService> apis) {
        this.apis = apis;
    }

    public Map<APIService, Object> executeAPICalls(UserSettings userSettings) {
        // TODO: Maybe some performance optimization is necessary here:
        // investigate Futures and completableFutures

        Map<APIService, Object> results = new HashMap<APIService, Object>();
        apis.stream().forEach(api -> results.put(api, api.executeCall(userSettings)));
        return results;
    }

}
