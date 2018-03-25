package com.deusgmbh.xcusatio.data.excuses;

import java.util.List;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.StorageUnit;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */

public class ExcusesManager extends StorageUnit<Excuse> {

    public ExcusesManager(Class<Excuse> parameterType) {
        super(parameterType);
    }

    /**
     * @param amount
     *            the maximal amount of excuses to return
     * @returns the most recently used excuses.
     */
    public List<Excuse> getMostRecentlyUsed(int amount) {
        return this.get()
                .stream()
                .sorted(Excuse.byLastUsed)
                .limit(amount)
                .collect(Collectors.toList());
    }
}
