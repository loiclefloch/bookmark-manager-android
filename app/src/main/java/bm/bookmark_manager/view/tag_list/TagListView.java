package bm.bookmark_manager.view.tag_list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.renderers.tag_renderer.TagRenderer;
import bm.bookmark_manager.common.renderers.tag_renderer.TagRendererBuilder;
import bm.bookmark_manager.common.view.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagListView extends BaseFragment
        implements TagListViewInterface {

    // -- views
    @Bind(R.id.tag_list__search_view)
    SearchView searchView;
    @Bind(R.id.tag_list__list_view)
    ListView listView;
    @Bind(R.id.tag_list__swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.tag_list__tag_add_fab)
    FloatingActionButton tagAddFab;

    // --
    TagListModuleInterface presenter;
    RendererAdapter<Tag> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        presenter = new TagListPresenter(this, getContext());

        View rootView = inflater.inflate(R.layout.fragment__tag_list, container, false);
        ButterKnife.bind(this, rootView);

        // -- Setup adapter

        AdapteeCollection<Tag> tagCollection = new ListAdapteeCollection<>();
        adapter = new RendererAdapter<>(
                inflater,
                new TagRendererBuilder(getContext(), onTagAction),
                tagCollection);
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

    // --- TagListViewInterface

    @Override
    public void reloadEntries(List<Tag> tagList) {
        adapter.clear();
        adapter.addAll(tagList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoContentMessage() {
        if (getView() != null) {
            Snackbar.make(getView(), "No tags", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void showUpcomingDisplayData() {

    }

    @Override
    public void showLoadingErrorMessage() {
        displayError(getString(R.string.error_when_loading_tags));
    }

    @Override
    public void showListRefreshDialog() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideListRefreshDialog() {
        swipeRefreshLayout.setRefreshing(false);
    }

    // -- Display tag menu (at long click)

    void showTagPopupMenu(final Tag tag) {

        final CharSequence menu[] = new CharSequence[]{
                getString(R.string.edit),
                getString(R.string.delete)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(tag.getName());
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: // edit
                        presenter.presentEditTag(tag);
                        break;
                    case 1: // delete
                        break;
                }

            }
        });

        builder.show();
    }

    // -- Display filter menu
    void showFilterMenu() {

        final CharSequence menu[] = new CharSequence[]{
                getString(R.string.filter_by_name),
                getString(R.string.filter_by_date)
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

    @OnClick(R.id.tag_list__tag_add_fab)
    void addNewTagOnClick(View v) {
        presenter.presentAddNewTag();
    }

    TagRenderer.OnTagAction onTagAction = new TagRenderer.OnTagAction() {
        @Override
        public void onTagClicked(Tag tag) {
            // TODO: What action do when click on tag ?
        }

        @Override
        public void onTagLongClicked(Tag tag) {
            showTagPopupMenu(tag);
        }
    };

    @OnClick(R.id.tag_list__filter_btn)
    void filterOnClick(View v) {
        showFilterMenu();
    }
}

