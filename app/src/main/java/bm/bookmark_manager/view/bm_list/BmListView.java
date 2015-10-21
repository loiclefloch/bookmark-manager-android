package bm.bookmark_manager.view.bm_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.List;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.model.Bookmark;
import bm.bookmark_manager.common.renderers.bookmark_renderer.BookmarkRenderer;
import bm.bookmark_manager.common.renderers.bookmark_renderer.BookmarkRendererBuilder;
import bm.bookmark_manager.common.tools.search.Search;
import bm.bookmark_manager.common.view.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BmListView extends BaseFragment
        implements BmListViewInterface {

    BmListPresenter presenter;
    RendererAdapter<Bookmark> adapter;

    // -- views

    @Bind(R.id.bm_list__list_view)
    ListView listView;
    @Bind(R.id.bm_list__search_view)
    SearchView searchView;
    @Bind(R.id.bm_list__swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    // --

    private int order;
    private int currentFilter;

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

        // -- Setup search view
        searchView.setOnQueryTextListener(searchOnQueryTextListener);
        searchView.setOnSearchClickListener(searchClickListener);


        // -- Setup refreshView
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        hideKeyboard();
        searchView.clearFocus(); // remove automatic keyboard focus
    }

    @Override
    public void onPause() {
        super.onPause();

        hideKeyboard();
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
        displayError(getString(R.string.error_when_loading_bookmarks));
    }

    @Override
    public void showListRefreshDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideListRefreshDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setCurrentFilter(int filter) {
        this.currentFilter = filter;
    }

    @Override
    public void setCurrentOrder(int order) {
        this.order = order;
    }

    // -- Display bookmark menu (at long click)

    void showBookmarkPopupMenu(final Bookmark bookmark) {

        final CharSequence menu[] = new CharSequence[]{
                getString(R.string.open_link),
                getString(R.string.preview).concat(" ").concat(bookmark.isReadable() ? "" : getString(R.string.bookmark_readable_content_empty_menu_indication)),
                getString(R.string.edit),
                getString(R.string.tags),
                getString(R.string.delete)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(bookmark.getTitle());
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: // open_link
                        presenter.openBookmarkLink(bookmark);
                        break;
                    case 1: // preview
                        presenter.previewBookmark(bookmark);
                        break;
                    case 2: // edit
                        presenter.editBookmark(bookmark);
                        break;
                    case 3: // tags
                        break;
                    case 4: // delete
                        break;
                }

            }
        });

        builder.show();
    }

    // -- Display filter menu
    void showFilterMenu() {

        final CharSequence menu[] = new CharSequence[]{
                getString(R.string.filter_by_name).concat(currentFilter == Search.Filter.NAME ? " X" : ""),
                getString(R.string.filter_by_date).concat(currentFilter == Search.Filter.DATE ? " X" : "")
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: // filter by name
                        presenter.filterByName();
                        break;
                    case 1: // filter by date
                        presenter.filterByDate();
                        break;
                }

            }
        });

        builder.show();
    }

    // -- Search listeners

    private final SearchView.OnQueryTextListener searchOnQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            presenter.searchSubmit(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            presenter.searchChange(newText);
            return false;
        }
    };

    /**
     * Call when the user submit the search input
     */
    private final View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (searchView.getQuery().length() == 0) {
                presenter.searchChange("");
                presenter.refresh();
            }
        }
    };

    // --- Events

    @OnClick(R.id.bm_list__bm_add_fab)
    void addNewBookmarkOnClick(View v) {
        presenter.presentAddNewBookmark();
    }

    BookmarkRenderer.OnBookmarkAction onBookmarkAction = new BookmarkRenderer.OnBookmarkAction() {
        @Override
        public void onBookmarkClicked(Bookmark bookmark) {
            presenter.openBookmark(bookmark);
        }

        @Override
        public void onBookmarkLongClicked(Bookmark bookmark) {
            showBookmarkPopupMenu(bookmark);
        }
    };

    @OnClick(R.id.bm_list__filter_btn)
    void onFilterBtm(View v) {
        showFilterMenu();
    }
}
