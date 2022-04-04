package com.buyMe.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyMe.common.entity.Setting;
import com.buyMe.common.entity.SettingCategory;

@Service
public class SettingService {
	@Autowired private SettingRepository repo;
	

	public List<Setting> getGeneralSettings() {
		return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
	}
	
}
