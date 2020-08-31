package com.ecommerce.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeAgo {

    public int howManyDaysPassed(String startDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");

        LocalDateTime localDateTime = LocalDateTime.now();
        String now = localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth();

        Date d1 = sdf.parse(startDate);
        Date d2 = sdf.parse(now);

        long difference_In_Time = d2.getTime() - d1.getTime();
        long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24))
                % 365;

        return (int) difference_In_Days;
    }
}
