package com.buyMe.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buyMe.common.entity.Country;
import com.buyMe.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
