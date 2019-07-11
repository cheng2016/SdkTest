package com.icloud.sdk.utils.encry;

public class RC4Http implements EncryptionTool {
  public static byte[] key;
  
  private static String shareKey = "cf79b00806591e4f8bfd411ef334a948";
  
  public static byte[] RC4Base(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
    char c = Character.MIN_VALUE;
    byte b = 0;
    byte[] arrayOfByte = initKey(shareKey);
    while (paramInt1 < paramInt2) {
      c = c + true & 0xFF;
      b = (arrayOfByte[c] & 0xFF) + b & 0xFF;
      byte b1 = arrayOfByte[c];
      arrayOfByte[c] = arrayOfByte[b];
      arrayOfByte[b] = b1;
      byte b2 = arrayOfByte[c];
      byte b3 = arrayOfByte[b];
      paramArrayOfByte[paramInt1] = (byte)(paramArrayOfByte[paramInt1] ^ arrayOfByte[(b2 & 0xFF) + (b3 & 0xFF) & 0xFF]);
      paramInt1++;
    } 
    return paramArrayOfByte;
  }
  
  public static byte[] copyOf(byte[] paramArrayOfByte, int paramInt) {
    byte[] arrayOfByte = new byte[paramInt];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, Math.min(paramArrayOfByte.length, paramInt));
    return arrayOfByte;
  }
  
  private static byte[] initKey(String paramString) {
    if (key == null) {
      byte[] arrayOfByte = paramString.getBytes();
      key = new byte[256];
      byte b;
      for (b = 0; b < 'Ā'; b++)
        key[b] = (byte)b; 
      int i = 0;
      byte b1 = 0;
      if (arrayOfByte == null || arrayOfByte.length == 0)
        return null; 
      for (b = 0; b < 'Ā'; b++) {
        b1 = (arrayOfByte[i] & 0xFF) + (key[b] & 0xFF) + b1 & 0xFF;
        byte b2 = key[b];
        key[b] = key[b1];
        key[b1] = b2;
        i = (i + true) % arrayOfByte.length;
      } 
    } 
    return copyOf(key, key.length);
  }
  
  public byte[] decry(byte[] paramArrayOfByte) { return RC4Base(paramArrayOfByte, 0, paramArrayOfByte.length); }
  
  public byte[] encry(byte[] paramArrayOfByte) { return RC4Base(paramArrayOfByte, 0, paramArrayOfByte.length); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\encry\RC4Http.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */