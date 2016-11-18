package com.liuahang.repo.dao;

import java.util.List;

import com.liuahang.repo.model.AlarmInfo;

public interface AlarmInfoDao {
	
	public List<AlarmInfo> getAlarmInfoList();

	public void saveAlarmInfo(AlarmInfo info);

	public void updateAlarmInfo(AlarmInfo info);

	public List<AlarmInfo> getAlarmInfoByOnly(String only, String dateString);
} 
