package cn.dajiahui.kidteacher.ui.homework.bean;

import java.util.List;

/**
 * Created by mj on 2018/3/23.
 * <p>
 * 解析作业详情
 */

public class BeHomeWorkDetails {

    private  String all_students;
    private  String complete_students;
    private  String complete_time;
    private  String correct_rate;
    private  String end_time;
    private  String is_complete;
    private  String name;
    private  String question_cnt;
    private  String start_time;
    private  String unit_id;
    private List<BeAnswerSheet> answer_sheet;

    public List<BeAnswerSheet> getAnswer_sheet() {
        return answer_sheet;
    }

    public String getAll_students() {
        return all_students;
    }

    public String getComplete_students() {
        return complete_students;
    }

    public String getComplete_time() {
        return complete_time;
    }

    public String getCorrect_rate() {
        return correct_rate;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getIs_complete() {
        return is_complete;
    }

    public String getName() {
        return name;
    }

    public String getQuestion_cnt() {
        return question_cnt;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getUnit_id() {
        return unit_id;
    }
}
