package com.jnu.mybookshop.data;

import java.io.Serializable;

public class Book implements Serializable {

    public String title;
    public int coverresourceid;

    public Book(String name, int id){
        this.title=name;
        this.coverresourceid=id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getCoverResourceId(){
        return coverresourceid;
    }
    public String getTitle(){
        return title;
    }
}
