package com.framgia.mysoundcloud.utils;

/**
 * Created by sonng266 on 28/02/2018.
 */

public final class Constant {

    private Constant(){
    }

    public static final String BASE_URL = "https://api-v2.soundcloud.com/";
    public static final String PARA_MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String PARA_OFFSET = "offset";
    public static final String CLIENT_ID = "client_id";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final int READ_TIME_OUT = 10000; /* milliseconds */
    public static final int CONNECT_TIME_OUT = 15000; /* milliseconds */
    public static final int OFFSET_DEFAULT = 0;
    public static final int LIMIT_DEFAULT = 10;
    public static final String[] MUSIC_GENRES = {"all-music", "all-audio",
            "alternativerock", "ambient", "classical", "country"};

    public static final String BREAK_LINE = "\n";
    public static final String LIMIT = "limit";
}
