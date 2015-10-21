package bm.bookmark_manager.common.view.ListPicker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.pedrogomez.renderers.RendererAdapter;

import java.util.ArrayList;
import java.util.List;

import bm.bookmark_manager.R;
import bm.bookmark_manager.common.tools.search.Search;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Picker<T extends Search.Sortable> extends DialogFragment {

    public static final String DIALOG_TITLE = "dialogTitle";

    // -- views
    @Bind(R.id.picker_search)
    SearchView searchView;

    @Bind(R.id.picker_list_view)
    ListView listView;

    // --
    protected Search search = new Search();
    protected RendererAdapter<T> adapter;
    protected List<T> allList;
    protected List<T> selectedList = new ArrayList<>();

    protected PickerListener<T> listener;


    // -- Public functions

    public void setAdapter(RendererAdapter<T> adapter) {
        listView.setAdapter(adapter);
    }

    public void setListener(PickerListener<T> listener) {
        this.listener = listener;
    }

    public void setList(List<T> list) {
        allList = list;
        updateAdaptorWithList(list);
    }

    private void updateAdaptorWithList(List<T> list) {
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    protected void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public ListView getListView() {
        return listView;
    }

    // -- View functions

    /**
     * Create view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.partial__picker, container);

        ButterKnife.bind(this, view);

        // Set dialog title if show as dialog
        Bundle args = getArguments();
        if (args != null) {
            String dialogTitle = args.getString(DIALOG_TITLE);
            getDialog().setTitle(dialogTitle);

            int width = getResources().getDimensionPixelSize(
                    R.dimen.partial__picker__dialog_width);
            int height = getResources().getDimensionPixelSize(
                    R.dimen.partial__picker__dialog_height);
            getDialog().getWindow().setLayout(width, height);
        }

        searchView.setOnQueryTextListener(searchOnQueryTextListener);

        return view;
    }

    private final android.widget.SearchView.OnQueryTextListener searchOnQueryTextListener = new android.widget.SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            search(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            search(query);
            return true;
        }
    };


    void search(String query) {
        search.setQuery(query);
        updateAdaptorWithList((List<T>) search.search(allList));
    }

    @OnClick(R.id.picker_validate_btn)
    void onValidate() {
        listener.onValidated(selectedList);
        dismiss();
    }

}
