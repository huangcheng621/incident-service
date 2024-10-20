package com.hsbc.incident.shared.utils;

import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {

    public static ZonedDateTime dateToZonedDateTime(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), java.time.ZoneId.systemDefault());
    }
}
