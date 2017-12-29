package cn.dajiahui.kidteacher.http.file;

import java.util.ArrayList;

import cn.dajiahui.kidteacher.ui.album.bean.BePhoto;

public interface OnNoticeFileUpdate extends OnFileUpdate {
    void saveFile(ArrayList<BePhoto> photos);
}