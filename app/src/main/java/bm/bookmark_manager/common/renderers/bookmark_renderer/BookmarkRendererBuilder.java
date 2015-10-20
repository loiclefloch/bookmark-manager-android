package bm.bookmark_manager.common.renderers.bookmark_renderer;

import android.content.Context;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import bm.bookmark_manager.common.model.Bookmark;

public class BookmarkRendererBuilder extends RendererBuilder<Bookmark> {

    @Inject
    public BookmarkRendererBuilder(Context context, BookmarkRenderer.OnBookmarkAction onBookmarkAction) {
        Collection<Renderer<Bookmark>> prototypes = getPrototypes(context, onBookmarkAction);
        setPrototypes(prototypes);
    }

    /**
     * Method to declare Bookmark-BookmarkRenderer mapping.
     * Normal bookmarks will be rendered using NormalBookmarkRenderer.
     *
     * @param content used to map object-renderers.
     * @return BookmarkRenderer subtype class.
     */
    @Override
    protected Class getPrototypeClass(Bookmark content) {
        Class prototypeClass;

        prototypeClass = NormalBookmarkRenderer.class;

        return prototypeClass;
    }

    /**
     * Create a list of prototypes to configure RendererBuilder.
     * The list of Renderer<Bookmark> that contains all the possible renderers that our RendererBuilder
     * is going to use.
     *
     * @return Renderer<Bookmark> prototypes for RendererBuilder.
     */
    private List<Renderer<Bookmark>> getPrototypes(Context context,
                                                BookmarkRenderer.OnBookmarkAction onBookmarkClickedListener) {

        List<Renderer<Bookmark>> prototypes = new LinkedList<Renderer<Bookmark>>();
        NormalBookmarkRenderer likeBookmarkRenderer = new NormalBookmarkRenderer(context);
        likeBookmarkRenderer.setListener(onBookmarkClickedListener);
        prototypes.add(likeBookmarkRenderer);

        return prototypes;
    }
}