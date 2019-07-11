package com.icloud.sdk.utils.encry;

public class TypeConvert {
  public static int MAX_HEX_LENGTH = 200;
  
  private byte btmp;
  
  public static byte[] hexStr2ByteArr(String paramString) {
    byte[] arrayOfByte1 = paramString.getBytes();
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[i / 2];
    for (byte b = 0; b < i; b += 2) {
      String str = new String(arrayOfByte1, b, 2);
      arrayOfByte2[b / 2] = (byte)Integer.parseInt(str, 16);
    } 
    return arrayOfByte2;
  }
  
  public static String toHexString(byte paramByte) { return new String(new char[] { Character.forDigit(paramByte >>> 4 & 0xF, 16), Character.forDigit(paramByte & 0xF, 16) }); }
  
  public static String toHexString(byte[] paramArrayOfByte) {
    StringBuffer stringBuffer = new StringBuffer();
    for (byte b = 0; b < paramArrayOfByte.length; b++)
      stringBuffer.append(toHexString(paramArrayOfByte[b])); 
    return stringBuffer.toString();
  }
  
  public int byte2int(byte[] paramArrayOfByte) { return paramArrayOfByte[3] & 0xFF | (paramArrayOfByte[2] & 0xFF) << 8 | (paramArrayOfByte[1] & 0xFF) << 16 | (paramArrayOfByte[0] & 0xFF) << 24; }
  
  public int byte2int(byte[] paramArrayOfByte, int paramInt) { return paramArrayOfByte[paramInt + 3] & 0xFF | (paramArrayOfByte[paramInt + 2] & 0xFF) << 8 | (paramArrayOfByte[paramInt + 1] & 0xFF) << 16 | (paramArrayOfByte[paramInt] & 0xFF) << 24; }
  
  public long byte2long(byte[] paramArrayOfByte) { return paramArrayOfByte[7] & 0xFFL | (paramArrayOfByte[6] & 0xFFL) << 8 | (paramArrayOfByte[5] & 0xFFL) << 16 | (paramArrayOfByte[4] & 0xFFL) << 24 | (paramArrayOfByte[3] & 0xFFL) << 32 | (paramArrayOfByte[2] & 0xFFL) << 40 | (paramArrayOfByte[1] & 0xFFL) << 48 | paramArrayOfByte[0] << 56; }
  
  public long byte2long(byte[] paramArrayOfByte, int paramInt) { return paramArrayOfByte[paramInt + 7] & 0xFFL | (paramArrayOfByte[paramInt + 6] & 0xFFL) << 8 | (paramArrayOfByte[paramInt + 5] & 0xFFL) << 16 | (paramArrayOfByte[paramInt + 4] & 0xFFL) << 24 | (paramArrayOfByte[paramInt + 3] & 0xFFL) << 32 | (paramArrayOfByte[paramInt + 2] & 0xFFL) << 40 | (paramArrayOfByte[paramInt + 1] & 0xFFL) << 48 | paramArrayOfByte[paramInt] << 56; }
  
  public short byte2short(byte[] paramArrayOfByte) { return (short)(paramArrayOfByte[1] & 0xFF | (paramArrayOfByte[0] & 0xFF) << 8); }
  
  public short byte2short(byte[] paramArrayOfByte, int paramInt) { return (short)(paramArrayOfByte[paramInt + 1] & 0xFF | (paramArrayOfByte[paramInt + 0] & 0xFF) << 8); }
  
  public int byte2short2int(byte[] paramArrayOfByte, int paramInt) { return paramArrayOfByte[paramInt + 1] & 0xFF | (paramArrayOfByte[paramInt + 0] & 0xFF) << 8; }
  
  public void int2byte(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
    paramArrayOfByte[paramInt2] = (byte)(paramInt1 >> 24);
    paramArrayOfByte[paramInt2 + 1] = (byte)(paramInt1 >> 16);
    paramArrayOfByte[paramInt2 + 2] = (byte)(paramInt1 >> 8);
    paramArrayOfByte[paramInt2 + 3] = (byte)paramInt1;
  }
  
  public byte[] int2byte(int paramInt) { return new byte[] { (byte)(paramInt >> 24), (byte)(paramInt >> 16), (byte)(paramInt >> 8), (byte)paramInt }; }
  
  public void long2byte(long paramLong, byte[] paramArrayOfByte, int paramInt) {
    paramArrayOfByte[paramInt] = (byte)(int)(paramLong >> 56);
    paramArrayOfByte[paramInt + 1] = (byte)(int)(paramLong >> 48);
    paramArrayOfByte[paramInt + 2] = (byte)(int)(paramLong >> 40);
    paramArrayOfByte[paramInt + 3] = (byte)(int)(paramLong >> 32);
    paramArrayOfByte[paramInt + 4] = (byte)(int)(paramLong >> 24);
    paramArrayOfByte[paramInt + 5] = (byte)(int)(paramLong >> 16);
    paramArrayOfByte[paramInt + 6] = (byte)(int)(paramLong >> 8);
    paramArrayOfByte[paramInt + 7] = (byte)(int)paramLong;
  }
  
  public byte[] long2byte(long paramLong) { return new byte[] { (byte)(int)(paramLong >> 56), (byte)(int)(paramLong >> 48), (byte)(int)(paramLong >> 40), (byte)(int)(paramLong >> 32), (byte)(int)(paramLong >> 24), (byte)(int)(paramLong >> 16), (byte)(int)(paramLong >> 8), (byte)(int)paramLong }; }
  
  public void reverse2Byte(byte[] paramArrayOfByte, int paramInt) {
    this.btmp = paramArrayOfByte[paramInt + 0];
    paramArrayOfByte[paramInt + 0] = paramArrayOfByte[paramInt + 1];
    paramArrayOfByte[paramInt + 1] = this.btmp;
  }
  
  public void reverse4Byte(byte[] paramArrayOfByte, int paramInt) {
    this.btmp = paramArrayOfByte[paramInt + 0];
    paramArrayOfByte[paramInt + 0] = paramArrayOfByte[paramInt + 3];
    paramArrayOfByte[paramInt + 3] = this.btmp;
    this.btmp = paramArrayOfByte[paramInt + 1];
    paramArrayOfByte[paramInt + 1] = paramArrayOfByte[paramInt + 2];
    paramArrayOfByte[paramInt + 2] = this.btmp;
  }
  
  public void reverse8Byte(byte[] paramArrayOfByte, int paramInt) {
    this.btmp = paramArrayOfByte[paramInt + 0];
    paramArrayOfByte[paramInt + 0] = paramArrayOfByte[paramInt + 7];
    paramArrayOfByte[paramInt + 7] = this.btmp;
    this.btmp = paramArrayOfByte[paramInt + 1];
    paramArrayOfByte[paramInt + 1] = paramArrayOfByte[paramInt + 6];
    paramArrayOfByte[paramInt + 6] = this.btmp;
    this.btmp = paramArrayOfByte[paramInt + 2];
    paramArrayOfByte[paramInt + 2] = paramArrayOfByte[paramInt + 5];
    paramArrayOfByte[paramInt + 5] = this.btmp;
    this.btmp = paramArrayOfByte[paramInt + 3];
    paramArrayOfByte[paramInt + 3] = paramArrayOfByte[paramInt + 4];
    paramArrayOfByte[paramInt + 4] = this.btmp;
  }
  
  public void short2byte(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
    paramArrayOfByte[paramInt2] = (byte)(paramInt1 >> 8);
    paramArrayOfByte[paramInt2 + 1] = (byte)paramInt1;
  }
  
  public byte[] short2byte(int paramInt) { return new byte[] { (byte)(paramInt >> 8), (byte)paramInt }; }
  
  public String toHexString(byte[] paramArrayOfByte, int paramInt1, int paramInt2) { // Byte code:
    //   0: new java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #7
    //   9: iconst_0
    //   10: istore #4
    //   12: iload_2
    //   13: istore #5
    //   15: iload #4
    //   17: istore #6
    //   19: iload #5
    //   21: iload_3
    //   22: iload_2
    //   23: iadd
    //   24: if_icmpge -> 45
    //   27: iload #4
    //   29: iconst_1
    //   30: iadd
    //   31: istore #4
    //   33: iload #4
    //   35: getstatic com/icloud/sdk/utils/encry/TypeConvert.MAX_HEX_LENGTH : I
    //   38: if_icmplt -> 83
    //   41: iload #4
    //   43: istore #6
    //   45: new java/lang/StringBuilder
    //   48: dup
    //   49: invokespecial <init> : ()V
    //   52: aload #7
    //   54: invokevirtual toString : ()Ljava/lang/String;
    //   57: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: astore #7
    //   62: iload #6
    //   64: getstatic com/icloud/sdk/utils/encry/TypeConvert.MAX_HEX_LENGTH : I
    //   67: if_icmplt -> 105
    //   70: ldc '...'
    //   72: astore_1
    //   73: aload #7
    //   75: aload_1
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual toString : ()Ljava/lang/String;
    //   82: areturn
    //   83: aload #7
    //   85: aload_1
    //   86: iload #5
    //   88: baload
    //   89: invokestatic toHexString : (B)Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   95: pop
    //   96: iload #5
    //   98: iconst_1
    //   99: iadd
    //   100: istore #5
    //   102: goto -> 15
    //   105: ldc ''
    //   107: astore_1
    //   108: goto -> 73 }
  
  public String toHexString2(byte[] paramArrayOfByte, int paramInt1, int paramInt2) { // Byte code:
    //   0: new java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #7
    //   9: new java/util/Formatter
    //   12: dup
    //   13: aload #7
    //   15: invokespecial <init> : (Ljava/lang/Appendable;)V
    //   18: astore #8
    //   20: iconst_0
    //   21: istore #4
    //   23: iload_2
    //   24: istore #5
    //   26: iload #4
    //   28: istore #6
    //   30: iload #5
    //   32: iload_3
    //   33: iload_2
    //   34: iadd
    //   35: if_icmpge -> 56
    //   38: iload #4
    //   40: iconst_1
    //   41: iadd
    //   42: istore #4
    //   44: iload #4
    //   46: getstatic com/icloud/sdk/utils/encry/TypeConvert.MAX_HEX_LENGTH : I
    //   49: if_icmplt -> 94
    //   52: iload #4
    //   54: istore #6
    //   56: new java/lang/StringBuilder
    //   59: dup
    //   60: invokespecial <init> : ()V
    //   63: aload #7
    //   65: invokevirtual toString : ()Ljava/lang/String;
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: astore #7
    //   73: iload #6
    //   75: getstatic com/icloud/sdk/utils/encry/TypeConvert.MAX_HEX_LENGTH : I
    //   78: if_icmplt -> 125
    //   81: ldc '...'
    //   83: astore_1
    //   84: aload #7
    //   86: aload_1
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual toString : ()Ljava/lang/String;
    //   93: areturn
    //   94: aload #8
    //   96: ldc '%02x'
    //   98: iconst_1
    //   99: anewarray java/lang/Object
    //   102: dup
    //   103: iconst_0
    //   104: aload_1
    //   105: iload #5
    //   107: baload
    //   108: invokestatic valueOf : (B)Ljava/lang/Byte;
    //   111: aastore
    //   112: invokevirtual format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/util/Formatter;
    //   115: pop
    //   116: iload #5
    //   118: iconst_1
    //   119: iadd
    //   120: istore #5
    //   122: goto -> 26
    //   125: ldc ''
    //   127: astore_1
    //   128: goto -> 84 }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\icloud\sd\\utils\encry\TypeConvert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */