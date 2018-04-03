package com.deusgmbh.xcusatio.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.deusgmbh.xcusatio.context.Context;
import com.deusgmbh.xcusatio.context.wildcard.Wildcards;
import com.deusgmbh.xcusatio.data.excuses.Excuse;
import com.deusgmbh.xcusatio.data.scenarios.Scenario;
import com.deusgmbh.xcusatio.data.tags.Tag;

public class ExcuseFilter {

    private List<Predicate<Excuse>> filter;

    public ExcuseFilter() {
        filter = new ArrayList<>();
    }

    public ExcuseFilter byValidWildcard(Wildcards wildcards, Context context) {
        filter.add(excuse -> wildcards.isValidContext(excuse.getText(), context.getApiContext()));
        return this;
    }

    public ExcuseFilter byScenario(Scenario scenario) {
        filter.add(Excuse.byScenario(scenario));
        return this;
    }

    public ExcuseFilter byContextTags(List<Tag> contextTags) {
        filter.add(Excuse.hasContextTagsInExcuse(contextTags));
        filter.add(Excuse.hasExcuseTagsInContext(contextTags));
        return this;
    }

    public List<Excuse> apply(List<Excuse> excuses) {
        return excuses.stream()
                .filter(filter.stream()
                        .reduce(Predicate::and)
                        .orElse(t -> true))
                .collect(Collectors.toList());
    }
}
