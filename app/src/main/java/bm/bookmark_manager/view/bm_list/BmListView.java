package bm.bookmark_manager.view.bm_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.List;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.view.BaseFragment;
import bm.bookmark_manager.view.bm_list.renderer.BookmarkRenderer;
import bm.bookmark_manager.view.bm_list.renderer.BookmarkRendererBuilder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BmListView extends BaseFragment
        implements BmListViewInterface {

    BmListPresenter presenter;

    @Bind(R.id.bm_list__list_view)
    ListView listView;

    RendererAdapter<Bookmark> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        presenter = new BmListPresenter(this, getContext());

        View rootView = inflater.inflate(R.layout.fragment__bm_list, container, false);
        ButterKnife.bind(this, rootView);

        // -- Setup adapter

        AdapteeCollection<Bookmark> bookmarkCollection = new ListAdapteeCollection<>();
        adapter = new RendererAdapter<>(
                inflater,
                new BookmarkRendererBuilder(getContext(), onBookmarkAction),
                bookmarkCollection);
        listView.setAdapter(adapter);

        presenter.load();

        return rootView;
    }

    // --- BmListViewInterface

    @Override
    public void reloadEntries(List<Bookmark> bookmarkList) {
        adapter.clear();
        adapter.addAll(bookmarkList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoContentMessage() {
        if (getView() != null) {
            Snackbar.make(getView(), "No bookmarks", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void showUpcomingDisplayData() {

    }

    @Override
    public void showLoadingErrorMessage() {

        if (getView() != null) {
            Snackbar.make(getView(), getString(R.string.error_when_loading_bookmarks), Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
        }
    }

    void showBookmarkPopupMenu(final Bookmark bookmark) {

        final CharSequence menu[] = new CharSequence[] {
                getString(R.string.open_link),
                getString(R.string.preview),
                getString(R.string.edit)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(bookmark.getTitle());
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        presenter.openBookmarkLink(bookmark);
                        break;
                    case 1:
                        presenter.previewBookmark(bookmark);
                        break;
                    case 2:
                        presenter.editBookmark(bookmark);
                }

            }
        });
        builder.show();

    }

    // --- Events

    @OnClick(R.id.bm_list__bm_add_fab)
    void addNewBookmarkOnClick(View v) {
        presenter.presentAddNewBookmark();
    }

    BookmarkRenderer.OnBookmarkAction onBookmarkAction = new BookmarkRenderer.OnBookmarkAction() {
        @Override
        public void onBookmarkClicked(Bookmark bookmark) {

//            showBookmarkPopupMenu(bookmark);
        }

        @Override
        public void onBookmarkLongClicked(Bookmark bookmark) {
            showBookmarkPopupMenu(bookmark);
        }
    };

}
