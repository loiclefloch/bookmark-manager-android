package bm.bookmark_manager.common.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class Bookmark extends Model {

    @SerializedName("_id")
    private String id;
    private String title;
    private String name;
    private String description;
    private String notes;
    private String url;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private String readableContent;
    private List<Tag> tags;

    private String owner;

    @Override
    protected String getClassName() {
        return "Bookmark";
    }

    public boolean canBeFilter(String searchQuery) {
        return searchQuery == null
                || (name != null && name.toUpperCase().contains(searchQuery.toUpperCase()))
                || (title != null && title.toUpperCase().contains(searchQuery.toUpperCase()));
    }

    /**
     * If the user set a name, we use it, otherwise, we use the page title.
     *
     * @return the name of the bookmark
     */
    public String getMainTitle() {
        if (!TextUtils.isEmpty(name)) {
            return name;
        }
        return title;
    }

    /**
     *
     * @return true if there is a readable content
     */
    public boolean isReadable() {
        Document doc = Jsoup.parse(getReadableContentHtml());
        String textOnly = doc.body().text();

        Logger.i(textOnly);

        return textOnly.length() > title.length() + 100;
    }

    public String getReadableContentHtml() {
        String css = "body { color: rgb(51, 51, 51); width: 96%; }\n" +
                "h1, h2, h3, h4, h5, h6 { font-family: \"RobotoDraft\", \"Roboto\", \"Helvetica Neue\", Helvetica, Arial, sans-serif }\n" +
                "h3 { font-size: 24px }\n" +
                "h4 { font-size: 18px }\n" +
                "a { color: #009688 } a:hover { color: #009688; }\n" +
                "img { max-width: 100% }\n" +
                "pre { " +
                "overflow: auto; font-family: Menlo,Monaco,Consolas,\"Courier New\",monospace;" +
                "display:block;padding:9.5px;margin:0 0 10px;font-size:13px;line-height:1.42857;word-break:break-all;word-wrap:break-word;color:#333;background-color:#f5f5f5;border:1px solid #ccc;border-radius:4px" +
                "}\n" +
                "p { margin: 0 0 10px }\n" +
                ".title { text-align: center; line-height: 1.42857; font-weight: 300; font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif }\n";

        String head = "<html><head>" +
                "<style>" + css + "</style>" +
                "</head><body>" +
                "<h3 class=\"title\">" + getTitle() + "</h3><br />";

        String footer = "</body></html>";

        return head + readableContent + footer;
    }

    // -- Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getReadableContent() {
        return readableContent;
    }

    public void setReadableContent(String readableContent) {
        this.readableContent = readableContent;
    }

}
