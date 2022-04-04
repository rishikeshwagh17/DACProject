package com.buyMe.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buyMe.common.entity.Setting;
import com.buyMe.common.entity.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {
	public List<Setting> findByCategory(SettingCategory category);
}
