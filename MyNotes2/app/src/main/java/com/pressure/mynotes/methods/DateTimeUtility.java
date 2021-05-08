package com.pressure.mynotes.methods;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtility {

    private static final String MM_DD_YY_HH_MM = "MM-dd-yy, HH:mm";

    private static SimpleDateFormat format = new SimpleDateFormat(MM_DD_YY_HH_MM);

    public static String getCurrentDateTime(){
        return format.format(new Date());
    }
}
