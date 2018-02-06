package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

/**
 * 作业（首页）按发布日期分类的作业列表
 * Created by wangzhi on 2018/2/5.
 */

public class BeHomeworkList {
    private String pubdate; // 发布日期；
    private List<BeHomewrok> home_list;

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public List<BeHomewrok> getHome_list() {
        return home_list;
    }

    public void setHome_list(List<BeHomewrok> home_list) {
        this.home_list = home_list;
    }
}
