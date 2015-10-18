package bm.bookmark_manager.view.bm_list;

import android.content.Context;
import android.content.Intent;

import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.view.bm_form.BmFormPresenter;
import bm.bookmark_manager.view.bm_form.BmFormView;
import bm.bookmark_manager.view.bm_view.BmViewPresenter;
import bm.bookmark_manager.view.bm_view.BmViewView;

public class BmListWireframe {

    void presentAddInterface(Context context) {
        Intent intent = new Intent(context, BmFormView.class);

        intent.putExtra(BmFormPresenter.EXTRA_MODE, BmFormPresenter.MODE_NEW);

        context.startActivity(intent);
    }

    void presentEditInterface(Context context, Bookmark bookmark) {
        Intent intent = new Intent(context, BmFormView.class);

        intent.putExtra(BmFormPresenter.EXTRA_MODE, BmFormPresenter.MODE_EDIT);
        intent.putExtra(BmFormPresenter.EXTRA_BOOKMARK, bookmark);

        context.startActivity(intent);
    }

    void presentBookmarkInterface(Context context, Bookmark bookmark) {
        Intent intent = new Intent(context, BmViewView.class);

        intent.putExtra(BmViewPresenter.EXTRA_BOOKMARK, bookmark);

        context.startActivity(intent);
    }

}
