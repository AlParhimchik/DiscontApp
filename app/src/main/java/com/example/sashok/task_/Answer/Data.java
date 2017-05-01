
package com.example.sashok.task_.Answer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Data {

    @SerializedName("sessionKey")
    @Expose
    private String sessionKey;
    @SerializedName("alive")
    @Expose
    private Integer alive;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getAlive() {
        return alive;
    }

    public void setAlive(Integer alive) {
        this.alive = alive;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
