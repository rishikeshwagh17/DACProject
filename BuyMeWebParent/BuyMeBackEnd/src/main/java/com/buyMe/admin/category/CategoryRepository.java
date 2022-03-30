package com.buyMe.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.buyMe.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>{
	
}
