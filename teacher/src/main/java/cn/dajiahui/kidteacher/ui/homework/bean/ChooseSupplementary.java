package cn.dajiahui.kidteacher.ui.homework.bean;

import cn.dajiahui.kidteacher.util.BeanObj;

/**
 * 选择教辅
 */

public class ChooseSupplementary extends BeanObj {
    public ChooseSupplementary(String imgbook, String bookname, int imgreous) {
        this.imgreous = imgreous;
        this.imgbook = imgbook;
        this.bookname = bookname;
    }

    private int imgreous;

    public ChooseSupplementary(String imgbook, String bookname) {
        this.imgbook = imgbook;
        this.bookname = bookname;
    }

    private String imgbook;
    private String bookname;

    public int getImgreous() {
        return imgreous;
    }

    public void setImgreous(int imgreous) {
        this.imgreous = imgreous;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getImgbook() {
        return imgbook;
    }

    public void setImgbook(String imgbook) {
        this.imgbook = imgbook;
    }


}
