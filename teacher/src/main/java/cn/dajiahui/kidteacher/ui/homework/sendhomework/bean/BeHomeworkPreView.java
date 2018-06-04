package cn.dajiahui.kidteacher.ui.homework.sendhomework.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mj on 2018/5/25.
 * <p>
 * <p>
 * 发送作业的模型
 */

public class BeHomeworkPreView implements Serializable {

    private String question_id;

    private int item_position;

    public BeHomeworkPreView() {
    }

    public BeHomeworkPreView(int item_position, String question_id) {
        this.question_id = question_id;
        this.item_position = item_position;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public int getItem_position() {
        return item_position;
    }

    @Override
    public String toString() {
        return "BeHomeworkPreView{" +
                "question_id='" + question_id + '\'' +
                ", item_position=" + item_position +
                '}';
    }
}
