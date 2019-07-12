package com.icloud.sdk.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.icloud.sdk.utils.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Response;

public class URLImageView extends ImageView {
  String[] allowedContentTypes = { "image/png", "image/jpeg" };
  
  public URLImageView(Context paramContext) { super(paramContext); }
  
  public URLImageView(Context paramContext, AttributeSet paramAttributeSet) { super(paramContext, paramAttributeSet); }
  
  public void LoadImage(String paramString) { OkHttpUtil.get(paramString, new OkHttpUtil.SimpleResponseHandler() {
          public void onFailure(Exception param1Exception) {}
          
          public void onSuccess(Call param1Call, Response param1Response) {
            try {
              byte[] arrayOfByte = param1Response.body().bytes();
              Bitmap bitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
              URLImageView.this.setImageBitmap(bitmap);
              return;
            } catch (Exception e) {
              e.printStackTrace();
              return;
            } 
          }
        }); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sdk\view\URLImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */
