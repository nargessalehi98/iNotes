package ceit.model;

import java.io.Serializable;

/**
 * provide a structure for note
 */
public class Note implements Serializable {

    //title of note
    private String title;
    //content of note
    private String content;
    //date of note
    private String date;

    /**
     * creat a new note with given data
     *
     * @param title   title of note
     * @param content content of note
     * @param date    date of note
     */
    public Note(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    /**
     * get content of note
     *
     * @return string value of content
     */
    public String getContent() {
        return content;
    }

    @Override
    /**
     * return note as a string
     */
    public String toString() {
        return "Note{" +
                "title=" + title +
                ", content=" + content +
                ", date=" + date +
                '}';
    }
}

