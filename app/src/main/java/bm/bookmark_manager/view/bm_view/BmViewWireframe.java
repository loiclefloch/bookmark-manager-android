package bm.bookmark_manager.view.bm_view;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.view.bm_form.BmFormPresenter;
import bm.bookmark_manager.view.bm_form.BmFormView;
import bm.bookmark_manager.view.bm_preview.BmPreviewPresenter;
import bm.bookmark_manager.view.bm_preview.BmPreviewView;

public class BmViewWireframe {

    void presentEditInterface(Context context, Bookmark bookmark) {
        Intent intent = new Intent(context, BmFormView.class);

        intent.putExtra(BmFormPresenter.EXTRA_MODE, BmFormPresenter.MODE_EDIT);
        intent.putExtra(BmFormPresenter.EXTRA_BOOKMARK, bookmark);

        context.startActivity(intent);
    }

    void presentPreviewInterface(Context context, Bookmark bookmark) {
        Intent intent = new Intent(context, BmPreviewView.class);

        intent.putExtra(BmPreviewPresenter.EXTRA_BOOKMARK, bookmark);

        context.startActivity(intent);
    }
}
