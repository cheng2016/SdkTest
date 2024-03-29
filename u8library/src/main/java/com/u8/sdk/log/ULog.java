package com.u8.sdk.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.json.JSONObject;


public class ULog {
    public static final String L_DEBUG = "DEBUG";
    public static final String L_INFO = "INFO";
    public static final String L_WARN = "WARNING";
    public static final String L_ERROR = "ERROR";
    private String level;
    private String tag;
    private String msg;
    private Throwable stack;
    private Date time;

    public ULog() {
    }

    public ULog(String level, String tag, String msg) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
        this.stack = null;
        this.time = new Date();
    }

    public ULog(String level, String tag, String msg, Throwable e) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
        this.stack = e;
        this.time = new Date();
    }

    private String parseStack(Throwable e) {
        StringWriter result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        return stacktrace;
    }


    public String toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("level", this.level);
            json.put("tag", this.tag);
            json.put("msg", this.msg);
            json.put("stack", (this.stack == null) ? "" : parseStack(this.stack));
            json.put("time", this.time);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();


            return "";
        }
    }
}


