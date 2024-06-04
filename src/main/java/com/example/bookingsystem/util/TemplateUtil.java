package com.example.bookingsystem.util;

import java.util.Map;

public class TemplateUtil {

    public static String replacePlaceholders(String template, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            template = template.replace("!!!" + entry.getKey() + "!!!", entry.getValue());
        }
        return template;
    }
}
