package com.fxtx.framework.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author djh-zy
 * @version :1
 * @CreateDate 2015年8月26日 上午9:43:13
 * @description : 头部信息解析
 */
public class HeadJson {
    private int status = -1;// 返回状态
    private String msg;// 错误消息
    private int isLastPage;// 列表对象时使用
    private JSONObject object;
    private final String strstatus = "status";
    private final String strMsg = "msg";
    private final String mObj = "data" ;
    private final String strIsLast = "isLastPage";


    public HeadJson(String object) {
        parsing(object);
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    /**
     * 1 返回正常 ,0 请求错误， -1 json 格式不正确
     *
     * @return
     */
    public int getstatus() {
        return status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(int isLastPage) {
        this.isLastPage = isLastPage;
    }

    /**
     * 基本信息解析信息解析
     *
     * @param json
     */
    private void parsing(String json) {
        try {
            object = new JSONObject(json);
            if (!object.isNull(strstatus)) {
                status = object.getInt(strstatus);
            } else {
                msg = "数据格式解析错误";
            }
            if (!object.isNull(strIsLast)) {
                isLastPage = object.getInt(strIsLast);
            }
            if (!object.isNull(strMsg)) {
                msg = object.getString(strMsg);
            }
        } catch (JSONException e) {
            object = new JSONObject();
            msg = "数据格式解析错误";
            e.printStackTrace();
        }
    }

    /**
     * 获取 list 数组json
     */
    public <T> T parsingListArray(String key, GsonType type) {
        String s = "";
        if (status != -1 && !object.isNull(key)) {
            s = object.optJSONArray(key).toString();
            GsonUtil gson = new GsonUtil();
            return gson.getJsonList(s, type);
        }
        return null;
    }

    public <T> T parsingListArray(GsonType type){
        return parsingListArray("list",type);
    }
    /**
     * 获取一个对象
     *
     * @return
     */
    public <T> T parsingObject(String objStr, Class<T> classs) {
        if (status != -1 && !object.isNull(objStr)) {
            GsonUtil gson = new GsonUtil();
            return gson.getJsonObject(object.optJSONObject(objStr).toString(), classs);
        }
        return null;
    }

    /**
     * 获取一个对象
     *
     * @return
     */
    public <T> T parsingObject( Class<T> classs) {
        if (status != -1 && !object.isNull(mObj)) {
            GsonUtil gson = new GsonUtil();
            return gson.getJsonObject(object.optJSONObject(mObj).toString(), classs);
        }
        return null;
    }



    public String parsingString(String key) {
        String value;
        if (!object.isNull(key)) {
            value = object.optString(key);
        } else {
            value = "";
        }
        return value;
    }

    public int parsingInt(String key) {
        int num;
        if (!object.isNull(key)) {
            num = object.optInt(key);
        } else {
            num = 0;
        }
        return num;
    }
}
