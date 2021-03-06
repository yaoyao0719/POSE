package com.finalteam.galleryfinal.model;

import java.io.Serializable;


public class PhotoTempModel implements Serializable{
    private int orientation;
    private String sourcePath;

    public PhotoTempModel(String path) {
        sourcePath = path;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
}
