package com.sa.baseproject.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by altafhussain.shaikh on 4/21/2016.
 */
public class DateUtils {

    public static final String MMDDYYY = "MM/dd/yyyy";
    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    public static final String MMDDYYHHMM = "MM/dd/yyyy HH:mm";

    public static SimpleDateFormat dateFormateTwentyFourHour = new SimpleDateFormat(MMDDYYHHMM);

    public static final String HHMM12HOURS = "hh:mm aaa";
    public static final String simpleDateFormateMM = "MM/dd/yyyy hh:mm aa";
    public static final String simpleDateFormate24MM = "MM-dd-yyyy hh:mm aa";
    public static final String dateISOFormate = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public static SimpleDateFormat simpleDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //2016-05-03T09:45:15.724Z
    public static DateFormat twentyFourHrFormat = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
    public static DateFormat twelveHrFormat = new SimpleDateFormat("hh:mm a");
    public static SimpleDateFormat formateHHMMYYYhhmm = new SimpleDateFormat(simpleDateFormateMM);
    public static SimpleDateFormat formatterDash = new SimpleDateFormat("MM-dd-yyyy");
    public static SimpleDateFormat formatterSeconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatterDate12Hours = new SimpleDateFormat(simpleDateFormate24MM);
    public static SimpleDateFormat formatter12HoursSpace = new SimpleDateFormat(simpleDateFormateMM);
    public static SimpleDateFormat fomratterTimeDate=new SimpleDateFormat("hh:mm a MM/dd/yyyy");


    /**
     * Calendar objects are rather expensive: for heavy usage it's a good idea to use a single instance per thread
     * instead of calling Calendar.getInstance() multiple times. Calendar.getInstance() creates a new instance each
     * time.
     */
    public static final class DefaultCalendarThreadLocal extends ThreadLocal<Calendar> {
        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        }
    }

    private static ThreadLocal<Calendar> calendarThreadLocal = new DefaultCalendarThreadLocal();

    public static long getTimeForDay(int year, int month, int day) {
        return getTimeForDay(calendarThreadLocal.get(), year, month, day);
    }

    /**
     * @param calendar helper object needed for conversion
     */
    public static long getTimeForDay(Calendar calendar, int year, int month, int day) {
        calendar.clear();
        calendar.set(year, month - 1, day);
        return calendar.getTimeInMillis();
    }

    /**
     * Sets hour, minutes, seconds and milliseconds to the given values. Leaves date info untouched.
     */
    public static void setTime(Calendar calendar, int hourOfDay, int minute, int second, int millisecond) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
    }

    /**
     * Readable yyyyMMdd int representation of a day, which is also sortable.
     */
    public static int getDayAsReadableInt(long time) {
        Calendar cal = calendarThreadLocal.get();
        cal.setTimeInMillis(time);
        return getDayAsReadableInt(cal);
    }

    /**
     * Readable yyyyMMdd representation of a day, which is also sortable.
     */
    public static int getDayAsReadableInt(Calendar calendar) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return year * 10000 + month * 100 + day;
    }

    /**
     * Returns midnight of the given day.
     */
    public static long getTimeFromDayReadableInt(int day) {
        return getTimeFromDayReadableInt(calendarThreadLocal.get(), day, 0);
    }

    /**
     * @param calendar helper object needed for conversion
     */
    public static long getTimeFromDayReadableInt(Calendar calendar, int readableDay, int hour) {
        int day = readableDay % 100;
        int month = readableDay / 100 % 100;
        int year = readableDay / 10000;

        calendar.clear(); // We don't set all fields, so we should clear the calendar first
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);

        return calendar.getTimeInMillis();
    }

    public static int getDayDifferenceOfReadableInts(int dayOfBroadcast1, int dayOfBroadcast2) {
        long time1 = getTimeFromDayReadableInt(dayOfBroadcast1);
        long time2 = getTimeFromDayReadableInt(dayOfBroadcast2);

        // Don't use getDayDifference(time1, time2) here, it's wrong for some days.
        // Do float calculation and rounding at the end to cover daylight saving stuff etc.
        float daysFloat = (time2 - time1) / 1000 / 60 / 60 / 24f;
        return Math.round(daysFloat);
    }

    public static int getDayDifference(long time1, long time2) {
        return (int) ((time2 - time1) / 1000 / 60 / 60 / 24);
    }

    public static long addDays(long time, int days) {
        Calendar calendar = calendarThreadLocal.get();
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTimeInMillis();
    }

    public static void addDays(Calendar calendar, int days) {
        calendar.add(Calendar.DAY_OF_YEAR, days);
    }

    public static String convertIsoFormatetoString(String isoType, String formate) {
//        yyyy-mm-dd 'T' HH:MM:SS.mmm-HH:SS
        SimpleDateFormat orignalFormate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date converTedDate = null;
        try {
            converTedDate = orignalFormate.parse(isoType.substring(0, 24));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        String newFormat = formatter.format(converTedDate);
        return newFormat;
//        System.out.println(".....Date..."+newFormat);
    }

    public static String getnextYearDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.YEAR, 1); // to get previous year add -1
        Date nextYear = cal.getTime();
        return format.format(nextYear);
    }

    public static String convertWele24Formate(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = null;
        try {
            date = parseFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date);
    }

    public static String convertTwentyFourTwelve(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = parseFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date);
    }

    public static String convertDateTimeToDate(String time) {
        SimpleDateFormat displayFormat = new SimpleDateFormat(MMDDYYHHMM);
        SimpleDateFormat parseFormat = new SimpleDateFormat(MMDDYYY);
        Date date = null;
        try {
            date = displayFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return parseFormat.format(date);
    }

    public static String getCurrentDateTime(String simpleDateFormateMM) {
        String Datetime;
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        SimpleDateFormat dateformat = new SimpleDateFormat(simpleDateFormateMM);
        Datetime = dateformat.format(c.getTime());
        System.out.println(Datetime);
        return Datetime;
    }


    public static float hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (float) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    //1 minute = 60 seconds
    //1 hour = 60 x 60 = 3600
    //1 day = 3600 x 24 = 86400
    public static void printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

    }

    public static Date convertStringDate(String strDate, String formate) {
//        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.simpleDateFormateMM, Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        Date dateStart = null;
        try {
            dateStart = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(dateStart);
        return dateStart;
    }

    public static Date convertStringDateFormatter(String strDate, String parseFormate) {
//        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.simpleDateFormateMM, Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat(parseFormate);
        Date dateStart = null;
        try {
            dateStart = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(dateStart);
        return dateStart;
    }

    public static String convertTimeZondeDefault(String timeZone, String dateTime) {
//        String s = "2011-01-01 12:00:00";
        DateFormat df = new SimpleDateFormat(HHMM12HOURS);
        df.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date timestamp = null;
        try {
            timestamp = df.parse(dateTime);
            df.setTimeZone(TimeZone.getDefault());
            System.out.println(df.format(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df.format(timestamp);
    }

    public static String convertDateTimeZone(String timeZone, String dateTime, String formatter, String strOutPuttformatter) {
//        String s = "2011-01-01 12:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat(
                formatter);
        SimpleDateFormat outPutFormate = new SimpleDateFormat(
                strOutPuttformatter);
//        DateFormat df = new SimpleDateFormat(formatter);
        inputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date timestamp = null;

        try {
            timestamp = inputFormat.parse(dateTime);
//             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp);
            outPutFormate.setTimeZone(TimeZone.getDefault());
//            System.out.println(df.format(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String format = "";
        try {
            format = outPutFormate.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return format;
    }

    public static String convertTimetoGMT(String timeZone, String dateTime, String formatter, String strOutPuttformatter) {
//        String s = "2011-01-01 12:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat(
                formatter);
        SimpleDateFormat outPutFormate = new SimpleDateFormat(
                strOutPuttformatter);
//        DateFormat df = new SimpleDateFormat(formatter);
        inputFormat.setTimeZone(TimeZone.getDefault());
        Date timestamp = null;

        try {
            timestamp = inputFormat.parse(dateTime);
//             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp);
            outPutFormate.setTimeZone(TimeZone.getTimeZone(timeZone));
//            System.out.println(df.format(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String format = "";
        try {
            format = outPutFormate.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return format;
    }

    public static String dateFormateToOtherFormate(String dateTime, String formatter, String strOutPuttformatter) {
//        String s = "2011-01-01 12:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat(
                formatter);
        SimpleDateFormat outPutFormate = new SimpleDateFormat(
                strOutPuttformatter);
//        DateFormat df = new SimpleDateFormat(formatter);
//        inputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date timestamp = null;

        try {
            timestamp = inputFormat.parse(dateTime);
//             timestamp = df.parse(dateTime);
            outPutFormate.format(timestamp);
//            outPutFormate.setTimeZone(TimeZone.getDefault());
//            System.out.println(df.format(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String format = "";
        try {
            format = outPutFormate.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return format;
    }


    public static String convertTimeZondeDefaultTo24Hrs(String timeZone, String dateTime,
                                                        SimpleDateFormat dateFormat, SimpleDateFormat dateParse) {
//        String s = "2011-01-01 12:00:00";
        dateParse.setTimeZone(TimeZone.getTimeZone(timeZone));
        Date timestamp = null;
        try {
            timestamp = dateParse.parse(dateTime);
            dateParse.setTimeZone(TimeZone.getDefault());
            System.out.println(dateFormat.format(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(timestamp);
    }
}
