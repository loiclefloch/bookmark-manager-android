package bm.bookmark_manager.common.view.ListPicker;

import java.util.List;

public interface PickerListener<T> {
    void onValidated(List<T> objects);
    List<T> getData();
}
