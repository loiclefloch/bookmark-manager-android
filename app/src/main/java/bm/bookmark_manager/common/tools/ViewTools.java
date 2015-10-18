package bm.bookmark_manager.common.tools;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bm.bookmark_manager.common.model.Tag;

public class ViewTools {

    public static void displayError(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public static void displayToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_LONG)
                .show();
    }

    public static void renderTagList(Context context, GridLayout tagListLayout, List<Tag> tags) {
        tagListLayout.removeAllViews();

        if (tags.isEmpty()) {
            tagListLayout.setVisibility(View.GONE);
        } else {
            tagListLayout.setVisibility(View.VISIBLE);
        }

        int i = 0;

        int size = tags.size();

        if (size > 0) {
            tagListLayout.setColumnCount(size);
            for (Tag tag : tags) {

                TextView tagView = new TextView(context);
                tagView.setText(tag.getName());
                tagView.setTextColor(Color.WHITE);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.height = GridLayout.LayoutParams.WRAP_CONTENT;
                param.width = GridLayout.LayoutParams.WRAP_CONTENT;
                param.rightMargin = 10;
                param.columnSpec = GridLayout.spec(i);
                param.rowSpec = GridLayout.spec(0);
                tagView.setLayoutParams(param);

                tagView.setPadding(8, 2, 8, 2);
                tagView.setBackgroundColor(Color.parseColor(tag.getColor()));

                tagListLayout.addView(tagView);
                ++i;
            }
        }
    }
}
