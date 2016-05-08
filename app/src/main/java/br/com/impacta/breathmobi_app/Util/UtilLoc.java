package br.com.impacta.breathmobi_app.Util;

/**
 * Created by TGarbulha on 07/05/2016.
 */
public final class UtilLoc {
    private static String LATITUDE;
    private static String LONGITUDE;

    public UtilLoc() {
    }

    static public String getLatitude(String lat) { return LATITUDE; }

    static public String getLongitude(String lon) {
        return LONGITUDE;
    }

}
