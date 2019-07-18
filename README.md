# SdkTest

life is so boring. ------------------------------fanbianyi------------------------------

### Android 反编译

主要流程

1. [apktool](https://www.softpedia.com/get/Programming/Debuggers-Decompilers-Dissasemblers/ApkTool.shtml)

        apktool_2.3.4.jar d -f demo.apk
    
2. [dex2Jar](https://nchc.dl.sourceforge.net/project/dex2jar/dex2jar-2.0.zip)

        \dex2jar-2.0\d2j-dex2jar.bat demo\classes.dex

3. [jd-gui](https://www.softpedia.com/get/Programming/Debuggers-Decompilers-Dissasemblers/JD-GUI.shtml)
  
        打开dex2Jar反编译后的classes-dex2jar.jar文件即可查看java源码
  
[防止反编译，代码混淆](https://github.com/cheng2016/OkhttpHelper)

### Android SDK开发
  
      U8SDK采用插件式的设计模式，将用户相关的功能定义为一个User插件；当支付相关的功能定义为一个Pay插件。具体的类代表分别是IUser接口和IPay接口。

      所以，在接入具体的SDK时，我们需要实现这两个插件。对应的，我们就必须定义一个XUser和XPay来分别实现IUser和IPay接口

      使用xml配置文件的方式，通过固定key查找到相应的插件值values，如：com.u8.sdk.BaiduUser

      最后通过反射的方式实例化插件的实现类。达到使用插件化的方式使用百度SDK替换空壳SDK的具体实现
  
  
  [PluginFactory](https://github.com/cheng2016/AndroidUtil/tree/master/util/PluginFactory.java)
  
  [核心思想利用Java的反射原理实现插件式的设计模式](https://github.com/cheng2016/developNote/blob/master/android/note/note0907.md)
  
  	public Object initPlugin(int type){
		Class localClass = null;
		try {
			if(!isSupportPlugin(type)){
				if(type == IUser.PLUGIN_TYPE || type == IPay.PLUGIN_TYPE){
					Log.e("U8SDK", "The config of the U8SDK is not support plugin type:"+type);
				}else{
					Log.w("U8SDK", "The config of the U8SDK is not support plugin type:"+type);	
				}
				return null;
			}
			String pluginName = getPluginName(type);
			localClass = Class.forName(pluginName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try {
			return localClass.getDeclaredConstructor(new Class[]{Activity.class}).newInstance(new Object[]{U8SDK.getInstance().getContext()});
		} catch (Exception e) {
			try {
				//以默认构造函数再次尝试实例化
				return localClass.getDeclaredConstructor().newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}

## Contact Me

- Github: github.com/cheng2016
- Email: mitnick.cheng@outlook.com

# Lisence

    Copyright 2019 cheng2016,Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
