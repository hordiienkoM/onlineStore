package com.hordiienko.onlinestore.exception.locale_message;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionMessages {
    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages", locale)
                .getString(messageKey);
    }
}
