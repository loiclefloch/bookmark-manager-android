package bm.bookmark_manager.view.tag_list;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import bm.bookmark_manager.common.api.RestCallback;
import bm.bookmark_manager.common.api.RestError;
import bm.bookmark_manager.common.model.Tag;
import bm.bookmark_manager.common.tools.search.Search;
import bm.bookmark_manager.common.view.Presenter;

public class TagListPresenter
        extends Presenter<TagListViewInterface,TagListWireframe, TagListInteractor>
        implements TagListModuleInterface {

    public static final String EXTRA_BOOKMARK = "EXTRA_BOOKMARK";

    protected Tag tag;
    private List<Tag> tagsList;
    Search search = new Search();

    public TagListPresenter(TagListViewInterface view, Context context) {
        initPresenter(context, view, new TagListWireframe(), new TagListInteractor());
        changeSearchType(Search.Filter.NAME, false);
    }

    @Override
    public void searchChange(String query) {
        search.setQuery(query);
        update();
    }

    @Override
    public void presentAddNewTag() {
        wireframe.presentAddInterface(context);
    }

    @Override
    public void presentEditTag(Tag tag) {
        wireframe.presentEditInterface(context, tag);
    }

    @Override
    public void searchSubmit(String query) {
        search.setQuery(query);
        view.hideKeyboard();
        update();
    }

    @Override
    public void load() {
        view.showLoading();
        refresh();
    }

    @Override
    public void filterByName() {
        changeSearchType(Search.Filter.NAME);
    }

    @Override
    public void filterByDate() {
        changeSearchType(Search.Filter.DATE);
    }

    private void changeSearchType(int type) {
        changeSearchType(type, true);
    }

    private void changeSearchType(int type, boolean shouldUpdate) {
        search.setFilter(type);
        view.setCurrentFilter(type);
        if (shouldUpdate) {
            update();
        }
    }

    @Override
    public void refresh() {
        view.showListRefreshDialog();
        interactor
                .getTags(new RestCallback<List<Tag>>() {
                    @Override
                    public void success(List<Tag> tagsResponse) {
                        tagsList = tagsResponse;
                        update();
                        view.hideListRefreshDialog();
                        view.hideLoading();
                    }

                    @Override
                    public void failure(RestError restError) {
                        view.hideListRefreshDialog();
                        view.hideLoading();

                        Logger.e("Error while getting data: ".concat(String.valueOf(restError)));

                        view.showLoadingErrorMessage();
                        view.showNoContentMessage();

                        update();
                    }
                });
    }

    public void update() {
        List<Tag> tags;

        if (null == this.tagsList) {
            view.showNoContentMessage();
            return ;
        }
        tags = (List<Tag>) search.search(this.tagsList);

        view.reloadEntries(tags);
    }

}
