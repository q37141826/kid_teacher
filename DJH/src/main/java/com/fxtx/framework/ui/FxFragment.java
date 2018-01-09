package com.fxtx.framework.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fxtx.framework.R;
import com.fxtx.framework.util.ActivityUtil;
import com.fxtx.framework.widgets.dialog.FxProgressDialog;
import com.fxtx.framework.widgets.refresh.MaterialRefreshLayout;
import com.fxtx.framework.widgets.refresh.MaterialRefreshListener;


/**
 * @author djh-zy
 * @version :1
 * @CreateDate 2015年8月3日 上午11:05:01
 * @description :
 */
public abstract class FxFragment extends Fragment {

    protected Bundle bundle;// 用于保存标题信息以及标志
    private FxProgressDialog progressDialog;
    protected final int PROGRESS_BACK = -1;
    protected boolean isCreateView = false;
    private SparseArray<View> mViews = new SparseArray<View>();
    protected View rootView;
    protected int pagNum = 1;
    public FxActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        activity = (FxActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = initinitLayout(inflater);
        return rootView;
    }


    protected void initRefresh(final MaterialRefreshLayout mRefresh) {
        if (mRefresh == null)
            return;
        mRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mRefresh.setLoadMore(true);
                pagNum = 1;
                httpData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                httpData();
            }
        });
    }


    public void httpData() {

    }


    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    protected abstract View initinitLayout(LayoutInflater inflater);

    protected void showfxDialog() {
        showfxDialog(R.string.fx_login);
    }

    protected void showfxDialog(Object title) {
        if (getActivity() == null || getActivity().isFinishing())
            return;
        if (progressDialog == null) {
            progressDialog = new FxProgressDialog(getActivity());
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        //点击返回
                        dismissfxDialog(PROGRESS_BACK);
                        return true;
                    }
                    return false;
                }
            });
        }
        if (title != null) {
            if (title instanceof String) {
                progressDialog.setTextMsg((String) title);
            } else {
                progressDialog.setTextMsg((Integer) title);
            }
        }
        progressDialog.show();
    }

    protected void dismissfxDialog(int flag) {
        if (getActivity() == null || getActivity().isFinishing())
            return;
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected void dismissfxDialog() {
        dismissfxDialog(0);
    }

    /**
     * @param isLastPag： 0 不是最后一页 1 是最后一页
     */
    public void finishRefreshAndLoadMoer(MaterialRefreshLayout mRefresh, int isLastPag) {
        if (mRefresh != null) {
            mRefresh.finishRefresh();
            mRefresh.finishRefreshLoadMore();
            mRefresh.finishRefreshing();
        }
        if (isLastPag == 0) {
            //不是最后一页
            mRefresh.setLoadMore(true);
        } else {
            mRefresh.setLoadMore(false);
        }
    }

    @Override
    public void onDestroyView() {
        dismissfxDialog();
        super.onDestroyView();
    }

    /**
     * 关闭Activity
     */
    protected void finishActivity() {
        dismissfxDialog();
        ActivityUtil.getInstance().finishThisActivity(getActivity());
    }

    public String getFxTag() {
        return getClass().getName();
    }
}