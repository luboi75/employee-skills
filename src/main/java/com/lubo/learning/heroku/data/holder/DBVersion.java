package com.lubo.learning.heroku.data.holder;

import java.util.Date;
import java.util.regex.Pattern;

public class DBVersion {
    private Integer major;
    private Integer minor;
    private Integer build;
    private Date updated;

    public DBVersion() {}

    public DBVersion(String versionName) {
        String[] components = versionName.split(Pattern.quote("."));
        assert components.length == 3;
        this.major = Integer.parseInt(components[0]);
        this.minor = Integer.parseInt(components[1]);
        this.build = Integer.parseInt(components[2]);
    }

    public Integer getMajor() {
        return this.major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Integer getBuild() {
        return build;
    }

    public void setBuild(Integer build) {
        this.build = build;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getVersionName () {
        return this.major.toString() + "." + this.minor.toString() + "." + this.build.toString();
    }
}
