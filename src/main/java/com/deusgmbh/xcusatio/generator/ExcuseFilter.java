package com.deusgmbh.xcusatio.generator;

import java.util.List;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.wildcard.WildcardTransformers;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.tags.Tag;

public class ExcuseFilter {

    private List<Excuse> excuses;

    public ExcuseFilter(List<Excuse> excuses) {
        this.excuses = excuses;
    }

    public ExcuseFilter byValidWildcard(WildcardTransformers wildcards, Context context) {
        excuses = excuses.stream()
                .filter(excuse -> wildcards.isValidContext(excuse.getText(), context.getApiContext()))
                .collect(Collectors.toList());
        return this;
    }

    public ExcuseFilter byScenario(Scenario scenario) {
        excuses = excuses.stream()
                .filter(Excuse.byScenario(scenario))
                .collect(Collectors.toList());
        return this;
    }

    public ExcuseFilter byContextTags(List<Tag> contextTags) {
        excuses = excuses.stream()
                .filter(Excuse.hasContextTagsInExcuse(contextTags))
                .collect(Collectors.toList());
        excuses = excuses.stream()
                .filter(Excuse.hasExcuseTagsInContext(contextTags))
                .collect(Collectors.toList());
        return this;
    }

    public List<Excuse> get() {
        return excuses;
    }
}
