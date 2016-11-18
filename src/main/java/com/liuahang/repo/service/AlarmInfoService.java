package com.liuahang.repo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liuahang.repo.dao.AlarmInfoDao;
import com.liuahang.repo.model.AlarmInfo;

@Service
public class AlarmInfoService {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private AlarmInfoDao alarmInfoDao;

	public List<AlarmInfo> getAlarmInfoList() {
		return alarmInfoDao.getAlarmInfoList();
	}

	/**
	 * 通过判断 only的值，在一个小时内，无重复则新增，有则更新，累计添加
	 * 
	 * @param info
	 */
	public void saveAlarmInfo(AlarmInfo info) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, - 1);
		Date date = calendar.getTime();
		String dateString = dateFormat.format(date);

		List<AlarmInfo> oldInfo = checkNewData(info.getOnly(), dateString);
		if (oldInfo == null || oldInfo.size() == 0) {
			info.setAlarmId(UUID.randomUUID().toString());
			alarmInfoDao.saveAlarmInfo(info);
		} else {
			info.setAlarmCount(oldInfo.get(0).getAlarmCount() + 1);
			info.setAlarmId(oldInfo.get(0).getAlarmId());
			alarmInfoDao.updateAlarmInfo(info);
		}

	}

	private List<AlarmInfo> checkNewData(String only, String dateString) {
		List<AlarmInfo> info = alarmInfoDao.getAlarmInfoByOnly(only, dateString);
		return info;
	}
}
