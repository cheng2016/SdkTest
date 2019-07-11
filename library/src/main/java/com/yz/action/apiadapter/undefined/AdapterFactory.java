package com.yz.action.apiadapter.undefined;

import com.yz.action.apiadapter.IActivityAdapter;
import com.yz.action.apiadapter.IAdapterFactory;
import com.yz.action.apiadapter.IFunctionAdapter;

public class AdapterFactory implements IAdapterFactory {
  public IActivityAdapter adtActivity() { return new ActivityAdapter(); }
  
  public IFunctionAdapter adtFunction() { return new FunctionAdapter(); }
}


/* Location:              C:\Users\mitni\Desktop\gitwork\AndroidTool\classes-dex2jar.jar!\com\yz\action\apiadapte\\undefined\AdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.6
 */