package com.example.mymovies.data;

/**
 * Created by Nikita Biryukov on 18.09.2021.
 */
public class Trailers {
    private String key;
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trailers(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
