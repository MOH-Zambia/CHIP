package com.argusoft.sewa.android.app.databean;

import androidx.annotation.NonNull;

import com.argusoft.sewa.android.app.model.UploadFileDataBean;

import java.util.List;

/**
 * @author alpeshkyada
 */
public class WorkLogScreenDataBean {

    private String date;
    private int image;
    private String name;
    private String task;
    private String message;
    private List<UploadFileDataBean> uploadFileDataBean;

    public WorkLogScreenDataBean(String date, int image, String name, String task, String message, List<UploadFileDataBean> uploadFileDataBean) {
        this.date = date;
        this.image = image;
        this.name = name;
        this.task = task;
        this.message = message;
        this.uploadFileDataBean = uploadFileDataBean;
    }

    public WorkLogScreenDataBean(String date, int image, String name, String task, String message) {
        this.date = date;
        this.image = image;
        this.name = name;
        this.task = task;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public int getImage() {
        return image;
    }

    public List<UploadFileDataBean> getUploadFileDataBean() {
        return uploadFileDataBean;
    }

    public String getName() {
        return name;
    }

    public String getTask() {
        return task;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkLogScreenDataBean{" + "date=" + date + ", image=" + image + ", name=" + name + ", task=" + task + '}';
    }

}
