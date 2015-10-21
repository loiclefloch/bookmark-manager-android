package bm.bookmark_manager.common.tools;

import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import bm.bookmark_manager.common.model.Tag;


public class Tools {

    public static class TagTools {

        public static Tag findTagOnList(List<Tag> selectedList, Tag tag) {
            for (Tag t : selectedList) {

                if (Tools.equals(t.getId(), tag.getId())) {
                    return t;
                }
            }
            return null;
        }


        public static List<Tag> cloneTagList(List<Tag> list) {
            List<Tag> clone = new ArrayList<Tag>(list.size());
            for (Tag item: list) clone.add(item.copy(Tag.class));
            return clone;
        }

    }

    public static class window {

        public static void changeStatusBarColor(Window window, int color) {

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(color);
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static Boolean checkBit(long data, long bit) {
        return (data & bit) == bit;
    }
}
