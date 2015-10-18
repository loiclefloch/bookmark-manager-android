package bm.bookmark_manager.common.tools;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class ViewTools {

    public static void displayError(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }

    public static void displayToast(Context context, String title) {
        Toast
                .makeText(context, title, Toast.LENGTH_LONG)
                .show();
    }

}
