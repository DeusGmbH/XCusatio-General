package com.deusgmbh.xcusatio.data.tags;

import java.util.ArrayList;
import java.util.List;

public class TagTranslator {
    public static String toGerman(TagText tagText) {
        switch (tagText) {
        default:
            return tagText.toString();
        }
    }

    private static String toGerman(Tag tag) {
        return toGerman(tag.getText());
    }

    public static List<String> toGerman(List<Tag> tagList) {
        List<String> translatedTagList = new ArrayList<String>();
        tagList.stream()
                .forEach(tag -> {
                    translatedTagList.add(toGerman(tag));
                });
        return translatedTagList;
    }

}
