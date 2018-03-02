package com.framgia.mysoundcloud.utils;

import com.framgia.mysoundcloud.BuildConfig;

/**
 * Created by sonng266 on 01/03/2018.
 */

public class StringUtil {
    public static String convertUrlFetchMusicGenre(String genre, int limit) {
        return Constant.BASE_URL + Constant.PARA_MUSIC_GENRE + genre +
                '&' +
                Constant.CLIENT_ID +
                '=' +
                BuildConfig.API_KEY +
                '&' +
                Constant.LIMIT +
                '=' +
                limit;
    }
}
