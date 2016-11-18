package com.liuahang.repo.cache;

import java.util.Calendar;

public class CacheObjectWrapper {
	   public Calendar expire;
	   public Object cachedObject;
	    public CacheObjectWrapper(Object cachedObject){
	        this.cachedObject = cachedObject;
	    }
	    public CacheObjectWrapper(Object cachedObject, Calendar expire){
	        this.cachedObject = cachedObject;
	        this.expire = expire;
	    }
}
