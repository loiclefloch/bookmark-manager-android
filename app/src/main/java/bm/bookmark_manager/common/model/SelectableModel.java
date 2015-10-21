package bm.bookmark_manager.common.model;

/**
 * A simple interface to handle list of objects who need to be selected
 */
public abstract class SelectableModel extends Model {

    private boolean selected;

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }
}
