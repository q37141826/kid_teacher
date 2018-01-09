package cn.dajiahui.kidteacher.ui.login.fr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Created by z on 2016/4/27.
 * Socket 通讯
 */
public class FrSocket extends FxFragment {
    private View cancle, login;
    private List<String> strings;
    private SocketIO client;
    private TextView tvMess;
    private int type = 1;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_auto_login, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancle = getView(R.id.btn_cancle);
        login = getView(R.id.btn_login);
        cancle.setOnClickListener(onClickListener);
        login.setOnClickListener(onClickListener);
        tvMess = getView(R.id.tv_msg);
        strings = StringUtil.getStringList(bundle.getString(Constant.bundle_obj), "_");
        type = 1;
        if (strings != null && strings.size() == 4) {
            setSocket(strings.get(1), strings.get(2));
        } else {
            //扫码 识别错误
            tvMess.setText("扫描数据不正确,请检查二维码是否合理.");
        }
    }


    private void setSocket(String host, String port) {
        try {
            String str = "http://" + host + ":" + port;
            client = new SocketIO(str);
            client.connect(new IOCallback() {
                @Override
                public void onDisconnect() {
                    dismissfxDialog();
                }

                @Override
                public void onConnect() {
                    //链接成功
                    dismissfxDialog();
                    if (type == 1) {
                        Message m = hadler.obtainMessage();
                        m.obj = "扫码成功，请点击确认登录";
                        m.what = 1;
                        hadler.sendMessage(m);
                    }
                }

                @Override
                public void onMessage(String s, IOAcknowledge ioAcknowledge) {
                }

                @Override
                public void onMessage(JSONObject jsonObject, IOAcknowledge ioAcknowledge) {
                }

                @Override
                public void on(String s, IOAcknowledge ioAcknowledge, Object... objects) {
                    dismissfxDialog();
                    if (StringUtil.sameStr(s, Constant.socket_result) && objects.length > 0) {
                        try {
                            JSONObject json = (JSONObject) objects[0];
                            String mes;
                            Message m = hadler.obtainMessage();
                            if (json.getBoolean("succeed")) {
                                mes = getString(R.string.login_eu);
                            } else {
                                mes = json.getString("info");
                                if (StringUtil.isEmpty(mes)) {
                                    mes = "发生了未知错误,登录失败!";

                                }
                            }
                            m.what = -1;
                            m.obj = mes;
                            hadler.sendMessage(m);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Message m = hadler.obtainMessage();
                            m.obj = e.getLocalizedMessage();
                            hadler.sendMessage(m);
                        }
                    }
                }

                @Override
                public void onError(SocketIOException e) {
                    dismissfxDialog();
                    Message m = hadler.obtainMessage();
                    m.obj = e.getLocalizedMessage();
                    hadler.sendMessage(m);

                }
            });
            client.emit(Constant.socket_scan, getScanJson(strings.get(3)));
            type = 1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Handler hadler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            tvMess.setText(msg.obj.toString());
            if (msg.what == 1) {
                //正确
                cancle.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
            } else {
                //错误
                cancle.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
            }
        }
    };


    public JSONObject getScanJson(String uuid) {
        JSONObject rootObject = new JSONObject();
        try {
            rootObject.put("uuid", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootObject;
    }

    public JSONObject getLoginJson(String uuid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", uuid);
            jsonObject.put("token", UserController.getInstance().getUser().gettoken());
            jsonObject.put("school_id", UserController.getInstance().getUser().getSchoolId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getCancleJson(String uuid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_cancle) {
                httpCancleLogin();
            } else {
                httpAutoLogin();
            }
        }
    };


    /**
     * 发送登录信息
     */
    public void httpAutoLogin() {
        try {
            type = 2;
            client.emit(Constant.socket_submit, getLoginJson(strings.get(3)));
            showfxDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送取消登录
    public void httpCancleLogin() {
        try {
            type = 3;
            client.emit(Constant.socket_reset, getCancleJson(strings.get(3)));
            finishActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        closeSocket();
        super.onDestroyView();
    }

    public void closeSocket() {
        if (client != null) {
            client.disconnect();
        }
    }
}
