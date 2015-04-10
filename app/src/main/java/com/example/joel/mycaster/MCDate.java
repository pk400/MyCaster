package com.example.joel.mycaster;

import java.util.Calendar;

/**
 * Created by Joel on 4/9/2015.
 */
public class MCDate {
    Calendar c;

    public MCDate() {
        c = Calendar.getInstance();
    }

    public long getLastUpdatedTimer() {
        return c.get(Calendar.DATE);
    }

    public long timeNow() {
        return c.get(Calendar.MINUTE);
    }

    public String getTodaysDate() {
        StringBuilder todaysDateStr = new StringBuilder();

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek) {
            case 0: todaysDateStr.append("Saturday");  break;
            case 1: todaysDateStr.append("Sunday");    break;
            case 2: todaysDateStr.append("Monday");    break;
            case 3: todaysDateStr.append("Tuesday");   break;
            case 4: todaysDateStr.append("Wednesday"); break;
            case 5: todaysDateStr.append("Thursday");  break;
            case 6: todaysDateStr.append("Friday");    break;
        }

        todaysDateStr.append(", ");

        int month = c.get(Calendar.MONTH);
        switch(month) {
            case 0:  todaysDateStr.append("January");   break;
            case 1:  todaysDateStr.append("February");  break;
            case 2:  todaysDateStr.append("March");     break;
            case 3:  todaysDateStr.append("April");     break;
            case 4:  todaysDateStr.append("May");       break;
            case 5:  todaysDateStr.append("June");      break;
            case 6:  todaysDateStr.append("July");      break;
            case 7:  todaysDateStr.append("August");    break;
            case 8:  todaysDateStr.append("September"); break;
            case 9:  todaysDateStr.append("October");   break;
            case 10: todaysDateStr.append("November");  break;
            case 11: todaysDateStr.append("December");  break;
        }
        todaysDateStr.append(" " + c.get(Calendar.DATE));
        todaysDateStr.append(", " + c.get(Calendar.YEAR));

        return todaysDateStr.toString();
    }
}
