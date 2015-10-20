package bm.bookmark_manager.common.api;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiTools {
    public static Date getDateWithString(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(str);
            Logger.d("Date: ", date.toString());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
