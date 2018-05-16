package com.example.hdh.smgproject;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hwangdahyeon on 2018. 5. 16..
 */

public class DateUtil {
    public static final String DATEFORMAT_DEFAULT = "yyyy년 MM월 dd일 HH시 mm분";

    /** [현재 시간]  **/
    public static Date getCurrentTime() {
        long now = System.currentTimeMillis();
        return new Date(now);
    }
    /**
     * [String에서 Date로 변환]
     *
     * 주의 : 첫 번째 인자 date의 형식과 두 번째 인자 dateFormat의 형식이 맞아야한다.
     *        date의 형식이 yyyy-mm-dd이면 dateFormat도 yyyy-mm-dd이어야한다.
     **/
    public static Date convertStringToDate(String date) {
        // 기본 format : yyyy(년도)-MM(월)-dd(일) HH(시간):mm(분):ss(초)
        return convertStringToDate(date, DATEFORMAT_DEFAULT);
    }
    public static Date convertStringToDate(String date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *  [Date에서 String로 변환]
     *
     *  주의 : 첫 번째 인자 date의 형식과 두 번째 인자 dateFormat의 형식이 맞아야한다.
     *        date의 형식이 yyyy-mm-dd이면 dateFormat도 yyyy-mm-dd이어야한다.
     **/
    public static String convertDateToString(Date date) {
        // 기본 format : yyyy(년도)-MM(월)-dd(일) HH(시간):mm(분):ss(초)
        return convertDateToString(date, DATEFORMAT_DEFAULT);
    }
    public static String convertDateToString(Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    /**
     *  [날짜 비교하기]
     *  first : 비교될 날짜
     *  second : 비교할 날짜
     *  dateFormat : 날짜 형식
     *
     *  return value
     *  true : first가 second보다 이후 일 경우
     *  false : second가 first보다 이후 일 경우 또는 Exception일 경우
     **/
    public static boolean compareDate(String first, String second) {
        // 기본 format : yyyy(년도)-MM(월)-dd(일) HH(시간):mm(분):ss(초)
        return compareDate(first, second, DATEFORMAT_DEFAULT);
    }
    public static boolean compareDate(String first, String second, String dateFormat) {
        Date firstDate = convertStringToDate(first, dateFormat);
        Date secondDate = convertStringToDate(second, dateFormat);
        if(!TextUtils.isEmpty(first) && !TextUtils.isEmpty(second)) {
            return firstDate.after(secondDate);
        } else {
            return false;
        }
    }

    public static String convertDateFormat(String dateStr, String originFormat, String convertFormat){
        String result = null;
        SimpleDateFormat originDateFormat = new SimpleDateFormat(originFormat);
        SimpleDateFormat convertDateFormat = new SimpleDateFormat(convertFormat);
        try {
            Date originDate = originDateFormat.parse(dateStr);
            result = convertDateFormat.format(originDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }
}
