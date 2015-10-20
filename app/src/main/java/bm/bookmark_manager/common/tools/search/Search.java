package bm.bookmark_manager.common.tools.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import bm.bookmark_manager.common.tools.Tools;

public class Search {

    public interface Sortable {
        String fieldToSortByName();
        Date fieldToSortByDate();
        boolean onQuery(String query);
    }

    public static class Order {
        public static final int NORMAL = 1;
        public static final int INVERSE = 2;
    }

    public static class Filter {
        public static final int DEFAULT = 1;
        public static final int NAME = 2;
        public static final int DATE = 4;
    }

    protected String query = "";
    protected int filter = Filter.DEFAULT;

    protected int order = Order.NORMAL;
    // --

    /**
     * Return a list with the search query and sort apply
     * @param list
     * @return
     */
    public List<? extends Sortable> search(List<? extends Sortable> list) {
        List<Sortable> tmp = new ArrayList<>();

        if (null == list) {
            return tmp;
        }
        for (Sortable obj : list) {
            if (query.length() > 0) {
                if (obj.onQuery(query)) {
                    tmp.add(obj);
                }
            }
            else {
                tmp.add(obj);
            }
        }

        return sort(tmp);
    }

    /**
     * Sort a list with the current search filter
     * @param list
     * @return
     */
    protected List<? extends Sortable> sort(List<? extends Sortable> list) {

        Collections.sort(list, new Comparator<Sortable>() {
            @Override
            public int compare(Sortable lhs, Sortable rhs) {
                if (Tools.checkBit(filter, Filter.NAME)) {
                    return compareStringWithoutCase(lhs.fieldToSortByName(), rhs.fieldToSortByName());
                }
                else if (Tools.checkBit(filter, Filter.DATE)) {
                    return compareDate(lhs.fieldToSortByDate(), rhs.fieldToSortByDate());
                }
                return 0;
            }
        });

        if (order == Order.INVERSE) {
            Collections.reverse(list);
        }

        return list;
    }

    private int compareStringWithoutCase(String a, String b) {
        if (a == null || b == null) {
            return 0;
        }
        return getStringForCompare(a).compareTo(getStringForCompare(b));
    }

    private String getStringForCompare(String str) {
        return str.replaceAll("[^A-Za-z0-9 ]", "").trim().toUpperCase();
    }

    private int compareDate(Date a, Date b) {
        return a.compareTo(b);
    }

    // -- Getters and setters

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public long getFilter() {
        return filter;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void removeQuery() {
        this.query = "";
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void reverseOrder() {
        this.order = this.order == Order.NORMAL ? Order.INVERSE : Order.NORMAL;
    }
}
