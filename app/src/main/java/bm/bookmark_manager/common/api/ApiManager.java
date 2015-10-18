package bm.bookmark_manager.common.api;

import java.util.List;

import bm.bookmark_manager.BuildConfig;
import bm.bookmark_manager.common.Constants;
import bm.bookmark_manager.common.model.Bookmark;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

public class ApiManager {

    private static ApiManager instance;

    public BookmarkService bookmarkService;

    RestAdapter catalogRestAdapter;

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public ApiManager() {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(Constants.api.HEADER_API_KEY, Constants.api.API_TEST_KEY);
            }
        };

        catalogRestAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.api.URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel((BuildConfig.DEBUG ?
                        RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE))
                .build();

        bookmarkService = catalogRestAdapter.create(BookmarkService.class);
    }

    public void getBookmarks(RestCallback<List<Bookmark>> callback) {
        bookmarkService.get(callback);
    }

    public void postBookmark(Bookmark bookmark, RestCallback<Bookmark> callback) {
        bookmarkService.post(bookmark, callback);
    }

    public void putBookmark(Bookmark bookmark, RestCallback<Bookmark> callback) {
        bookmarkService.put(bookmark, callback);
    }

    // ----------- Services.

    public interface BookmarkService {
        @GET("/bookmark")
        void get(RestCallback<List<Bookmark>> callback);

        @POST("/bookmark")
        void post(Bookmark bookmark, RestCallback<Bookmark> callback);

        @PUT("/bookmark")
        void put(Bookmark bookmark, RestCallback<Bookmark> callback);
    }

}
