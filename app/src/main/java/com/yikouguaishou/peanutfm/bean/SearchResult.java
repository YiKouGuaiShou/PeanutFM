package com.yikouguaishou.peanutfm.bean;

/**
 * Created by snowflake on 2016/12/27.
 */
public class SearchResult {
    String title;
    String pic;
    String source;

    public SearchResult() {
    }

    public SearchResult(String title, String pic, String source) {
        this.title = title;
        this.pic = pic;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
