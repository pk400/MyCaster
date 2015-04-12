package com.example.joel.mycaster;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by Joel on 4/9/2015.
 */
public class MCDate {
    Calendar c;

    public MCDate() {
        c = Calendar.getInstance();
    }

    public String getTodaysDate() {
        StringBuilder todaysDateStr = new StringBuilder();

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek) {
            case Calendar.SATURDAY:     todaysDateStr.append("Saturday");  break;
            case Calendar.SUNDAY:       todaysDateStr.append("Sunday");    break;
            case Calendar.MONDAY:       todaysDateStr.append("Monday");    break;
            case Calendar.TUESDAY:      todaysDateStr.append("Tuesday");   break;
            case Calendar.WEDNESDAY:    todaysDateStr.append("Wednesday"); break;
            case Calendar.THURSDAY:     todaysDateStr.append("Thursday");  break;
            case Calendar.FRIDAY:       todaysDateStr.append("Friday");    break;
        }

        todaysDateStr.append(", ");

        int month = c.get(Calendar.MONTH);
        switch(month) {
            case Calendar.JANUARY:      todaysDateStr.append("January");   break;
            case Calendar.FEBRUARY:     todaysDateStr.append("February");  break;
            case Calendar.MARCH:        todaysDateStr.append("March");     break;
            case Calendar.APRIL:        todaysDateStr.append("April");     break;
            case Calendar.MAY:          todaysDateStr.append("May");       break;
            case Calendar.JUNE:         todaysDateStr.append("June");      break;
            case Calendar.JULY:         todaysDateStr.append("July");      break;
            case Calendar.AUGUST:       todaysDateStr.append("August");    break;
            case Calendar.SEPTEMBER:    todaysDateStr.append("September"); break;
            case Calendar.OCTOBER:      todaysDateStr.append("October");   break;
            case Calendar.NOVEMBER:     todaysDateStr.append("November");  break;
            case Calendar.DECEMBER:     todaysDateStr.append("December");  break;
        }
        todaysDateStr.append(" " + c.get(Calendar.DATE));
        todaysDateStr.append(", " + c.get(Calendar.YEAR));

        return todaysDateStr.toString();
    }
}
