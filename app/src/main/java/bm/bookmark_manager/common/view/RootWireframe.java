package bm.bookmark_manager.common.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import bm.bookmark_manager.view.bm_list.BmListView;

public class RootWireframe {

    private static RootWireframe instance;

    public static RootWireframe getInstance() {
        if (instance == null) {
            instance = new RootWireframe();
        }
        return instance;
    }

    public static void presentHome(Context context) {
        Intent intent = new Intent(context, BmListView.class);
        context.startActivity(intent);
    }

    public static void openLinkInBrowser(Context context, String url) {
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(viewIntent);
    }
}