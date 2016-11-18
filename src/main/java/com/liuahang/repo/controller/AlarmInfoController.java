package com.liuahang.repo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liuahang.repo.model.AlarmInfo;
import com.liuahang.repo.service.AlarmInfoService;
import com.liuahang.repo.service.RedisService;

@Controller
@RequestMapping(Route.BASE_URI)
public class AlarmInfoController {

	@Autowired
	private AlarmInfoService alarmInfoService;
	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping(value="/alarmInfos", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAlarmInfo(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<AlarmInfo> list = alarmInfoService.getAlarmInfoList();
		map.put("result", list);
		return map;
	}
	

	@RequestMapping(value="/redistest", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> testRedis() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		 ArrayList<Map> maps = redisService.getRedisHelper().get("app.host.checks.0dd57f3b986f4b528cf697619676f626.cd532559fb9e4bb59113d1a9e25035bd", ArrayList.class);
		System.out.println("obj==" + maps);
		map.put("result", maps);
		return map;
	}
	
	@RequestMapping(value="/alarmInfos", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveAlarmInfo(HttpServletRequest request, @Valid @RequestBody AlarmInfo info){
		Map<String,Object> map = new HashMap<String,Object>();
		alarmInfoService.saveAlarmInfo(info);
		map.put("result", "success");
		return map;
	}
}
