package com.liuahang.repo.rabbitmq;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import com.alibaba.fastjson.JSONObject;

public class Receiver implements MessageListener {
	private Logger logger = Logger.getLogger(Receiver.class);

    
    @Override
    public void onMessage(Message message) {
    	//从mq中接收到的数据
       byte[] body = message.getBody();
        try{
			Map mqMap = JSONObject.parseObject(body,Map.class);
		}catch(Exception e){
			e.printStackTrace();
		}	
    }
}