package com.hencesimplified.wallapaper;

public class SamplePhotos {
    private String Name;
    private String Url;

    public SamplePhotos() {
    }

    public SamplePhotos(String name, String url) {
        Name = name;
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public String getUrl() {
        return Url;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
