package bm.bookmark_manager.common.view;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.view.bm_list.BmListView;

public class RootWireframe {

    private static RootWireframe instance;

    public static RootWireframe getInstance() {
        if (instance == null) {
            instance = new RootWireframe();
        }
        return instance;
    }

    public void presentHome(Context context) {
        Intent intent = new Intent(context, BmListView.class);
        context.startActivity(intent);
    }
}