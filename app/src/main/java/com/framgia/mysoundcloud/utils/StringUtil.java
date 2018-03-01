package com.framgia.mysoundcloud.utils;

import com.framgia.mysoundcloud.BuildConfig;

/**
 * Created by sonng266 on 01/03/2018.
 */

public class StringUtil {
    public static String convertUrlFetchMusicGenre(String genre, int limit, int offset) {
        return Constant.BASE_URL + Constant.PARA_MUSIC_GENRE + genre +
                '&' +
                Constant.CLIENT_ID +
                '=' +
                BuildConfig.API_KEY +
                '&' +
                Constant.LIMIT +
                '=' +
                limit +
                '&' +
                Constant.PARA_OFFSET +
                '=' +
                offset;
    }

    public static String parseMilliSecondsToTimer(long milliseconds) {
        String parseTimer = "";
        String secondsString = "";

        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            parseTimer = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        parseTimer = parseTimer + minutes + ":" + secondsString;
        return parseTimer;
    }
}
