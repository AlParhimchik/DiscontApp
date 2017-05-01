
package com.example.sashok.task_.Answer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Category {

    @SerializedName("iconUrl")
    @Expose
    private String iconUrl;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subcategoryes")
    @Expose
    private List<Subcategorye> subcategoryes = null;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategorye> getSubcategoryes() {
        return subcategoryes;
    }

    public void setSubcategoryes(List<Subcategorye> subcategoryes) {
        this.subcategoryes = subcategoryes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
