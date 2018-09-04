package org.chenming.retrofitdemo.model;

public class Albums {

    private int userId;
    private int id;
    private String title;

    public Albums(int userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    public Albums(int userId, int id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
