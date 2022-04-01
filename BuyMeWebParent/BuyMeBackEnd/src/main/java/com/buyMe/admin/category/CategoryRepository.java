package com.buyMe.admin.category;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.buyMe.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{
	
	//this method return top level categories
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories(Sort sort);
	
	public Category findByName(String name);
	
	public Category findByAlias(String alias);
	
	//method to set enable status
	@Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateEnableStatus(Integer id, boolean  enabled);
	//implement in category service class
	
	//count method to count the category and children
	public Long countById(Integer id);
}
