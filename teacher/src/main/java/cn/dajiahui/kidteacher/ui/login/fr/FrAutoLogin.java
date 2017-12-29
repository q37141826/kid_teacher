package cn.dajiahui.kidteacher.ui.login.fr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fxtx.framework.http.ErrorCode;
import com.fxtx.framework.http.callback.ResultCallback;
import com.fxtx.framework.json.HeadJson;
import com.fxtx.framework.log.ToastUtil;
import com.fxtx.framework.text.StringUtil;
import com.fxtx.framework.ui.FxFragment;
import com.squareup.okhttp.Request;

import java.util.List;

import cn.dajiahui.kidteacher.R;
import cn.dajiahui.kidteacher.controller.Constant;
import cn.dajiahui.kidteacher.controller.UserController;
import cn.dajiahui.kidteacher.http.RequestUtill;

/**
 * Created by z on 2016/4/27.
 */
public class FrAutoLogin extends FxFragment {
    private TextView tvMess;
    private View cancle, login;
    private String code;

    @Override
    protected View initinitLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fr_auto_login, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMess = getView(R.id.tv_msg);
        cancle = getView(R.id.btn_cancle);
        cancle.setOnClickListener(onClickListener);
        login = getView(R.id.btn_login);
        login.setOnClickListener(onClickListener);
        List<String> strings = StringUtil.getStringList(bundle.getString(Constant.bundle_obj), "_");
        if (strings != null && strings.size() == 2) {
            code = strings.get(1);
            cancle.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        } else {
            //扫码 识别错误
            tvMess.setText("扫描数据不正确,请检查二维码是否合理.");
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_cancle) {
                finishActivity();
            } else {
                httpAutoLogin(code);
            }
        }
    };

    /**
     * 扫码登录
     *
     * @param qrCode
     */
    public void httpAutoLogin(String qrCode) {
        showfxDialog();
        ResultCallback callback = new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dismissfxDialog();
                ToastUtil.showToast(getActivity(), ErrorCode.error(e));
            }

            @Override
            public void onResponse(String response) {
                dismissfxDialog();
                HeadJson json = new HeadJson(response);
                if (json.getFlag() == 1) {
                    tvMess.setText(R.string.login_eu);
                    cancle.setVisibility(View.GONE);
                    login.setVisibility(View.GONE);
                } else {
                    ToastUtil.showToast(getActivity(), json.getMsg());
                }
            }
        };
        RequestUtill.getInstance().httpAutoLogin(getActivity(), callback, qrCode, UserController.getInstance().getUser().getUserName(), UserController.getInstance().getUser().getPwd());
    }
}
