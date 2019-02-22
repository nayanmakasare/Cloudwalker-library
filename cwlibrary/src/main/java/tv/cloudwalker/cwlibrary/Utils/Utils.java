package tv.cloudwalker.cwlibrary.Utils;

import android.content.pm.PackageManager;

import java.util.ArrayList;

public class Utils
{
    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public static String getSeperatedValuesWithHeader(String seperator, String header, ArrayList<String> list) {
        String values = "";
        for (String value : list) {
            if (value.length() > 0) {
                values += value + seperator;
            } else {
                return "";
            }
        }
        values = values.replaceAll("[" + seperator + "] $", "");
        if (header.length() > 0) {
            return (header + " : " + values);
        } else {
            return values;
        }
    }
}
