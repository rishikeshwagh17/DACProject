package com.buyMe.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.buyMe.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{
	
	//this method return top level categories
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories();
}
