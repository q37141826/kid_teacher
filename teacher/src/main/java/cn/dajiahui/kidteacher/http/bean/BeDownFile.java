package cn.dajiahui.kidteacher.http.bean;

/**
 * Created by Administrator on 2016/4/10.
 */
public class BeDownFile {
    private String materialId;//资料id

    private int fileType; //文件类型
    private String fileUrl;//文件路径
    private String name; //文件名字
    private String locaUrl;//本地路径

    public BeDownFile(String materialId, int fileType, String fileUrl, String name) {
        this.materialId = materialId;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.name = name;
    }

    public String getMaterialId() {
        return materialId;
    }

    public int getFileType() {
        return fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocaUrl() {
        return locaUrl;
    }

    public void setLocaUrl(String locaUrl) {
        this.locaUrl = locaUrl;
    }
}
