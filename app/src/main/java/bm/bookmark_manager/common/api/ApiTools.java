package bm.bookmark_manager.common.api;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bm.bookmark_manager.common.Constants;

public class ApiTools {
    public static Date getDateWithString(String str) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.api.DATE_FORMAT);

        Date test = new Date();
        Logger.i(format.format(test));

        try {
            Date date = format.parse(str);
            Logger.d("Date: " + date.toString());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
