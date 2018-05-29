package util;

import android.os.Environment;

import java.io.File;

/**
 * Created by mj on 2018/01/24.
 */

public class KidConfig {

    // Singleton

    private static KidConfig instance;

    private KidConfig() {

    }

    //写成静态单利
    public static KidConfig getInstance() {

        if (instance == null) {

            instance = new KidConfig();
        }
        return instance;
    }

    private String pathUserRoot;

    // Const Config
    private String envConfig = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String pathRoot = this.envConfig + File.separator + "kid_teacher" + File.separator;

    /*班级空间图片*/
    private String pathClassSpace = this.pathRoot + "ClassSpace" + File.separator;

    /*发送作业存截取的图片的文件夹*/
    private String pathSendHomework = this.pathRoot + "ClassSendHomework" + File.separator;

    public void init() {
        // App Init
        File folder = null;

        folder = new File(this.pathRoot);
        if (!folder.exists()) {
            folder.mkdir();
        }   folder = new File(this.pathClassSpace);

        if (!folder.exists()) {
            folder.mkdir();
        }

        folder = new File(this.pathSendHomework);
        if (!folder.exists()) {
            folder.mkdir();
        }

    }


    public void initUserConfig(String userPhone) {

        //User Init
        this.pathUserRoot = this.pathRoot + userPhone + File.separator;


        File folder = null;
        folder = new File(this.pathUserRoot);
        if (!folder.exists()) {
            folder.mkdir();
        }

    }

    public String getPathClassSpace() {
        return pathClassSpace;
    }

    public String getPathSendHomework() {
        return pathSendHomework;
    }
}
