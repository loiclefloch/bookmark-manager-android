package bm.bookmark_manager.view.bm_list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import bm.bookmark_manager.view.bm_form.BmFormPresenter;
import bm.bookmark_manager.view.bm_form.BmFormView;

public class BmListWireframe {

    void presentAddInterface(Context context) {
        Intent intent = new Intent(context, BmFormView.class);

        intent.putExtra(BmFormPresenter.EXTRA_MODE, BmFormPresenter.MODE_NEW);

        context.startActivity(intent);
    }

    void presentEditInterface(Context context) {
        Intent intent = new Intent(context, BmFormView.class);

        intent.putExtra(BmFormPresenter.EXTRA_MODE, BmFormPresenter.MODE_EDIT);

        context.startActivity(intent);
    }

    void openLinkInBrowser(Context context, String url) {
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        context.startActivity(viewIntent);
    }

}
