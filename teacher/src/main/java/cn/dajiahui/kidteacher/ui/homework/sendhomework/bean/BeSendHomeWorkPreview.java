package cn.dajiahui.kidteacher.ui.homework.sendhomework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mj on 2018/5/25.
 * <p>
 * <p>
 * 发送作业的模型
 */

public class BeSendHomeWorkPreview implements Serializable {

    private String book_id;
    private String unit_id;

    private List<BeHomeworkPreView> bePreViewList;


    public BeSendHomeWorkPreview(List<BeHomeworkPreView> bePreViewList) {
        this.bePreViewList = bePreViewList;
    }

    public BeSendHomeWorkPreview(List<BeHomeworkPreView> bePreViewList, String book_id, String unit_id) {
        this.bePreViewList = bePreViewList;
        this.book_id = book_id;
        this.unit_id = unit_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public String getUnit_id() {
        return unit_id;
    }


    public List<BeHomeworkPreView> getBePreViewList() {
        return bePreViewList;
    }
}
