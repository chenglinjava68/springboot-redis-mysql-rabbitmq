package com.liuahang.repo.util;

import java.util.Map;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {

	public static final int CONNECTION_TIMEOUT = 100000;
	public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
	
	
	public static String requestGet(String url, Map<String, String> headerMap) throws Exception {  
		String jsonStr = "";
        HttpGet httpget = new HttpGet(url);     
        //配置请求的超时设置  
        RequestConfig requestConfig = RequestConfig.custom()    
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)  
                .setConnectTimeout(CONNECTION_TIMEOUT)    
                .setSocketTimeout(CONNECTION_TIMEOUT).build();    
        httpget.setConfig(requestConfig);
        httpget.addHeader("Content-Type", CONTENT_TYPE_APPLICATION_JSON);
        //httpPost.addHeader("Authorization", headerMap.get("Authorization"));
        for(Map.Entry<String, String> entry:headerMap.entrySet()){    
        	httpget.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();  
        CloseableHttpResponse response = httpclient.execute(httpget);
        if(response != null ){
        	 HttpEntity entity = response.getEntity();          
             jsonStr = EntityUtils.toString(entity);
             httpget.releaseConnection();  
        }
         
        return jsonStr;
	}
}
