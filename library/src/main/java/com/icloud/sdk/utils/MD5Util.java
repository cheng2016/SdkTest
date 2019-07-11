package com.icloud.sdk.utils;

import java.security.MessageDigest;

public class MD5Util {
  private static final String[] hexDigits = {
          "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
          "a", "b", "c", "d", "e", "f" };

  public static String MD5Encode(String paramString1, String paramString2) {
    try {
      paramString1 = new String(paramString1);
      try {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return (paramString2 == null || "".equals(paramString2)) ? byteArrayToHexString(messageDigest.digest(paramString1.getBytes())) : byteArrayToHexString(messageDigest.digest(paramString1.getBytes(paramString2)));
      } catch (Exception paramString2) {
        return paramString1;
      }
    } catch (Exception paramString1) {
      return null;
    }
  }

  private static String byteArrayToHexString(byte[] paramArrayOfByte) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramArrayOfByte.length; b++)
      stringBuffer.append(byteToHexString(paramArrayOfByte[b]));
    return stringBuffer.toString();
  }

  private static String byteToHexString(byte paramByte) {
    byte b = paramByte;
    paramByte = b;
    if (b < 0)
      paramByte = b + 256;
    b = paramByte / 16;
    return hexDigits[b] + hexDigits[paramByte % 16];
  }
}
