package com.fxtx.framework.file;

import android.content.Context;
import android.os.Environment;

import com.fxtx.framework.text.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * File 管理工具类
 */
public class FileUtil {
    /**
     * 根据路径获取一个在sd卡上的目录文件 ，没有则创建
     *
     * @param path
     */
    public File makeDirFile(String path) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }


    /**
     * 根据路径获取一个在sd卡上的文件 ，没有则创建
     *
     * @param path
     */
    public File creatNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            String dirPath = StringUtil.getStrBeforeSplitter(path,
                    File.separator);
            try {
                makeDirFile(dirPath);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    /**
     * 根据路径获取一个在sd卡上的文件 ，没有则创建
     *
     * @param dirPath
     * @param fileName
     */
    public File creatNewFile(String dirPath, String fileName) {
        return creatNewFile(makeDirFile(dirPath), fileName);
    }

    public File creatNewFile(File dirFile, String fileName) {
        return new File(dirFile, fileName);
    }

    /**
     * 删除sd卡上的某一个文件
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file != null && file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 根据路径删除sd卡上的某一个文件
     *
     * @param path
     */
    public void deleteFile(String path) {
        deleteFile(new File(path));
    }

    /**
     * 递归删除某一个路径下的所有文件
     *
     * @param dirPath
     */
    public void deleteAllFile(String dirPath) {
        deleteAllFile(new File(dirPath));
    }

    public void deleteAllFile(File dirFile) {
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isFile()) {
            dirFile.delete();
            return;
        }
        if (dirFile.isDirectory()) {
            File[] files = dirFile.listFiles();
            if (files == null || files.length == 0) {
                dirFile.delete();
                return;
            }
            for (File file : files) {
                deleteAllFile(file);
            }
            dirFile.delete();
        }
    }


    /**
     * 保存信息到sd卡
     */
    public void saveData(String path, String data) {
        File file = creatNewFile(path);
        if (file != null) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 读取sd卡上文件信息
     */
    public String getData(String path) {
        String data = "";
        File file = creatNewFile(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            data = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    /**
     * 将文件大小 进行格式化转换
     *
     * @param fileS
     * @return
     */
    public String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    //获取DirFile文件夹
    public String dirFile(Context context) {
        String dirFile;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            dirFile = Environment.getExternalStorageDirectory().toString();//获取跟目录
        } else {
            dirFile = context.getFilesDir().toString();
        }
        return dirFile;
    }


}
