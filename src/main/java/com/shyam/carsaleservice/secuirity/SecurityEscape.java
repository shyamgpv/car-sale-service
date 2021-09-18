package com.shyam.carsaleservice.secuirity;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Calendar;
import java.util.Locale;


public class SecurityEscape {
    public static String cleanIt(String arg0) {
        return Jsoup.clean(
                StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeEcmaScript (StringUtils.replace(arg0, "'", "''")))
                , Whitelist.basic()).toLowerCase();
    }

    public static Double cleanDouble(Double arg0){
        arg0 = arg0 == Double.NaN ? 0.0 : arg0;
        arg0 = arg0 == Double.NEGATIVE_INFINITY ? (0.0) : arg0;
        arg0= arg0 == Double.POSITIVE_INFINITY ? (0.0) : arg0;

        return arg0;
    }
    public static Integer cleanInt(Integer arg0) {
        try {

            // If the user enters a valid integer there will be no problem
            // parsing it. Otherwise, the program will throw a 'NumberFormatException'.
            Integer result = Integer.parseInt(arg0+"");

            int year = Calendar.getInstance().get(Calendar.YEAR);
            result = result > year ? year : result; // if greater than current year return current year

            // If the parsing has been successful,
            //we break the while loop and return the result
            return result;
        } catch (NumberFormatException nfe) {

            // If the user did not enter a valid integer we will just
            return 1900;
        }
    }

    public static Long cleanLong(Long arg0) {
        try {

            // If the user enters a valid integer there will be no problem
            // parsing it. Otherwise, the program will throw a 'NumberFormatException'.
            Long result = Long.parseLong(arg0+"");

            return result;
        } catch (NumberFormatException nfe) {

            // If the user did not enter a valid integer we will just
            return Long.valueOf(0);
        }
    }

}
