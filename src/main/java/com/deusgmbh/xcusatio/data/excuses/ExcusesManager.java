package com.deusgmbh.xcusatio.data.excuses;

import java.util.List;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.data.StorageUnit;
import com.deusgmbh.xcusatio.data.scenarios.ScenarioType;
import com.deusgmbh.xcusatio.data.tags.Tag;

/**
 * 
 * @author Tobias.Schmidt@de.ibm.com
 *
 */

public class ExcusesManager extends StorageUnit<Excuse> {

    public ExcusesManager() {
        super(Excuse.class);
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

    @Override
    public ExcusesManager addDefaultValues() {
        // TODO: Replace with correct default values, this is for
        this.add(new Excuse("Die Bahn kam zu spät", ScenarioType.LATE_ARRIVAL).addTag(Tag.TRAIN)
                .addTag(Tag.MALE)
                .addTag(Tag.FEMALE))
                .add(new Excuse("Der Regen hat unser Projekt zerstört", ScenarioType.DELAYED_SUBMISSION)
                        .addTag(Tag.RAINY)
                        .addTag(Tag.MALE)
                        .addTag(Tag.FEMALE));
        return this;
    }

}
