package com.deusgmbh.xcusatio.data.excuses;

import java.util.List;

import com.deusgmbh.xcusatio.data.StorageUnit;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */
public class ExcusesManager extends StorageUnit<Excuse> {

    /**
     * @param amount
     *            the maximal amount of excuses to return
     * @returns the most recently used excuses.
     */
    public List<Excuse> getRecentlyUsed(int amount) {
        return null;
    }
}
