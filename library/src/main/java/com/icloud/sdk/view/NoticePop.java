package com.icloud.sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.icloud.sdk.R;
import com.icloud.sdk.bean.NoticeResp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NoticePop extends Dialog implements View.OnClickListener {
  private static final int NOTICE_ACTIVITY = 1;
  
  private static final int NOTICE_CLOSE = 0;
  
  private Button btn_submit;
  
  private Button close;
  
  private FrameLayout contentLayout;
  
  private Context context;
  
  private List<NoticeResp> dataList = new ArrayList();
  
  private int index = 0;
  
  boolean isNext = true;
  
  private OnCancellCallBack listener;
  
  private FrameLayout titleLayout;
  
  private WebView webView1;
  
  private WebView webView2;
  
  public NoticePop(Context paramContext, OnCancellCallBack paramOnCancellCallBack) {
    super(paramContext, R.style.base_pop);
    this.context = paramContext;
    this.listener = paramOnCancellCallBack;
    setCanceledOnTouchOutside(false);
    setCancelable(false);
    setContentView(R.layout.pop_notice);
    initview();
    setListener();
  }
  
  public NoticePop(Context paramContext, String paramString, OnCancellCallBack paramOnCancellCallBack) {
    this(paramContext, paramOnCancellCallBack);
    JsonArray jsonArray = (new JsonParser()).parse(paramString).getAsJsonArray();
    Gson gson = new Gson();
    Iterator iterator2 = jsonArray.iterator();
    while (iterator2.hasNext()) {
      NoticeResp noticeResp = (NoticeResp)gson.fromJson((JsonElement)iterator2.next(), NoticeResp.class);
      this.dataList.add(noticeResp);
      Log.e("OnResuct print", (new Gson()).toJson(noticeResp));
    } 
    if (this.dataList.size() > 0) {
      NoticeResp noticeResp = (NoticeResp)this.dataList.get(0);
      this.webView1.loadData(getHtmlData(noticeResp.getTitle()), "text/html;charset=utf-8", "utf-8");
      this.webView2.loadData(getHtmlData(noticeResp.getContent()), "text/html;charset=utf-8", "utf-8");
    } 
    Iterator iterator1 = this.dataList.iterator();
    while (iterator1.hasNext()) {
      if (((NoticeResp)iterator1.next()).getType() == 0)
        this.isNext = false; 
    } 
  }
  
  private String getHtmlData(String paramString) { return "<html><body>" + paramString + "</body></html>"; }
  
  private void initview() {
    this.btn_submit = (Button)findViewById(R.id.btn_submit);
    this.close = (Button)findViewById(R.id.close);
    this.titleLayout = (FrameLayout)findViewById(R.id.title_layout);
    this.contentLayout = (FrameLayout)findViewById(R.id.content_layout);
    createWevView();
  }
  
  private void setListener() {
    this.btn_submit.setOnClickListener(this);
    this.close.setOnClickListener(this);
  }
  
  public void cancel() {
    super.cancel();
    if (this.listener != null)
      if (this.isNext) {
        this.listener.login();
      } else {
        this.listener.exit();
      }  
    cleanWebView();
  }
  
  void cleanWebView() {
    if (this.webView1 != null) {
      this.titleLayout.removeView(this.webView1);
      this.webView1.stopLoading();
      this.webView1.clearView();
      this.webView1.removeAllViews();
      try {
        this.webView1.destroy();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      } 
    } 
    if (this.webView2 != null) {
      this.contentLayout.removeView(this.webView2);
      this.webView2.stopLoading();
      this.webView2.clearView();
      this.webView2.removeAllViews();
      try {
        this.webView2.destroy();
        return;
      } catch (Throwable throwable) {
        throwable.printStackTrace();
        return;
      } 
    } 
  }
  
  void createWevView() {
    this.webView1 = new WebView(this.context);
    this.titleLayout.addView(this.webView1, 0);
    this.webView1.setHorizontalScrollBarEnabled(false);
    this.webView1.setVerticalScrollBarEnabled(false);
    this.webView1.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) { return (param1MotionEvent.getAction() == 2); }
        });
    this.webView2 = new WebView(this.context);
    this.webView2.setHorizontalScrollBarEnabled(false);
    this.contentLayout.addView(this.webView2, 0);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == R.id.close)
      cancel(); 
    if (paramView.getId() == R.id.btn_submit && this.dataList.size() > 0) {
      if (((NoticeResp)this.dataList.get(this.index)).getType() == 0) {
        if (this.index == this.dataList.size() - 1) {
          this.index = 0;
        } else {
          this.index++;
        } 
        refreshPage();
        return;
      } 
    } else {
      return;
    } 
    this.index++;
    if (this.dataList.size() > this.index) {
      refreshPage();
      return;
    } 
    cancel();
  }
  
  void refreshPage() {
    cleanWebView();
    createWevView();
    NoticeResp noticeResp = (NoticeResp)this.dataList.get(this.index);
    this.webView1.loadData(getHtmlData(noticeResp.getTitle()), "text/html;charset=utf-8", "utf-8");
    this.webView2.loadData(getHtmlData(noticeResp.getContent()), "text/html;charset=utf-8", "utf-8");
  }
  
  public static interface OnCancellCallBack {
    void exit();
    
    void login();
  }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\NoticePop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */