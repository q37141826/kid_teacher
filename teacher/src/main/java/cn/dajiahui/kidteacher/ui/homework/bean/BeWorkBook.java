package cn.dajiahui.kidteacher.ui.homework.bean;

import java.io.Serializable;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * Created by wangzhi on 2018/2/1.
 */

public class BeWorkBook extends BeanObj implements Serializable {
    private String id;
    private String name;
    private String logo;
    private String book_type;
    private String series;


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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBook_type() {
        return book_type;
    }

    public void setBook_type(String book_type) {
        this.book_type = book_type;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
